package justdj.top.service;

import justdj.top.pojo.Question;
import justdj.top.pojo.TestPaper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

public interface TestPaperService {
	
	TestPaper selectTestPaperByPaperId(@Param("paperId") BigInteger paperId);
	
	List<TestPaper> selectTestPaperByCourseId(@Param("courseId") BigInteger courseId);
	
	List<TestPaper> selectTestPaperByExamId(BigInteger examId);
	
	@Deprecated
	List<Question> selectQuestionByTestPaperId(BigInteger paperId);
	
	@Deprecated
	List<Question> selectQuestionByTestPaperIdAndKindName(@Param("paperId") BigInteger paperId, @Param("kindName") String kindName);
	
	@Deprecated
	List<Question>selectQuestionByTestPaperIdAndKindId(@Param("paperId") BigInteger paperId, @Param("kindId") BigInteger kindId);
	
	
}
