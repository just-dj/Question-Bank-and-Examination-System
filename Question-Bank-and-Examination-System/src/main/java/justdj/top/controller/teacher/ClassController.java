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

import java.math.BigInteger;
import java.util.List;

@Controller
public class ClassController {
	
	@Autowired
	@Qualifier("courseService")
	private CourseService courseService;
	
	
	
	/**
	 *@author  ShanDJ
	 *@params [courseId, model]
	 *@return  void
	 *@date  18.5.28
	 *@description 班级管理界面
	 */
	@RequestMapping("/te/class")
	public void classManager(@RequestParam("id") BigInteger courseId,
	                         Model model){
		List<Clazz> classList = courseService.selectClazzByCourseId(courseId);
		
		
		model.addAttribute("classList",classList);
	}
	
	/**
	 *@author  ShanDJ
	 *@params [classId]
	 *@return  void
	 *@date  18.5.28
	 *@description 删除教学班 Dao层还没写
	 */
	@RequestMapping("/te/class/delete")
	public void deleteClassByClassId(@RequestParam("id")BigInteger classId){
	
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
		
	}
	
	/**
	 *@author  ShanDJ
	 *@params [studentId, model]
	 *@return  void
	 *@date  18.5.28
	 *@description 班级详情 删除学生DAO层还没实现
	 */
	@RequestMapping(value = "/te/class/student/delete")
	public void deleteStudentByStudentId(@RequestParam("id")BigInteger studentId,
	                                     Model model){
		  
		
	}
	
	/**
	 *@author  ShanDJ
	 *@params [kind, studentId, uploadFile]
	 *@return  void
	 *@date  18.5.28
	 *@description 添加学生  dao层还没实现 注意是往班级里添加学生
	 * 考虑一下接口分开写
	 */
	@RequestMapping(value = "/te/class/student",method = RequestMethod.POST)
	public void addStudent(@RequestParam(value = "kind",required = true) String kind,
	                       @RequestParam(value = "id",required = false)BigInteger studentId,
	                       @RequestParam(value = "file",required = false)MultipartFile uploadFile){
		 

	}
	
	
	
}
