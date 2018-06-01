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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
//	修改密码接口
}
