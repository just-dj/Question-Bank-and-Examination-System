/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.13
  Time: 22:58
*/

package justdj.top.controller;

import justdj.top.pojo.*;
import justdj.top.service.*;
import justdj.top.util.KindHelper;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController1 {

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
	
	@RequestMapping("/info")
	public String courseInfo(@RequestParam("id") BigInteger courseId, Model model){
		
		List<Clazz> classList = courseService.selectClazzByCourseId(courseId);
		
		List<Knowledge> knowledgeList = courseService.selectKnowledgeByCourseId(courseId);
		
		List<TestDatabase> testDatabaseList = testDatabaseService.selectTestDatabaseByCourseId(courseId);
		
		List<TestPaper> testPaperList = testPaperService.selectTestPaperByCourseId(courseId);
		
		List<Exam> examList = examService.selectExamByCourseId(courseId);
		
		model.addAttribute("classList",classList);
		
		model.addAttribute("knowledgeList",knowledgeList);
		
		model.addAttribute("testDatabaseList",testDatabaseList);
		
		model.addAttribute("testPaperList",testPaperList);
	
		model.addAttribute("examList",examList);
		
		return "courseInfo";
	}

	
	@RequestMapping("/student")
	public String courseStudent(@RequestParam BigInteger id,Model model){
		List<User> studentList = courseService.selectStudentByClassId(id);
		model.addAttribute("studentList",studentList);
		return "courseStudent";
	}

	@RequestMapping("/testDatabase")
	public String courseTestDatabase(@RequestParam("id")BigInteger testDatabaseId,Model model){
		KindHelper.setKindService(kindService);
		List<String> kindName = KindHelper.getKindNameList();
		
//		for (String a:kindName){
//			List<Question> list = testDatabaseService.selectTestDatabaseQuestionByKindName(testDatabaseId,a);
//			model.addAttribute(a,list);
//		}
		
		model.addAttribute("kindName",kindName);
		
		List<Question> list = testDatabaseService.selectTestDatabaseQuestionByTDId(testDatabaseId);
		model.addAttribute("question",list);
		
		return "courseTestDatabase";
	}
}
