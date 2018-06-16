<%--
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.6.15
  Time: 23:11
--%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>错误信息如下</title>
    　<% Exception ex = (Exception)request.getAttribute("exception"); %>
　　<H2>Exception: <%= ex.getMessage()%></H2>
　　<P/>
　　<% ex.printStackTrace(new java.io.PrintWriter(out)); %>
    <link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
</head>

<body>
<%--<h1 class="text-warning">对不起，您没有权限请求此连接！</h1>--%>
<div><a class="btn btn-primary " onclick="back()">返回上一页</a></div>

<img alt="" src="/static/img/warn.jpg">

<script type="text/javascript">
    function back() {
        window.location.href = document.referrer;
        window.history.back(-2);
    }
</script>
</body>
</html>

