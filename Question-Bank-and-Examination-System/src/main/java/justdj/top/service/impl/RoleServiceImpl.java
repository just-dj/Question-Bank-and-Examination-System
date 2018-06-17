/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.6.6
  Time: 23:21
  Info:
*/

package justdj.top.service.impl;

import justdj.top.dao.RoleMapper;
import justdj.top.pojo.Role;
import justdj.top.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleMapper roleMapper;
	
	
	@Override
	public Role selectRole(BigInteger roleId) {
		return roleMapper.selectRole(roleId);
	}
	
	@Override
	public List<Role> selectAllRole() {
		return roleMapper.selectAllRole();
	}
	
	
	@Override
	public Integer addRoleWithPermission(Role role) throws RuntimeException {
		
		Integer a = roleMapper.addRole(role);
		
		for (String name:role.getPermission()){
			roleMapper.addPermission(role.getId(),name);
		}
		
		return  a;
	}
	
	
	@Override
	public Integer addRole(Role role) {
		return roleMapper.addRole(role);
	}
	
	@Override
	public Integer addPermission(BigInteger roleId, String permissionName) {
		return roleMapper.addPermission(roleId,permissionName);
	}
}
