package justdj.top.dao;

import justdj.top.pojo.Clazz;
import justdj.top.pojo.Course;
import justdj.top.pojo.Knowledge;
import justdj.top.pojo.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.math.BigInteger;
import java.util.List;

//@CacheNamespace(implementation = justdj.top.cache.MybatisRedisCache.class)
public interface CourseMapper {
	
	@Select("select id,teacher_id,name,introduce,img from course where teacher_id = #{teacherId}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "teacher_id",property = "teacherId"),
			@Result(column = "name",property = "name"),
			@Result(column = "introduce",property = "introduce"),
			@Result(column = "img",property = "img")
	})
	List<Course> selectCourseByTeacherId(BigInteger teacherId);
	
	@Select("select course.img,course.id,teacher_id,course.name,course.introduce\n" +
			"from user inner join class_student inner join class inner join course\n" +
			"on user.id = student_id and class_id = class.id and course_id = course.id\n" +
			"where user.id = #{studentId}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "teacher_id",property = "teacherId"),
			@Result(column = "name",property = "name"),
			@Result(column = "introduce",property = "introduce"),
			@Result(column = "img",property = "img")
	})
	List<Course> selectCourseByStudentId(BigInteger studentId);
	
	@Select("select course.img,id,teacher_id,name,introduce from course where id = #{courseId}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "teacher_id",property = "teacherId"),
			@Result(column = "name",property = "name"),
			@Result(column = "introduce",property = "introduce"),
			@Result(column = "img",property = "img"),
			@Result(column = "id",property = "knowledgeList",
			many = @Many(select = "justdj.top.dao.CourseMapper.selectKnowledgeByCourseId",fetchType = FetchType.EAGER)),
			@Result(column = "id",property = "clazzList",
			many = @Many(select = "justdj.top.dao.CourseMapper.selectClazzByCourseId",fetchType = FetchType.EAGER))
	})
	Course selectCourseByCourseId(BigInteger courseId);
	
	
//	知识点
	@Select("select id,course_id,name,introduce from knowledge where course_id = #{courseId}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "name",property = "name"),
			@Result(column = "introduce",property = "introduce")
	})
	List<Knowledge> selectKnowledgeByCourseId(BigInteger courseId);
	
	@Delete("delete from knowledge where id = #{knowledgeId}")
	@Options(flushCache = true)
	Integer deleteKnowledge(@Param("knowledgeId") BigInteger knowledgeId);
	
	@Insert("insert into knowledge (course_id,name,introduce)" +
			"values (#{courseId},#{name},#{introduce})")
	@Options(flushCache = true)
	Integer addKnowledge(@Param("courseId")BigInteger courseId,
	                     @Param("name")String name,
	                     @Param("introduce")String introduce);
//	班级
	@Select("select id,course_id,name,time,place from class where course_id = #{courseId}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result (column = "name",property = "name"),
			@Result(column = "time",property = "time"),
			@Result(column = "place",property = "place"),
			@Result(column = "id",property = "userList",
					many = @Many(select = "justdj.top.dao.CourseMapper.selectStudentByClassId",fetchType =  FetchType.EAGER))
	})
	List<Clazz> selectClazzByCourseId(BigInteger courseId);
	
	
	@Select("select user.id,account,password,salt,name,age,sex,email,img,is_use " +
			"from class_student join user \n" +
			"where student_id = user.id " +
			"and class_id = #{classId};\n")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "account",property = "account"),
			@Result(column = "email",property = "email"),
			@Result(column = "password",property = "password"),
			@Result(column = "salt",property = "salt"),
			@Result(column = "name",property = "name"),
			@Result(column = "age",property = "age"),
			@Result(column = "sex",property = "sex"),
			@Result(column = "img",property = "img"),
			@Result(column = "is_use",property = "use")
	})
	List<User> selectStudentByClassId(BigInteger classId);
	
	@Delete("delete from class where id = #{classId}")
	@Options(flushCache = true)
	Integer deleteClass(BigInteger classId);
	
	@Delete("delete from class_student where class_id = #{classId} && student_id = #{studentId}")
	@Options(flushCache = true)
	Integer deleteClassStudent(@Param("classId") BigInteger classId,
	                           @Param("studentId") BigInteger studentId);
	
	@Insert("insert into class_student (class_id,student_id) " +
			"values (#{classId},#{studentId})")
	@Options(flushCache = true)
	Integer addStudentToClass(@Param("classId") BigInteger classId,
	                          @Param("studentId") BigInteger studentId);
	
	
	@Insert("insert into class (course_id,name,time,place) " +
			"values (#{courseId},#{name},#{time},#{place})")
	Integer addClass(Clazz clazz);
	
	
	@Insert("insert into course (teacher_id,name,introduce,img) " +
			"values (#{teacherId},#{name},#{introduce},#{img})")
	Integer addCourse(Course course);
	
	@Select("select * from class where id = #{id}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result (column = "name",property = "name"),
			@Result(column = "time",property = "time"),
			@Result(column = "place",property = "place")
	})
	Clazz selectClass(BigInteger classId);
}
