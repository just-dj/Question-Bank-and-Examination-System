/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.6.1
  Time: 10:18
  Info:
*/

package justdj.top.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;

@Controller
public class RoleController {
	
	
	/**
	 *@author  ShanDJ
	 *@params []
	 *@return  void
	 *@date  18.6.1
	 *@description 角色管理界面 待完善
	 */
	@RequestMapping(value = "/ma/role",method = RequestMethod.GET)
	public void roleManagerPage(){

	}
	
	/**
	 *@author  ShanDJ
	 *@params [name, permissionList, model]
	 *@return  void
	 *@date  18.6.1
	 *@description 角色管理界面 待完善 添加角色
	 */
	@RequestMapping(value = "/ma/role",method = RequestMethod.POST)
	public void addRole(@RequestParam("name")String name,
		                @RequestParam("permission")String[] permissionList,
		                Model model){

	}
	
	
	/**
	 *@author  ShanDJ
	 *@params [roleId]
	 *@return  void
	 *@date  18.6.1
	 *@description 角色管理界面 待完善 删除角色 危险
	 */
	@RequestMapping(value = "/ma/role/delete",method = RequestMethod.POST)
	public void deleteRole(@RequestParam("id")BigInteger roleId){

	}
	
	
}
