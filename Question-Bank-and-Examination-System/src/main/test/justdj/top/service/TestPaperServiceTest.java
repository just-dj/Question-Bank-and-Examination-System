package justdj.top.service;

import com.alibaba.fastjson.JSON;
import justdj.top.pojo.Kind;
import justdj.top.pojo.Question;
import justdj.top.pojo.TestPaper;
import justdj.top.util.KindHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

import java.math.BigInteger;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)  //此处调用Spring单元测试类
@WebAppConfiguration()//声明为集成测试加载的ApplicationContext应该是WebApplicationContext类型
//下面的注解告诉测试运行器如何加载和配置WebApplicationContext
@ContextConfiguration(locations = {"/spring/test_springContext.xml","/spring/test_spring-shiro.xml"})
public class TestPaperServiceTest {
	
	@Autowired
	@Qualifier("testPaperService")
	TestPaperService testPaperService;
	
	@Autowired
	@Qualifier("kindService")
	private KindService kindService;
	
	@Test
	public void selectTestPaperByPaperId() throws Exception {
		TestPaper testPapers = testPaperService.selectTestPaperByPaperId(BigInteger.valueOf(1));
		assertNotNull(testPapers);
		System.err.println("\n selectTestPaperByPaperId()\n"+ JSON.toJSONString(testPapers) + "\n");
	}
	
	@Test
	public void selectTestPaperByCourseId() throws Exception {
		List<TestPaper> list = testPaperService.selectTestPaperByCourseId(BigInteger.valueOf(1));
		assertNotNull(list);
		assertNotEquals(0,list.size());
		System.err.println("\n selectTestPaperByCourseId() \n"+ JSON.toJSONString(list) + "\n");
	}
	
	@Test
	public void selectQuestionByTestPaperId() throws Exception {
		List<Question> list = testPaperService.selectQuestionByTestPaperId(BigInteger.valueOf(1));
		assertNotNull(list);
		assertNotEquals(0,list.size());
		System.err.println("\n  selectQuestionByTestPaperId() \n"+ JSON.toJSONString(list) + "\n");
	}
	
	
	@Test
	public void selectQuestionKindByTestPaperId() throws Exception{
		List<Kind> list = testPaperService.selectQuestionKindByTestPaperId(BigInteger.valueOf(1));
		assertNotNull(list);
		assertNotEquals(0,list.size());
		System.err.println("\n  selectQuestionKindByTestPaperId() \n"+ JSON.toJSONString(list) + "\n");
	}
	
	
	@Test
	public void selectQuestionByTestPaperIdAndKindName() throws Exception {
		KindHelper.setKindService(kindService);
		for (String a:KindHelper.getKindNameList()){
			List<Question> list = testPaperService.selectQuestionByTestPaperIdAndKindName(BigInteger.valueOf(1),a);
			assertNotNull(list);
			assertNotEquals(0,list.size());
			System.err.println("\n  selectQuestionByTestPaperIdAndKindName() "+a+"\n"+ JSON.toJSONString(list) + "\n");
		}
		
	}
	
	@Test
	public void selectQuestionByTestPaperIdAndKindId() throws Exception {
		KindHelper.setKindService(kindService);
		for (String a:KindHelper.getKindNameList()){
			List<Question> list  = testPaperService.selectQuestionByTestPaperIdAndKindId(BigInteger.valueOf(1),KindHelper.getKindId(a));
			assertNotNull(list);
			assertNotEquals(0,list.size());
			System.err.println("\n  selectQuestionByTestPaperIdAndKindId() "+a+"\n"+ JSON.toJSONString(list) + "\n");
		}
		
	}
	
}