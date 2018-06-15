/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.12
  Time: 18:41
*/

package justdj.top.service.impl;

import justdj.top.dao.CourseMapper;
import justdj.top.pojo.Clazz;
import justdj.top.pojo.Course;
import justdj.top.pojo.Knowledge;
import justdj.top.pojo.User;
import justdj.top.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service(value = "courseService")
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CourseMapper courseMapper;
	
	@Override
	public List<Course> selectCourseByTeacherId(BigInteger teacherId) {
		return courseMapper.selectCourseByTeacherId(teacherId);
	}
	
	@Override
	public List <Course> selectCourseByStudentId(BigInteger studentId) {
		return courseMapper.selectCourseByStudentId(studentId);
	}
	
	@Override
	public Course selectCourseByCourseId(BigInteger courseId) {
		return courseMapper.selectCourseByCourseId(courseId);
	}
	
	@Override
	public List <Knowledge> selectKnowledgeByCourseId(BigInteger courseId) {
		return courseMapper.selectKnowledgeByCourseId(courseId);
	}
	
	@Override
	public Integer deleteKnowledge(BigInteger knowledgeId) {
		return courseMapper.deleteKnowledge(knowledgeId);
	}
	
	@Override
	public Integer addKnowledge(BigInteger courseId, String name, String introduce) {
		return  courseMapper.addKnowledge(courseId,name,introduce);
	}
	
	@Override
	public List <Clazz> selectClazzByCourseId(BigInteger courseId) {
		return courseMapper.selectClazzByCourseId(courseId);
	}
	
	@Override
	public List <User> selectStudentByClassId(BigInteger classId) {
		return courseMapper.selectStudentByClassId(classId);
	}
	
	
	@Override
	public Integer deleteClass(BigInteger classId) {
		return courseMapper.deleteClass(classId);
	}
	
	@Override
	public Integer deleteClassStudent(BigInteger classId, BigInteger studentId) {
		return courseMapper.deleteClassStudent(classId,studentId);
	}
	
	@Override
	public Integer addStudentToClass(BigInteger classId, BigInteger studentId) throws DataIntegrityViolationException {
		return courseMapper.addStudentToClass(classId,studentId);
	}
	
	
	@Override
	public Integer addClass(Clazz clazz) {
		return courseMapper.addClass(clazz);
	}
	
	@Override
	public Integer addCourse(Course course) throws RuntimeException{
		return courseMapper.addCourse(course);
	}
	
	
	@Override
	public Clazz selectClass(BigInteger classId) {
		return courseMapper.selectClass(classId);
	}
}
