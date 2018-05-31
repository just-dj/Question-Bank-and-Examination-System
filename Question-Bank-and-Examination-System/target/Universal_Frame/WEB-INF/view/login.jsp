<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login</title>
<link href="../static/css/login.css" rel="stylesheet">
</head>
<body>
<div class="body_box">
	<div class="login_box">
		<div class="login_box_bar">
			<span>登录</span>
		</div>
		
		<div class="login_box_body">
			<form id="form_login" name="form_login" action="/login" method="post">
				<input type="text" name="account" placeholder="请输入账号" /><br>
				<input type="password" name="pwd" placeholder="请输入密码" /><br>
				<div class="identify_box">
					<div>
						<input id="identify" type="text" name="identifyNum" placeholder="请输入验证码"/>
						<img src="/getGifCode"><br>
					</div>
				</div>
				<div class="pwd_box">
					<div>
						<input id="checkbox" type="checkbox" name="checkbox1" value="记住密码" />
						<span>记住密码</span>
					</div>
				</div>
				<div class="btn_box">
						<input class="login_box_btn" type="submit" value="登录">
				</div>
			</form>
		</div>
		
		<div class="login_box_footer">
			<a href="/register">还没有账号？马上注册>></a>
		</div>
	</div>
</div>
</body>
</html>