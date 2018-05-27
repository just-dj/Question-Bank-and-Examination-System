<%--
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.14
  Time: 12:45
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>学生考试详情</title>
    <link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
</head>
<body>

    <div class="container">
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <c:forEach items="${kindName}" var="kindNames" varStatus="status">
                    <h2>${kindNames}</h2>
                    <table class="table table-bordered">
                        <c:forEach items="${answerQuestionList}" var="question" varStatus="status">
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
                                    <td>
                                        <p class="text-success">
                                                ${question.answer}
                                        </p>
                                    </td>
                                    <td>
                                            <p class="text-primary">
                                                    ${question.userAnswer}
                                            </p>
                                    </td>
                                    <c:choose>
                                        <c:when test="${question.answer eq question.userAnswer}">
                                            <td class="bg-info">${question.score} 分</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td class="bg-danger">0 分</td>
                                        </c:otherwise>
                                    </c:choose>
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
