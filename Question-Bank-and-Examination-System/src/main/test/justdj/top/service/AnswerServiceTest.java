package justdj.top.service;

import com.alibaba.fastjson.JSON;
import justdj.top.JUnit4ClassRunner;
import justdj.top.pojo.Answer;
import justdj.top.pojo.AnswerQuestion;
import justdj.top.pojo.Kind;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigInteger;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4ClassRunner.class)  //此处调用Spring单元测试类
@WebAppConfiguration()//声明为集成测试加载的ApplicationContext应该是WebApplicationContext类型
//下面的注解告诉测试运行器如何加载和配置WebApplicationContext
@ContextConfiguration(locations = {"/spring/test_springContext.xml","/spring/test_spring-shiro.xml"})
public class AnswerServiceTest {
	
	@Autowired
	@Qualifier("answerService")
	AnswerService answerService;
	
	@Test
	public void selectAnswerByExamId() throws Exception {
		List<Answer> list = answerService.selectAnswerByExamId(BigInteger.valueOf(1));
		assertNotNull(list);
		assertNotEquals(0,list.size());
		System.err.println("\n selectAnswerByExamId() \n"+ JSON.toJSONString(list) + "\n");
		
	}
	
	@Test
	public void selectAnswerByTestPaperId() throws Exception {
		List<Answer> list = answerService.selectAnswerByTestPaperId(BigInteger.valueOf(1));
		assertNotNull(list);
		assertNotEquals(0,list.size());
		System.err.println("\n selectAnswerByTestPaperId() \n"+ JSON.toJSONString(list) + "\n");
	}
	
	@Test
	public void selectAnswerByAnswerId() throws Exception {
		Answer answer = answerService.selectAnswerByAnswerId(BigInteger.valueOf(1));
		assertNotNull(answer);
		System.err.println("\n selectAnswerByAnswerId() \n"+ JSON.toJSONString(answer) + "\n");
	}
	
	@Test
	public void selectAnswerQuestionByAnswerId() throws Exception {
		
		List<AnswerQuestion> list =answerService.selectAnswerQuestionByAnswerId(BigInteger.valueOf(1));
		assertNotNull(list);
		assertNotEquals(0,list.size());
		System.err.println("\n selectAnswerQuestionByAnswerId() \n"+ JSON.toJSONString(list) + "\n");
		
	}
	
	@Test
	public void selectQuestionKindByAnswerId() throws  Exception{
		List<Kind> list = answerService.selectQuestionKindByAnswerId(BigInteger.valueOf(1));
		
		assertNotNull(list);
		assertNotEquals(0,list.size());
		System.err.println("\n selectQuestionKindByAnswerId() \n"+ JSON.toJSONString(list) + "\n");
		
	}
	
	
	@Test
	public void selectAnswerByExamIdAndStudentId() throws Exception {
		
		Answer answer =answerService.selectAnswerByExamIdAndStudentId(BigInteger.valueOf(1),
				BigInteger.valueOf(1));
		assertNotNull(answer);
		System.err.println("\n selectAnswerByExamIdAndStudentId() \n"+ JSON.toJSONString(answer) + "\n");
		
	}
}