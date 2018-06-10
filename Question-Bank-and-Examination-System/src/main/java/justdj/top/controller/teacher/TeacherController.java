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
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
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
	@RequiresRoles({"teacher"})
	public String teacherMainPage(@RequestParam(value = "id",required = false)BigInteger teacherId,
	                            Model model){
		User user = null;
		if (null == teacherId || "".equals(teacherId)){
			Subject subject = SecurityUtils.getSubject();
			user = userService.selectUserByAccount(subject.getPrincipal().toString());
			teacherId = user.getId();
		}else {
			user = userService.selectUserById(teacherId);
		}
		List<Course> courseList = courseService.selectCourseByTeacherId(teacherId);
		
		model.addAttribute("user",user);
		model.addAttribute("courseList",courseList);
		
		return "/te/teacherView";
	}
	

	
}
