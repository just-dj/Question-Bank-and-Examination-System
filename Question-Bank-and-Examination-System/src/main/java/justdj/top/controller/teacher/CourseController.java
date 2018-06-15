/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.27
  Time: 23:22
  Info:
*/

package justdj.top.controller.teacher;

import com.alibaba.fastjson.JSON;
import justdj.top.pojo.*;
import justdj.top.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CourseController {
	
	@Autowired
	@Qualifier("courseService")
	private CourseService courseService;
	
	@Autowired
	@Qualifier("testDatabaseService")
	private TestDatabaseService testDatabaseService;
	
	@Autowired
	@Qualifier("kindService")
	private KindService kindService;
	
	@Autowired
	@Qualifier("testPaperService")
	private TestPaperService testPaperService;
	
	@Autowired
	@Qualifier("examService")
	private ExamService examService;
	
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	
	private static Logger logger  = LoggerFactory.getLogger(CourseController.class);
	
	/**
	 *@author  ShanDJ
	 *@params [courseId, model]
	 *@return  void
	 *@date  18.5.27
	 *@description 课程信息界面
	 */
	@RequestMapping(value = "/te/course",method = RequestMethod.GET)
	public String myCourse(@RequestParam("id") BigInteger courseId, Model model){
		
		List<Clazz> classList = courseService.selectClazzByCourseId(courseId);
		
//		List<Knowledge> knowledgeList = courseService.selectKnowledgeByCourseId(courseId);
		
		List<TestDatabase> testDatabaseList = testDatabaseService.selectTestDatabaseByCourseId(courseId);
		
		List<TestPaper> testPaperList = testPaperService.selectTestPaperByCourseId(courseId);
		
		List<Exam> examList = examService.selectExamByCourseId(courseId);
		
		model.addAttribute("classList",classList);

//		model.addAttribute("knowledgeList",knowledgeList);
		
		model.addAttribute("testDatabaseList",testDatabaseList);
		
		model.addAttribute("testPaperList",testPaperList);
		
		model.addAttribute("examList",examList);
		
		model.addAttribute("courseId",courseId);
		
		return "/te/courseInfo";
	}
	
	@RequestMapping(value = "/te/course/add",method = RequestMethod.GET)
	public String addCoursePage(){
		
		return "/te/course-addCourse";
	}
	
	@RequestMapping(value = "/te/course/add",method = RequestMethod.POST)
	@ResponseBody
	public String addCourse(@RequestParam("name")String name,
	                        @RequestParam("introduce")String introduce,
	                        @RequestParam("file")MultipartFile uploadFile,
	                        HttpServletRequest request,
	                        RedirectAttributes redirectAttributes){
		Map<String,String> result = new HashMap <>();
		Subject subject = SecurityUtils.getSubject();
		User user  = userService.selectUserByAccount(subject.getPrincipal().toString());
		
		Course course = new Course();
		course.setTeacherId(user.getId());
		course.setName(name);
		course.setIntroduce(introduce);
		
		logger.info("教师"+subject.getPrincipal().toString()+"尝试创建课程" + course.toString());
		
		
		if (!uploadFile.isEmpty()) {
//			判断文件大小
			if (uploadFile.getSize() > (1 * 1024 * 1024)){
				logger.warn(user.getAccount() +"尝试上传过大头像图片");
				result.put("message","失败！文件最大尺寸1M");
				return JSON.toJSONString(result);
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
				logger.warn(user.getAccount() +"上传的课程图片保存失败！"+webPath+e.getCause());
				result.put("message"," 图片保存失败！请稍后重试。");
				return JSON.toJSONString(result);
			}

			course.setImg(webPath);
			logger.warn(user.getAccount() +"上传的课程图片保存成功！" + filePath + webPath);

			try{
				courseService.addCourse(course);
			}catch (RuntimeException e){
				logger.info("教师"+subject.getPrincipal().toString()+"创建课程" + course.toString() + "失败！");
			}
			logger.info("教师"+subject.getPrincipal().toString()+"创建课程" + course.toString() + "成功！");
			result.put("message","新建课程成功！");
			return JSON.toJSONString(result);
		} else {
			logger.info("教师"+subject.getPrincipal().toString()+"课程图片上传失败！" );
			result.put("message"," 课程图片上传失败！请稍后重试。");
			return JSON.toJSONString(result);
		}

		
		
	}
	
}
