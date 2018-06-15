package justdj.top.service;

import justdj.top.pojo.Kind;
import justdj.top.pojo.Question;
import justdj.top.pojo.TestPaper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.List;

public interface TestPaperService {
	
	TestPaper selectTestPaperByPaperId(@Param("paperId") BigInteger paperId);
	
	List<TestPaper> selectTestPaperByCourseId(@Param("courseId") BigInteger courseId);
	
	List<TestPaper> selectTestPaperByExamId(BigInteger examId);
	

	List<Question> selectQuestionByTestPaperId(BigInteger paperId);
	
	
	List<Kind> selectQuestionKindByTestPaperId(BigInteger testPaperId);
	

	List<Question> selectQuestionByTestPaperIdAndKindName(@Param("paperId") BigInteger paperId, @Param("kindName") String kindName);
	

	List<Question>selectQuestionByTestPaperIdAndKindId(@Param("paperId") BigInteger paperId, @Param("kindId") BigInteger kindId);
	
	
	Integer addTestPaper(@RequestParam("courseId") BigInteger courseId,
	                     @RequestParam("name") String testPaperName,
	                     @RequestParam("isUse") Boolean isUse);
	
	Integer deleteTestPaperQuestion(@Param("testPaperId") BigInteger testPaperId,
	                                @Param("questionId") BigInteger questionId);
	
	Integer addQuestion(@Param("testPaperId") BigInteger testPaperId,
	                    @Param("questionId") BigInteger questionId,
	                    @Param("score")Integer score) throws RuntimeException;
}
