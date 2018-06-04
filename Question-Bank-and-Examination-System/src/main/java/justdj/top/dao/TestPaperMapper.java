package justdj.top.dao;

import justdj.top.dao.dynaSql.SelectTDQuestion;
import justdj.top.pojo.Kind;
import justdj.top.pojo.Question;
import justdj.top.pojo.TestPaper;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.List;

public interface TestPaperMapper {
	
	@Select("select id,course_id,name,is_use from test_paper\n" +
			"where id = #{paperId}")
	@Results({
			@Result(id =true,column = "id",property = "id"),
			@Result(column = "course_id",property = "courseId"),
			@Result(column = "name",property = "name"),
			@Result(column = "is_use",property = "use")
	})
	TestPaper selectTestPaperByPaperId(@Param("paperId") BigInteger paperId);
	
	@Select("select id,course_id,name,is_use from test_paper\n" +
			"where course_Id = #{courseId}")
	@Results({
			@Result(id =true,column = "id",property = "id"),
			@Result(column = "course_id",property = "courseId"),
			@Result(column = "name",property = "name"),
			@Result(column = "is_use",property = "use")
	})
	List<TestPaper> selectTestPaperByCourseId(@Param("courseId") BigInteger courseId);
	
	
	@Select("select test_paper.id,test_paper.course_id,test_paper.name,test_paper.is_use \n" +
			"from exam join exam_test_paper join test_paper\n" +
			"on exam.id = exam_id and test_paper_id = test_paper.id\n" +
			"where exam.id = #{examId}")
	@Results({
			@Result(id =true,column = "id",property = "id"),
			@Result(column = "course_id",property = "courseId"),
			@Result(column = "name",property = "name"),
			@Result(column = "is_use",property = "use")
	})
	List<TestPaper> selectTestPaperByExamId(@Param("examId") BigInteger examId);
	
	@Select("select question.id,kind_id,kind.name,test_database_id,question,a,b,c,d,answer,score \n" +
			"from kind join question join test_paper_question join test_paper\n" +
			"on kind.id = kind_id and question.id = question_id and test_paper_id = test_paper.id\n" +
			"where test_paper.id = #{paperId} \n" +
			"order by question.id")
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
			@Result(column = "answer",property = "answer"),
			@Result(column = "score",property = "score")
	})
	List<Question> selectQuestionByTestPaperId(BigInteger paperId);
	
	@Select("select kind.id,kind.name \n" +
			"from kind  join question  join test_paper_question  join test_paper \n" +
			"on kind.id = kind_id and question.id = question_id and test_paper_id = test_paper.id \n" +
			"where test_paper.id = #{testPaperId} \n" +
			"group by kind.id \n" +
			"order by kind.id")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "name",property = "name")
	})
	List<Kind> selectQuestionKindByTestPaperId(@Param("testPaperId") BigInteger testPaperId);
	
	@Select("select question.id,kind_id,kind.name,test_database_id,question,a,b,c,d,answer \n" +
			"from kind join question join test_paper_question join test_paper\n" +
			"on kind.id = kind_id and question.id = question_id and test_paper_id = test_paper.id\n" +
			"where test_paper.id = #{paperId} and kind.name = #{kindName}\n" +
			"order by question.id")
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
	List<Question> selectQuestionByTestPaperIdAndKindName(@Param("paperId") BigInteger paperId,
	                                                      @Param("kindName") String kindName);
	
	@Select("select question.id,kind_id,kind.name,test_database_id,question,a,b,c,d,answer \n" +
			"from kind join question join test_paper_question join test_paper\n" +
			"on kind.id = kind_id and question.id = question_id and test_paper_id = test_paper.id\n" +
			"where test_paper.id = #{paperId} and kind.id = #{kindId}\n" +
			"order by question.id")
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
	List<Question>selectQuestionByTestPaperIdAndKindId(@Param("paperId") BigInteger paperId, @Param("kindId") BigInteger kindId);

	

	@Insert("insert into test_paper (course_id,name,is_use)" +
			"values (#{courseId},#{name},#{isUse})")
	Integer addTestPaper(@Param("courseId") BigInteger courseId,
	                     @Param("name") String testPaperName,
	                     @Param("isUse") Boolean isUse);

	@Delete("delete from test_paper_question " +
			"where test_paper_id = #{testPaperId} and question_id = #{questionId}")
	Integer deleteTestPaperQuestion(@Param("testPaperId") BigInteger testPaperId,
	                                @Param("questionId") BigInteger questionId);
	
	
	@Insert("insert into test_paper_question (test_paper_id,question_id) " +
			"values (#{testPaperId},#{questionId})")
	Integer addQuestion(@Param("testPaperId") BigInteger testPaperId,
	                    @Param("questionId") BigInteger questionId);
}
