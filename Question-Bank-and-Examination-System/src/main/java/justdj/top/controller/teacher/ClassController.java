/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.28
  Time: 10:13
  Info:
*/

package justdj.top.controller.teacher;

import justdj.top.pojo.Clazz;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigInteger;
import java.util.List;

@Controller
public class ClassController {
	
	@Autowired
	@Qualifier("courseService")
	private CourseService courseService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	/**
	 *@author  ShanDJ
	 *@params [courseId, model]
	 *@return  void
	 *@date  18.5.28
	 *@description 班级管理界面
	 */
	@RequestMapping("/te/class")
	public String classManager(@RequestParam("id") BigInteger courseId,
	                         Model model){
		List<Clazz> classList = courseService.selectClazzByCourseId(courseId);
		
		
		model.addAttribute("classList",classList);
		return "/te/classManager";
	}
	
	/**
	 *@author  ShanDJ
	 *@params [courseId, classId, redirectAttributes]
	 *@return  java.lang.String
	 *@date  18.6.3
	 *@description
	 */
	@RequestMapping(value = "/te/class/delete",method = RequestMethod.POST)
	public String deleteClassByClassId(@RequestParam("courseId")BigInteger courseId,
	                                 @RequestParam("classId")BigInteger classId,
	                                 RedirectAttributes redirectAttributes){
		int result = courseService.deleteClass(classId);
		if (result > 0 ){
			List<User> studentList = courseService.selectStudentByClassId(classId);
			for (User user:studentList) {
				courseService.deleteClassStudent(classId,user.getId());
			}
		}

		redirectAttributes.addAttribute("id",courseId);
		return "redirect:/te/class";
//		return "班级管理界面";
	}
	
	/**
	 *@author  ShanDJ
	 *@params [classId, model]
	 *@return  void
	 *@date  18.5.28
	 *@description 班级详情
	 */
	@RequestMapping("/te/class/student")
	public void getStudentListByClassId(@RequestParam("id")BigInteger classId,
	                                    Model model){
		List<User> studentList = courseService.selectStudentByClassId(classId);
		
		model.addAttribute("studentList",studentList);
		
//		return"班级详情";
	}
	
	/**
	 *@author  ShanDJ
	 *@params [studentId, model]
	 *@return  void
	 *@date  18.5.28
	 *@description 班级详情 删除学生DAO层还没实现
	 */
	@RequestMapping(value = "/te/class/student/delete",method = RequestMethod.POST)
	public String deleteStudentByStudentId(@RequestParam("studentId")BigInteger studentId,
	                                     @RequestParam("classId")BigInteger classId,
	                                     RedirectAttributes redirectAttributes,
	                                     Model model){
		  int result = courseService.deleteClassStudent(classId,studentId);
		  
		  redirectAttributes.addAttribute("id",classId);
		  return "redirect:/te/class/student";
//		return "班级详情";
	}
	
	/**
	 *@author  ShanDJ
	 *@params [kind, studentId, uploadFile]
	 *@return  void
	 *@date  18.5.28
	 *@description 添加学生  dao层还没实现 注意是往班级里添加学生
	 * 考虑一下接口分开写
	 * 文件上传逻辑需要处理
	 */
	@RequestMapping(value = "/te/class/student",method = RequestMethod.POST)
	public String addStudent(@RequestParam(value = "kind",required = true) String kind,
	                       @RequestParam("classId")BigInteger classId,
	                       @RequestParam(value = "account",required = false)String studentAccount,
	                       @RequestParam(value = "file",required = false)MultipartFile uploadFile,
	                         RedirectAttributes redirectAttributes){
//		单个添加
		 if (null != studentAccount && !"".equals(studentAccount)){
		    User student = userService.selectUserByAccount(studentAccount);
		    int result = courseService.addStudentToClass(classId,student.getId());
//		    return "班级详情";
		 }
//		批量添加
		 if (null != uploadFile){
		 
		 
		 }
		
		redirectAttributes.addAttribute("id",classId);
		return "redirect:/te/class/student";
	}
	
	
	
}
