/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.12
  Time: 22:52
*/

package justdj.top.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
public class Kind implements Serializable{
	
	private BigInteger id;
	
	private String name;
	
}
