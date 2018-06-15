/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.18
  Time: 14:00
*/

package justdj.top.service;

import justdj.top.pojo.Question;
import justdj.top.pojo.User;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service("parseFileService")
public class ParseFileService {
	
	//解析选择题
	public List<Question> parseSelectFile(InputStream inputStream){
		List<Question> list = new ArrayList<>();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String lineString = "";
		StringBuffer partString = new StringBuffer();
		Question question = new Question();
		try{
			while (null != (lineString = bufferedReader.readLine())){
				if (!lineString.equals("")){//判断是否是空白行
					if (lineString.startsWith("A:")){
						question.setQuestion(partString.toString());
						partString = new StringBuffer(lineString.substring(2));
					}
					else if (lineString.startsWith("B:")){
						question.setA(partString.toString());
						partString = new StringBuffer(lineString.substring(2));
					}
					else if (lineString.startsWith("C:")){
						question.setB(partString.toString());
						partString = new StringBuffer(lineString.substring(2));
					}
					else if (lineString.startsWith("D:")){
						question.setC(partString.toString());
						partString = new StringBuffer(lineString.substring(2));
					} else if(lineString.startsWith("参考答案")) {
						question.setD(partString.toString());
						question.setAnswer(lineString.substring(4).toLowerCase().trim());
					} else {
						partString.append(lineString);
					}
				}else{
					list.add(question);
					question = new Question();
					partString = new StringBuffer();
				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		
		checkQuestion(list);
		
		return list;
	}
	
	//解析填空题
	public List<Question> parseEmptyFile(InputStream inputStream){
		List<Question> list = new ArrayList<>();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String lineString = "";
		StringBuffer partString = new StringBuffer();
		Question question = new Question();
		try{
			while (null != (lineString = bufferedReader.readLine())){
				if (!lineString.equals("")){//判断是否是空白行
					if (lineString.startsWith("答:")){
						question.setQuestion(partString.toString());
						partString = new StringBuffer(lineString.substring(2));
					}
					else {
						partString.append(lineString);
					}
				}else{
					question.setAnswer(partString.toString());
					list.add(question);
					question = new Question();
					partString = new StringBuffer();
				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		//这里为了加入最后一题
		//最后一个空行不会被读取的
		question.setAnswer(partString.toString());
		list.add(question);
		
		checkQuestion(list);
		return list;
	}
	
	//解析问答题
	public List<Question> parseQuestionFile(InputStream inputStream){
		List<Question> list = new ArrayList<>();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String lineString = "";
		StringBuffer partString = new StringBuffer();
		Question question = new Question();
		try{
			while (null != (lineString = bufferedReader.readLine())){
				if (!lineString.equals("")){//判断是否是空白行
					if (lineString.startsWith("答:")){
						question.setQuestion(partString.toString());
						partString = new StringBuffer(lineString.substring(2));
					}
					else {
						partString.append(lineString);
					}
				}else{
					question.setAnswer(partString.toString());
					list.add(question);
					question = new Question();
					partString = new StringBuffer();
				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		//这里为了加入最后一题
		//最后一个空行不会被读取的
		question.setAnswer(partString.toString());
		list.add(question);
		checkQuestion(list);
		return list;
	}
	
	//解析判断题
	public List<Question> parseJudgementFile(InputStream inputStream){
		List<Question> list = new ArrayList<>();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String lineString = "";
		StringBuffer partString = new StringBuffer();
		Question question = new Question();
		try{
			while (null != (lineString = bufferedReader.readLine())){
				if (!lineString.equals("")){//判断是否是空白行
					if (lineString.startsWith("答:")){
						question.setQuestion(partString.toString());
						partString = new StringBuffer(lineString.substring(2));
					}
					else {
						partString.append(lineString);
					}
				}else{
					question.setAnswer(partString.toString());
					list.add(question);
					question = new Question();
					partString = new StringBuffer();
				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		//这里为了加入最后一题
		//最后一个空行不会被读取的
		question.setAnswer(partString.toString());
		list.add(question);
		checkQuestion(list);
		
		return list;
	}
	
	/**
	 *@author  ShanDJ
	 *@params [inputStream]
	 *@return  java.util.List<java.lang.String>
	 *@date  18.6.15
	 *@description 解析导入的学生文件
	 */
	public List<String>  parseStudentAccountFile(InputStream inputStream){
		List<String> list = new ArrayList<>();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String lineString = "";
		try {
			while (null != (lineString = bufferedReader.readLine())){
//				if (!list.contains(lineString))
					list.add(lineString);
			}
		}catch (Exception e){
			list = null;
		}
		
		return list;
		
	}
	
	/**
	 *@author  ShanDJ
	 *@params [questionList]
	 *@return  void
	 *@date  18.6.15
	 *@description 排除没有正常导入的试题
	 */
	private void checkQuestion(List<Question> questionList){
		
		if (null != questionList){
			for (int i = 0; i < questionList.size(); i++) {
				if (null == questionList.get(i).getQuestion() || questionList.get(i).getQuestion().trim().equals("")){
					questionList.remove(i);
				}
			}
		}
	}
}
