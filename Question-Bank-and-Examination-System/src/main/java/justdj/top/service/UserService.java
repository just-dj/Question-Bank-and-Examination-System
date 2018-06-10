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
	
	Integer addUserWithRole(User user,List<BigInteger> roleList) throws Exception;
	
	Integer updateRole(BigInteger userId,List<BigInteger> roleList);
	
	Integer updateUserImg(User user);
}
