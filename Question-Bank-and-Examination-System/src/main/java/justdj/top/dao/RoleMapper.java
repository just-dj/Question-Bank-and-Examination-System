package justdj.top.dao;

import justdj.top.pojo.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.math.BigInteger;
import java.util.List;

//@CacheNamespace(implementation = justdj.top.cache.MybatisRedisCache.class)
public interface RoleMapper {
	
	@Select("select id,role_name from role where id = #{roleId}" )
	@Results({
			@Result(column = "role_name",property = "name"),
			@Result(column = "id",property = "permission",
					one = @One(select = "justdj.top.dao.selectPermission",fetchType = FetchType.EAGER))
	})
	Role selectRole(@Param("roleId") BigInteger roleId);
	
	@Select("select permission_name from permission where role_id = #{roleId}")
	List<String> selectPermission(@Param("roleId") BigInteger roleId);
	
	@Select("select id,role_name from role")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "role_name",property = "name"),
			@Result(column = "id",property = "permission",
					one = @One(select = "justdj.top.dao.RoleMapper.selectPermission",fetchType = FetchType.EAGER))
	})
	List<Role> selectAllRole();
}
