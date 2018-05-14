<%--
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.13
  Time: 23:35
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>班级学生列表</title>
    <link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-8">
            <h2>学生信息</h2>
            <table class="table table-bordered">
                <tr>
                    <th>编号</th>
                    <th>姓名</th>
                    <th>年龄</th>
                    <th>性别</th>
                </tr>
                <c:forEach items="${studentList}" var="a" varStatus="status">
                    <tr>
                        <td>${status.index}</td>
                        <td>${a.name}</td>
                        <td>${a.age}</td>
                        <td>${a.sex}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

</div>
</body>
</html>
