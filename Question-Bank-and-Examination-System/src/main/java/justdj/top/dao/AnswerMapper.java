package justdj.top.dao;

import justdj.top.pojo.Answer;
import justdj.top.pojo.AnswerQuestion;
import justdj.top.pojo.Kind;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.math.BigInteger;
import java.util.List;

public interface AnswerMapper {
	
	//当前考试的所有答卷
	@Select("select answer.id,student_id,exam_id,answer.test_paper_id,answer.start_time,answer.end_time,result," +
			"is_commit \n" +
			"from  answer \n" +
			"where exam_id = #{examId};")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "student_id",property = "studentId"),
			@Result(column = "exam_id",property = "examId"),
			@Result(column = "test_paper_id",property = "testPaperId"),
			@Result(column = "start_time",property = "startTime"),
			@Result(column = "end_time",property = "endTime"),
			@Result(column = "result",property = "result"),
			@Result(column = "is_commit",property = "commit"),
			@Result(column = "student_id",property = "student",
			one = @One(select = "justdj.top.dao.UserMapper.selectUserById",fetchType = FetchType.EAGER)),
			@Result(column = "test_paper_id",property = "testPaper",
					one = @One(select = "justdj.top.dao.TestPaperMapper.selectTestPaperByPaperId",fetchType = FetchType.EAGER))
			
	})
	List<Answer> selectAnswerByExamId(BigInteger examId);
	
	//当前考试当前学生的答卷
	@Select("select answer.id,student_id,exam_id,answer.test_paper_id,answer.start_time,answer.end_time,result," +
			"is_commit\n " +
			"from  answer\n " +
			"where exam_id = #{examId} and student_id =#{studentId};")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "student_id",property = "studentId"),
			@Result(column = "exam_id",property = "examId"),
			@Result(column = "test_paper_id",property = "testPaperId"),
			@Result(column = "start_time",property = "startTime"),
			@Result(column = "end_time",property = "endTime"),
			@Result(column = "result",property = "result"),
			@Result(column = "is_commit",property = "commit"),
			@Result(column = "student_id",property = "student",
					one = @One(select = "justdj.top.dao.UserMapper.selectUserByAccount",fetchType = FetchType.EAGER)),
			@Result(column = "test_paper_id",property = "testPaper",
					one = @One(select = "justdj.top.dao.TestPaperMapper.selectTestPaperByPaperId",fetchType = FetchType.EAGER))
		
	})
	Answer selectAnswerByExamIdAndStudentId(@Param("examId") BigInteger examId,
	                                        @Param("studentId") BigInteger studentId);
	
	
	@Select("select id,student_id,test_paper_id,start_time,end_time,result,is_commit\n" +
			"from answer\n" +
			"where id = #{answerId}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "student_id",property = "studentId"),
			@Result(column = "test_paper_id",property = "testPaperId"),
			@Result(column = "start_time",property = "startTime"),
			@Result(column = "end_time",property = "endTime"),
			@Result(column = "is_commit",property = "commit"),
			@Result(column = "test_paper_id",property = "testPaper",
					one = @One(select = "justdj.top.dao.TestPaperMapper.selectTestPaperByPaperId",fetchType = FetchType.EAGER))
	})
	Answer selectAnswerByAnswerId(BigInteger answerId);
	
	
	@Select("select answer_question.id,question,a,b,c,d,question.answer as an,answer_question.answer," +
			"answer_question.score,kind_id, test_paper_question.score as questionscore," +
			"kind.name\n" +
			"from answer join answer_question join question join kind join test_paper_question\n" +
			"on answer.id = answer_id and answer_question.question_id = question.id and  kind_id = kind.id and " +
			"question.id = test_paper_question.question_id and answer.test_paper_id = test_paper_question" +
			".test_paper_id \n" +
			"where answer.id = #{answerId}\n" +
			"order by answer_question.answer_id\n")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "question",property = "question"),
			@Result(column = "a",property = "a"),
			@Result(column = "b",property = "b"),
			@Result(column = "c",property = "c"),
			@Result(column = "d",property = "d"),
			@Result(column = "an",property = "answer"),
			@Result(column = "answer",property = "userAnswer"),
			@Result(column = "score",property = "score"),
			@Result(column = "questionscore",property = "questionScore"),
			@Result(column = "kind_id",property = "kindId"),
			@Result(column = "name",property = "kindName")
	})
	 List<AnswerQuestion> selectAnswerQuestionByAnswerId(BigInteger answerId);
	
	
	@Select("select answer_question.id,question,a,b,c,d,question.answer as an,answer_question.answer," +
			"answer_question.score,kind_id, test_paper_question.score as questionscore," +
			"kind.name\n" +
			"from answer join answer_question join question join kind join test_paper_question\n" +
			"on answer.id = answer_id and answer_question.question_id = question.id and  kind_id = kind.id and " +
			"question.id = test_paper_question.question_id and answer.test_paper_id = test_paper_question" +
			".test_paper_id \n" +
			"where answer.id = #{answerId} and question.kind_id = #{kindId}\n" +
			"order by answer_question.answer_id\n")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "question",property = "question"),
			@Result(column = "a",property = "a"),
			@Result(column = "b",property = "b"),
			@Result(column = "c",property = "c"),
			@Result(column = "d",property = "d"),
			@Result(column = "an",property = "answer"),
			@Result(column = "answer",property = "userAnswer"),
			@Result(column = "score",property = "score"),
			@Result(column = "questionscore",property = "questionScore"),
			@Result(column = "kind_id",property = "kindId"),
			@Result(column = "name",property = "kindName")
	})
	List<AnswerQuestion> selectAnswerQuestion(@Param("answerId") BigInteger answerId,@Param("kindId") BigInteger
			kindId);
	
//	获取答卷所含题目类型 好像哪里有逻辑错误 暂时理不清 标记一下
	@Select("select kind.id,kind.name\n" +
			"from answer join answer_question join question join kind\n" +
			"on answer.id = answer_id and question_id = question.id and  kind_id = kind.id\n" +
			"where answer.id = #{answerId}\n" +
			"group by kind.id\n" +
			"order by kind.id\n")
	List<Kind> selectQuestionKindByAnswerId(BigInteger answerId);
	
	
	@Insert("insert into answer (student_id,exam_id,test_paper_id,start_time,is_commit)" +
			"values (#{studentId},#{examId},#{testPaperId},#{startTime},#{commit})")
	@Options(useGeneratedKeys = true,keyProperty = "id",flushCache = true)
	Integer addAnswer(Answer answer);
	
	@Update("update answer set " +
			"student_id = #{studentId}," +
			"test_paper_id = #{testPaperId}," +
			"start_time  = #{startTime}," +
			"end_time = #{endTime}," +
			"result = #{result}," +
			"is_commit = #{commit} " +
			"where id = #{id}")
	@Options(flushCache = true)
	Integer updateAnswer(Answer answer);
	
	@Insert("insert into answer_question (answer_id,question_id,answer,score)" +
			"values (#{answerId},#{questionId},#{answer},#{score})")
	@Options(flushCache = true)
	Integer addAnswerQuestion(AnswerQuestion answerQuestion);
	
	
}
