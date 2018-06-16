/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.6.1
  Time: 9:53
  Info:
*/

package justdj.top.controller.manager;

import com.alibaba.fastjson.JSON;
import justdj.top.pojo.Role;
import justdj.top.pojo.User;
import justdj.top.service.RoleService;
import justdj.top.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigInteger;
import java.util.*;

@Controller
public class ManagerController {
	
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;
	
	
	private Logger logger = LoggerFactory.getLogger(ManagerController.class);
	
	@Autowired
	SecureRandomNumberGenerator secureRandomNumberGenerator;
	/**
	 *@author  ShanDJ
	 *@params [managerId, model]
	 *@return  void
	 *@date  18.6.1
	 *@description 管理员主页
	 */
	@RequestMapping(value = "/ma",method = RequestMethod.GET)
	public String managerMainPage(
	                            Model model){
		
		Subject subject = SecurityUtils.getSubject();
		
		User user = userService.selectUserByAccount(subject.getPrincipal().toString());
		
		
		model.addAttribute(user);
		
		return "/ma/adminHome";
	}
	
	
	
	@RequestMapping("/ma/userManager")
	public String userManager(Model model){
		
		List<User> userList = userService.selectAllUser();
		
		model.addAttribute("users",userList);
		
	     return "/ma/adminUserManage";
	}
	
	/**
	 *@author  ShanDJ
	 *@params [model]
	 *@return  void
	 *@date  18.6.1
	 *@description 用户管理界面 待完善
	 *
	 */
	@RequestMapping(value = "/ma/user",method = RequestMethod.GET)
	public void managerUser(Model model){
		//可能要分页筛选用户
	
		//一个获取所有角色的dao层
		
		
	}
	
	
	
	/**
	 *@author  ShanDJ
	 *@params [account, name, model]
	 *@return  void
	 *@date  18.6.1
	 *@description 用户管理 待完善 根据账户和姓名筛选用户 纯接口
	 */
	@RequestMapping(value = "/ma/user",method = RequestMethod.POST)
	@ResponseBody
	public void getUserByCondition(@RequestParam(value = "account",required = false)String account,
	@RequestParam(value = "name",required = false)String name,
	                               Model model){
	  
		

	}
	
	
	/**
	 *@author  ShanDJ
	 *@params [roleList, model]
	 *@return  void
	 *@date  18.6.1
	 *@description 用户管理 待完善 更改用户的 角色
	 */
	@RequestMapping(value = "/ma/user/role",method = RequestMethod.GET)
	@ResponseBody
	public String changeUserRole(@RequestParam("userId")BigInteger userId,
				@RequestParam("role")BigInteger[] roleList,
	             Model model){
		User user = userService.selectUserById(userId);
		if (null == roleList){
			logger.warn("管理员未对账户" + user.getAccount()  +"分配角色，默认学生角色。");
			roleList = new BigInteger[]{BigInteger.valueOf(1)};
		}
		int result = userService.updateRole(userId,Arrays.asList(roleList));
		logger.info("账户" + user.getAccount() +"角色修改成功" + roleList);
		return "角色修改成功";
	}
	
	/**
	 *@author  ShanDJ
	 *@params []
	 *@return  void
	 *@date  18.6.1
	 *@description 添加用户界面 待完善 仅做跳转作用
	 */
	@RequestMapping(value = "/ma/user/add",method = RequestMethod.GET)
	public void addStudentPage(){

	
	}
	
