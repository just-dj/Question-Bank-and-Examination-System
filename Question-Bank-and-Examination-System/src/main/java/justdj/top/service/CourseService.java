package justdj.top.service;

import justdj.top.pojo.Clazz;
import justdj.top.pojo.Course;
import justdj.top.pojo.Knowledge;
import justdj.top.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigInteger;
import java.util.List;

public interface CourseService {
	
	List<Course> selectCourseByTeacherId(BigInteger teacherId);
	
	List<Course> selectCourseByStudentId(BigInteger studentId);
	
	Course selectCourseByCourseId(BigInteger courseId);
	
	List<Knowledge> selectKnowledgeByCourseId(BigInteger courseId);
	
	Integer deleteKnowledge(@Param("knowledgeId") BigInteger knowledgeId);
	
	Integer addKnowledge(@Param("courseId")BigInteger courseId,
	                     @Param("name")String name,
	                     @Param("introduce")String introduce);
	
	List<Clazz> selectClazzByCourseId(BigInteger courseId);
	
	List<User> selectStudentByClassId(BigInteger classId);
	
	Integer deleteClass(BigInteger classId);
	
	Integer deleteClassStudent(@Param("classId") BigInteger classId,
	                           @Param("studentId") BigInteger studentId);
	
	Integer addStudentToClass(@Param("classId") BigInteger classId,
	                          @Param("studentId") BigInteger studentId) throws DataIntegrityViolationException;
	
	
	Integer addClass(Clazz clazz);
	
	Integer addCourse(Course course) throws RuntimeException;
	
	Clazz selectClass(BigInteger classId);
}
