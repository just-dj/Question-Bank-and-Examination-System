<%--
  Created by IntelliJ IDEA.
  User: 霜
  Date: 18.4.21
  Time: 23:33
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>请登录</title>
    <link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
</head>

<body>

<div class="container">
    <div class="row">
        <div class="col-md-4">
            <h3>登录页面----${message }</h3>
            <img alt="" src="/static/img/1.jpg">
            <form:form action="/login" modelAttribute="user" method="post">
                <div class="form-group">
                    <label for="exampleInputEmail1">账号</label>
                    <form:input path="account"  class="form-control" id="exampleInputEmail1" placeholder="Account"/>
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword1">密码</label>
                    <form:password path="password" class="form-control" id="exampleInputPassword1" placeholder="Password"/>
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword1">验证码</label>
                    <input type="text" name="vcode" id="vcode" class="form-control" id="exampleInputEmail1"
                           placeholder="identity"/>
                </div>
                <div class="form-group">
                    <img id="image" alt="验证码" src="/getGifCode">
                    <a class="btn btn-default" onclick="changeImage()" style="cursor:pointer">看不清</a>
                </div>
                <button type="submit" class="btn btn-default">Submit</button>
            </form:form>
        </div>
    </div>
</div>

<script>
    function changeImage(){
        console.log("刷新验证码")
        var obj = document.getElementById("image");
        obj.src = "/getGifCode?a="+Math.random();
    }
</script>
</body>

</html>