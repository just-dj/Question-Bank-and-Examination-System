/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.13
  Time: 20:42
*/

package justdj.top.service.impl;

import justdj.top.dao.ExamMapper;
import justdj.top.pojo.Exam;
import justdj.top.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service("examService")
public class ExamServiceImpl implements ExamService {
	
	@Autowired
	private ExamMapper examMapper;
	
	@Override
	public List<Exam> selectExamByCourseId(BigInteger courseId) {
		return examMapper.selectExamByCourseId(courseId);
	}
	
	@Override
	public List <Exam> selectStudentExamByCourseId(BigInteger courseId) {
		return examMapper.selectStudentExamByCourseId(courseId);
	}
	
	@Override
	public List <BigInteger> selectClassListByExamId(BigInteger examId) {
		return examMapper.selectClassListByExamId(examId);
	}
	
	@Override
	public Exam selectExamByExamId(BigInteger examId) {
		return examMapper.selectExamByExamId(examId);
	}
}
