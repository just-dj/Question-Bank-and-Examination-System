/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.12
  Time: 23:01
*/

package justdj.top.service;

import justdj.top.pojo.Kind;

import java.util.List;

public interface KindService {
	
	List<Kind> selectAllKind();
	
	List<String> selectAllKindName();
}
