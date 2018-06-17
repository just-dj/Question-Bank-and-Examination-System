<%--
  Created by IntelliJ IDEA.
  User: 霜
  Date: 18.4.21
  Time: 23:35
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>权限错误</title>
    <link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.min.css" >
</head>

<body>

<img style="width: 600px;height: 600px" alt="" src="/static/img/warn.jpg">

<div style="position:absolute;left: 600px;top: 500px">
    <h1 class="text-warning">对不起，您没有权限请求此连接！</h1>

    <div>
        <a class="btn btn-primary" onclick="back()">返回上一页</a>
        <a class="btn btn-primary" onclick="location.href='/logout'">清除会话信息</a>
    </div>

</div>

<script type="text/javascript">
    function back() {
        window.location.href = document.referrer;
        window.history.back(-2);
    }
</script>
</body>
</html>
