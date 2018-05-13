/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.12
  Time: 23:49
*/

package justdj.top.service.impl;

import justdj.top.dao.TestDatabaseMapper;
import justdj.top.pojo.Question;
import justdj.top.pojo.TestDatabase;
import justdj.top.service.TestDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service("testDatabaseService")
public class TestDatabaseServiceImpl implements TestDatabaseService {
	
	@Autowired
	private TestDatabaseMapper testDatabaseMapper;
	
	@Override
	public List<TestDatabase> selectTestDatabaseByCourseId(BigInteger courseId) {
		return testDatabaseMapper.selectTestDatabaseByCourseId(courseId);
	}
	
	@Override
	public List <Question> selectTestDatabaseQuestionByKindName(BigInteger testDatabaseId, String kindName) {
		return testDatabaseMapper.selectTestDatabaseQuestionByKindName(testDatabaseId,kindName);
	}
	
	@Override
	public List <Question> selectTestDatabaseQuestionByKindId(BigInteger testDatabaseId, BigInteger kindId) {
		return testDatabaseMapper.selectTestDatabaseQuestionByKindId(testDatabaseId,kindId);
	}
}