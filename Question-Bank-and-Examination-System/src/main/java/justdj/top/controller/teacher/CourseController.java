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
	
	
	/**
	 *@author  ShanDJ
	 *@params [courseId, model]
	 *@return  void
	 *@date  18.5.27
	 *@description 课程信息界面
	 */
	@RequestMapping(value = "/te/course",method = RequestMethod.GET)
	public void myCourse(@RequestParam("id") BigInteger courseId, Model model){
		
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
	}
	
	
}
