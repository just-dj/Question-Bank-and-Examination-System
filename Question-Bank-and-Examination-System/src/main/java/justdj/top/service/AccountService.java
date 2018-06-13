/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.6.12
  Time: 16:06
  Info:
*/

package justdj.top.service;

import org.apache.shiro.SecurityUtils;

public class AccountService {
	
	public static String getAccount(){
		return  SecurityUtils.getSubject().getPrincipal().toString();
	}
}
