package justdj.top.service;

import justdj.top.pojo.Question;
import justdj.top.pojo.TestDatabase;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

public interface TestDatabaseService {
	
	List<TestDatabase> selectTestDatabaseByCourseId(BigInteger courseId);
	
	//注意这个方法是不安全的，返回了question的所有信息
	List<Question> selectTestDatabaseQuestionByKindName(BigInteger testDatabaseId, String kindName);
	
	//注意这个方法是不安全的，返回了question的所有信息
	List<Question> selectTestDatabaseQuestionByKindId(BigInteger testDatabaseId,BigInteger kindId);
	
	List<Question> selectTestDatabaseQuestionByTDId(BigInteger testDataBaseId);
	
	Integer addTestDatabase(@Param("name") String name,
	                        @Param("introduce") String introduce,
	                        @Param("courseId")BigInteger courseId);
	
	List<Question> selectQuestionByCondition(BigInteger testDatabaseId,
	                                         BigInteger kindId,
	                                         String keyWord);
}
