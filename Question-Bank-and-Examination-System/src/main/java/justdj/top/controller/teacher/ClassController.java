/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.28
  Time: 10:13
  Info:
*/

package justdj.top.controller.teacher;

import com.alibaba.fastjson.JSON;
import justdj.top.pojo.Clazz;
import justdj.top.pojo.User;
import justdj.top.service.CourseService;
import justdj.top.service.ParseFileService;
import justdj.top.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;

@Controller
public class ClassController {
	
	@Autowired
	@Qualifier("courseService")
	private CourseService courseService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("parseFileService")
	private ParseFileService parseFileService;
	
	private static Logger logger = LoggerFactory.getLogger(ClassController.class);
	
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
		
		Subject subject = SecurityUtils.getSubject();
		
		logger.warn("教师 "+subject.getPrincipal().toString() + " 管理 课程" + courseId + "班级");
		
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
	public String getStudentListByClassId(@RequestParam("id")BigInteger classId,
	                                    Model model){
		List<User> studentList = courseService.selectStudentByClassId(classId);
		
		model.addAttribute("studentList",studentList);
		model.addAttribute("classId",classId);
		
		Subject subject = SecurityUtils.getSubject();
		
		logger.warn("教师 "+subject.getPrincipal().toString() + " 管理班级 " + classId);
		
		return"/te/classDetail";
	}
	
	/**
	 *@author  ShanDJ
	 *@params [studentId, model]
	 *@return  void
	 *@date  18.5.28
	 *@description 班级详情 删除学生DAO层还没实现
	 */
	@RequestMapping(value = "/te/class/student/delete",method = RequestMethod.GET)
	public String deleteStudentByStudentId(@RequestParam("studentId")BigInteger studentId,
	                                     @RequestParam("classId")BigInteger classId,
	                                     RedirectAttributes redirectAttributes,
	                                     Model model){
		  int result = courseService.deleteClassStudent(classId,studentId);
		  
		  redirectAttributes.addAttribute("id",classId);
		
		Subject subject = SecurityUtils.getSubject();
		
		  logger.warn("教师 "+subject.getPrincipal().toString() + " 删除" + classId +" 班 " + " 学生 " + studentId);
		  return "redirect:/te/class/student?"+ new Random().nextInt();
//		return "班级详情";
	}
	
	
	@RequestMapping(value = "/te/class/student/add",method = RequestMethod.GET)
	public String addStudent(@RequestParam("classId")BigInteger classId,
	                         Model model){
		
		Subject subject = SecurityUtils.getSubject();
		logger.warn("教师 "+subject.getPrincipal().toString() + " 访问 班级 "+classId+" 添加学生界面");
		
		model.addAttribute("classId",classId);
		return "/te/class-addStudent";
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
	@RequestMapping(value = "/te/class/student/add",method = RequestMethod.POST)
	@ResponseBody
	public String addStudent(HttpServletRequest request){
		
		Subject subject = SecurityUtils.getSubject();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Integer kind = Integer.valueOf(multipartRequest.getParameter("kind"));
		BigInteger classId = new BigInteger(multipartRequest.getParameter("classId"));
		String studentAccount = "";
		MultipartFile uploadFile = null;
		
		if (0 == kind){
			studentAccount = multipartRequest.getParameter("account");
			logger.warn("教师 "+subject.getPrincipal().toString() + " 向 班级 "+classId+" 添加学生 " + studentAccount);
			
			User user = userService.selectUserByAccount(studentAccount);
			if (null == user){
				return "账号不存在，请检查输入是否正确";
			}
			//进行插入
			int result = 0;
			try {
				result = courseService.addStudentToClass(classId,user.getId());
			}catch (DataIntegrityViolationException e){
				logger.warn("教师 " + subject.getPrincipal().toString() + "尝试导入重复账号 " +studentAccount);
				return "该账号已在班级名单内！";
			}
			
			logger.info("教师 " + subject.getPrincipal().toString() + "成功为班级 "+classId+" 导入 " + user.getAccount());
			return "成功导入！";
			
		}else {
			Map<String,String> resultMap = new HashMap <>();
			List<String> accountList = null;
			List<String> errorAccountList = new ArrayList <>();
			uploadFile = multipartRequest.getFile("file");
			logger.warn("教师 "+subject.getPrincipal().toString() + " 批量向 班级 "+classId+" 添加学生");
			try {
				 accountList = parseFileService.parseStudentAccountFile(uploadFile.getInputStream());
			}catch (Exception e){
				logger.warn("教师 "+subject.getPrincipal().toString() + " 批量向 班级 "+classId + " 添加学生" + " 文件上传失败" + e.getMessage());
				resultMap.put("message","文件上传失败，请稍后重试！");
			}
			if (null == studentAccount){
				logger.warn("教师 "+subject.getPrincipal().toString() + " 批量向 班级 "+classId + " 添加学生" + " 文件处理失败");
				resultMap.put("message","文件处理失败，请检查文件格式！");
			}
			for (String account:accountList) {
				User user = userService.selectUserByAccount(account);
				if (null == user){
					errorAccountList.add(account + " 不存在。" + "<br>");
				}else{
					//进行插入
					int result = 0;
					try{
						result = courseService.addStudentToClass(classId,user.getId());
					}catch (DataIntegrityViolationException e){
						errorAccountList.add(account +" 已在班级名单内。" + "<br>");
					}
					
				}
				
			}
			
			logger.info("教师 " + subject.getPrincipal().toString() + "尝试为班级 "+classId+" 导入 "+ accountList.size()
					+"条, 导入成功 " + (accountList.size() - errorAccountList
					.size()) + "条，失败 " + errorAccountList.size() +"条。");
			
			
			resultMap.put("message", "共尝试为班级 "+classId+" 导入 "+ accountList.size() +"条, 导入成功 " + (accountList.size() -
					errorAccountList.size()) + "条，失败 " + errorAccountList.size() +"条。");
			resultMap.put("data",errorAccountList.toString());
			
			 return JSON.toJSONString(resultMap);
			
		}
		
		
	}
	
	
	
}
