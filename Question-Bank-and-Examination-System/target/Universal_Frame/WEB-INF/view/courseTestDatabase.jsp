<%--
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.14
  Time: 10:38
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>题库信息</title>
    <link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-md-8">
                <c:forEach items="${kindName}" var="kindNames" varStatus="status">
                    <h2>${kindNames}</h2>
                    <table class="table table-bordered">
                        <c:forEach items="${question}" var="question" varStatus="status">
                            <tr>
                                <c:if test="${question.kindName == kindNames}">
                                    <td>${question.question}</td>
                                    <c:if test="${not empty question.a}">
                                        <td>${question.a}</td>
                                    </c:if>
                                    <c:if test="${not empty question.b}">
                                        <td>${question.b}</td>
                                    </c:if>
                                    <c:if test="${not empty question.c}">
                                        <td>${question.c}</td>
                                    </c:if>
                                    <c:if test="${not empty question.d}">
                                        <td>${question.d}</td>
                                    </c:if>
                                    <td><a class="btn btn-default"
                                           href="/course/testDatabase/editQuestion?id=${question.id}">修改</a></td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </table>
                </c:forEach>
            </div>
        </div>
    </div>

</body>
</html>
