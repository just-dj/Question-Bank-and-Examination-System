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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
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
	
	
	@Override
	public Integer insertExam(Exam exam) {
		return examMapper.insertExam(exam);
	}
	
	@Override
	public Integer insertExamClass(BigInteger examId, BigInteger classId) {
		return examMapper.insertExamClass(examId,classId);
	}
	
	@Override
	public Integer insertExamTestPaper(BigInteger examId, BigInteger testPaperId) {
		return examMapper.insertExamTestPaper(examId,testPaperId);
	}
	
	@Override
	@Transactional(readOnly = false,propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public Integer insertExamAllInfo(Exam exam, List <BigInteger> classId, List <BigInteger> testPaperId) throws Exception{
		int result = examMapper.insertExam(exam);
		for (BigInteger a:classId) {
			insertExamClass(exam.getId(),a);
		}
		for (BigInteger a:testPaperId) {
			insertExamTestPaper(exam.getId(),a);
		}
		
		return result;
	}
}
