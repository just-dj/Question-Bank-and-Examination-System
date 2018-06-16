/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.12
  Time: 17:54
*/

package justdj.top.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
public class Knowledge implements Serializable {
	
	private BigInteger id;
	
	private String name;
	
	private String introduce;
}
