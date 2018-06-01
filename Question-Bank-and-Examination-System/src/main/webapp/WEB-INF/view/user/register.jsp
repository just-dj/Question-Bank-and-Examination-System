<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>register</title>
<link href="../static/css/register.css" rel="stylesheet">
</head>
<body>
<div class="body_box">
	<div class="register_box">
		<div class="register_box_bar">
			<span>注册</span>
		</div>
		<div class="register_box_body">
			<form id="form_register" name="form_register" action="" method="post">
				<input type="text" placeholder="请输入姓名" name="name"/><br>
				<input type="text" placeholder="请输入账户" name="accountNum"/><br>
				<input type="password" placeholder="请输入6位密码" name="pwd"/><br>
				<input type="password" placeholder="请确认密码" name="pwdCheck"/><br>
				<input type="text" placeholder="请输入邮箱" name="e_mail"/><br>
				<div class="identify_box">
					<div>
						<input id="identify" type="text" placeholder="请输入验证码" name="identifyCode" />
						<div class="register_box_body_btn" >
							<span>发送验证</span>
						</div><br>
					</div>
				</div>
				<input id="submit" type="submit" value="提交" /><br>
			</form>
		</div>
		<div class="register_box_footer">
			<a href="/login">已有账号，马上登录>></a>
		</div>
	</div>
</div>
</body>
</html>