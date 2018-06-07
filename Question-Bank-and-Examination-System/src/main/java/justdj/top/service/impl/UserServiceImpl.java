/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.6
  Time: 15:32
*/

package justdj.top.service.impl;

import justdj.top.dao.UserMapper;
import justdj.top.pojo.User;
import justdj.top.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User selectUserById(BigInteger userId) {
		return userMapper.selectUserById(userId);
	}
	
	@Override
	public User selectUserByAccount(String userAccount) {
		return userMapper.selectUserByAccount(userAccount);
	}
	
	@Override
	public List <String> selectPermissionByUserId(BigInteger userId) {
		return userMapper.selectPermissionByUserId(userId);
	}
	
	@Override
	public List <String> selectRoleByUserId(BigInteger userId) {
		return userMapper.selectRoleByUserId(userId);
	}
	
	@Override
	public Integer insertUser(User user) {
		return userMapper.insertUser(user);
	}
	
	@Override
	public BigInteger selectClassByStudentIdAndCourseId(BigInteger studentId, BigInteger courseId) {
		return userMapper.selectClassByStudentIdAndCourseId(studentId,courseId);
	}
	
	@Override
	public Integer changePassword(User user) {
		return userMapper.changePassword(user);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer addUserWithRole(User user, List <BigInteger> roleList) throws Exception{
		User oldUser = userMapper.selectUserByAccount(user.getAccount());
		if (null == oldUser)
			throw new Exception("账号已存在！");
		int result = userMapper.insertUser(user);
		if (null == user.getId())
			throw new Exception("用户插入失败。");
		for (BigInteger roleId:roleList) {
			result = userMapper.addRoleToUser(user.getId(),roleId);
		}
		return result;
	}
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer updateRole(BigInteger userId, List <BigInteger> roleList)  {
		int result = 0;
		result = userMapper.deleteRole(userId);
		
		for (BigInteger roleId:roleList) {
			result = userMapper.addRoleToUser(userId,roleId);
		}
		
		return result;
	}
}
