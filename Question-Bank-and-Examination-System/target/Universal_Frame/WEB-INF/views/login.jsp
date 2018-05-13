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
    <title>My JSP 'MyJsp.jsp' starting page</title>
</head>

<body>
<h1>登录页面----${message }</h1>
<img alt="" src="/static/img/1.jpg">
<form:form action="/login" modelAttribute="user" method="post">
    <table>
        <tr>
            <td>账&nbsp;户：</td>
            <td><form:input path="account"/> <form:errors path="account" cssClass="error"/> <br/></td>
        </tr>

        <tr>
            <td> 密 &nbsp;码：</td>
            <td>   <form:password path="password"/> <form:errors path="password" cssClass="error" /></td>
        </tr>

        <tr>
            <td>验证码：</td>
            <td><input type="text" name="vcode" id="vcode"/></td>
            <td><img id="image" alt="验证码" src="/getGifCode"></td>
            <td><a onclick="changeImage()" style="cursor:pointer">看不清楚？点击这里更换</a></td>
        </tr>

        <tr>
            <td>    <form:button name="button">submit</form:button></td>
        </tr>
    </table>
</form:form>

<script>
    function changeImage(){
        console.log("刷新验证码")
        var obj = document.getElementById("image");
        obj.src = "/getGifCode?a="+Math.random();
    }
</script>
</body>

</html>