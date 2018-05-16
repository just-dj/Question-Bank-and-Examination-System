/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.13
  Time: 19:27
*/

package justdj.top.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

@Data
public class Answer implements Serializable {
	
	private BigInteger id;
	
	private BigInteger studentId;
	
	private BigInteger testPaperId;
	
	private Timestamp startTime;
	
	private Timestamp endTime;
	
	private Short result;
	
	private Boolean commit;
	
	private User student;
	
}
