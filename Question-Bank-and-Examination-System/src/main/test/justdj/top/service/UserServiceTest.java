package justdj.top.service;

import justdj.top.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigInteger;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)  //此处调用Spring单元测试类
@WebAppConfiguration()//声明为集成测试加载的ApplicationContext应该是WebApplicationContext类型
//下面的注解告诉测试运行器如何加载和配置WebApplicationContext
@ContextConfiguration(locations = {"/spring/test_springContext.xml","/spring/test_spring-shiro.xml"})
public class UserServiceTest {
	
	@Autowired
	@Qualifier("userService")
	UserService userService;
	
	@Test
	public void selectUserById() throws Exception {
		assertNotNull(userService);
	}
	
	@Test
	public void insertUser(){
		User user = new User();
		user.setAccount("23");
		user.setEmail("2269090020@qq.com");
		user.setPassword("4ac86a78043b6d1dec8218304fd3ef9b");
		user.setSalt("3c16b6b092c0ea87bdeb5f0d3599f2b2");
		user.setName("大鹏");
		user.setAge(new Short("12"));
		user.setSex('男');
		user.setUse(false);
		int result = userService.insertUser(user);
		assertNotEquals(0,result);
		assertNotNull(user.getId());
		assertEquals(BigInteger.valueOf(25),user.getId());
	}
	
}