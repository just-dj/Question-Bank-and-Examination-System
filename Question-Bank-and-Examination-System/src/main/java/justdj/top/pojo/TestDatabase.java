/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.12
  Time: 22:39
*/

package justdj.top.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
public class TestDatabase implements Serializable {
	
	private BigInteger id;
	
	private BigInteger courseId;
	
	private String name;
	
}
