/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.25
  Time: 11:29
*/

package justdj.top.controller.student;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import justdj.top.pojo.*;
import justdj.top.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class StudentController {
	
	@Autowired
	@Qualifier("courseService")
	private CourseService courseService;
	
	@Autowired
	@Qualifier("examService")
	private ExamService examService;
	
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("answerService")
	private AnswerService answerService;
	
	@Autowired
	@Qualifier("testPaperService")
	private TestPaperService testPaperService;
	
	
	/**
	 *@author  ShanDJ
	 *@params [courseId, model]
	 *@return  java.lang.String
	 *@date  18.5.25
	 *@description 获得学生所学课程 难点？学生 课程
	 */
	@RequestMapping("/st")
	public void selectCourseByStudentId(@RequestParam(value = "id",required = true) BigInteger studentId,
	                                    Model model){
		//通过学生Id获取课程
		List<Course> courseList = courseService.selectCourseByStudentId(studentId);
		
		model.addAttribute("courseList",courseList);
	}
	
	
	/**
	 *@author  ShanDJ
	 *@params [studentId, model]
	 *@return  void
	 *@date  18.5.26
	 *@description 获取学生个人信息
	 */
	@RequestMapping(value = "/st/info",method = RequestMethod.GET)
	public void getStudentInfoByStudentId(@RequestParam(value = "id",required = true) BigInteger studentId,
	                                      Model model){
		
		User user = userService.selectUserById(studentId);
		
		model.addAttribute("user",user);
		
	}
	
//	修改密码接口没写
	
	
	
	/**
	 *@author  ShanDJ
	 *@params [courseId, model]
	 *@return  void
	 *@date  18.5.26
	 *@description 学生获得课程知识点列表 以及考试列表的接口
	 */
	@RequestMapping("/st/course")
	public  void selectCourseInfoByCourseId(@RequestParam(value = "id",required = true) BigInteger courseId,
																		Model model){
		List<Knowledge> knowledgeList = courseService.selectKnowledgeByCourseId(courseId);
		List<Exam> examList = examService.selectExamByCourseId(courseId);
		
		model.addAttribute("knowledgeList",knowledgeList);
		model.addAttribute("examList",examList);
	}
	
	
	/**
	 *@author  ShanDJ
	 *@params [courseId, model]
	 *@return  void
	 *@date  18.5.26
	 *@description 获得课程考试信息
	 */
	@RequestMapping("/st/course/examInfo")
	public void selectExamByCourseId(@RequestParam("id")BigInteger courseId,
	                                 Model model){
		//注意这里获得课程考试信息 不是学生考试信息
		List<Exam> examList = examService.selectExamByCourseId(courseId);
		
		model.addAttribute("examList",examList);
	}
	
	
	/**
	 *@author  ShanDJ
	 *@params [examId, model]
	 *@return  void
	 *@date  18.5.26
	 *@description 学生开始考试
	 */
	@RequestMapping(value = "/st/course/exam",method = RequestMethod.GET)
	public String startExam(@RequestParam("id") BigInteger examId,
	                      Model model){
		List<TestPaper> testPaperList =  testPaperService.selectTestPaperByExamId(examId);
		
		//这里应该随机获取试卷
		
		Integer randomNum = 0;
		
		//假设随机到第一张试卷
		//获取到试卷的所有题型
		List<Kind> kindList = testPaperService.selectQuestionKindByTestPaperId(testPaperList.get(randomNum).getId());
		//获取到试卷对应的全部的问题
		List<Question> questionList = testPaperService.selectQuestionByTestPaperId(testPaperList.get(randomNum).getId());
		
		//根据题型获取试卷问题
//		List<Question> questionList1 =
//				testPaperService.selectQuestionByTestPaperIdAndKindId(testPaperList.get(randomNum).getId(),);
		
		model.addAttribute("testPaper",testPaperList.get(randomNum));
		model.addAttribute("kindList",kindList);
		model.addAttribute("questionList",questionList);
		
		List<String> kindName = new LinkedList <>();
		for (Kind a:kindList)
			kindName.add(a.getName());
		model.addAttribute("kindName",kindName);
		return "testPaper";
	}
	
	/**
	 *@author  ShanDJ
	 *@params [request]
	 *@return  java.lang.String
	 *@date  18.5.27
	 *@description 这是个学生提交答案的接口 具体逻辑还没实现
	 */
	@RequestMapping(value = "/st/course/exam",method = RequestMethod.POST)
	@ResponseBody
	public String saveAnswer(HttpServletRequest request){
		System.out.println(request.getParameter("1"));
		
		
	return "success";
	}
	
	/**
	 *@author  ShanDJ
	 *@params [kindName]
	 *@return  java.lang.String
	 *@date  18.5.27
	 *@description 按题目类型名获取试卷对应的题目
	 */
	@RequestMapping("/st/course/exam/question")
	@ResponseBody
	public String getQuestionByQuestionKind(@RequestParam("id")BigInteger testPaperId,
	                                        @RequestParam("kind")String kindName,
	                                        Model model){
		List<Question> questionList = testPaperService.selectQuestionByTestPaperIdAndKindName(testPaperId,kindName);
		
//		System.out.println(JSONObject.toJSON(questionList).toString());
		return JSONObject.toJSON(questionList).toString();
	}
	
	
	/**
	 *@author  ShanDJ
	 *@params [examId, model]
	 *@return  void
	 *@date  18.5.26
	 *@description 获取学生答卷信息 包括题型 题目
	 */
	@RequestMapping("/st/course/exam/answer")
	public void selectAnswerByExamIdAndStudentId(@RequestParam("id")BigInteger examId,
	                                             Model model){
//		Subject subject = SecurityUtils.getSubject();
//		subject.getPrincipal();


//		这里从内部获取学生ID
		Answer answer = answerService.selectAnswerByExamIdAndStudentId(examId,BigInteger.valueOf(1));
		List<AnswerQuestion> answerQuestionList = answerService.selectAnswerQuestionByAnswerId(answer.getId());
		List<Kind> kindList = answerService.selectQuestionKindByAnswerId(answer.getId());
		
		model.addAttribute("answer",answer);
		model.addAttribute("answerQuestionList",answerQuestionList);
		model.addAttribute("kindList",kindList);
	}
	
	

}
