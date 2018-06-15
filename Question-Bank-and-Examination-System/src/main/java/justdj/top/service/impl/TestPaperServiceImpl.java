/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.13
  Time: 15:23
*/

package justdj.top.service.impl;

import justdj.top.dao.TestPaperMapper;
import justdj.top.pojo.Kind;
import justdj.top.pojo.Question;
import justdj.top.pojo.TestPaper;
import justdj.top.service.TestPaperService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service("testPaperService")
public class TestPaperServiceImpl implements TestPaperService {
	
	@Autowired
	private TestPaperMapper testPaperMapper;
	
	@Override
	public TestPaper selectTestPaperByPaperId(BigInteger paperId) {
		return testPaperMapper.selectTestPaperByPaperId(paperId);
	}
	
	@Override
	public List<TestPaper> selectTestPaperByCourseId(BigInteger courseId) {
		return testPaperMapper.selectTestPaperByCourseId(courseId);
	}
	
	@Override
	public List <TestPaper> selectTestPaperByExamId(BigInteger examId) {
		return testPaperMapper.selectTestPaperByExamId(examId);
	}
	
	@Override
	public List <Question> selectQuestionByTestPaperId(BigInteger paperId) {
		return testPaperMapper.selectQuestionByTestPaperId(paperId);
	}
	
	@Override
	public List <Kind> selectQuestionKindByTestPaperId(BigInteger testPaperId) {
		return testPaperMapper.selectQuestionKindByTestPaperId(testPaperId);
	}
	
	@Override
	public List <Question> selectQuestionByTestPaperIdAndKindName(BigInteger paperId, String kindName) {
		return testPaperMapper.selectQuestionByTestPaperIdAndKindName(paperId,kindName);
	}
	
	@Override
	public List <Question> selectQuestionByTestPaperIdAndKindId(BigInteger paperId, BigInteger kindId) {
		return testPaperMapper.selectQuestionByTestPaperIdAndKindId(paperId,kindId);
	}
	
	@Override
	public Integer addTestPaper(BigInteger courseId, String testPaperName, Boolean isUse) {
		return testPaperMapper.addTestPaper(courseId,testPaperName,isUse);
	}
	
	@Override
	public Integer deleteTestPaperQuestion(BigInteger testPaperId, BigInteger questionId) {
		return testPaperMapper.deleteTestPaperQuestion(testPaperId,questionId);
	}
	
	@Override
	public Integer addQuestion(BigInteger testPaperId, BigInteger questionId,Integer score) throws RuntimeException{
		return testPaperMapper.addQuestion(testPaperId,questionId,score);
	}
}
