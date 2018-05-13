/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.12
  Time: 17:52
*/

package justdj.top.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Data
public class Course implements Serializable {
	private BigInteger id;
	
	private BigInteger teacherId;
	
	private String name;
	
	private String introduce;
	
	private List<Knowledge> knowledgeList;
	
	private List<Clazz> clazzList;
}
