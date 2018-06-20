/*
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.6.20
  Time: 10:21
  Info:
*/

package justdj.top.controller;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler({UnauthorizedException.class, UnauthenticatedException.class})
	public String unauthorizedExceptionError(Exception ex,HttpServletRequest request){
//		System.err.println("权限异常");
		request.setAttribute("exception", ex);
		
		return "/error/403";
	}
	
	
	@ExceptionHandler({IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
	public String illegalArgumentExceptionError(Exception ex,HttpServletRequest request){
		
//		System.err.println("参数异常");
		request.setAttribute("exception", ex);
		
		return "/error/400";
	}
	
	
	
	@ExceptionHandler({Exception.class,RuntimeException.class})
	public String exceptionError(Exception ex,HttpServletRequest request){
		
//		System.err.println("其他异常");
		request.setAttribute("exception", ex);
		
		return "/error/error";
	}
	

	
}
