<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>register</title>
<link href="/static/css/register.css" rel="stylesheet">
	<link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
</head>
<body>
<div class="body_box">
	<div class="register_box">
		<div class="register_box_bar">
			<span>注册</span>
		</div>
		<div class="register_box_body">
			<form id="form_register" name="form_register" action="/register" method="post" onsubmit="return check()">
				<c:if test="${message != null}">
					<div class="btn_box">
						<div class="myCheckbox">
							<span class="text-danger">${message}</span>
						</div>
					</div>
				</c:if>
				<c:if test="${success != null}">
					<div class="btn_box">
						<div class="myCheckbox">
							<span class="text-success">${success}</span>
						</div>
					</div>
				</c:if>
				<input type="text" placeholder="请输入姓名" name="name" /><br>
				<input type="text" placeholder="请输入账户" name="account" /><br>
				<input type="password" placeholder="请输入6位密码" name="pwd" /><br>
				<input type="password" placeholder="请确认密码" name="pwdCheck" /><br>
				<input id="email" type="text" placeholder="请输入邮箱" name="email" /><br>
				<div class="identify_box">
					<div>
						<input id="identify" type="text" placeholder="请输入验证码" name="identifyCode" />
						<div class="register_box_body_btn" onclick="sendEmail()">
							<span>发送验证码</span>
						</div><br>
					</div>
				</div>
				<input class="submit" id="submit" type="submit" value="提交" /><br>
			</form>
		</div>
		<div class="register_box_footer">
			<a href="/login" style="color: #ffffff">已有账号，马上登录>></a>
		</div>
	</div>
</div>

<script>
	var timer = 65;
	var t;
    function sendEmail(){
        if (timer < 60){
            layer.msg("邮件发送过于频繁，请 "+ (60 - timer) +" S之后重试");
            return;
		}

        var email = $("#email").val();

        var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
        if (email == null || email == "" || !reg.test(email)) {
            layer.msg('邮箱格式不正确，请重新填写!');
            return ;
        }
        $.ajax({
            url: '/sendEmail',
            //type是无所谓的 但是get只能传递1kb数据
            type: 'Post',
            // dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
            data: {email:email},
        })
            .done(function(data) {
                console.log(email);
                layer.msg("已向 "  +  email + " 发送邮件，请查收！ 如遇网络延迟请耐心等待或重试。")
            })
            .fail(function() {
                console.log("error");
                layer.msg("邮件发送失败，请稍后重试！")
            })
            .always(function() {
                console.log("complete");
            });
        	timer = 0;
			timedCount();

    }

    function timedCount()
	{
        timer += 1;
        if (timer >= 62){
            clearTimeout(t);
		}
        else {
            t=setTimeout("timedCount()",1000);
		}


    }

    function check (){
        var condition = false;

        var name = $("input[name='name']").val();
        var account = $("input[name='account']").val();
        var password = $("input[name='pwd']").val();
        var password1 = $("input[name='pwdCheck']").val();
		var email = $("input[name='email']").val();
		var code = $("input[name='identifyCode']").val();

        if ( empty(name) || name.length > 100){
            layer.msg("用户名不合法！");
            condition = true;
		}else  if (empty(account) || account.length < 6){
            layer.msg("账号不合法！");
            condition = true;
		}else  if(empty(password) || password < 6){
		    layer.msg("密码不合法！");
            condition=true
		}else if (password != password1){
		    layer.msg("两次输入的密码不匹配！");
            condition = true;
		}else if (empty(code)){
		    layer.msg("验证码不能为空！");
            condition = true;
		}

        return (!condition);

    }

    function empty(a) {
		return a==null || a=="" || a.trim()=="";
    }

</script>
<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
<script src="/static/lib/layer-v3.1.1/layer/layer.js"></script>
</body>
</html>