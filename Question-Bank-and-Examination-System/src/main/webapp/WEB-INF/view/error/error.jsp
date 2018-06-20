<%--
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.6.15
  Time: 23:11
--%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ page import="org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>错误页面</title>
    <link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
</head>

<body>
<%
    Logger logger = LoggerFactory.getLogger("错误页面");
    Exception ex = (Exception)request.getAttribute("exception");
//    logger.error("有异常发生！");
    if (null != ex){
        ex.printStackTrace();
        logger.error(ex.getMessage() + ex.getStackTrace().toString() + ex.getLocalizedMessage());
    }


%>

    <img style="width: 600px;height: 600px" alt="" src="/static/img/warn.jpg">

    <div style="position:absolute;left: 600px;top: 500px">

    <h1 class="text-warning">页面不存在或者系统出现错误！(系统已经记录这次异常，我们会尽快修复！)</h1>

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

