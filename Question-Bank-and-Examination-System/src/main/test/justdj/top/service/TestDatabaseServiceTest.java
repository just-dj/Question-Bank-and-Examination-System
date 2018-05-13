package justdj.top.service;

import com.alibaba.fastjson.JSON;
import justdj.top.JUnit4ClassRunner;
import justdj.top.pojo.Kind;
import justdj.top.pojo.Question;
import justdj.top.pojo.TestDatabase;
import justdj.top.util.KindHelper;
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
public class TestDatabaseServiceTest {
	
	@Autowired
	@Qualifier("testDatabaseService")
	private TestDatabaseService testDatabaseService;
	
	@Autowired
	@Qualifier("kindService")
	private KindService kindService;
	
	@Test
	public void selectTestDatabaseByCourseId() throws Exception {
		KindHelper.setKindService(kindService);
		assertNotNull(testDatabaseService);
		List<TestDatabase> list = testDatabaseService.selectTestDatabaseByCourseId(BigInteger.valueOf(1));
		assertNotNull(list);
		assertEquals(2,list.size());
		System.err.println("\n" +"selectTestDatabaseByCourseId()" + "\n");
		System.err.println("\n" +JSON.toJSONString(list) + "\n");
	}
	
	@Test
	public void selectTestDatabaseQuestionByKindName() throws Exception {
		KindHelper.setKindService(kindService);
		assertNotNull(KindHelper.getKindNameList());
		List<Question> list = testDatabaseService.selectTestDatabaseQuestionByKindName(BigInteger.valueOf(1),
				KindHelper.getKindNameList().get(0));
		assertNotNull(list);
		assertNotEquals(0,list.size());
		System.err.println("\n" +"selectTestDatabaseQuestionByKindName()"+ "\n");
		for (String a:KindHelper.getKindNameList()){
			list = testDatabaseService.selectTestDatabaseQuestionByKindName(BigInteger.valueOf(1), a);
			System.err.println("\n" +JSON.toJSONString(list) + "\n");
		}
		
	}
	
	@Test
	public void selectTestDatabaseQuestionByKindId() throws Exception {
		KindHelper.setKindService(kindService);
		List<Question> list = testDatabaseService.selectTestDatabaseQuestionByKindId(BigInteger.valueOf(1),
				BigInteger.valueOf(1));
		assertNotNull(list);
		assertNotEquals(0,list.size());
		System.err.println("\n" +"selectTestDatabaseQuestionByKindId()" + "\n");
		System.err.println("\n" + JSON.toJSONString(list) + "\n");
	}
	
}