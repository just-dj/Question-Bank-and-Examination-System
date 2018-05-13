package justdj.top.dao;

import justdj.top.pojo.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.math.BigInteger;
import java.util.List;

@CacheNamespace(implementation = justdj.top.cache.MybatisRedisCache.class)
public interface UserMapper {
	
	@Select("select * from user where id = #{userId}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "account",property = "account"),
			@Result(column = "password",property = "password"),
			@Result(column = "salt",property = "salt"),
			@Result(column = "name",property = "name"),
			@Result(column = "age",property = "age"),
			@Result(column = "sex",property = "sex"),
			@Result(column = "is_use",property = "use")
	})
	User selectUserById(BigInteger userId);
	
	
	@Select("select * from user where account = #{userAccount}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "account",property = "account"),
			@Result(column = "password",property = "password"),
			@Result(column = "salt",property = "salt"),
			@Result(column = "name",property = "name"),
			@Result(column = "age",property = "age"),
			@Result(column = "sex",property = "sex"),
			@Result(column = "is_use",property = "use")
	})
	User selectUserByAccount(String userAccount);
	
	@Select("select permission_name from user_role join permission on user_role.role_id = permission.role_id " +
			"where user_role.user_id = #{userId}")
	List<String> selectPermissionByUserId(BigInteger userId);
	
	
	@Select(value = " select  role_name from role join user_role on role.id = user_role.role_id where " +
			"user_role.user_id = #{userId}")
	List<String> selectRoleByUserId(BigInteger userId);
}
