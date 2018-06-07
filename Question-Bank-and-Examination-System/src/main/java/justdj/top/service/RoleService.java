package justdj.top.service;

import justdj.top.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

public interface RoleService {
	
	Role selectRole(@Param("roleId") BigInteger roleId);
	
	
	List<Role> selectAllRole();
}
