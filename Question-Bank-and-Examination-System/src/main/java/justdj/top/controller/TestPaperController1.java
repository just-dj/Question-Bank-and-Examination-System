/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.14
  Time: 11:50
*/

package justdj.top.controller;

import justdj.top.pojo.Question;
import justdj.top.service.KindService;
import justdj.top.service.TestPaperService;
import justdj.top.util.KindHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.List;

@Controller
@RequestMapping("/testPaper")
public class TestPaperController1 {
	
	@Autowired
	@Qualifier("kindService")
	private KindService kindService;
	
	@Autowired
	@Qualifier("testPaperService")
	private TestPaperService testPaperService;
	
	@RequestMapping("/question")
	private String getTestPaperQuestion(@RequestParam("id")BigInteger testPaperId,Model model){
		KindHelper.setKindService(kindService);
		List<String> kindName = KindHelper.getKindNameList();

//		for (String a:kindName){
//			List<Question> list = testDatabaseService.selectTestDatabaseQuestionByKindName(testDatabaseId,a);
//			model.addAttribute(a,list);
//		}
		
		model.addAttribute("kindName",kindName);
		
		List<Question> list = testPaperService.selectQuestionByTestPaperId(testPaperId);
		model.addAttribute("question",list);
		return "testPaperQuestion";
	}
	
	
}
