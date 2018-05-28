/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.28
  Time: 10:14
  Info:
*/

package justdj.top.controller.teacher;

import com.alibaba.fastjson.JSON;
import justdj.top.pojo.Question;
import justdj.top.pojo.TestDatabase;
import justdj.top.service.KindService;
import justdj.top.service.TestDatabaseService;
import justdj.top.util.KindHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
public class TestDatabaseController {
	
	@Autowired
	@Qualifier("testDatabaseService")
	private TestDatabaseService testDatabaseService;
	
	@Autowired
	@Qualifier("kindService")
	private KindService kindService;
	
	
	/**
	 *@author  ShanDJ
	 *@params [courseId, model]
	 *@return  void
	 *@date  18.5.28
	 *@description 题库管理界面 要统计题库题目类型对应的题数
	 */
	@RequestMapping("/te/testDatabase")
	public void testDatabase(@RequestParam("id")BigInteger courseId,
	                         Model model){
		KindHelper.setKindService(kindService);
		List<TestDatabase> testDatabaseList = testDatabaseService.selectTestDatabaseByCourseId(courseId);
		List<List<Integer>> questionNumList = new LinkedList <>();
		
		if (null != testDatabaseList)
			for (int i = 0;i < testDatabaseList.size();++i) {
					List<Question> questionList = testDatabaseService.selectTestDatabaseQuestionByTDId(testDatabaseList
							.get(i).getId());
					if (null == questionNumList.get(i))
						questionNumList.set(i,new ArrayList <Integer>(KindHelper.getKindNameList().size()));
					getQuestionNumByKind(questionNumList.get(i),questionList,KindHelper.getKindNameList());
			}
		
		model.addAttribute("testDatabaseList",testDatabaseList);
		model.addAttribute("questionNumList",questionNumList);
		System.out.println(JSON.toJSONString(questionNumList));
	}



	
	private void getQuestionNumByKind(List<Integer> resultList,List<Question> questionList,List<String> kindNameList){
		if (null == questionList)
			return;
		for (int i = 0; i < questionList.size(); i++) {
			for(int j = 0;j <kindNameList.size(); j++){
				if (questionList.get(i).getKindName().equals(kindNameList.get(j)));
					resultList.set(j ,resultList.get(j)+1);
			}
			
		}
		
		
	}
	
	
	
}
