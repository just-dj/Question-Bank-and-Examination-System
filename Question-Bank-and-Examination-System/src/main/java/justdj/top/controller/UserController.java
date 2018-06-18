/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.6
  Time: 15:35
*/

package justdj.top.controller;

import com.alibaba.fastjson.JSON;
import justdj.top.pojo.Question;
import justdj.top.pojo.User;
import justdj.top.service.CourseService;
import justdj.top.vcode.Captcha;
import justdj.top.vcode.GifCaptcha;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;


@Controller
public class UserController {
	
	
	@Autowired
	@Qualifier(value = "userService")
	justdj.top.service.UserService userService;
	
	@Autowired
	@Qualifier("courseService")
	private CourseService courseService;
	
	@Autowired
	SecureRandomNumberGenerator secureRandomNumberGenerator;
	
	
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsQueueTemplate;
	
	@Autowired
	private Destination queueDestination;
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	@RequestMapping("/")
	public String main(){
		Subject subject = SecurityUtils.getSubject();
		if (subject.isRemembered()){
			if (subject.hasRole("teacher")){
				return "redirect:/te";
			}else if (subject.hasRole("student")){
				return "redirect:/st";
			}else if (subject.hasRole("manager")){
				return "redirect:/ma";
			}else {
				return "redirect:/st";
			}
		}
		return "redirect:/login";
	}
	
	/**
	 *@author  ShanDJ
	 *@params [model]
	 *@return  java.lang.String
	 *@date  18.5.25
	 *@description 仅作跳转作用
	 */
	@RequestMapping(value="/login",method= RequestMethod.GET)
	public String loginForm(Model model){
		return "/user/login";
	}
	
	/**
	 *@author  ShanDJ
	 *@params [user, vcode, bindingResult, redirectAttributes, request, model]
	 *@return  java.lang.String
	 *@date  18.5.25
	 *@description 处理登陆逻辑
	 */
	@RequestMapping(value="/login",method= RequestMethod.POST)
	public String login(@RequestParam(value = "account")String account,
			@RequestParam(value = "password")String password,
			@RequestParam(value = "identifyNum") String vcode,
	        @RequestParam(value = "rember",required = false)String[] rememberMe,
	         RedirectAttributes redirectAttributes,
	         HttpServletRequest request,
	         HttpSession session,
	         Model model){
		User user= new User();
		user.setAccount(account);
		user.setPassword(password);
		Boolean remember = false;
		redirectAttributes.addFlashAttribute("user",user);
		//验证码判断
		if (!codeIdentify(vcode,redirectAttributes)){
			return "redirect:/login";
		}
		
		if (null != rememberMe && rememberMe[0].equals("true"))
			remember = true;
		UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getPassword());
		//七天免登录,注意权限问题，是必须登录还是记住密码即可
		if (remember)
			token.setRememberMe(true);
		//获取当前用户
		Subject subject = SecurityUtils.getSubject();
		//登录，即身份验证，传输凭证并登录
		//if语句是为了避免重复登录问题
		User userNow = userService.selectUserByAccount(user.getAccount());
		
		if (subject.isRemembered()){
			//  用户是使用了记住密码
		}else{
			try{
				subject.login(token);
			}
			catch (AuthenticationException e) {
				if (e.getMessage().length()>15){
					redirectAttributes.addFlashAttribute("message","账号或密码错误");
				}else {
					//认证异常
					redirectAttributes.addFlashAttribute("message",e.getMessage());
				}
				logger.error("用户 "+user.getId()+" 身份认证失败!错误信息 : " + e.getMessage());
				return "redirect:/login";
			}
		}
		
