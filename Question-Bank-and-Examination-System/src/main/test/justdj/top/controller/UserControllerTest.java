package justdj.top.controller;

import justdj.top.JUnit4ClassRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;


@RunWith(JUnit4ClassRunner.class)  //此处调用Spring单元测试类
@WebAppConfiguration()//声明为集成测试加载的ApplicationContext应该是WebApplicationContext类型
//下面的注解告诉测试运行器如何加载和配置WebApplicationContext
@ContextConfiguration(locations = {"/spring/test_springContext.xml","/spring/test_spring-shiro.xml",
		"/test_springMVC.xml"})
public class UserControllerTest {
	
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setUp(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	
	@Test
	public void sendEmail() throws Exception {
	
	}
	
	
	
}