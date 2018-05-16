/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.16
  Time: 11:50
*/

package justdj.top.controller;

import justdj.top.pojo.*;
import justdj.top.service.AnswerService;
import justdj.top.service.KindService;
import justdj.top.service.TestPaperService;
import justdj.top.util.KindHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.List;

@Controller
@RequestMapping("/exam")
public class ExamController {

	@Autowired
	@Qualifier("kindService")
	public KindService kindService;
	
	@Autowired
	@Qualifier("testPaperService")
	public TestPaperService testPaperService;

	@Autowired
	@Qualifier("answerService")
	public AnswerService answerService;
	
	
	@RequestMapping("/info")
	public String examInfo(@RequestParam("id")BigInteger examId, Model model){
		List<TestPaper> testPaperList = testPaperService.selectTestPaperByExamId(examId);
		List<Answer> answerList = answerService.selectAnswerByExamId(examId);
		
		
		model.addAttribute("testPaperList",testPaperList);
		model.addAttribute("answerList",answerList);
		return "examInfo";
	}
	
	@RequestMapping("/info/studentTestPaper")
	public String studentTestPaper(@RequestParam(value = "id",required = true)BigInteger answerId,Model model){
		
		KindHelper.setKindService(kindService);
		
		List<String> kindName = KindHelper.getKindNameList();
		List<AnswerQuestion> answerQuestionList = answerService.selectAnswerQuestionByAnswerId(answerId);
		
		model.addAttribute("kindName",kindName);
		model.addAttribute("answerQuestionList",answerQuestionList);
		
		return "studentTestPaper";
	}
	
}
