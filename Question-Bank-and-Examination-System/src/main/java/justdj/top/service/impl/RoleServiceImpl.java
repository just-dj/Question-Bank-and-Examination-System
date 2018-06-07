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
}
