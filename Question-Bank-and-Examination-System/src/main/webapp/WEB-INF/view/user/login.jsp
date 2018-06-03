<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login</title>
<link href="../static/css/login.css" rel="stylesheet">
	<link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
</head>
<body>
<div class="body_box">
	<div class="login_box">
		<div class="login_box_bar">
			<span>登录</span>
		</div>
		
		<div class="login_box_body">
			<form id="form_login" name="form_login" action="/login" method="post" onsubmit="return check()">
				<c:if test="${message != null}">
					<div class="btn_box">
						<div class="myCheckbox">
							<span class="text-danger">${message}</span>
						</div>
					</div>
				</c:if>
				<input type="text" name="account" placeholder="请输入账号" value="${user.account}"/><br>
				<input type="password" name="password" placeholder="请输入密码" /><br>
				<div class="identify_box">
					<div>
						<input id="identify" type="text" name="identifyNum" placeholder="请输入验证码"/>
						<img id="image" onclick="changeImage()" src="/getGifCode"><br>
					</div>
				</div>
				<div class="btn_box">
					<div class="myCheckbox">
						<input style="margin: 0px" id="checkbox" type="checkbox" name="rember" value="true"  />
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

<script>
    function changeImage(){
        console.log("刷新验证码")
        var obj = document.getElementById("image");
        obj.src = "/getGifCode?a="+Math.random();
    }

    function check() {
        var condition = true;
        var account = $("input[name='account']").val();
        var password = $("input[name='password']").val();
        var code = $("input[name='identifyNum']").val();

        if (empty(account) ){
            alert("账号不合法！");
            condition = false;
        }else  if(empty(password) || password < 6){
            alert("密码不合法！");
            condition = false;
        }else if (empty(code)){
            alert("验证码不能为空！");
            condition = false;
		}
        return condition;
    }

    function empty(a) {
        return a==null || a=="" || a.trim()=="";
    }
</script>
<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
</body>
</html>