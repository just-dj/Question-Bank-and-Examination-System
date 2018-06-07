/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.6.1
  Time: 9:53
  Info:
*/

package justdj.top.controller.manager;

import justdj.top.pojo.User;
import justdj.top.service.UserService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
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
import java.util.Arrays;

@Controller
public class ManagerController {
	
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
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
	public void managerMainPage(@RequestParam(value = "id",required = false)BigInteger managerId,
	                            Model model){
		User user = userService.selectUserById(managerId);
		
		
		model.addAttribute(user);
		
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
	

	
}
