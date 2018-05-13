/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.12
  Time: 18:55
*/

package justdj.top;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import java.io.FileNotFoundException;

public class JUnit4ClassRunner extends SpringJUnit4ClassRunner {
	
	static {
		try {
			Log4jConfigurer.initLogging("/property/test_log4j.properties");
		} catch (FileNotFoundException ex) {
			System.err.println("Cannot Initialize log4j");
		}
	}
	
	void JUnit4ClassRunner(){
	
	}
	
	public JUnit4ClassRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}
	
}