package justdj.top.dao;

import com.sun.xml.internal.bind.v2.model.core.ID;
import justdj.top.pojo.Clazz;
import justdj.top.pojo.Course;
import justdj.top.pojo.Knowledge;
import justdj.top.pojo.User;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.math.BigInteger;
import java.util.List;

public interface CourseMapper {
	
	@Select("select id,teacher_id,name,introduce from course where teacher_id = #{teacherId}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "teacher_id",property = "teacherId"),
			@Result(column = "name",property = "name"),
			@Result(column = "introduce",property = "introduce")
	})
	List<Course> selectCourseByTeacherId(BigInteger teacherId);
	
	
	@Select("select id,teacher_id,name,introduce from course where id = #{courseId}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "teacher_id",property = "teacherId"),
			@Result(column = "name",property = "name"),
			@Result(column = "introduce",property = "introduce"),
			@Result(column = "id",property = "knowledgeList",
			many = @Many(select = "justdj.top.dao.CourseMapper.selectKnowledgeByCourseId",fetchType = FetchType.EAGER)),
			@Result(column = "id",property = "clazzList",
			many = @Many(select = "justdj.top.dao.CourseMapper.selectClazzByCourseId",fetchType = FetchType.EAGER))
	})
	Course selectCourseByCourseId(BigInteger courseId);
	
	
//	知识点
	@Select("select id,course_id,name,introduce from knowledge where course_id = #{courseId}")
	List<Knowledge> selectKnowledgeByCourseId(BigInteger courseId);
	
//	班级
	@Select("select id,course_id,name from class where course_id = #{courseId}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result (column = "name",property = "name"),
			@Result(column = "id",property = "userList",
					many = @Many(select = "justdj.top.dao.CourseMapper.selectStudentByClassId",fetchType =  FetchType.EAGER))
	})
	List<Clazz> selectClazzByCourseId(BigInteger courseId);
	
	
	@Select("select user.id,account,password,salt,name,age,sex from class_student join user \n" +
			"where student_id = user.id " +
			"and class_id = #{classId};\n")
	List<User> selectStudentByClassId(BigInteger classId);
}
