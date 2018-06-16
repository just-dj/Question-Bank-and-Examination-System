<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员主题</title>
<style>
	.containers{
		display:flex;
		flex-direction:column;
		justify-content:center;
		align-items:center;
	}
	.main{
		width:50%;
		margin-top:130px;
		display:flex;
		justify-content:space-between;
		align-items:center;
	}
	.main-user-manager{
		display:flex;
		width:280px;
		height:160px;
		background:#E4E4E4;
		align-items:center;
		justify-content:center;
		cursor:pointer;
	}
	.main-role-manager{
		display:flex;
		width:280px;
		height:160px;
		background:#E4E4E4;
		align-items:center;
		justify-content:center;
		cursor:pointer;
	}
</style>
</head>
<body>
<%@ include file="../head.jsp"%>

<div class=containers>
	<div class="main">
		<div class="main-user-manager" onclick="location.href='/ma/userManager'">用户管理</div>
		<div class="main-role-manager" onclick="location.href='/ma/role'">角色管理</div>
	</div>
</div>
</body>
</html>