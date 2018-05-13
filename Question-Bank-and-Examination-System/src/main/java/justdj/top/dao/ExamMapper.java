package justdj.top.dao;

import justdj.top.pojo.Exam;
import justdj.top.pojo.TestPaper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.math.BigInteger;
import java.util.List;

public interface ExamMapper {
	
	@Select("select exam.id,exam.name,course_id,start_time,end_time,is_use\n" +
			"from  course join exam\n" +
			"on course.id = exam.id\n" +
			"where course.id = #{courseId};")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "name",property = "name"),
			@Result(column = "course_id",property = "courseId"),
			@Result(column = "start_time",property = "startTime"),
			@Result(column = "end_Time",property = "endTime"),
			@Result(column = "is_use",property = "use")
	})
	List<Exam> selectExamByCourseId(BigInteger courseId);
	
	@Select("select id,name,course_id,start_time,end_time,is_use\n" +
			"from exam\n" +
			"where id = #{examId}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "name",property = "name"),
			@Result(column = "course_id",property = "courseId"),
			@Result(column = "start_time",property = "startTime"),
			@Result(column = "end_Time",property = "endTime"),
			@Result(column = "is_use",property = "use")
	})
	Exam selectExamByExamId(BigInteger examId);
	
	
}
