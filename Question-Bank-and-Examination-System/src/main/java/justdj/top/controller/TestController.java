/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.13
  Time: 16:57
*/

package justdj.top.controller;

import com.alibaba.fastjson.JSON;
import justdj.top.pojo.Course;
import justdj.top.pojo.Question;
import justdj.top.service.TestPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.util.List;

@Controller
public class TestController {
	
	@Autowired
	@Qualifier("testPaperService")
	TestPaperService testPaperService;
	
	
	@RequestMapping("/test")
	@ResponseBody
	public String test(){
		List<Question> list = testPaperService.selectQuestionByTestPaperIdAndKindId(BigInteger.valueOf(1),BigInteger
				.valueOf(1));
		return JSON.toJSONString(list);
	}
}
