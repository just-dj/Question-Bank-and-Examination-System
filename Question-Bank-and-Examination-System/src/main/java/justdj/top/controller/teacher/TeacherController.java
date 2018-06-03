/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.27
  Time: 21:40
  Info:
*/

package justdj.top.controller.teacher;

import justdj.top.pojo.Course;
import justdj.top.pojo.User;
import justdj.top.service.CourseService;
import justdj.top.service.UserService;
import org.apache.commons.net.util.SubnetUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigInteger;
import java.util.List;

@Controller
public class TeacherController {
	
	@Autowired
	@Qualifier("courseService")
	private CourseService courseService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	
	@Autowired
	SecureRandomNumberGenerator secureRandomNumberGenerator;
	/**
	 *@author  ShanDJ
	 *@params [teacherId, model]
	 *@return  void
	 *@date  18.5.27
	 *@description 教师主页
	 */
	@RequestMapping("/te")
	public String teacherMainPage(@RequestParam("id")BigInteger teacherId,
	                            Model model){
		User user = userService.selectUserById(teacherId);
		List<Course> courseList = courseService.selectCourseByTeacherId(teacherId);
		
		model.addAttribute("user",user);
		model.addAttribute("courseList",courseList);
		
		return "userInfo";
	}
	
	/**
	 *@author  ShanDJ
	 *@params [studentId, model]
	 *@return  void
	 *@date  18.5.27
	 *@description 老师个人中心
	 */
	@RequestMapping(value = "/te/info",method = RequestMethod.GET)
	public void teacherInfo(@RequestParam(value = "id",required = true) BigInteger teacherId,
	                        Model model){
		User user = userService.selectUserById(teacherId);
		
		model.addAttribute("user",user);
		
	}
	
	/**
	 *@author  ShanDJ
	 *@params [password]
	 *@return  void
	 *@date  18.6.3
	 *@description 修改密码 
	 */
	@RequestMapping(value = "/te/changePassword",method = RequestMethod.POST)
	public  String changePassword(@RequestParam("password")String password,
	                            RedirectAttributes redirectAttributes){
	
//		注意获取用户账号
//		User userNow = userService.selectUserByAccount(SecurityUtils.getSubject().getPrincipal().toString());
		User userNow = userService.selectUserByAccount("1");
		String salt = secureRandomNumberGenerator.nextBytes().toHex();
		//加密两次 盐为用户名+随机数
		String cryptedPwd = new SimpleHash("MD5",password , ByteSource.Util.bytes(salt),2).toHex();
		userNow.setPassword(cryptedPwd);
		userNow.setSalt(salt);
		int result = userService.changePassword(userNow);
		System.out.println("密码修改结果" +result);
		
		redirectAttributes.addFlashAttribute("message","密码修改成功，请重新登录。");
		return "redirect:/logout";
	}
}
