package justdj.top.service;

import justdj.top.pojo.Exam;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

public interface ExamService {
	
	List<Exam> selectExamByCourseId(BigInteger courseId);
	
	List<Exam> selectStudentExamByCourseId(BigInteger courseId);
	
	List<BigInteger> selectClassListByExamId(BigInteger examId);
	
	Exam selectExamByExamId(BigInteger examId);
	
	Integer insertExam(Exam exam);
	
	Integer insertExamClass(@Param("examId")BigInteger examId,
	                        @Param("classId")BigInteger classId);
	
	
	Integer insertExamTestPaper(@Param("examId")BigInteger examId,
	                            @Param("testPaperId")BigInteger testPaperId);
	

	Integer insertExamAllInfo(Exam exam,List<BigInteger> classId,List<BigInteger> testPaperId) throws Exception;
	
	Integer updateAnswerQuestionScore(@Param("answerQuestionId")BigInteger answerQuestionId,
	                                  @Param("score")Integer score);



}
