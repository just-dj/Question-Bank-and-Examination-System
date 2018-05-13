/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.12
  Time: 17:56
*/

package justdj.top.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Data
public class Clazz implements Serializable{
	private BigInteger id;
	
	private String name;
	
	private List<User> userList;
	
}
