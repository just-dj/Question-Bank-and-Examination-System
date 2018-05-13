package justdj.top.service;

import com.alibaba.fastjson.JSON;
import justdj.top.JUnit4ClassRunner;
import justdj.top.pojo.Clazz;
import justdj.top.pojo.Course;
import justdj.top.pojo.Knowledge;
import justdj.top.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

import java.math.BigInteger;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4ClassRunner.class)  //此处调用Spring单元测试类
@WebAppConfiguration()//声明为集成测试加载的ApplicationContext应该是WebApplicationContext类型
//下面的注解告诉测试运行器如何加载和配置WebApplicationContext
@ContextConfiguration(locations = {"/spring/test_springContext.xml","/spring/test_spring-shiro.xml"})
public class CourseServiceTest {
	
	@Resource(name = "courseService")
	CourseService courseService;
	
	@Test
	public void test()throws Exception{
		assertNotNull(courseService);
	}
	
	@Test
	public void selectCourseByTeacherId() throws Exception {
		List<Course> courseList = courseService.selectCourseByTeacherId(BigInteger.valueOf(2));
		assertNotNull(courseList);
		assertNotEquals(0,courseList.size());
		assertEquals(2,courseList.size());
		System.err.println("\n selectCourseByTeacherId() \n"+JSON.toJSONString(courseList) + "\n");
	}
	
	@Test
	public void selectCourseByCourseId() throws Exception {
		Course course = courseService.selectCourseByCourseId(BigInteger.valueOf(1));
		assertNotNull(course);
		assertNotNull(course.getClazzList());
		assertNotEquals(0,course.getClazzList().size());
		assertNotNull(course.getKnowledgeList());
		assertNotEquals(0,course.getKnowledgeList().size());
		System.err.println("\n selectCourseByCourseId( ) \n"+JSON.toJSONString(course) + "\n");
	}
	
	@Test
	public void selectKnowledgeByCourseId() throws Exception {
		List<Knowledge> list = courseService.selectKnowledgeByCourseId(BigInteger.valueOf(1));
		assertNotNull(list);
		System.err.println("\n selectKnowledgeByCourseId() \n"+JSON.toJSONString(list) + "\n");
	}
	
	@Test
	public void selectClazzByCourseId() throws Exception {
		List<Clazz> list = courseService.selectClazzByCourseId(BigInteger.valueOf(1));
		assertNotNull(list);
		System.err.println("\n selectClazzByCourseId() \n"+JSON.toJSONString(list) + "\n");
	}
	
	@Test
	public void selectStudentByClassId() throws Exception {
		List<User> list = courseService.selectStudentByClassId(BigInteger.valueOf(1));
		assertNotNull(list);
		System.err.println("\n selectStudentByClassId() \n"+JSON.toJSONString(list) + "\n");
	}
	
}