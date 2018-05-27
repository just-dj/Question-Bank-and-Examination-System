package justdj.top.service;

import justdj.top.pojo.Clazz;
import justdj.top.pojo.Course;
import justdj.top.pojo.Knowledge;
import justdj.top.pojo.User;

import java.math.BigInteger;
import java.util.List;

public interface CourseService {
	
	List<Course> selectCourseByTeacherId(BigInteger teacherId);
	
	List<Course> selectCourseByStudentId(BigInteger studentId);
	
	Course selectCourseByCourseId(BigInteger courseId);
	
	List<Knowledge> selectKnowledgeByCourseId(BigInteger courseId);
	
	List<Clazz> selectClazzByCourseId(BigInteger courseId);
	
	@Deprecated
	List<User> selectStudentByClassId(BigInteger classId);
	
}
