package justdj.top.service;

import justdj.top.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.List;

public interface UserService {
	
	User selectUserById(BigInteger userId);
	
	User selectUserByAccount(String userAccount);
	
	List<String> selectPermissionByUserId(BigInteger userId);
	
	List<String> selectRoleByUserId(BigInteger userId);
	
	Integer insertUser(User user);
	
	BigInteger selectClassByStudentIdAndCourseId(@Param("studentId") BigInteger studentId,
	                                          @Param("courseId") BigInteger courseId);
	
	Integer changePassword(User user);
	
	Integer addUserWithRole(User user,List<BigInteger> roleList) throws RuntimeException;
	
	Integer updateRole(BigInteger userId,List<BigInteger> roleList)throws RuntimeException;
	
	Integer updateUserImg(User user);
	
	List<User> selectAllUser();
	
	Integer stopUser(BigInteger userId,@Param("kind") Boolean kind);
	
	List<User> selectUserByCondition(@Param("account")String account,
	                                 @Param("name")String name) throws RuntimeException;
}
