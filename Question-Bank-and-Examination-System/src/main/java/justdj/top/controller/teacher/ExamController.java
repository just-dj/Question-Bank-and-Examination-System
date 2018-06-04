/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.28
  Time: 10:14
  Info:
*/

package justdj.top.controller.teacher;


import justdj.top.pojo.Answer;
import justdj.top.pojo.AnswerQuestion;
import justdj.top.pojo.Exam;
import justdj.top.pojo.User;
import justdj.top.service.AnswerService;
import justdj.top.service.CourseService;
import justdj.top.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
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
	
	/**
	 *@author  ShanDJ
	 *@params [courseId, model]
	 *@return  void
	 *@date  18.5.31
	 *@description 考试管理
	 */
	@RequestMapping(value = "/te/exam",method = RequestMethod.GET)
	public void examManager(@RequestParam("id")BigInteger courseId,
	                        Model model){
		
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
	}
	
	/**
	 *@author  ShanDJ
	 *@params []
	 *@return  void
	 *@date  18.5.31
	 *@description 新建考试界面 待完善
	 */
	@RequestMapping(value = "/te/exam/new",method = RequestMethod.GET)
	public void createExam(){
	
	}
	
	/**
	 *@author  ShanDJ
	 *@params []
	 *@return  void
	 *@date  18.5.31
	 *@description 新建考试 待完善
	 */
	@RequestMapping(value = "/te/exam/new",method = RequestMethod.POST)
	public void insertExam(@RequestParam("courseId")BigInteger courseId,
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
		//是否操作成功
		try{
			examService.insertExamAllInfo(exam, Arrays.asList(classList),Arrays.asList(testPaperList));
		}catch (Exception e){
		
		}
		
	
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
	 */
    @RequestMapping(value = "/te/exam/answer",method = RequestMethod.POST)
    public void setAnswerScore(@RequestParam("id")BigInteger answerQuestionId,
                               @RequestParam("score")Double score,
                               Model model){
 		  
    }
}
