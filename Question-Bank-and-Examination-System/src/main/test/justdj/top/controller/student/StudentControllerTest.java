package justdj.top.controller.student;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(JUnit4ClassRunner.class)  //此处调用Spring单元测试类
@WebAppConfiguration()//声明为集成测试加载的ApplicationContext应该是WebApplicationContext类型
//下面的注解告诉测试运行器如何加载和配置WebApplicationContext
@ContextConfiguration(locations = {"/spring/test_springContext.xml","/spring/test_spring-shiro.xml",
		"/test_springMVC.xml","/spring/test_spring-activemq.xml"})
public class StudentControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setUp(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	
	@Test
	public  void getStudentInfoByStudentId() throws Exception{
		mockMvc.perform(get("/st/info?id=1"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("user"))
				.andDo(print());
		
	}
	
	
	@Test
	public void selectCourseByStudentId() throws Exception {
		
		mockMvc.perform(get("/st?id=1"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("courseList"))
				.andDo(print());
		
	}
	
	
	@Test
	public void selectCourseInfoByCourseId() throws Exception{
		
		mockMvc.perform(get("/st/course?id=1"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("knowledgeList"))
				.andExpect(model().attributeExists("examList"))
				.andDo(print());
	}
	
	
	@Test
	public void selectExamByCourseId() throws Exception{
		mockMvc.perform(get("/st/course/examInfo?id=1"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("examList"))
				.andDo(print());
	}
	
	@Test
	public void startExam()throws Exception{
		mockMvc.perform(get("/st/course/exam?id=1"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("testPaper"))
				.andExpect(model().attributeExists("kindList"))
				.andDo(print());
	}
	
	@Test
	public void getQuestionByQuestionKind() throws Exception{
		mockMvc.perform(get("/st/course/exam/question?id=1&&kind=单选题"))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	@Test
	public void selectAnswerByExamIdAndStudentId() throws Exception{
		mockMvc.perform(get("/st/course/exam/answer?id=1"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("answer"))
				.andExpect(model().attributeExists("answerQuestionList"))
				.andExpect(model().attributeExists("kindList"))
				.andDo(print());
	}
	
	
}