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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terracotta.statistics.Time;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

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
	 *@description 学生主页 获得学生所学课程 难点？学生 课程
	 */
	@RequestMapping("/st")
	public String selectCourseByStudentId(@RequestParam(value = "id",required = false) BigInteger studentId,
	                                    Model model){
		User user = null;
		if (null == studentId || "".equals(studentId)){
			Subject subject = SecurityUtils.getSubject();
			user = userService.selectUserByAccount(subject.getPrincipal().toString());
			studentId = user.getId();
		}else {
			user = userService.selectUserById(studentId);
		}
		//通过学生Id获取课程
		List<Course> courseList = courseService.selectCourseByStudentId(studentId);
		
		model.addAttribute(user);
		model.addAttribute("courseList",courseList);
		
		return "/st/studentView";
	}
	
	
	/**
	 *@author  ShanDJ
	 *@params [studentId, model]
	 *@return  void
	 *@date  18.5.26
	 *@description 学生个人中心 获取学生个人信息
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
	 *@description 课程详情 学生获得课程知识点列表 以及考试列表的接口
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
	 *@description 课程详情 可以考虑和上面的合并 获得课程考试信息 需要完善
	 */
	@RequestMapping(value = "/st/course/examInfo",method = RequestMethod.GET
	)
	public void selectExamByCourseId(@RequestParam("id")BigInteger courseId,
	                                 Model model){
		BigInteger studentId = BigInteger.valueOf(1);
		//这里获得学生对应的考试信息
		List<Exam> examList = examService.selectStudentExamByCourseId(courseId);
		List<Exam> newExamList = new ArrayList <>();
		BigInteger classId = userService.selectClassByStudentIdAndCourseId(studentId,courseId);
		if (null != examList){
			for (int i = 0; i < examList.size(); i++) {
				if (null != examList.get(i).getClassList()){
					if (examList.get(i).getClassList().contains(classId)){
						newExamList.add(examList.get(i));
					}
				}
			}
		}
		model.addAttribute("examList",newExamList);
	}
	
	
	/**
	 *@author  ShanDJ
	 *@params [examId, model]
	 *@return  void
	 *@date  18.5.26
	 *@description 开始考试界面 学生开始考试
	 */
	@RequestMapping(value = "/st/course/exam",method = RequestMethod.GET)
	public String startExam(@RequestParam("id") BigInteger examId,
	                      Model model){
		Subject subject = SecurityUtils.getSubject();
		//获得考试使用的所有试卷
		List<TestPaper> testPaperList =  testPaperService.selectTestPaperByExamId(examId);
		
		Answer answer = answerService.selectAnswerByExamIdAndStudentId(examId,
				userService.selectUserByAccount(subject.getPrincipal().toString()).getId());
		BigInteger randomTestPaper  = BigInteger.valueOf(1);
		if (null == answer){
			//多张试卷并且是第一次访问的时候进行一下随机算法，,这里应该随机获取试卷
			int random = new Random().nextInt(testPaperList.size());
			randomTestPaper = testPaperList.get(random).getId();
			//将Answer插入数据库
			Answer newAnswer = new Answer();
			newAnswer.setTestPaperId(randomTestPaper);
			newAnswer.setStartTime(new Timestamp(Time.absoluteTime()));
			newAnswer.setCommit(false);
			newAnswer.setStudentId(userService.selectUserByAccount(subject.getPrincipal().toString()).getId());
			answerService.addAnswer(newAnswer);
		}else{
			randomTestPaper = answer.getTestPaperId();
		}
		
		
		//假设随机到第一张试卷
		//获取到试卷的所有题型
		List<Kind> kindList = testPaperService.selectQuestionKindByTestPaperId(randomTestPaper);
		//获取到试卷对应的全部的问题
		List<Question> questionList = testPaperService.selectQuestionByTestPaperId(randomTestPaper);
		
		//根据题型获取试卷问题
//		List<Question> questionList1 =
//				testPaperService.selectQuestionByTestPaperIdAndKindId(testPaperList.get(randomNum).getId(),);
		
		for (TestPaper testPaper:testPaperList){
			if (testPaper.getId().equals(randomTestPaper)){
				model.addAttribute("testPaper",testPaper);
				break;
			}
		}
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
	 *@description 开始考试提交 这是个学生提交答案的接口 具体逻辑还没实现
	 */
	@RequestMapping(value = "/st/course/exam",method = RequestMethod.POST)
	public String saveAnswer(
			@RequestParam("courseId")BigInteger courseId,
			@RequestParam("examId")BigInteger examId,
			@RequestParam("testPaperId")BigInteger testPaperId,
			RedirectAttributes redirectAttributes,
			HttpServletRequest request){
		Subject subject = SecurityUtils.getSubject();
		Answer answer = answerService.selectAnswerByExamIdAndStudentId(examId,
				userService.selectUserByAccount(subject.getPrincipal().toString()).getId());
		
		//插入AnswerQuestion
		List<Question> questionList = testPaperService.selectQuestionByTestPaperId(testPaperId);
		System.out.println(request.getParameter("1"));
		//更新答案提交时间
		answer.setEndTime(new Timestamp(Time.absoluteTime()));
		answerService.updateAnswer(answer);
		//获取答卷信息并评分
		for (Question question:questionList){
			AnswerQuestion answerQuestion = new AnswerQuestion();
			answerQuestion.setQuestionId(question.getId());
			answerQuestion.setAnswerId(answer.getId());
			answerQuestion.setAnswer(request.getParameter(question.getId().toString()));
			if (question.getKindName().equals("单选题")
					|| question.getKindName().equals("多选题")
					|| question.getKindName().equals("填空题")){
				if (answer.equals(question.getAnswer())){
					answerQuestion.setScore(question.getScore());
				}
			}
			answerService.addAnswerQuestion(answerQuestion);
		}
		redirectAttributes.addFlashAttribute("id",courseId);
		return "redirect:/st/course/examInfo";
	}
	
	/**
	 *@author  ShanDJ
	 *@params [kindName]
	 *@return  java.lang.String
	 *@date  18.5.27
	 *@description 开始考试 按题目类型名获取试卷对应的题目
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
	 *@description 查看考试 获取学生答卷信息 包括题型 题目
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
