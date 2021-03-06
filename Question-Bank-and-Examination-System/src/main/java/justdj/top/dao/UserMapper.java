package justdj.top.dao;

import justdj.top.pojo.Role;
import justdj.top.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.List;

//@CacheNamespace(implementation = justdj.top.cache.MybatisRedisCache.class)
public interface UserMapper {
	
	@Select("select * from user where id = #{userId}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "account",property = "account"),
			@Result(column = "email",property = "email"),
			@Result(column = "password",property = "password"),
			@Result(column = "salt",property = "salt"),
			@Result(column = "name",property = "name"),
			@Result(column = "age",property = "age"),
			@Result(column = "sex",property = "sex"),
			@Result(column = "img",property = "img"),
			@Result(column = "is_use",property = "use")
	})
	User selectUserById(BigInteger userId);
	
	@Select("select * from user")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "account",property = "account"),
			@Result(column = "email",property = "email"),
			@Result(column = "password",property = "password"),
			@Result(column = "salt",property = "salt"),
			@Result(column = "name",property = "name"),
			@Result(column = "age",property = "age"),
			@Result(column = "sex",property = "sex"),
			@Result(column = "img",property = "img"),
			@Result(column = "is_use",property = "use")
	})
	List<User> selectAllUser();
	
	@Select("select * from user where account = #{userAccount}")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "account",property = "account"),
			@Result(column = "email",property = "email"),
			@Result(column = "password",property = "password"),
			@Result(column = "salt",property = "salt"),
			@Result(column = "name",property = "name"),
			@Result(column = "age",property = "age"),
			@Result(column = "sex",property = "sex"),
			@Result(column = "img",property = "img"),
			@Result(column = "is_use",property = "use")
	})
	User selectUserByAccount(String userAccount);
	
	@Select("select permission_name from user_role join permission on user_role.role_id = permission.role_id " +
			"where user_role.user_id = #{userId}")
	List<String> selectPermissionByUserId(BigInteger userId);
	
	
	@Select(value = " select  role_name from role join user_role on role.id = user_role.role_id where " +
			"user_role.user_id = #{userId}")
	List<String> selectRoleByUserId(BigInteger userId);
	
	
	@Insert(value = "insert into user (account,email,password,salt,name,age,sex,is_use) \n" +
			"values (#{account},#{email},#{password},#{salt},#{name},#{age},#{sex},#{use})")
	//	获取数据库内部生成主键,将返回值赋值给id属性
	@Options(useGeneratedKeys = true,keyProperty = "id",flushCache = true)
	Integer insertUser(User user);
	
	
	@Select("select class.id\n" +
			"from user join class_student  join class join course\n" +
			"on user.id=class_student.student_id and class_id=class.id and course_id=course.id\n" +
			"where user.id=#{studentId} and course.id=#{courseId};")
	BigInteger selectClassByStudentIdAndCourseId(@Param("studentId") BigInteger studentId,
	                                          @Param("courseId") BigInteger courseId);
	
	@Update("update user set password = #{password} , salt = #{salt}" +
			"where account = #{account}")
	@Options(flushCache = true)
	Integer changePassword(User user);
	
	@Insert("insert into user_role (user_id,role_id) " +
			"values (#{userId},#{roleId})")
	@Options(flushCache = true)
	Integer addRoleToUser(@Param("userId") BigInteger userId,
	                      @Param("roleId")BigInteger roleId);
	
	@Delete("delete from user_role where user_id = #{userId}")
	@Options(flushCache = true)
	Integer deleteRole(@Param("userId") BigInteger userId);
	
	@Update("update user set img=#{img} where id = #{id}")
	@Options(flushCache = true)
	Integer updateUserImg(User user);
	
	
	@Update("update user set is_use = #{kind} where id = #{id}")
	@Options(flushCache = true)
	Integer stopUser(@Param("id") BigInteger userId,@Param("kind") Boolean kind);
	
	@Select("select * from user where account like #{account} and name like #{name} ")
	@Results({
			@Result(id = true,column = "id",property = "id"),
			@Result(column = "account",property = "account"),
			@Result(column = "email",property = "email"),
			@Result(column = "password",property = "password"),
			@Result(column = "salt",property = "salt"),
			@Result(column = "name",property = "name"),
			@Result(column = "age",property = "age"),
			@Result(column = "sex",property = "sex"),
			@Result(column = "img",property = "img"),
			@Result(column = "is_use",property = "use")
	})
	List<User> selectUserByCondition(@Param("account")String account,
	                              @Param("name")String name);
	
	
	
}
