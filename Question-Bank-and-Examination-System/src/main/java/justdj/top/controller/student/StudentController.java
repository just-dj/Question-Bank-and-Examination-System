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
import justdj.top.util.KindHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
	
	
	@Autowired
	@Qualifier("kindService")
	private KindService kindService;
	
	private static Logger logger = LoggerFactory.getLogger(StudentController.class);
	
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
	public String selectCourseInfoByCourseId(@RequestParam(value = "id",required = true) BigInteger courseId,
																		Model model){
		List<Knowledge> knowledgeList = courseService.selectKnowledgeByCourseId(courseId);
		List<Exam> examList = examService.selectExamByCourseId(courseId);
		
		model.addAttribute("knowledgeList",knowledgeList);
		model.addAttribute("examList",examList);
		model.addAttribute("courseId",courseId);
		
		return "/st/studentCourse";
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
	public String selectExamByCourseId(@ModelAttribute("id")BigInteger courseId,
	                                 Model model){
		Subject subject = SecurityUtils.getSubject();
		User user = userService.selectUserByAccount(subject.getPrincipal().toString());
		
		//这里获得学生对应的考试信息
		List<Exam> examList = examService.selectStudentExamByCourseId(courseId);
		List<Exam> newExamList = new ArrayList <>();
		
		BigInteger classId = userService.selectClassByStudentIdAndCourseId(user.getId(),courseId);
		
		if (null != examList){
			for (int i = 0; i < examList.size(); i++) {
				if (null != examList.get(i).getClassList()){
					if (examList.get(i).getClassList().contains(classId)){
						newExamList.add(examList.get(i));
					}
				}
			}
		}
		
		List<Answer> answerList = new ArrayList <>();
		for (Exam exam:newExamList){
			answerList.add(answerService.selectAnswerByExamIdAndStudentId(exam.getId(),user.getId()));
		}
		
		System.err.println(JSON.toJSONString(answerList));
		
		model.addAttribute("examList",newExamList);
		model.addAttribute("answerList",answerList);
		model.addAttribute("courseId",courseId);
		
		return "/st/studentExams";
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
	                      @RequestParam("courseId")BigInteger courseId,
	                      Model model){
		Subject subject = SecurityUtils.getSubject();
		Exam exam = examService.selectExamByExamId(examId);
		
		//获得考试使用的所有试卷
		List<TestPaper> testPaperList =  testPaperService.selectTestPaperByExamId(examId);
		
		Answer answer = answerService.selectAnswerByExamIdAndStudentId(examId,
				userService.selectUserByAccount(subject.getPrincipal().toString()).getId());
		BigInteger randomTestPaper  = BigInteger.valueOf(1);
		
		if (null == answer){
			logger.info("第一次访问考试界面！插入Answer");
			//多张试卷并且是第一次访问的时候进行一下随机算法，,这里应该随机获取试卷
			int random = new Random().nextInt(testPaperList.size());
			randomTestPaper = testPaperList.get(random).getId();
			//将Answer插入数据库
			Answer newAnswer = new Answer();
			newAnswer.setTestPaperId(randomTestPaper);
			newAnswer.setExamId(examId);
			newAnswer.setStartTime(new Timestamp(Time.absoluteTime()));
			newAnswer.setCommit(false);
			newAnswer.setStudentId(userService.selectUserByAccount(subject.getPrincipal().toString()).getId());
			answerService.addAnswer(newAnswer);
		}else{
			logger.info("非第一次访问考试界面！获取上次信息");
			randomTestPaper = answer.getTestPaperId();
		}
		
		

		//获取到试卷的所有题型
		List<Kind> kindList = testPaperService.selectQuestionKindByTestPaperId(randomTestPaper);
		//获取到试卷对应的全部的问题
//		List<Question> questionList = testPaperService.selectQuestionByTestPaperId(randomTestPaper);
		List<Question> questionList = new ArrayList <>();
		//根据题型获取试卷问题
//		List<Question> questionList1 =
//				testPaperService.selectQuestionByTestPaperIdAndKindId(testPaperList.get(randomNum).getId(),);
		
		for (TestPaper testPaper:testPaperList){
			if (testPaper.getId().equals(randomTestPaper)){
				model.addAttribute("testPaper",testPaper);
				break;
			}
		}
		
		List<String> kindName = new LinkedList <>();
		for (Kind a:kindList)
			kindName.add(a.getName());
		
		
		if (kindName.contains("单选题")){
			List<Question> single = testPaperService.selectQuestionByTestPaperIdAndKindName(randomTestPaper,
					"单选题");
			model.addAttribute("single",single);
			if (null != single && single.size() != 0){
				questionList.addAll(single);
			}
		}
		if (kindName.contains("多选题")){
			List<Question> muti = testPaperService.selectQuestionByTestPaperIdAndKindName(randomTestPaper,
					"多选题");
			model.addAttribute("muti",muti);
			if (null != muti){
				questionList.addAll(muti);
			}
		}
		if (kindName.contains("判断题")){
			List<Question> judge = testPaperService.selectQuestionByTestPaperIdAndKindName(randomTestPaper,
					"判断题");
			model.addAttribute("judge",judge);
			if (null != judge){
				questionList.addAll(judge);
			}
		}
		if (kindName.contains("填空题")){
			List<Question> empty = testPaperService.selectQuestionByTestPaperIdAndKindName(randomTestPaper,
					"填空题");
			model.addAttribute("emp",empty);
			if (null != empty){
				questionList.addAll(empty);
			}
		}
		
		if (kindName.contains("简答题")){
			List<Question> question  = testPaperService.selectQuestionByTestPaperIdAndKindName(randomTestPaper,
					"简答题");
			model.addAttribute("question",question);
			if (null != question){
				questionList.addAll(question);
			}
		}
		
		Long time = (exam.getEndTime().getTime() - Time.absoluteTime())/1000;
		
		for (Question question:questionList){
			question.setAnswer(null);
		}
		
		//所有Kind信息
		model.addAttribute("kindList",kindList);
		//所有问题
		model.addAttribute("questionList",JSON.toJSONString(questionList));
		//题型队列
		model.addAttribute("kindName",kindName);
		
		model.addAttribute("time",time);
		
		model.addAttribute("examId",examId);
		model.addAttribute("courseId",courseId);
		System.err.println(JSON.toJSONString(questionList));
		return "/st/startExam";
	}
	
	/**
	 *@author  ShanDJ
	 *@params [request]
	 *@return  java.lang.String
	 *@date  18.5.27
	 *@description 开始考试提交 这是个学生提交答案的接口 具体逻辑还没实现
	 */
	@RequestMapping(value = "/st/course/exam",method = RequestMethod.POST)
	@ResponseBody
	public String saveAnswer(
			@RequestParam("courseId")BigInteger courseId,
			@RequestParam("examId")BigInteger examId,
			RedirectAttributes redirectAttributes,
			HttpServletRequest request){
		Subject subject = SecurityUtils.getSubject();
		Answer answer = answerService.selectAnswerByExamIdAndStudentId(examId,
				userService.selectUserByAccount(subject.getPrincipal().toString()).getId());
		//插入AnswerQuestion
		
		List<Question> questionList = testPaperService.selectQuestionByTestPaperId(answer.getTestPaperId());
		
		answer.setEndTime(new Timestamp(Time.absoluteTime()));
		answer.setCommit(true);
		
		System.out.println(request.getParameter("1"));
		//更新答案提交时间
		answer.setEndTime(new Timestamp(Time.absoluteTime()));
		answerService.updateAnswer(answer);
		//获取答卷信息并评分
		for (Question question:questionList){
			AnswerQuestion answerQuestion = new AnswerQuestion();
			answerQuestion.setQuestionId(question.getId());
			answerQuestion.setAnswerId(answer.getId());
			if (question.getKindName().equals("多选题")){
				String[] arr = request.getParameterValues(question.getId().toString());
				if (arr != null){
					String temp = "";
					for (String str:arr){
						temp += str+" ";
					}
					answerQuestion.setAnswer(temp);
				}
			}else{
				answerQuestion.setAnswer(request.getParameter(question.getId().toString()));
			}
			
			if (question.getKindName().equals("单选题")
					|| question.getKindName().equals("多选题")
					|| question.getKindName().equals("判断题")){
				if (answerQuestion.getAnswer().trim().equals(question.getAnswer().trim())){
					answerQuestion.setScore(question.getScore());
				}
			}
			try{
				answerService.addAnswerQuestion(answerQuestion);
			}catch (RuntimeException e){
				e.printStackTrace();
				logger.warn(subject.getPrincipal().toString() + "保存答案出现异常！" +e.getMessage());
			}
			
		}

//		更新一下answer的分值
		
		List<AnswerQuestion> answerQuestionList = answerService.selectAnswerQuestionByAnswerId(answer.getId());
		int sum = 0;
		for (AnswerQuestion answerQuestion:answerQuestionList){
			if (answerQuestion == null || answerQuestion.getScore()== null){
				sum += 0;
			}else{
				sum += answerQuestion.getScore();
			}
		}
		answer.setResult((short)sum);
		
		try{
			answerService.updateAnswer(answer);
		}catch (RuntimeException e){
			e.printStackTrace();
		}
		

		return "数据保存成功！";
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
	public String selectAnswerByExamIdAndStudentId(@RequestParam("id")BigInteger examId,
	                                             @RequestParam(value = "courseId",required = false)BigInteger courseId,
	                                             Model model,
	                                               RedirectAttributes redirectAttributes){
		KindHelper.setKindService(kindService);
//		Subject subject = SecurityUtils.getSubject();
//		subject.getPrincipal();

		redirectAttributes.addFlashAttribute("message","");
		
		List<String> kindList = KindHelper.getKindNameList();
		String[] attrName = new String[]{"singleList","multipleList","judgeList","fillInList","essayList"};

		List<AnswerQuestion>[] arr = new ArrayList[5];
		Integer[] sum = new Integer[]{0,0,0,0,0};
		
		Subject subject = SecurityUtils.getSubject();
		User user = userService.selectUserByAccount(subject.getPrincipal().toString());
		Answer answer = answerService.selectAnswerByExamIdAndStudentId(examId,user.getId());
		if (answer == null){
			redirectAttributes.addFlashAttribute("message","你没有参加这场考试！");
			redirectAttributes.addFlashAttribute("id",courseId);
			return "redirect:/st/course/examInfo";
		}
		for (int i = 0; i < kindList.size(); i++) {
		
			arr[i] = answerService.selectAnswerQuestion(answer.getId(),KindHelper.getKindId(kindList.get(i)));
			if ( null == arr[i]){
				System.err.println(kindList.get(i) +" 数据为空");
			}
			model.addAttribute(attrName[i],arr[i]);
			
			for (AnswerQuestion answerQuestion:arr[i]) {
				sum[i] += answerQuestion.getQuestionScore();
			}
		}
//		这里从内部获取学生ID
		
		System.err.println(JSON.toJSONString(answer));
		System.err.println(JSON.toJSONString(kindList));
		
		
		model.addAttribute("answer",answer);
		model.addAttribute("kindList",kindList);
		model.addAttribute("sum",sum);
		
		return "/st/viewExam";
	}
	
	

	@RequestMapping("/te/course/knowledge")
	public String getKnowledge(@RequestParam("id")BigInteger knowledgeId,
	                           Model model){
		
		Knowledge knowledge = courseService.selectKnowledge(knowledgeId);
		
		model.addAttribute("knowledge",knowledge);
		
		return "/st/knowledgeDetails";
		
	}
}
