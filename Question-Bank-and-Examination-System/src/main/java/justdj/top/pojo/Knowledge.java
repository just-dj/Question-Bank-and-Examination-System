/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.12
  Time: 17:54
*/

package justdj.top.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Knowledge implements Serializable {
	
	private String name;
	
	private String introduce;
}
