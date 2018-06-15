/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.28
  Time: 10:14
  Info:
*/

package justdj.top.controller.teacher;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xpath.internal.operations.Mod;
import justdj.top.pojo.Question;
import justdj.top.pojo.TestDatabase;
import justdj.top.service.KindService;
import justdj.top.service.ParseFileService;
import justdj.top.service.TestDatabaseService;
import justdj.top.util.KindHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

@Controller
public class TestDatabaseController {
	
	@Autowired
	@Qualifier("testDatabaseService")
	private TestDatabaseService testDatabaseService;
	
	@Autowired
	@Qualifier("kindService")
	private KindService kindService;
	
	@Autowired
	@Qualifier("parseFileService")
	private ParseFileService parseFileService;
	
	
	private static Logger logger  = LoggerFactory.getLogger(TestDatabaseController.class);
	
	/**
	 *@author  ShanDJ
	 *@params [courseId, model]
	 *@return  void
	 *@date  18.5.28
	 *@description 题库管理界面 要统计题库题目类型对应的题数
	 */
	@RequestMapping("/te/testDatabase")
	public String testDatabase(@RequestParam("id")BigInteger courseId,
	                         Model model){

		KindHelper.setKindService(kindService);
		List<TestDatabase> testDatabaseList = testDatabaseService.selectTestDatabaseByCourseId(courseId);
		List<List<Integer>> questionNumList = new LinkedList <>();
		
		if (null != testDatabaseList)
			for (int i = 0;i < testDatabaseList.size();++i) {
					//题库对应的题目数量数组
					questionNumList.add(new ArrayList <Integer>(KindHelper.getKindNameList().size()));
					List<Question> questionList = testDatabaseService.selectTestDatabaseQuestionByTDId(testDatabaseList
							.get(i).getId());
					getQuestionNumByKind(questionNumList.get(i),questionList,KindHelper.getKindNameList());
			}
		
		model.addAttribute("testDatabaseList",testDatabaseList);
		model.addAttribute("quesNum",questionNumList);
		model.addAttribute("courseId",courseId);
		
		
		return "/te/testDatabaseManager";
	}
	
	/**
	 *@author  ShanDJ
	 *@params [testDatabaseId, model]
	 *@return  void
	 *@date  18.5.29
	 *@description 查看题库  可以增加一个按题库题目类型获取问题的接口
	 */
	@RequestMapping(value = "/te/testDatabase/question")
	public String getDatabaseQuestion(@RequestParam("id")BigInteger testDatabaseId,
	                                Model model){
		KindHelper.setKindService(kindService);
		List<String> kindName = KindHelper.getKindNameList();
		

		model.addAttribute("kindNameList",kindName);
		
		List<Question> list = testDatabaseService.selectTestDatabaseQuestionByTDId(testDatabaseId);
		model.addAttribute("questionList",list);
		model.addAttribute("testDatabaseId",testDatabaseId);
		
		return "/te/testDatabase-viewQuestions";
	}
	
	
	
	/**
	 *@author  ShanDJ
	 *@params [testDatabaseId, kindName, model]
	 *@return  java.lang.String
	 *@date  18.5.29
	 *@description 查看题库 备用接口
	 */
	@RequestMapping(value = "/te/testDatabase/ques",method = RequestMethod.POST)
	@ResponseBody
	public String getDatabaseQuestionByKindName(@RequestParam(value = "id",required = true)BigInteger testDatabaseId,
	                                @RequestParam(value = "kindName",required = true)String kindName,
	                                Model model){

		List<Question> list = testDatabaseService.selectTestDatabaseQuestionByKindName(testDatabaseId,kindName);

		return JSON.toJSONString(list);
	}
	
