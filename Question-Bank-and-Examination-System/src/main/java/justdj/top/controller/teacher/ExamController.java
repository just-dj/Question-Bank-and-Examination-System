/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.28
  Time: 10:14
  Info:
*/

package justdj.top.controller.teacher;


import justdj.top.pojo.*;
import justdj.top.service.*;
import justdj.top.util.KindHelper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

		request.setAttribute("examList",examList);
		request.setAttribute("studentNum",studentNum);
		
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
	public void createExam(@RequestParam("courseId")BigInteger courseId,
	                       RedirectAttributes redirectAttributes,
	                       Model model){
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
		
		model.addAttribute(clazzList);
		
		model.addAttribute(testPaperList);
		
		model.addAttribute("testPaperInfo",questionCount);
	}
	
	/**
	 *@author  ShanDJ
	 *@params []
	 *@return  void
	 *@date  18.5.31
	 *@description 新建考试 待完善
	 */
	@RequestMapping(value = "/te/exam/new",method = RequestMethod.POST)
	public String insertExam(@RequestParam("courseId")BigInteger courseId,
			               @RequestParam("classList")BigInteger[] classList,
	                       @RequestParam("examName")String examName,
	                       @RequestParam("startTime")Timestamp startTime,
	                       @RequestParam("endTime")Timestamp endTime,
	                       @RequestParam("testPaperList")BigInteger[] testPaperList,
	                       RedirectAttributes redirectAttributes,
	                       Model model){
		Exam exam = new Exam();
		exam.setCourseId(courseId);
		exam.setName(examName);
		exam.setStartTime(startTime);
		exam.setEndTime(endTime);
		//班级或者试卷是否为空
		if (null == classList || classList.length == 0 || null == testPaperList || testPaperList.length == 0){
			redirectAttributes.addFlashAttribute("message","请完善班级或试卷信息！");
			return "redirect:/te/exam";
		}

		//是否操作成功
		try{
			examService.insertExamAllInfo(exam, Arrays.asList(classList),Arrays.asList(testPaperList));
		}catch (Exception e){
		
		}
		return "redirect:/te/exam";
	
	}
	
	
	/**
	 *@author  ShanDJ
	 *@params [examId, model]
	 *@return  void
	 *@date  18.5.31
	 *@description 查看试卷
	 */
	@RequestMapping(value = "/te/exam/info",method = RequestMethod.GET)
	public void examInfo(@RequestParam("id")BigInteger examId,
	                     Model model){
		Exam exam = examService.selectExamByExamId(examId);
		List<Answer> answerList = answerService.selectAnswerByExamId(examId);
		
		model.addAttribute(exam);
		model.addAttribute(answerList);
 	}
	
	
	/**
	 *@author  ShanDJ
	 *@params [answerId]
	 *@return  void
	 *@date  18.5.31
	 *@description 批阅试卷 学生答卷页面
	 */
 	@RequestMapping(value = "/te/exam/answer",method = RequestMethod.GET)
 	public void answerOfExam(@RequestParam("id")BigInteger answerId,
                              Model model){
		
 		Answer answer = answerService.selectAnswerByAnswerId(answerId);
 		List<AnswerQuestion> answerQuestionList =  answerService.selectAnswerQuestionByAnswerId(answerId);
 		
 		
 		model.addAttribute(answer);
	    model.addAttribute(answerQuestionList);
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
    public String setAnswerScore(@RequestParam("examId")BigInteger examId,
//    		@RequestParam("id")BigInteger answerQuestionId,
//           @RequestParam("score")Integer score,
           RedirectAttributes redirectAttributes,
           HttpServletRequest request,
           Model model){
	      User student = userService.selectUserByAccount(SecurityUtils.getSubject().getPrincipal().toString());
 		  Answer answer = answerService.selectAnswerByExamIdAndStudentId(examId,student.getId());
 		  List<AnswerQuestion> answerQuestionList = answerService.selectAnswerQuestionByAnswerId(answer.getId());
 		  for (AnswerQuestion a:answerQuestionList){
 		  	String newScore = request.getParameter(a.getId().toString());
 		  	if (null != newScore && !newScore.equals("")){
 		  		int result = examService.updateAnswerQuestionScore(a.getId(),Integer.parseInt(newScore));
		    }
	      }
	      redirectAttributes.addFlashAttribute("id",examId);
	      return "redirect:/te/exam/info";
    }
}
