package justdj.top.dao;

import justdj.top.dao.dynaSql.SelectTDQuestion;
import justdj.top.pojo.Question;
import justdj.top.pojo.TestDatabase;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Column;
import java.math.BigInteger;
import java.util.List;

public interface TestDatabaseMapper {
	
	@Select("select  id,name,introduce,course_id from test_database where course_id = #{courseId}" +
			" order by id")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column ="name" ,property = "name"),
			@Result(column = "introduce",property = "introduce"),
			@Result(column = "course_id",property = "courseId")
	})
	List<TestDatabase> selectTestDatabaseByCourseId(BigInteger courseId);
	
	@Select("select question.id,kind_id,kind.name,test_database_id,question,a,b,c,d,answer \n" +
			"from kind join question join test_database \n" +
			"on kind.id = kind_id and test_database_id = test_database.id \n" +
			"where test_database.id = #{testDatabaseId} and kind.name = #{kindName}" +
			" order by question.id")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "kind_id",property = "kindId"),
			@Result(column = "name",property = "kindName"),
			@Result(column = "test_database_id",property = "testDatabaseId"),
			@Result(column = "question",property = "question"),
			@Result(column = "a",property = "a"),
			@Result(column = "b",property = "b"),
			@Result(column = "c",property = "c"),
			@Result(column = "d",property = "d"),
			@Result(column = "answer",property = "answer")
	})
	List<Question> selectTestDatabaseQuestionByKindName(@Param("testDatabaseId") BigInteger testDatabaseId, @Param("kindName") String
	                                                    kindName);
	
	
	@Select("select question.id,kind_id,kind.name,test_database_id,question,a,b,c,d,answer \n" +
			"from kind join question join test_database \n" +
			"on kind.id = kind_id and test_database_id = test_database.id \n" +
			"where test_database.id = #{testDatabaseId} and kind.id = #{kindId}" +
			" order by question.id")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "kind_id",property = "kindId"),
			@Result(column = "name",property = "kindName"),
			@Result(column = "test_database_id",property = "testDatabaseId"),
			@Result(column = "question",property = "question"),
			@Result(column = "a",property = "a"),
			@Result(column = "b",property = "b"),
			@Result(column = "c",property = "c"),
			@Result(column = "d",property = "d"),
			@Result(column = "answer",property = "answer")
	})
	List<Question> selectTestDatabaseQuestionByKindId(@Param("testDatabaseId") BigInteger testDatabaseId,
	                                                  @Param("kindId") BigInteger kindId);
	
	@Select("select question.id,kind_id,kind.name,test_database_id,question,a,b,c,d,answer \n" +
			"from kind join question join test_database \n" +
			"on kind.id = kind_id and test_database_id = test_database.id \n" +
			"where test_database.id = #{testDatabaseId} " +
			" order by question.id")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "kind_id",property = "kindId"),
			@Result(column = "name",property = "kindName"),
			@Result(column = "test_database_id",property = "testDatabaseId"),
			@Result(column = "question",property = "question"),
			@Result(column = "a",property = "a"),
			@Result(column = "b",property = "b"),
			@Result(column = "c",property = "c"),
			@Result(column = "d",property = "d"),
			@Result(column = "answer",property = "answer")
	})
	List<Question> selectTestDatabaseQuestionByTDId(BigInteger testDataBaseId);
	
	@Insert("insert into test_database (name,introduce,course_id) values" +
			"(#{name},#{introduce},#{courseId})")
	Integer addTestDatabase(@Param("name") String name,
	                        @Param("introduce") String introduce,
	                        @Param("courseId")BigInteger courseId);
	
	
	
	@SelectProvider(type = SelectTDQuestion.class,method = "selectTDQuestionByCondition")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "kind_id",property = "kindId"),
			@Result(column = "name",property = "kindName"),
			@Result(column = "test_database_id",property = "testDatabaseId"),
			@Result(column = "question",property = "question"),
			@Result(column = "a",property = "a"),
			@Result(column = "b",property = "b"),
			@Result(column = "c",property = "c"),
			@Result(column = "d",property = "d"),
			@Result(column = "answer",property = "answer")
	})
	List<Question> selectQuestionByCondition(@Param("testDatabaseId") BigInteger testDatabaseId,
	                                         @Param("kindId") BigInteger kindId,
	                                         @Param("keyWord") String keyWord);
}
