package justdj.top.dao;

import justdj.top.pojo.Exam;
import justdj.top.pojo.TestPaper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

public interface ExamMapper {
	
	@Select("select exam.id,exam.name,course_id,start_time,end_time,is_use\n" +
			"from  course join exam \n" +
			"on course.id = exam.course_id \n" +
			"where course.id = #{courseId}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "name",property = "name"),
			@Result(column = "course_id",property = "courseId"),
			@Result(column = "start_time",property = "startTime"),
			@Result(column = "end_Time",property = "endTime"),
			@Result(column = "is_use",property = "use"),
			@Result(column = "id",property = "classList",
					many = @Many(select = "justdj.top.dao.ExamMapper.selectClassListByExamId",fetchType = FetchType.EAGER))
	})
	List<Exam> selectExamByCourseId(BigInteger courseId);
	
	@Select("select exam.id,exam.name,course_id,start_time,end_time,is_use\n" +
			"from  course join exam\n" +
			"on course.id = exam.course_id  \n" +
			"where course.id = #{courseId}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "name",property = "name"),
			@Result(column = "course_id",property = "courseId"),
			@Result(column = "start_time",property = "startTime"),
			@Result(column = "end_Time",property = "endTime"),
			@Result(column = "is_use",property = "use"),
			@Result(column = "id",property = "classList",
			many = @Many(select = "justdj.top.dao.ExamMapper.selectClassListByExamId",fetchType = FetchType.EAGER))
	})
	List<Exam> selectStudentExamByCourseId(BigInteger courseId);
	
	
	@Select("select class_id from exam_class " +
			"where exam_id = #{examId}")
	List<BigInteger> selectClassListByExamId(BigInteger examId);
	
	
	@Select("select id,name,course_id,start_time,end_time,is_use\n" +
			"from exam\n" +
			"where id = #{examId}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "name",property = "name"),
			@Result(column = "course_id",property = "courseId"),
			@Result(column = "start_time",property = "startTime"),
			@Result(column = "end_Time",property = "endTime"),
			@Result(column = "is_use",property = "use"),
			@Result(column = "id",property = "testPaperList",
					many = @Many(select = "justdj.top.dao.TestPaperMapper.selectTestPaperByExamId",fetchType = FetchType
							.EAGER)),
			@Result(column = "id",property = "classList",
					many = @Many(select = "justdj.top.dao.ExamMapper.selectClassListByExamId",fetchType = FetchType.EAGER))
	})
	Exam selectExamByExamId(BigInteger examId);
	
	
	@Insert("insert into exam (name,course_id,start_time,end_time,is_use)" +
			"values (#{name},#{courseId},#{startTime},#{endTime},#{use})")
	//	获取数据库内部生成主键,将返回值赋值给id属性
	@Options(useGeneratedKeys = true,keyProperty = "id")
	Integer insertExam(Exam exam);
	
	@Insert("insert into exam_class (exam_id,class_id)" +
			"values (#{examId},#{classId})")
	Integer insertExamClass(@Param("examId")BigInteger examId,
	                        @Param("classId")BigInteger classId);
	
	@Insert("insert into exam_test_paper (exam_id,test_paper_id)" +
			"values (#{examId},#{testPaperId})")
	Integer insertExamTestPaper(@Param("examId")BigInteger examId,
	                            @Param("testPaperId")BigInteger testPaperId);
	
	
	@Update("update answer_question set score =#{score} " +
			"where id = #{answerQuestionId}")
	Integer updateAnswerQuestionScore(@Param("answerQuestionId")BigInteger answerQuestionId,
	                                      @Param("score")Integer score);
}
