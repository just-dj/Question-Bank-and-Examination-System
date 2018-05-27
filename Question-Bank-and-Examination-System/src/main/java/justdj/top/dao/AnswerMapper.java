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
	@Select("select answer.id,student_id,answer.test_paper_id,answer.start_time,answer.end_time,result,is_commit\n" +
			"from exam join exam_test_paper join test_paper join answer\n" +
			"on exam.id = exam_id and exam_test_paper.test_paper_id = test_paper.id and test_paper.id = answer.test_paper_id\n" +
			"where exam.id = #{examId}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "student_id",property = "studentId"),
			@Result(column = "test_paper_id",property = "testPaperId"),
			@Result(column = "start_time",property = "startTime"),
			@Result(column = "end_time",property = "endTime"),
			@Result(column = "result",property = "result"),
			@Result(column = "is_commit",property = "commit"),
			@Result(column = "student_id",property = "student",
			one = @One(select = "justdj.top.dao.UserMapper.selectUserByAccount",fetchType = FetchType.EAGER))
			
	})
	List<Answer> selectAnswerByExamId(BigInteger examId);
	
	//当前考试当前学生的答卷
	@Select("select answer.id,student_id,answer.test_paper_id,answer.start_time,answer.end_time,result,is_commit\n" +
			"from exam join exam_test_paper join test_paper join answer\n" +
			"on exam.id = exam_id and exam_test_paper.test_paper_id = test_paper.id and test_paper.id = answer.test_paper_id\n" +
			"where exam.id = #{examId} and answer.student_id = #{studentId}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "student_id",property = "studentId"),
			@Result(column = "test_paper_id",property = "testPaperId"),
			@Result(column = "start_time",property = "startTime"),
			@Result(column = "end_time",property = "endTime"),
			@Result(column = "result",property = "result"),
			@Result(column = "is_commit",property = "commit"),
			@Result(column = "student_id",property = "student",
					one = @One(select = "justdj.top.dao.UserMapper.selectUserByAccount",fetchType = FetchType.EAGER))
		
	})
	Answer selectAnswerByExamIdAndStudentId(@Param("examId") BigInteger examId,
	                                        @Param("studentId") BigInteger studentId);
	
	
	
	
	@Select("select answer.id,student_id,test_paper_id,start_time,end_time,result,is_commit\n" +
			"from test_paper join answer\n" +
			"on test_paper.id = test_paper_id\n" +
			"where test_paper.id = #{testPaperId}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "student_id",property = "studentId"),
			@Result(column = "test_paper_id",property = "testPaperId"),
			@Result(column = "start_time",property = "startTime"),
			@Result(column = "end_time",property = "endTime"),
			@Result(column = "is_commit",property = "commit")
	})
	List<Answer> selectAnswerByTestPaperId(BigInteger testPaperId);
	
	@Select("select id,student_id,test_paper_id,start_time,end_time,result,is_commit\n" +
			"from answer\n" +
			"where id = #{answerId}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "student_id",property = "studentId"),
			@Result(column = "test_paper_id",property = "testPaperId"),
			@Result(column = "start_time",property = "startTime"),
			@Result(column = "end_time",property = "endTime"),
			@Result(column = "is_commit",property = "commit")
	})
	Answer selectAnswerByAnswerId(BigInteger answerId);
	
	
	@Select("select answer_question.id,question,a,b,c,d,question.answer as an,answer_question.answer,score,kind_id," +
			"kind.name\n" +
			"from answer join answer_question join question join kind\n" +
			"on answer.id = answer_id and question_id = question.id and  kind_id = kind.id\n" +
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
			@Result(column = "kind_id",property = "kindId"),
			@Result(column = "name",property = "kindName")
	})
	 List<AnswerQuestion> selectAnswerQuestionByAnswerId(BigInteger answerId);
	
//	获取答卷所含题目类型 好像哪里有逻辑错误 暂时理不清 标记一下
	@Select("select kind.id,kind.name\n" +
			"from answer join answer_question join question join kind\n" +
			"on answer.id = answer_id and question_id = question.id and  kind_id = kind.id\n" +
			"where answer.id = #{answerId}\n" +
			"group by kind.id\n" +
			"order by kind.id\n")
	List<Kind> selectQuestionKindByAnswerId(BigInteger answerId);
}
