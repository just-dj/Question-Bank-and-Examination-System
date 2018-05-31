/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.13
  Time: 19:49
*/

package justdj.top.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Data
public class Exam implements Serializable {
	
	private BigInteger id;
	
	private BigInteger testPaperId;
	
	private BigInteger courseId;
	
	private String name;
	
	private Timestamp startTime;
	
	private Timestamp endTime;
	
	private Boolean use;
	
	private List<TestPaper> testPaperList;
	
	private List<BigInteger> classList;
}
