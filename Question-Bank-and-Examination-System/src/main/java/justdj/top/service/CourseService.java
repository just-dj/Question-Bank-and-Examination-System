package justdj.top.service;

import justdj.top.pojo.Clazz;
import justdj.top.pojo.Course;
import justdj.top.pojo.Knowledge;
import justdj.top.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

public interface CourseService {
	
	List<Course> selectCourseByTeacherId(BigInteger teacherId);
	
	List<Course> selectCourseByStudentId(BigInteger studentId);
	
	Course selectCourseByCourseId(BigInteger courseId);
	
	List<Knowledge> selectKnowledgeByCourseId(BigInteger courseId);
	
	List<Clazz> selectClazzByCourseId(BigInteger courseId);
	
	List<User> selectStudentByClassId(BigInteger classId);
	
	Integer deleteClass(BigInteger classId);
	
	Integer deleteClassStudent(@Param("classId") BigInteger classId,
	                           @Param("studentId") BigInteger studentId);
	
	Integer addStudentToClass(@Param("classId") BigInteger classId,
	                          @Param("studentId") BigInteger studentId);
}
