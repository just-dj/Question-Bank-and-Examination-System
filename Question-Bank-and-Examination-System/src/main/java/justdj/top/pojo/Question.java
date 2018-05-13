/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.12
  Time: 23:19
*/

package justdj.top.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
public class Question implements Serializable {
	
	private BigInteger id;
	
	private BigInteger kindId;
	
	private String kindName;
	
	private BigInteger testDatabaseId;
	
	private String question;
	
	private String a;
	
	private String b;
	
	private String c;
	
	private String d;
	
	private String answer;
	
	private Short score;
}
