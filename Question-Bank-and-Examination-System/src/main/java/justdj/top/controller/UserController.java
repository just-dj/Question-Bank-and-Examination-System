/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.6
  Time: 15:35
*/

package justdj.top.controller;

import justdj.top.pojo.User;
import justdj.top.service.CourseService;
import justdj.top.vcode.Captcha;
import justdj.top.vcode.GifCaptcha;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class UserController {
	
	
	@Autowired
	@Qualifier(value = "userService")
	justdj.top.service.UserService UserService;
	
	@Autowired
	@Qualifier("courseService")
	private CourseService courseService;
	
	@Autowired
	SecureRandomNumberGenerator secureRandomNumberGenerator;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="" +
			"",method= RequestMethod.GET)
	public String mainPage(Model model){
		model.addAttribute("user", new User());
		return "/login";
	}
	
	@RequestMapping(value="/login",method= RequestMethod.GET)
	public String loginForm(Model model){
		model.addAttribute("user", new User());
		return "login";
	}
	
	@RequestMapping(value="/login",method= RequestMethod.POST)
	public String login(@Valid User user, @RequestParam(value = "vcode") String vcode, BindingResult bindingResult,
	                    RedirectAttributes redirectAttributes, HttpServletRequest request,Model model){
		//验证码判断
		if (!codeIdentify(vcode,redirectAttributes))
			return "redirect:/login";
		UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getPassword());
		
		//七天免登录,注意权限问题，是必须登录还是记住密码即可
		if (false)
			token.setRememberMe(true);
		//获取当前用户
		Subject subject = SecurityUtils.getSubject();
		//登录，即身份验证，传输凭证并登录
		//if语句是为了避免重复登录问题
		if (subject.isAuthenticated()){
			return "userInfo";
		}else{
			try{
				subject.login(token);
				
				User userNow = UserService.selectUserByAccount(user.getAccount());
				if (subject.hasRole("teacher")){
					model.addAttribute("courseList",courseService.selectCourseByTeacherId(userNow.getId()));
				}
				
				logger.warn("用户 "+ userNow.getAccount()+" 已登录！");
				
				//判断是否是从其他页跳转过来的
				String temp = getForwardUrl(request);
				if (temp != null)
					return "forward:"+temp;
				return "userInfo";
			}
			catch (AuthenticationException e) {
				//认证异常
				redirectAttributes.addFlashAttribute("message",e.getMessage());
				logger.error("用户 "+user.getId()+" 身份认证失败!错误信息 : " + e.getMessage());
				return "redirect:/login";
			}
		}
	}
	
	@RequestMapping(value="/logout",method= RequestMethod.GET)
	public String logout(RedirectAttributes redirectAttributes ){
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated() || subject.isRemembered()) {
			//	这里会将记住密码功能保存的也给删除掉
			logger.warn("用户 " + subject.getPrincipal().toString() + " 已安全退出!");
			subject.logout();
		}
		redirectAttributes.addFlashAttribute("message", "您已安全退出");
		return "redirect:/login";
	}
	
	@RequestMapping("/403")
	public String unauthorizedRole(){
		return "/shiro/403";
	}
	
	@RequestMapping(value = "/remberme")
	@ResponseBody
	public String testRemberMe(){
		Subject subject = SecurityUtils.getSubject();
		String info = "";
		info = String.format("是否通过登录认证 + " + subject.isAuthenticated()+ " 是否通过记住密码功能登录 : "+ subject.isRemembered());
		return info;
	}
	
	/**
	 * 获取验证码（Gif版本）
	 * @param response
	 */
	@RequestMapping(value="/getGifCode",method= RequestMethod.GET)
	public void getGifCode(@RequestParam(required = false,name = "a") String a, HttpServletResponse response,
	                       HttpServletRequest request,
	                       HttpSession httpSession)throws
			Exception{
		
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/gif");
		/**
		 * gif格式动画验证码
		 * 宽，高，位数。
		 */
		Captcha captcha = new GifCaptcha(146,33,4);
		//输出
		captcha.out(response.getOutputStream());
		HttpSession session = request.getSession(true);
		//存入Session
		session.setAttribute("_code",captcha.text().toLowerCase());
//			if (null != SecurityUtils.getSubject())
//				logger.error("用户"+SecurityUtils.getSubject().getPrincipal().toString()+"请求获取验证码");
		
	}
	
	private boolean codeIdentify(String vcode,RedirectAttributes redirectAttributes){
		//首先判断验证码是否为空
		if(vcode==null||vcode==""){
			redirectAttributes.addFlashAttribute("message","验证码不能为空");
			logger.error("验证码不能为空");
			return false;
		}
		
		Session session = SecurityUtils.getSubject().getSession();
		//转化成小写字母
		vcode = vcode.toLowerCase();
		String v = (String) session.getAttribute("_code");
		//还可以读取一次后把验证码清空，这样每次登录都必须获取验证码
//		session.removeAttribute("_code");
		if(!vcode.equals(v)){
			redirectAttributes.addFlashAttribute("message","验证码不正确!");
			logger.error("验证码不正确!");
			return false;
		}else {
			logger.info("验证码验证通过!");
			return true;
		}
	}
	
	private String getForwardUrl(HttpServletRequest request){
		
		//	返回跳转前界面 暂时没没有bug啦
		if (request!=null  && WebUtils.getSavedRequest(request)!= null){
			String url = WebUtils.getSavedRequest(request).getRequestUrl();
			if (url != null){
				logger.info("登陆前界面: " + url);
				return url;
			}
			return null;
		}
		return null;
	}
}
