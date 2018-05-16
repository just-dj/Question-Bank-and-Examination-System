/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.13
  Time: 15:23
*/

package justdj.top.service.impl;

import justdj.top.dao.TestPaperMapper;
import justdj.top.pojo.Question;
import justdj.top.pojo.TestPaper;
import justdj.top.service.TestPaperService;
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
	public List <Question> selectQuestionByTestPaperIdAndKindName(BigInteger paperId, String kindName) {
		return testPaperMapper.selectQuestionByTestPaperIdAndKindName(paperId,kindName);
	}
	
	@Override
	public List <Question> selectQuestionByTestPaperIdAndKindId(BigInteger paperId, BigInteger kindId) {
		return testPaperMapper.selectQuestionByTestPaperIdAndKindId(paperId,kindId);
	}
}
