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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
	public void getAllTestPaperByCourseId(@RequestParam(value = "id",required = true)BigInteger courseId,
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
		
		model.addAttribute("testPaperList",testPaperList);
		model.addAttribute("testPaperInfo",testPaperInfo);
		
		
	}
	
	/**
	 *@author  ShanDJ
	 *@params [testPaperName, model]
	 *@return  void
	 *@date  18.5.29
	 *@description 新建试卷 待完善
	 */
	
	@RequestMapping(value = "/te/testPaper/new",method = RequestMethod.POST)
	public String createTestPaper(@RequestParam("courseId")BigInteger courseId,
			@RequestParam("name")String testPaperName,
	                            Model model){
	    int result = testPaperService.addTestPaper(courseId,testPaperName,true);
		
		return "redirect:/te/testPaper";
	}
	
	
	/**
	 *@author  ShanDJ
	 *@params [testPaperId, kindName]
	 *@return  java.lang.String
	 *@date  18.5.29
	 *@description 预览试卷 根据试卷试题类型获取对应题目
	 */
	@RequestMapping(value = "/te/testPaper/question",method = RequestMethod.GET)
	public String getTestPaperQuestionByKindName(@RequestParam(value = "id",required = true)BigInteger testPaperId,
	                                           @RequestParam(value = "kind",required = true)String kindName){
		List<Question> questionList = testPaperService.selectQuestionByTestPaperIdAndKindName(testPaperId,kindName);
		
		
		return JSON.toJSONString(questionList);
		
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
	public void importQuestion(@RequestParam("courseId")BigInteger courseId,
			@RequestParam(value = "testPaperId")BigInteger testPaperId,
	                           Model model){
		
		KindHelper.setKindService(kindService);
		List<String> kindNameList = KindHelper.getKindNameList();
		
		List<Question> questionList  = testPaperService.selectQuestionByTestPaperId(testPaperId);
		
		List<Integer> resultList = new ArrayList <>();
		for (String a : kindNameList) {
			resultList.add(0);
		}
		//调用函数计数
		TestDatabaseController.getQuestionNumByKind(resultList,questionList,kindNameList);
		
		List<TestDatabase> testDatabaseList = testDatabaseService.selectTestDatabaseByCourseId(courseId);
		
		model.addAttribute(kindNameList);
		model.addAttribute(questionList);
		model.addAttribute(resultList);
		model.addAttribute(testDatabaseList);
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
	public String  getTestDatabaseQuestionByKey(@RequestParam(value = "id",required = true)BigInteger testDatabaseId,
	                                         @RequestParam(value = "kind",required = true)String kindName,
	                                         @RequestParam(value = "key",required = false)String keyWord){
		KindHelper.setKindService(kindService);
		List<Question> questionList = testDatabaseService.selectQuestionByCondition(testDatabaseId,KindHelper.getKindId(kindName),keyWord);
		
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
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class})
	@RequestMapping(value = "/te/testPaper/import",method = RequestMethod.POST)
	public String updateTestPaperQuestion(@RequestParam("id")BigInteger testPaperId ,
	                                      @RequestParam("questionId")String questionId,
	                                      Model model,
	                                      RedirectAttributes redirectAttributes){
		if (null != questionId){
			List<Question> questionList = testPaperService.selectQuestionByTestPaperId(testPaperId);
			for (Question question : questionList){
				testPaperService.deleteTestPaperQuestion(testPaperId,question.getId());
			}
			String[] idList= questionId.split(" ");

			for (String id:idList){
				testPaperService.addQuestion(testPaperId,new BigInteger("id"));
			}
			logger.info(JSON.toJSONString(questionList));
			logger.info(questionId);
			redirectAttributes.addAttribute("id",testPaperId);
			return "redirect:/te/testPaper/import";
		}
		else{
			redirectAttributes.addAttribute("message","数据出错,请联系管理员");
			return "redirect:/te/testPaper/import";
		}
		
		
	}
	
	
	/**
	 *@author  ShanDJ
	 *@params [questionList, kindNameList, resultList, sumList]
	 *@return  void
	 *@date  18.5.29
	 *@description 计算试卷中题型对应的题目数量和分值
	 *
	 */
	private void getQuestionNumByQuestionKind(List<Question> questionList,
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
