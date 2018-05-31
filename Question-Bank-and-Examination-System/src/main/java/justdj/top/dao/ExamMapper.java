package justdj.top.dao;

import justdj.top.pojo.Exam;
import justdj.top.pojo.TestPaper;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.math.BigInteger;
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
	
	
}
