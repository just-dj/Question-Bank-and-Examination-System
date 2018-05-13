/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.13
  Time: 21:32
*/

package justdj.top.service;

import justdj.top.pojo.Answer;
import justdj.top.pojo.AnswerQuestion;

import java.math.BigInteger;
import java.util.List;

public interface AnswerService {
	
	List<Answer> selectAnswerByExamId(BigInteger examId);
	
	List<Answer> selectAnswerByTestPaperId(BigInteger testPaperId);
	
	Answer selectAnswerByAnswerId(BigInteger answerId);
	
	List<AnswerQuestion> selectAnswerQuestionByAnswerId(BigInteger answerId);
	
}
