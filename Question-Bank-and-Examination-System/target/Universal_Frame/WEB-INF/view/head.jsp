<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@page pageEncoding="UTF-8" %>
<head>
<style type="text/css">
body{
	margin:0;
}
	.head-header{
		width:100%;
		height:50px;
		display:flex;
		justify-content:space-between;
		align-items:center;
		background-color:#5ac1df;
		margin-bottom: 25px;
	}
	#head-title{
		margin-left:50px;
		color:#fff;
	}
	.head-dropdown-user{
		min-width: 100px;
		height:50px;
		background-color:#5ac1df;
		color:#fff;
		border: none;
		cursor: pointer;
	}
	.head-dropdown{
		position:relative;
		display:inline-block;
		margin-right:50px;
		color:#fff;
	}
	.head-dropdown-content{
		display: none;
		position: absolute;
		background-color: #91d5e9;
		min-width: 100px;
		box-shadow:0px 2px 2px 0px rgba(0,0,0,0.2);
	}
	.head-dropdown-content a{
		color:#fff;
		padding: 10px 10px;
		text-decoration: none;
		display: block;
	}
	.head-dropdown-content a:hover{
		background-color: #ACDFEE;
	}
	.head-dropdown:hover .head-dropdown-content{
		display:block;
	}
</style>
</head>
<div class="head-header">
	<div id="head-title">题库与考试系统</div>
	<div class="head-dropdown">

		<button class="head-dropdown-user">${sessionScope.user.name},你好！</button>
		<div class="head-dropdown-content">
			<shiro:hasRole name="teacher">
				<a href="/te">我教的课程</a>
			</shiro:hasRole>
			<shiro:hasRole name="student">
				<a href="/st">我学的课程</a>
			</shiro:hasRole>
			<a href="/logout">退出登录</a>
		</div>
	</div>
</div>
