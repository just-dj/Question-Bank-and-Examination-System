/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.6.6
  Time: 23:11
  Info:
*/

package justdj.top.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Data
public class Role implements Serializable {
	
	private BigInteger id;
	
	private String name;
	
	private List<String> permission;
}