	/**
	 *@author  ShanDJ
	 *@params []
	 *@return  void
	 *@date  18.6.1
	 *@description 添加用户界面 待完善 接收数据 批量导入还没处理
	 */
	@RequestMapping(value = "/ma/user/add",method = RequestMethod.POST)
	public String addStudent(@RequestParam("account")String account,
	                       @RequestParam("name")String name,
	                       @RequestParam("password")String password,
	                       @RequestParam("email")String email,
	                       @RequestParam("role")BigInteger[] roleList,
	                       RedirectAttributes redirectAttributes){
		
		//		这里为了更清楚的显示参数
		User user = new User();
		user.setAccount(account);
		user.setName(name);
		user.setPassword(password);
		user.setEmail(email);
		String salt = secureRandomNumberGenerator.nextBytes().toHex();
		//加密两次 盐为用户名+随机数
		String cryptedPwd = new SimpleHash("MD5",user.getPassword() , ByteSource.Util.bytes(salt),2).toHex();
		user.setPassword(cryptedPwd);
		user.setSalt(salt);
		user.setUse(true);
		//默认为学生权限
		if (null == roleList){
			logger.warn("管理员未对账户" +user.getAccount() +"分配角色，默认学生角色。");
			roleList = new BigInteger[]{BigInteger.valueOf(1)};
		}
		try {
			userService.addUserWithRole(user, Arrays.asList(roleList));
		}catch (Exception e){
			logger.error("账户插入失败，"+ e.getMessage());
			redirectAttributes.addFlashAttribute("message",name);
		}
		
		logger.info("账户" +user.getAccount() +"添加成功");
		redirectAttributes.addFlashAttribute("message","账户" +user.getAccount() +"添加成功");
		return "redirect:/ma/user/add";
	}
	
	
	@RequestMapping(value = "/ma/stop",method = RequestMethod.POST)
	@ResponseBody
	public String changeAccount(@RequestParam("kind")int kind,
	                          @RequestParam("id")BigInteger userId){
		Map<String,String> map = new HashMap();
		
		map.put("message","");
		
		
		if ( 0 == kind){
			//停用
			userService.stopUser(userId,false);
			logger.info("用户" + userId + "已被停用");
			map.put("message","停用账户成功");
			return JSON.toJSONString(map);
		}else if(1 == kind){
			userService.stopUser(userId,true);
			logger.info("用户" + userId + "已被启用");
			map.put("message","启用账户成功");
			return JSON.toJSONString(map);
		}
		else if (2 == kind){
			User userNow = userService.selectUserById(userId);
			String password = "123456";
			String salt = secureRandomNumberGenerator.nextBytes().toHex();
			//加密两次 盐为用户名+随机数
			String cryptedPwd = new SimpleHash("MD5",password , ByteSource.Util.bytes(salt),2).toHex();
			userNow.setPassword(cryptedPwd);
			userNow.setSalt(salt);
			int result = userService.changePassword(userNow);
			logger.info("用户" + userId + "密码已被停用");
			map.put("message","密码重置成功");
			return JSON.toJSONString(map);
		}
		
		map.put("message","数据错误，请稍后重试");
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "/ma/search",method = RequestMethod.POST)
	@ResponseBody
	public String searchUser(@RequestParam("account")String account,
	                         @RequestParam("name")String name){
		
		
		List<User> userList = null;
		String newAccount = '%'+account +'%';
		String newName ='%' + name + '%';
		try{
			userList = userService.selectUserByCondition(newAccount,newName);
		}
		catch (RuntimeException e){
			e.printStackTrace();
		}
		
		if (null != account && !account.trim().equals("")){
			for (User u:userList) {
				u.setAccount(u.getAccount().replace(account,"<span class='text-danger'>"+account+"<span>"));
				System.err.println(u.getAccount());
			}
		}
		if (null != name && !name.trim().equals("")){
			for (User u:userList) {
				u.setName(u.getName().replace(name,"<span class='text-danger'>"+name+"<span>"));
				System.err.println(u.getName());
			}
		}
		
		
		return JSON.toJSONString(userList);
	}
	
	
	@RequestMapping(value = "/ma/getUserRole",method = RequestMethod.POST)
	@ResponseBody
	public String getUserRole(@RequestParam("id")BigInteger userId){
		List<String> roleList = userService.selectRoleByUserId(userId);
		
		
		return JSON.toJSONString(roleList);
	};
	
	
	@RequestMapping("/ma/changeUserRole")
	@ResponseBody
	public String changeUserRole(@RequestParam("id")BigInteger userId,
	                             @RequestParam("role")String[] roleArray){
		
		List<BigInteger> roleId = new ArrayList <>();
		User user = userService.selectUserById(userId);
		
		if (Arrays.asList(roleArray).contains("student"))
			roleId.add(BigInteger.valueOf(1));
		if(Arrays.asList(roleArray).contains("teacher"))
			roleId.add(BigInteger.valueOf(2));
		if (Arrays.asList(roleArray).contains("manager"))
			roleId.add(BigInteger.valueOf(3));
		
		try{
			userService.updateRole(userId,roleId);
		}catch (RuntimeException e){
//			e.printStackTrace();
			logger.info("修改用户角色失败！" + userId );
			return "修改用户角色失败！";
		}
		
		logger.info("成功修改用户角色！" + userId);
		return "成功修改用户角色！";
	}
	
}
