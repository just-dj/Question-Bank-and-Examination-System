/*
  Created by IntelliJ IDEA.
  User: 霜
  Date: 18.4.20
  Time: 16:04
*/

package justdj.top.realm;

import justdj.top.pojo.User;
import justdj.top.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IDEA
 * Created by ${jie.chen} on 2016/7/14.
 * Shiro自定义域
 */

//用来获取用户信息,用户相应的角色/权限 并提供给SecurityManager
//可以把Realm看成DataSource，即安全数据源

//	我们需要给Shiro的SecurityManager注入Realm，
// 从而让SecurityManager能得到合法的用户及其权限进行判断

public class MyRealm extends AuthorizingRealm {
	
	private  final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	private EhCacheManager shiroEhcacheManager;
	
	/**
	 * 用于的权限的认证。
	 * 表示根据用户身份获取授权信息。
	 * @param principalCollection
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		logger.warn("获取用户权限信息！");
		//获取登陆时输入的用户ID
		String account = principalCollection.getPrimaryPrincipal().toString() ;
		User user = userService.selectUserByAccount(account);
		//权限信息对象info用来存放用户的所有角色和权限
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo() ;
		
		//用户角色集合
		Set<String> roleName = new HashSet <>(userService.selectRoleByUserId(user.getId()));
		
		//用户角色对应的所有权限
		Set<String> permissions = new HashSet <>(userService.selectPermissionByUserId(user.getId()));
		
		info.setRoles(roleName);
		info.setStringPermissions(permissions);
		logger.info("权限信息获取完成");
		return info;
	}
	/**
	 * 首先执行这个登录验证
	 * 表示获取身份验证信息
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		//获取用户账号
		if (token.getPrincipal() == null)
			return null;
		String account = token.getPrincipal().toString() ;
		//	首先判断账号状态
		Cache passwordRetryCache = shiroEhcacheManager.getCache("passwordRetryCache");
		AtomicInteger retryCount = (AtomicInteger) passwordRetryCache.get(account);
		if (null != retryCount && retryCount.get() >= 5) {
			logger.warn("用户 "+account+" 账号已被锁定,已尝试 "+retryCount.get()+" 次" );
			throw new LockedAccountException("账户被锁定");
		}
		
		//查询用户是否存在
		User user = userService.selectUserByAccount(account) ;
		if (user != null){
			if (!user.getUse())
				throw new AuthenticationException("当前账号待管理员审核！") ;
			//登录认证info
			SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getAccount(),user.getPassword
					(), ByteSource.Util.bytes(user.getSalt()),getName()) ;
			logger.warn("登录验证执行完成");
			return authenticationInfo ;
		}else{
			throw new AuthenticationException("账号或密码错误！") ;
		}
	}
	
	/**
	 * 清除所有用户的缓存
	 */
	public void clearAuthorizationInfoCache() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if(cache!=null) {
			cache.clear();
		}
	}
	
	/**
	 * 清除指定用户的缓存
	 * @param user
	 */
	private void clearAuthorizationInfoCache(User user) {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		cache.remove(user.getId());
	}
	
//	使用的ehcache
	private void clearCache(){
		Subject subject = SecurityUtils.getSubject();
		PrincipalCollection principalCollection = subject.getPrincipals();
		logger.warn("清除用户 "+subject.getPrincipal() +" 缓存");
		super.clearCache(principalCollection);
	}
}