		logger.warn("用户 "+ userNow.getAccount()+" 已登录！");
		
		
		//判断是否是从其他页跳转过来的
		String temp = getForwardUrl(request);
		session.setAttribute("user",userNow);
//		forward redirect
		if (temp != null)
			return "redirect:"+temp;
		else{
			//				这里应该有一段判断跳转到哪里的逻辑
			if (subject.hasRole("teacher")){
				return "redirect:/te";
			}else if (subject.hasRole("student")){
				return "redirect:/st";
			}else if (subject.hasRole("manager")){
				return "redirect:/ma";
			}else {
			return "redirect:/st";
			}
		}
		
		
	}
	
	
	/**
	 *@author  ShanDJ
	 *@params [redirectAttributes]
	 *@return  java.lang.String
	 *@date  18.5.25
	 *@description 用户登出处理
	 */
	@RequestMapping(value="/logout",method= RequestMethod.GET)
	public String logout(@RequestParam(value = "message",required = false)String message,
			HttpSession session,
			RedirectAttributes redirectAttributes ){
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated() || subject.isRemembered()) {
			//	这里会将记住密码功能保存的也给删除掉
			logger.warn("用户 " + subject.getPrincipal().toString() + " 已安全退出!");
			subject.logout();
		}
		//判断是否是修改密码后跳转过来的
		if (null == message)
			redirectAttributes.addFlashAttribute("message", "您已安全退出");
		else
			redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/login";
	}
	
	/**
	 *@author  ShanDJ
	 *@params []
	 *@return  java.lang.String
	 *@date  18.5.25
	 *@description 简单错误页面,后期可以删除
	 */
	@RequestMapping("/403")
	public String unauthorizedRole(){
		
		return "/403";
	}
	
	/**
	 *@author  ShanDJ
	 *@params []
	 *@return  java.lang.String
	 *@date  18.5.25
	 *@description 实现记住我功能 后期可以删除
	 */
	@RequestMapping(value = "/remberme")
	@ResponseBody
	@Deprecated
	public String testRemberMe(){
		Subject subject = SecurityUtils.getSubject();
		String info = "";
		info = String.format("是否通过登录认证 + " + subject.isAuthenticated()+ " 是否通过记住密码功能登录 : "+ subject.isRemembered());
		return info;
	}
	
	/**
	 *@author  ShanDJ
	 *@params [a, response, request, httpSession]
	 *@return  void
	 *@date  18.5.25
	 *@description 提供验证码的接口
	 */
	@RequestMapping(value="/getGifCode",method= RequestMethod.GET)
	public void getGifCode(@RequestParam(required = false,name = "a") String a, HttpServletResponse response,
	                       HttpServletRequest request,
	                       HttpSession httpSession)throws Exception{
		
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/gif");
		/**
		 * gif格式动画验证码
		 * 宽，高，位数。
		 */
		Captcha captcha = new GifCaptcha(64,27,3);
		//输出
		captcha.out(response.getOutputStream());
		HttpSession session = request.getSession(true);
		//存入Session
		session.setAttribute("_code",captcha.text().toLowerCase());
//			if (null != SecurityUtils.getSubject())
//				logger.error("用户"+SecurityUtils.getSubject().getPrincipal().toString()+"请求获取验证码");
		
	}
	
	@RequestMapping(value = "/register",method = RequestMethod.GET)
	public String register(){
		return "/user/register";
	}
	
	@RequestMapping(value = "/sendEmail",method = RequestMethod.POST)
	@ResponseBody
	public String sendEmail(@RequestParam(value = "email",required = false)String email,
	                      HttpServletRequest request,
	                      Model model){
		User user =  new User();
		if (null == email || email.equals("")){
			Subject subject = SecurityUtils.getSubject();
			User userNow = userService.selectUserByAccount(subject.getPrincipal().toString());
			user.setEmail(userNow.getEmail());
		}else {
			user.setEmail(email);
		}
		
		Random random = new Random();
		String result="";
		for (int i=0;i<6;i++) {
			result+=random.nextInt(10);
		}
		user.setCode(result);
		HttpSession session = request.getSession(true);
		session.setAttribute("code",user.getCode());
		session.setAttribute("email",user.getEmail());
		System.out.println(user.getEmail() + "  "+ user.getCode());
		
		jmsQueueTemplate.send(queueDestination, new MessageCreator() {
			@Override
			public Message createMessage(javax.jms.Session session) throws JMSException {
				Message message = session.createObjectMessage(user);
				return message;
			}
		});
		
		return "已发送";
	}
	
	/**
	 *@author  ShanDJ
	 *@params [password]
	 *@return  void
	 *@date  18.6.3
	 *@description 修改密码
	 */
	@RequestMapping(value = "/changePassword",method = RequestMethod.POST)
	@ResponseBody
	public  String changePassword(@RequestParam("identifyCode")String identifyCode,
			@RequestParam("password")String password,
			                      HttpServletRequest request){
		HttpSession session = request.getSession(true);
		System.err.println(identifyCode + password);
		//		注意获取用户账号
		Subject subject = SecurityUtils.getSubject();
		User userNow = userService.selectUserByAccount(subject.getPrincipal().toString());

		
		logger.info("当前用户验证信息 "+ userNow.getEmail()+"  ");
//		防止恶意修改
		if (null == session.getAttribute("email") ||
				null == session.getAttribute("code") ||
				!session.getAttribute("code").toString().toLowerCase()
						.trim().equals(identifyCode.trim().toLowerCase())){
			logger.warn(userNow.getAccount() +" 修改密码失败，验证码错误");
			return "修改密码失败，验证码错误";
		}
		
		String salt = secureRandomNumberGenerator.nextBytes().toHex();
		//加密两次 盐为用户名+随机数
		String cryptedPwd = new SimpleHash("MD5",password , ByteSource.Util.bytes(salt),2).toHex();
		userNow.setPassword(cryptedPwd);
		userNow.setSalt(salt);
		int result = userService.changePassword(userNow);
		
		logger.warn(userNow.getAccount() +"修改密码成功。");
		return "密码修改成功!";
	}
	
	/**
	 *@author  ShanDJ
	 *@params []
	 *@return  java.lang.String
	 *@date  18.5.25
	 *@description 用户注册接口 具体参数还没写
	 */
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public String addUser(@RequestParam("name")String name,
	                      @RequestParam("account")String account,
	                      @RequestParam("pwd")String password,
	                      @RequestParam("pwdCheck")String password1,
	                      @RequestParam("email")String email,
	                      @RequestParam("identifyCode")String code,
	                      RedirectAttributes redirectAttributes,
	                      HttpSession session,
	                      Model model){
		User user = new User();
		user.setAccount(account);
		user.setPassword(password);
		user.setName(name);
		user.setEmail(email);
		user.setCode(code);
		user.setSex('男');
		user.setUse(false);
		redirectAttributes.addFlashAttribute("user",user);
		logger.info("注册用户信息："+JSON.toJSONString(user));
		logger.info("当前用户验证信息"+ session.getAttribute("email").toString()+" "+session.getAttribute("code").toString());
		//防止恶意注册
		if (null == session.getAttribute("email") ||
				null == session.getAttribute("code") ||
				!session.getAttribute("code").toString().toLowerCase()
						.trim().equals(user.getCode().trim().toLowerCase())){
			redirectAttributes.addFlashAttribute("message","验证码错误！");
			return "redirect:/register";
		}
		user.setEmail(session.getAttribute("email").toString());
		User user1 = userService.selectUserByAccount(account);
		if (null != user1){
			redirectAttributes.addFlashAttribute("message","账号已存在！");
			return "redirect:/register";
		}
		String salt = secureRandomNumberGenerator.nextBytes().toHex();
		//加密两次 盐为用户名+随机数
		String cryptedPwd = new SimpleHash("MD5",user.getPassword() , ByteSource.Util.bytes(salt),2).toHex();
		user.setPassword(cryptedPwd);
		user.setSalt(salt);
		int result = userService.insertUser(user);
		logger.warn("新用户"+ user.getAccount()+ "注册成功！待审核。");
		redirectAttributes.addFlashAttribute("user",null);
		redirectAttributes.addFlashAttribute("success","注册成功！待管理员审核。");
		return "redirect:/register";
	}
	
	@RequestMapping(value = "/changeImg",method = RequestMethod.POST)
	@ResponseBody
	public String changeImg(
			@RequestParam(value = "file") MultipartFile uploadFile,
			HttpServletRequest request,
			RedirectAttributes redirectAttributes){
		
		Subject subject = SecurityUtils.getSubject();
		User user = userService.selectUserByAccount(subject.getPrincipal().toString());
		if (!uploadFile.isEmpty()) {
//			判断文件大小
			if (uploadFile.getSize() > (1 * 1024 * 1024)){
				logger.warn(user.getAccount() +"尝试上传过大头像图片");
				return "失败！文件最大尺寸1M";
			}

//			保存文件路径
			String realPath = request.getServletContext().getRealPath("/upload/");
			String webPath = "";
			
			String prefix=uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf(".")+1);
			String time = new Timestamp(org.terracotta.statistics.Time.absoluteTime()).toString();
			time = time.replace(':','-');
			String fileName = time+" " +user.getAccount()+ "." +prefix;
			File filePath = new File(realPath,fileName);
			if (!filePath.getParentFile().exists()){
				filePath.getParentFile().mkdirs();
			}
//			保存文件
			try{
				uploadFile.transferTo(filePath);
				webPath = "/upload"+File.separator+ time+" " +user.getAccount()+"."+prefix;
			}catch (IOException e){
				e.printStackTrace();
				logger.warn(user.getAccount() +"上传的图片保存失败！"
						+webPath+e.getCause());
				return "图片保存失败！请稍后重试。";
			}
			
			user.setImg(webPath);
			userService.updateUserImg(user);
			logger.warn(user.getAccount() +"上传的图片保存成功！" + filePath + webPath);
			return webPath;
		} else {
			return "文件上传失败！请稍后重试。";
		}
		
	}
	
	
	@RequestMapping("/defaultImg")
	@Transactional
	public String setDefaultImg(){
		Subject subject = SecurityUtils.getSubject();
		User user = userService.selectUserByAccount(subject.getPrincipal().toString());
		user.setImg("");
		userService.updateUserImg(user);
		return "redirect:/info";
	}
	
	
	/**
	 *@author  ShanDJ
	 *@params [studentId, model]
	 *@return  void
	 *@date  18.5.27
	 *@description 个人中心
	 */
	@RequestMapping(value = "/info",method = RequestMethod.GET)
	public String teacherInfo(
			Model model){
		Subject subject = SecurityUtils.getSubject();
		
		User user = userService.selectUserByAccount(subject.getPrincipal().toString());
		
		model.addAttribute("user",user);
		
		return "/user/personalCenter";
	}
	
	
	
	/**
	 *@author  ShanDJ
	 *@params [vcode, redirectAttributes]
	 *@return  boolean
	 *@date  18.5.25
	 *@description 具体处理验证码方面的逻辑
	 */
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
	
	
	/**
	 *@author  ShanDJ
	 *@params [request]
	 *@return  java.lang.String
	 *@date  18.5.25
	 *@description 查找用户是否从其他页面跳转过来,处理相关逻辑
	 */
	private String getForwardUrl(HttpServletRequest request){
		
		//	返回跳转前界面 暂时没没有bug啦
		if (request!=null  && WebUtils.getSavedRequest(request)!= null){
			String url = WebUtils.getSavedRequest(request).getRequestUrl();
			if (url != null && !url.equals("") && !url.equals("/")){
				logger.info("登陆前界面: " + url);
				return url;
			}
			return null;
		}
		return null;
	}
}
