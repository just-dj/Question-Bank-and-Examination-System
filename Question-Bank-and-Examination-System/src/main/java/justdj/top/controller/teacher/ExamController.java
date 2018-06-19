/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.28
  Time: 10:14
  Info:
*/

package justdj.top.controller.teacher;


import com.alibaba.fastjson.JSON;
import justdj.top.pojo.*;
import justdj.top.service.*;
import justdj.top.util.KindHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class ExamController {
	
	@Autowired
	@Qualifier("examService")
	private ExamService examService;
	
	@Autowired
	@Qualifier("courseService")
	private CourseService courseService;
	
	@Autowired
	@Qualifier("answerService")
	private AnswerService answerService;
	
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("testPaperService")
	private TestPaperService testPaperService;
	
	
	@Autowired
	@Qualifier("kindService")
	private KindService kindService;
	
	private static Logger logger = LoggerFactory.getLogger(ExamController.class);
	
	/**
	 *@author  ShanDJ
	 *@params [courseId, model]
	 *@return  void
	 *@date  18.5.31
	 *@description 考试管理
	 */
	@RequestMapping(value = "/te/exam",method = RequestMethod.GET)
	public String examManager(@RequestParam("id")BigInteger courseId,
	                          Model model,
	                          HttpServletRequest request){
		
		List<Exam> examList = examService.selectExamByCourseId(courseId);
		List<Integer> studentNum = new ArrayList <>();
		if (null != examList){
			for (int i = 0; i < examList.size(); i++) {
				if (null !=examList.get(i).getClassList()){
					Integer result = 0;
					for (int j = 0; j < examList.get(i).getClassList().size(); j++) {
						BigInteger classId = examList.get(i).getClassList().get(j);
						List<User> studentList= courseService.selectStudentByClassId(classId);
						result += studentList.size();
					}
					studentNum.add(result);
				}
				
			}
		}

		model.addAttribute("examList",examList);
		model.addAttribute("studentNum",studentNum);
		model.addAttribute("courseId",courseId);
		
		return "/te/examManager";
	}
	
	/**
	 *@author  ShanDJ
	 *@params []
	 *@return  void
	 *@date  18.5.31
	 *@description 新建考试界面 待完善
	 */
	@RequestMapping(value = "/te/exam/new",method = RequestMethod.GET)
	public String createExam(@RequestParam("courseId")BigInteger courseId,
	                       RedirectAttributes redirectAttributes,
	                       Model model){
		Subject subject = SecurityUtils.getSubject();
		
		List<TestPaper> testPaperList = testPaperService.selectTestPaperByCourseId(courseId);
		
		List<Clazz> clazzList = courseService.selectClazzByCourseId(courseId);
		
		KindHelper.setKindService(kindService);
		
		List<String> kindNameList = KindHelper.getKindNameList();
		//获得题型对应的题目数量 和分值
		List<List<Integer>> testPaperInfo[] = new ArrayList[testPaperList.size()] ;
		for (int i = 0;i < testPaperList.size(); i++) {
			List<Question> questionList = testPaperService.selectQuestionByTestPaperId(testPaperList.get(i).getId());
			if (null == testPaperInfo[i])
				testPaperInfo[i] = new ArrayList <>();
			testPaperInfo[i].add(new ArrayList <>());
			testPaperInfo[i].add(new ArrayList <>());
			TestPaperController.getQuestionNumByQuestionKind(questionList,kindNameList,testPaperInfo[i].get(0),
					testPaperInfo[i].get(1));
		}
		
		List<List<Integer>> questionCount = new ArrayList <>();
		for (List<List<Integer>> info:testPaperInfo) {
			questionCount.add(info.get(0));
		}
		
		logger.info("教师 " + subject.getPrincipal() + " 访问 课程 " + courseId +" 新建考试界面");
		
		model.addAttribute("courseId",courseId);
		
		model.addAttribute("classList",clazzList);
		
		model.addAttribute("testPaperList",testPaperList);
		
		model.addAttribute("testPaperInfo",questionCount);
		
		return "/te/exam-addExam";
	}
	
	/**
	 *@author  ShanDJ
	 *@params []
	 *@return  void
	 *@date  18.5.31
	 *@description 新建考试 待完善
	 */
	@RequestMapping(value = "/te/exam/new",method = RequestMethod.POST)
	@ResponseBody
	public String insertExam(@RequestParam("courseId")BigInteger courseId,
			               @RequestParam("classList")BigInteger[] classList,
	                       @RequestParam("examName")String examName,
	                       @RequestParam("startTime")Timestamp startTime,
	                       @RequestParam("endTime")Timestamp endTime,
	                       @RequestParam("testPaperList")BigInteger[] testPaperList,
	                       RedirectAttributes redirectAttributes,
	                       Model model){
		Map<String,String> resultMap = new HashMap <>();
		Exam exam = new Exam();
		exam.setCourseId(courseId);
		exam.setName(examName);
		exam.setStartTime(startTime);
		exam.setEndTime(endTime);
		exam.setUse(true);
		
		//班级或者试卷是否为空
		if (null == classList || classList.length == 0 || null == testPaperList || testPaperList.length == 0){
			resultMap.put("message","请完善班级或试卷信息！");
			return JSON.toJSONString(resultMap);
		}
		//是否操作成功
		try{
			examService.insertExamAllInfo(exam, Arrays.asList(classList),Arrays.asList(testPaperList));
			resultMap.put("message","新建考试成功！");
		}catch (Exception e){
			logger.warn("教师" + AccountService.getAccount() + "新建考试失败" + e.getMessage());
			resultMap.put("message","新建考试失败，请稍后重试！");
		}
		
		return JSON.toJSONString(resultMap);
	}
	
	
	/**
	 *@author  ShanDJ
	 *@params [examId, model]
	 *@return  void
	 *@date  18.5.31
	 *@description 查看试卷
	 */
	@RequestMapping(value = "/te/exam/info",method = RequestMethod.GET)
	public String examInfo(@ModelAttribute("id")BigInteger examId,
	                     Model model){
		Subject subject  = SecurityUtils.getSubject();
		
		logger.info("教师" + subject.getPrincipal().toString() +"查看考试" + examId);
		
		Exam exam = examService.selectExamByExamId(examId);
		
		List<Answer> answerList = new ArrayList <>();
		
		if (exam.getClazzList() == null){
			exam.setClazzList(new ArrayList <>());
		}

		
		for (BigInteger id:exam.getClassList()) {
			exam.getClazzList().add(courseService.selectClass(id));
		}
		
		List<User> userList = new ArrayList <>();
		for (Clazz clazz:exam.getClazzList()){
			List<User> students = courseService.selectStudentByClassId(clazz.getId());
			if (students != null){
				userList.addAll(students);
			}
		}
		
		List<Answer> submitAnswer = answerService.selectAnswerByExamId(examId);
		
		logger.info("获取所有answer(已提交和未提交)");
		for (User student:userList){
			Answer answer = answerService.selectAnswerByExamIdAndStudentId(examId,student.getId());
			if (answer == null){
				Answer temp  = new Answer();
				temp.setResult((short)0);
				temp.setStudent(student);
				temp.setCommit(false);
				answerList.add(temp);
			}else {
				if (answer.getResult() == null )
					answer.setResult((short)0);
				answerList.add(answer);
			}
		}
		
		logger.info("处理分值统计数据");
		
		List<Integer> scoreList  = new ArrayList <>();
		List<String> nameList = new ArrayList <>();
		for (Answer answer : submitAnswer){
			nameList.add("'"+answer.getStudent().getName()+"'");
			System.err.println("'"+answer.getStudent().getName()+"'");
			if (answer.getResult() == null){
				scoreList.add((int)0);
				answer.setResult((short)0);
				answerService.updateAnswer(answer);
			}else {
				scoreList.add((int)answer.getResult());
			}
		}
		
		logger.info("放置数据");
		
		model.addAttribute(exam);
		model.addAttribute(answerList);
		model.addAttribute("examId",examId);
		model.addAttribute("submitAnswer",submitAnswer);
		model.addAttribute("scoreList",scoreList);
		model.addAttribute("nameList",nameList);
		
		logger.info("返回！");
		
		return "/te/exam-viewTest";
		
 	}
	
	
	/**
	 *@author  ShanDJ
	 *@params [answerId]
	 *@return  void
	 *@date  18.5.31
	 *@description 批阅试卷 学生答卷页面
	 */
 	@RequestMapping(value = "/te/exam/answer",method = RequestMethod.GET)
 	public String answerOfExam(@RequestParam("id")BigInteger answerId,
                              Model model){
		
 		Answer answer = answerService.selectAnswerByAnswerId(answerId);
 		List<AnswerQuestion> answerQuestionList =  answerService.selectAnswerQuestionByAnswerId(answerId);
 		
 		
 		model.addAttribute(answer);
	    model.addAttribute(answerQuestionList);
	    
	    return "/te/viewStudentTestPaper";
    }
	
    
    @RequestMapping(value = "/te/exam/getAnswer",method = RequestMethod.POST)
    @ResponseBody
    public  String getAnswerByAnswerIdAndKindName(@RequestParam("answerId")BigInteger answerId,
                                                  @RequestParam("kind")String kindName){
//	    Answer answer = answerService.selectAnswerByAnswerId(answerId);
	    List<AnswerQuestion> answerQuestionList =  answerService.selectAnswerQuestionByAnswerId(answerId);
	    
	    List<AnswerQuestion> result = new ArrayList <>();
	    for (AnswerQuestion answerQuestion:answerQuestionList){
	    	if (answerQuestion.getKindName().equals(kindName)){
			    result.add(answerQuestion);
		    }
	    	
	    }
 		return JSON.toJSONString(result);
    }
    
    
    
	/**
	 *@author  ShanDJ
	 *@params [answerQuestionId, score, model]
	 *@return  void
	 *@date  18.5.31
	 *@description 批改试卷 修改学生对卷对应的题目的分值 待完善
	 *  现在不确定是单道提交还是全部提交
	 *  先假设全部提交吧
	 *
	 */
    @RequestMapping(value = "/te/exam/answer",method = RequestMethod.POST)
    @ResponseBody
    public String setAnswerScore(@RequestParam("id")String answerQuestionId,
                                 @RequestParam ("score")String score,
                                    @RequestParam("answerId")BigInteger answerId,
                                    Model model){
    	Map<String,String> map =new HashMap <>();
    	map.put("message","");
	    
    	map.put("sum",0+"");
//	      User student = userService.selectUserByAccount(SecurityUtils.getSubject().getPrincipal().toString());
 		 Answer answer = answerService.selectAnswerByAnswerId(answerId);
	
	    String[] temp1 = answerQuestionId.split(" ");
	    String[] temp2 = score.split(" ");
	    
	    int result;
	    
		try{
			List<AnswerQuestion> answerQuestionList = answerService.selectAnswerQuestionByAnswerId(answerId);
			int sum = 0;
			for (AnswerQuestion answerQuestion:answerQuestionList){
				if (answerQuestion == null || answerQuestion.getScore() ==null){
					sum += 0;
				}else {
					sum += answerQuestion.getScore();
				}
			}
			answer.setResult((short)sum);
			
			for (int i = 0; i < temp1.length; i++) {
				result = examService.updateAnswerQuestionScore(new BigInteger(temp1[i]),new Short(temp2[i]));
			}
		}catch (RuntimeException e){
			e.printStackTrace();
			map.put("message","分数修改失败!");
			map.put("sum",answer.getResult()+"");
			return JSON.toJSONString(map);
		}
	 
		List<AnswerQuestion> answerQuestionList = answerService.selectAnswerQuestionByAnswerId(answerId);
		int sum = 0;
		for (AnswerQuestion answerQuestion:answerQuestionList){
			if (answerQuestion == null || answerQuestion.getScore() == null){
				sum += 0;
			}else {
				sum += answerQuestion.getScore();
			}
			
		}
		answer.setResult((short)sum);
	 
		try{
			answerService.updateAnswer(answer);
		}catch (RuntimeException e){
			e.printStackTrace();
		}
		map.put("message","分数修改成功！");
		map.put("sum",answer.getResult()+"");
	    return JSON.toJSONString(map);
    }
}

