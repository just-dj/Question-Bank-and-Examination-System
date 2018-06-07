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
    <link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
</head>

<body>
<h1 class="text-warning">对不起，您没有权限请求此连接！</h1>
<div><a class="text-primary" onclick="back()">返回上一页</a></div>

<img alt="" src="/static/img/warn.jpg">

<script type="text/javascript">
    function back() {
        window.location.href = document.referrer;
        window.history.back(-2);
    }
</script>
</body>
</html>
