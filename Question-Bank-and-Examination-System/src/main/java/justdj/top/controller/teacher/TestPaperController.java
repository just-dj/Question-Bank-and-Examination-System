/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.28
  Time: 10:14
  Info:
*/

package justdj.top.controller.teacher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import justdj.top.pojo.Question;
import justdj.top.pojo.TestDatabase;
import justdj.top.pojo.TestPaper;
import justdj.top.service.KindService;
import justdj.top.service.TestDatabaseService;
import justdj.top.service.TestPaperService;
import justdj.top.util.KindHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigInteger;
import java.util.*;

@Controller
public class TestPaperController {
	
	@Autowired
	@Qualifier("kindService")
	private KindService kindService;
	
	@Autowired
	@Qualifier("testPaperService")
	private TestPaperService testPaperService;
	
	@Autowired
	@Qualifier("testDatabaseService")
	private TestDatabaseService testDatabaseService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(TestPaperController.class);
	/**
	 *@author  ShanDJ
	 *@params [courseId, model]
	 *@return  void
	 *@date  18.5.29
	 *@description 试卷管理 提供所有试卷队列  和相关的题目信息
	 */
	@RequestMapping(value = "/te/testPaper",method = RequestMethod.GET)
	public String getAllTestPaperByCourseId(@ModelAttribute(value = "id")BigInteger courseId,
	                                      Model model){
		KindHelper.setKindService(kindService);
		
		List<String> kindNameList = KindHelper.getKindNameList();
		List<TestPaper> testPaperList = testPaperService.selectTestPaperByCourseId(courseId);
		//获得题型对应的题目数量 和分值
		List<List<Integer>> testPaperInfo[] = new ArrayList[testPaperList.size()] ;
		for (int i = 0;i < testPaperList.size(); i++) {
			List<Question> questionList = testPaperService.selectQuestionByTestPaperId(testPaperList.get(i).getId());
			if (null == testPaperInfo[i])
				testPaperInfo[i] = new ArrayList <>();
			testPaperInfo[i].add(new ArrayList <>());
			testPaperInfo[i].add(new ArrayList <>());
			getQuestionNumByQuestionKind(questionList,kindNameList,testPaperInfo[i].get(0),testPaperInfo[i].get(1));
		}
		
		
		for (List list:testPaperInfo) {
			int allNum = 0;
			for (Integer num:((List<Integer>)list.get(0))){
				allNum += num;
			}
			((List<Integer>)list.get(0)).add(allNum);
			
			int soreNum = 0;
			for (Integer score:((List<Integer>)list.get(1))){
				soreNum += score;
			}
			((List<Integer>)list.get(1)).add(soreNum);
			
		}
		
		model.addAttribute("kindName",kindNameList);
		model.addAttribute("testPaperList",testPaperList);
		model.addAttribute("testPaperInfo",testPaperInfo);
		model.addAttribute("courseId",courseId);
		
		return "/te/testPaperManager";
		
	}
	
	
	@RequestMapping(value = "/te/testPaper/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deleteTestPaper(@RequestParam("testPaperId")BigInteger testPaperId,
	                              RedirectAttributes redirectAttributes){
		
		List<Question> questionList = testPaperService.selectQuestionByTestPaperId(testPaperId);
		
		for (Question question:questionList){
			testPaperService.deleteTestPaperQuestion(testPaperId,question.getId());
		}
		testPaperService.deleteTestPaper(testPaperId);
		
		
		return "成功删除";
	}
	
	/**
	 *@author  ShanDJ
	 *@params [testPaperName, model]
	 *@return  void
	 *@date  18.5.29
	 *@description 新建试卷 待完善
	 */
	
	@RequestMapping(value = "/te/testPaper/new",method = RequestMethod.POST)
	@ResponseBody
	public String createTestPaper(@RequestParam("courseId")BigInteger courseId,
			@RequestParam("name")String testPaperName,
	                            Model model){
		
	    int result = testPaperService.addTestPaper(courseId,testPaperName,false);
		
		return "创建成功！";
	}
	
	
	@RequestMapping(value = "/te/testPaper/question",method = RequestMethod.GET)
	public String viewTestPaper(@RequestParam("id")BigInteger testPaperId,
	                            Model model){
		
		KindHelper.setKindService(kindService);
		
		model.addAttribute("kinList",KindHelper.getKindNameList());
		model.addAttribute("testPaperId",testPaperId);
		return "/te/testPaper-viewQuestions";
	}
	
