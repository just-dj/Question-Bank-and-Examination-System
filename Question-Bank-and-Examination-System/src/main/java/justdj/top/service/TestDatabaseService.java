package justdj.top.service;

import justdj.top.pojo.Question;
import justdj.top.pojo.TestDatabase;

import java.math.BigInteger;
import java.util.List;

public interface TestDatabaseService {
	
	List<TestDatabase> selectTestDatabaseByCourseId(BigInteger courseId);
	
	//注意这个方法是不安全的，返回了question的所有信息
	@Deprecated
	List<Question> selectTestDatabaseQuestionByKindName(BigInteger testDatabaseId, String kindName);
	
	//注意这个方法是不安全的，返回了question的所有信息
	@Deprecated
	List<Question> selectTestDatabaseQuestionByKindId(BigInteger testDatabaseId,BigInteger kindId);
	
	List<Question> selectTestDatabaseQuestionByTDId(BigInteger testDataBaseId);
}
