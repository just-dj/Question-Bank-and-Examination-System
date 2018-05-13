/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.12
  Time: 22:57
*/

package justdj.top.dao;

import justdj.top.pojo.Kind;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface KindMapper {
	
	@Select("select id,name from kind")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "name",property = "name")
	})
	List<Kind> selectAllKind();
	
	@Select("select name from kind")
	List<String> selectAllKindName();
}