	/**
	 *@author  ShanDJ
	 *@params [testPaperId, kindName]
	 *@return  java.lang.String
	 *@date  18.5.29
	 *@description 预览试卷 根据试卷试题类型获取对应题目
	 */
	@RequestMapping(value = "/te/testPaper/question",method = RequestMethod.POST)
	@ResponseBody
	public String getTestPaperQuestionByKindName(@RequestParam(value = "id",required = true)BigInteger testPaperId,
	                                           @RequestParam(value = "kind",required = true)BigInteger kindId){
		
		List<Question> questionList = testPaperService.selectQuestionByTestPaperIdAndKindId(testPaperId,kindId);
		
		System.err.println(JSON.toJSONString(questionList));
		
		return JSON.toJSONString(questionList);
		
	}
	
	/**
	 *@author  ShanDJ
	 *@params [testPaperId, questionId, status, score, redirectAttributes]
	 *@return  java.lang.String
	 *@date  18.6.10
	 *@description 删除或增加试卷题目 返回结果是数据库处理影响条数 1 正确 0 失败
	 */
	@RequestMapping(value = "/te/testPaper/question/{status}",method = RequestMethod.POST)
	@ResponseBody
	public String managerQuestion(@RequestParam(value = "testPaperId")BigInteger testPaperId,
	                              @RequestParam("questionId")BigInteger questionId,
	                              @PathVariable("status")Integer status,
	                              @RequestParam("score")Integer score,
	                              RedirectAttributes redirectAttributes){
		Integer result = 0;
		if (1 == status){
			result = testPaperService.addQuestion(testPaperId,questionId,score);
		}else {
			result = testPaperService.deleteTestPaperQuestion(testPaperId,questionId);
		}
		
		return  result.toString();
	}
	
	/**
	 *@author  ShanDJ
	 *@params [testPaperId, model]
	 *@return  void
	 *@date  18.5.29
	 *@description 导入试题界面 复杂的逻辑任务没有给Dao层
	 * 单纯的获取现有的试卷已有的题目
	 * 还需要一个筛选题库题目的接口
	 */
	@RequestMapping(value = "/te/testPaper/import",method = RequestMethod.GET)
	public String importQuestion(@RequestParam("courseId")BigInteger courseId,
			@RequestParam(value = "testPaperId")BigInteger testPaperId,
	                           Model model){
		
		KindHelper.setKindService(kindService);
		List<String> kindNameList = KindHelper.getKindNameList();
		
		List<Question> questionList  = testPaperService.selectQuestionByTestPaperId(testPaperId);
		
		List<Integer> resultList = new ArrayList <>();
		//调用函数计数
		TestDatabaseController.getQuestionNumByKind(resultList,questionList,kindNameList);
		
		List<TestDatabase> testDatabaseList = testDatabaseService.selectTestDatabaseByCourseId(courseId);
		
		model.addAttribute("kindNameList",kindNameList);
		model.addAttribute("questionList",questionList);
		model.addAttribute("quesNum",resultList);
		model.addAttribute("testDatabaseList",testDatabaseList);
		model.addAttribute("testPaperId",testPaperId);
		model.addAttribute("courseId",courseId);
		
		return "/te/testPaper-importQuestions";
	}
	
	
	
	
	
	/**
	 *@author  ShanDJ
	 *@params [testDatabaseId, kindName, keyWord]
	 *@return  void
	 *@date  18.5.29
	 *@description 导入试题 返回题库 题型 关键字对应的 题目
	 */
	@RequestMapping(value = "/te/testDatabase/search",method = RequestMethod.POST)
	@ResponseBody
	public String  getTestDatabaseQuestionByKey(
			@RequestParam("courseId")BigInteger courseId,
			@RequestParam(value = "id",required = true)BigInteger testDatabaseId,
             @RequestParam(value = "kind",required = true)String kindName,
             @RequestParam(value = "key",required = false)String keyWord){
		KindHelper.setKindService(kindService);
		System.out.println(testDatabaseId + kindName + keyWord);

		List<Question> questionList = testDatabaseService.selectQuestionByCondition(courseId,testDatabaseId,KindHelper
				.getKindId(kindName),keyWord);
		
		System.out.println(JSON.toJSONString(questionList));
		
		return JSON.toJSONString(questionList);
	}
	
	

	
	
