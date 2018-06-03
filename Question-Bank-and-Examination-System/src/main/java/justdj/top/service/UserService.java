package justdj.top.service;

import justdj.top.pojo.User;
import org.apache.ibatis.annotations.Param;

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
}
