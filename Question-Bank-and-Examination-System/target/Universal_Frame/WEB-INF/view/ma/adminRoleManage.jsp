<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
<link href="/static/css/adminRoleManage.css" rel="stylesheet" type="text/css" />
<title>用户角色管理</title>
</head>
<body>


<%@ include file="../head.jsp" %>

	<div class="containers">

		<div class="main">
			<div class="role-table">
				<table class="table">
					<tr>
						<th>角色ID</th>
						<th>角色名</th>
						<th>角色权限</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${roleList}" var="role" varStatus="status">
						<tr>
							<td>${role.id}</td>
							<td>${role.name}</td>
							<td>${role.permission.toString()}</td>
							<td><button class="role-delete">删除</button></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="role-add">
				<form>
					<div class="role-add-text" style="font-weight: bold">角色名</div>
					<input type="text" name="role-name" id="role-name">
					<div class="role-add-text">
						<span style="font-weight: bold">权限</span>
						<br>
						<input type="checkbox" value="insert" name="">insert<br>
						<input type="checkbox" value="delete" name="">delete<br>
						<input type="checkbox" value="update" name="">update<br>
						<input type="checkbox" value="select" name="">select<br> 
					</div>
					<input class="btn btn-primary" type="submit" value="添加角色" id="role-submit">
					
				</form>
			</div>
		</div>
		
	</div>
	
</body>
</html>