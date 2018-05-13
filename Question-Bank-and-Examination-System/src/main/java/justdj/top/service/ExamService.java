package justdj.top.service;

import justdj.top.pojo.Exam;

import java.math.BigInteger;
import java.util.List;

public interface ExamService {
	
	List<Exam> selectExamByCourseId(BigInteger courseId);
	
	Exam selectExamByExamId(BigInteger examId);
}
