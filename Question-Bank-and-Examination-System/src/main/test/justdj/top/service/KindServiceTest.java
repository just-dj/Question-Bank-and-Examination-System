package justdj.top.service;

import com.alibaba.fastjson.JSON;
import justdj.top.pojo.Kind;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)  //此处调用Spring单元测试类
@WebAppConfiguration()//声明为集成测试加载的ApplicationContext应该是WebApplicationContext类型
//下面的注解告诉测试运行器如何加载和配置WebApplicationContext
@ContextConfiguration(locations = {"/spring/test_springContext.xml","/spring/test_spring-shiro.xml"})
public class KindServiceTest {
	
	@Autowired
	@Qualifier("kindService")
	private KindService kindService;
	
	@Test
	public void selectAllKind() throws Exception {
		List<Kind> list = kindService.selectAllKind();
		assertNotNull(list);
		assertEquals(5,list.size());
		System.err.println("\n" + JSON.toJSONString(list) + "\n");
	}
	
	@Test
	public void selectAllKindName() throws Exception{
		List<String> list = kindService.selectAllKindName();
		assertNotNull(list);
		System.err.println("\n" + JSON.toJSONString(list) + "\n");
	}
}