	/** 还未测试 逻辑复杂有隐患
	 *@author  ShanDJ
	 *@params [testPaperId, questionId,model]
	 *@return  void
	 *@date  18.5.29
	 *@description 导入试题界面提交 更新当前试卷所有的题目
	 * 比较粗暴的是直接删除当前所有的联系 然后重新创建
	 * questionId是一个字符串，题目Id之间用空格隔开
	 */
	@RequestMapping(value = "/te/testPaper/import",method = RequestMethod.POST)
	@ResponseBody
	public String updateTestPaperQuestion(@RequestParam("paperId")BigInteger testPaperId ,
	                                      @RequestParam("questionId")String questionId,
	                                      @RequestParam(value = "value",required = false)String score,
	                                      @RequestParam("operation")Integer operation,
	                                      @RequestParam(value = "kindId",required = false)BigInteger kindId,
	                                      Model model){
		Map<String,String> result = new HashMap <>();
		String[] questionIdArray = questionId.split(" ");
		String[] scoreArray = null;
		
		logger.info("操作试卷");
		
		if (1 == operation){
			scoreArray = score.split(" ");
			
			System.err.println(Arrays.asList(questionIdArray));
			System.err.println(Arrays.asList(scoreArray));
			
			//添加
			int sum = 0;
			for (int i = 0;i < questionIdArray.length;++i){
				try{
					testPaperService.addQuestion(testPaperId,new BigInteger(questionIdArray[i]),
							Integer.parseInt(scoreArray[i]));
					sum ++;
				}catch (RuntimeException e){
					//题目重复了
					logger.info("重复题型");
				}
			}
			result.put("message","共添加成功" + sum +"题。"+ "<br>" + " 失败 "+(questionIdArray.length-sum) +"题（已在试卷中）");
			return JSON.toJSONString(result);
		}else  if (0 == operation){
			for (int i = 0;i < questionIdArray.length;++i){
				testPaperService.deleteTestPaperQuestion(testPaperId,new BigInteger(questionIdArray[i]));
			}
			List<Question> questionList = testPaperService.selectQuestionByTestPaperIdAndKindId(testPaperId,kindId);
			return JSON.toJSONString(questionList);
		}
		
		
		return "数据错误";
	}
	

	
	@RequestMapping(value = "/te/testPaper/info",method = RequestMethod.POST)
	@ResponseBody
	public String getInfo(@RequestParam("id")BigInteger testPaperId){
		
		List<Question> questionList = testPaperService.selectQuestionByTestPaperId(testPaperId);
		
		KindHelper.setKindService(kindService);
		List<Integer> resultList = new ArrayList <>();
		List<Integer> sumList = new ArrayList <>();
		
		getQuestionNumByQuestionKind(questionList,KindHelper.getKindNameList(),resultList,sumList);
		
		return JSON.toJSONString(resultList);
	}
	
	
	
	/**
	 *@author  ShanDJ
	 *@params [questionList, kindNameList, resultList, sumList]
	 *@return  void
	 *@date  18.5.29
	 *@description 计算试卷中题型对应的题目数量和分值
	 *
	 */
	public static void getQuestionNumByQuestionKind(List<Question> questionList,
	                                          List<String> kindNameList,
	                                          List<Integer> resultList,
	                                          List<Integer> sumList){
		if (null == questionList)
			return;
		for (String a:kindNameList) {
			resultList.add(0);
			sumList.add(0);
		}
		for (int i = 0; i < questionList.size(); i++) {
			for (int j = 0; j < kindNameList.size(); j++){
				if (questionList.get(i).getKindName().equals(kindNameList.get(j))){
					resultList.set(j,resultList.get(j) + 1);
					sumList.set(j,sumList.get(j) + questionList.get(i).getScore());
					break;
				}
			}
		}
		
		System.out.println(JSON.toJSONString(resultList) + "    " + JSON.toJSONString(sumList));
	}
}
