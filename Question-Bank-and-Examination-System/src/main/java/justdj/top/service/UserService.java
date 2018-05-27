package justdj.top.service;

import justdj.top.pojo.User;

import java.math.BigInteger;
import java.util.List;

public interface UserService {
	
	User selectUserById(BigInteger userId);
	
	User selectUserByAccount(String userAccount);
	
	List<String> selectPermissionByUserId(BigInteger userId);
	
	List<String> selectRoleByUserId(BigInteger userId);
	
	Integer insertUser(User user);
	
}
