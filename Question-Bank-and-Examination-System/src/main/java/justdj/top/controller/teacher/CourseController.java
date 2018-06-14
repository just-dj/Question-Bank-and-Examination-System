/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.27
  Time: 23:22
  Info:
*/

package justdj.top.controller.teacher;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigInteger;
import java.util.List;

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
	public String addCourse(@RequestParam("name")String name,
	                        @RequestParam("introduce")String introduce,
	                        @RequestParam("file")MultipartFile file,
	                        RedirectAttributes redirectAttributes){
		Subject subject = SecurityUtils.getSubject();
		User user  = userService.selectUserByAccount(subject.getPrincipal().toString());
		
		Course course = new Course();
		course.setTeacherId(user.getId());
		course.setName(name);
		course.setIntroduce(introduce);
		
		logger.info(course.toString());
		
		
		
		return "redirect:/te";
	}
	
}
