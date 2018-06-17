package justdj.top.service;

import justdj.top.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

public interface RoleService {
	
	Role selectRole(@Param("roleId") BigInteger roleId);
	
	
	List<Role> selectAllRole();
	
	
	Integer addRoleWithPermission(Role role) throws RuntimeException;
	
	Integer addRole(Role role);
	
	Integer addPermission(@Param("roleId") BigInteger roleId,@Param("permissionName") String permissionName);
}
