package justdj.top.service;

import com.alibaba.fastjson.JSON;
import justdj.top.JUnit4ClassRunner;
import justdj.top.pojo.Exam;
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
public class ExamServiceTest {
	
	
	@Autowired
	@Qualifier("examService")
	private ExamService examService;
	
	@Test
	public void selectExamByCourseId() throws Exception {
		List<Exam> list = examService.selectExamByCourseId(BigInteger.valueOf(1));
		assertNotNull(list);
		assertNotEquals(0,list.size());
		System.err.println("\n selectExamByCourseId() \n"+ JSON.toJSONString(list) + "\n");
	}
	
	@Test
	public void selectExamByExamId() throws Exception {
		Exam exam = examService.selectExamByExamId(BigInteger.valueOf(1));
		assertNotNull(exam);
		System.err.println("\n selectExamByExamId() \n"+ JSON.toJSONString(exam) + "\n");
	}
	
}