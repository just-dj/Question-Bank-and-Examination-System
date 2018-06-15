/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.13
  Time: 19:42
*/

package justdj.top.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
public class AnswerQuestion implements Serializable {
	
	
	private BigInteger id;
	
	private BigInteger answerId;
	
	private BigInteger questionId;
	
	private BigInteger kindId;
	
	private String kindName;
	
	private String question;
	
	private String a;
	
	private String b;
	
	private String c;
	
	private String d;
	
	private String answer;
	
	private String userAnswer;
	
	private Short score;
	
	private Short questionScore;
	
}
