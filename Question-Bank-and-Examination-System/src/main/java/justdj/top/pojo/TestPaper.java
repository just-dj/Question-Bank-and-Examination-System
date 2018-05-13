/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.13
  Time: 10:11
*/

package justdj.top.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Data
public class TestPaper implements Serializable {
	
	private BigInteger id;
	
	private BigInteger courseId;
	
	private String name;
	
	private boolean use;
	
	private List<Question> questionList;
	
}