	/**
	 *@author  ShanDJ
	 *@params [testDatabaseId, kindName, uploadFile, model]
	 *@return  void
	 *@date  18.5.29
	 *@description 导入题目 向题库按题型插入数据 逻辑还没实现
	 *涉及文件上传 现在还不能写
	 * dao层已完成
	 */
	@RequestMapping(value = "/te/testDatabase/import",method = RequestMethod.POST)
	@ResponseBody
	public String importQuestionByFile(@RequestParam(value = "id",required = true)BigInteger testDatabaseId,
	                                 @RequestParam(value = "kind",required = true)String kindName,
	                                 @RequestParam("file")MultipartFile uploadFile,
	                                 Model model){
		Map<String,String> map = new HashMap <>();
		map.put("result","");
		map.put("message","");
		
		if (uploadFile.isEmpty()){
			map.put("message","上传文件错误，请稍后重试！");
			return JSON.toJSONString(map);
		}
		
		KindHelper.setKindService(kindService);

		Subject subject  = SecurityUtils.getSubject();

		String userAccount = subject.getPrincipal().toString();

		List<Question> questionList = null;

		try{
			if(kindName.equals("单选题") || kindName.equals("多选题")){
				questionList = parseFileService.parseSelectFile(uploadFile.getInputStream());
			}else  if (kindName.equals("判断题")){
				questionList = parseFileService.parseJudgementFile(uploadFile.getInputStream());
			}else if(kindName.equals("填空题")){
				questionList = parseFileService.parseEmptyFile(uploadFile.getInputStream());
			}else if (kindName.equals("简答题")){
				questionList = parseFileService.parseQuestionFile(uploadFile.getInputStream());
			}
			logger.info("教师"+ userAccount +"尝试向题库"+testDatabaseId+"导入问题"+"文件解析成功！");
		}catch(IOException e){

			logger.warn("教师"+ userAccount +"尝试向题库"+testDatabaseId+"导入问题失败" + "文件读取失败！");
			map.put("message","文件读取失败，请稍后重试！");
			return JSON.toJSONString(map);
		}

		List<String> errorQuestion = new ArrayList <>();

		if (null != questionList){
			for (int i = 0;i <questionList.size();++i) {
				Question question = questionList.get(i);
				question.setTestDatabaseId(testDatabaseId);
				question.setKindId(KindHelper.getKindId(kindName));
				try{
					testDatabaseService.addQuestion(question);
					errorQuestion.add("<br><span class='text-success'>"+"第 " +i + " 条插入成功" + "</span>");
				}catch (RuntimeException e){
					errorQuestion.add("<span class='text-warn'>"+"第 " +i + " 条插入失败" + "</span>");
				}

			}
		}

		logger.info("教师"+ userAccount +"尝试向题库"+testDatabaseId+"导入问题成功！");

		map.put("message","导入成功，详细情况如下:<br>");
		map.put("result",errorQuestion.toString());
		
		return JSON.toJSONString(map);
		
	}
	
	/**
	 *@author  ShanDJ
	 *@params [testDatabaseName, testDatabaseIntroduce, model]
	 *@return  void
	 *@date  18.5.29
	 *@description 新建题库 新建一个题库
	 */
	@RequestMapping(value = "/te/testDatabase/new",method = RequestMethod.POST)
	@ResponseBody
	public String addTestDatabase(@RequestParam(value = "testDatabaseName",required = true)String testDatabaseName,
	                            @RequestParam(value = "introduce",required = true)String testDatabaseIntroduce,
	                            @RequestParam("courseId")BigInteger courseId,
	                            RedirectAttributes redirectAttributes,
	                            Model model){
				int result = testDatabaseService.addTestDatabase(testDatabaseName,testDatabaseIntroduce,courseId);
				
				redirectAttributes.addAttribute("courseId",courseId);
				
				return "创建成功";
	}
	
	
	public static void  getQuestionNumByKind(List<Integer> resultList,List<Question> questionList,List<String>
			kindNameList){
		for (String a: kindNameList) {
			resultList.add(new Integer(0));
		}
		if (null == questionList)
			return;
		for (int i = 0; i < questionList.size(); i++) {
			for(int j = 0;j <kindNameList.size(); j++){
				if (questionList.get(i).getKindName().equals(kindNameList.get(j))){
					resultList.set(j ,resultList.get(j)+1);
					break;
				}
				
				
			}
			
		}
		
		
	}
	
	
	
}
