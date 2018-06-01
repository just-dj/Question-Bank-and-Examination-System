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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;

@Controller
public class ManagerController {
	
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
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
	public void changeUserRole(@RequestParam("role")String[] roleList,
	                           Model model){

	}
	
	/**
	 *@author  ShanDJ
	 *@params []
	 *@return  void
	 *@date  18.6.1
	 *@description 添加用户界面 待完善
	 */
	@RequestMapping(value = "/ma/user/add",method = RequestMethod.GET)
	public void addStudentPage(){
	
	}
	
	/**
	 *@author  ShanDJ
	 *@params []
	 *@return  void
	 *@date  18.6.1
	 *@description 添加用户界面 待完善 接收数据
	 */
	@RequestMapping(value = "/ma/user/add",method = RequestMethod.POST)
	public void addStudent(){

	}
	

	
}
