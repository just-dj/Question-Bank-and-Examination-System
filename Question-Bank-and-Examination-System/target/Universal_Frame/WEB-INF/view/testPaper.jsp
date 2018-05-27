<%--
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.26
  Time: 21:45
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-10">
            <form action="/st/course/exam" method="post">

                    <c:if test="${kindName.contains('单选题')}">
                        <table>
                        <c:forEach var="question" items="${questionList}">
                            <c:if test="${question.kindName eq '单选题'}">
                                <tr>
                                    <td>${question.question}</td>
                                    <td>
                                        <div>
                                            <input type="radio" name="${question.id}"  value="a"/>A ${question.a}
                                            <input type="radio" name="${question.id}"  value="b"/>B ${question.b}
                                            <input type="radio" name="${question.id}"  value="c"/>C ${question.c}
                                            <input type="radio" name="${question.id}"  value="d"/>D ${question.d}
                                        </div>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </table>
                    </c:if>

                    <c:if test="${kindName.contains('多选题')}">
                        <table>
                        <c:forEach var="question" items="${questionList}">
                            <c:if test="${question.kindName eq '多选题'}">
                                <tr>
                                    <td>${question.question}</td>
                                    <td>
                                        <div>
                                            <input type="checkbox" name="${question.id}"
                                                   value="a"/>A ${question.a}
                                            <input type="checkbox" name="${question.id}"
                                                   value="b"/>B ${question.b}
                                            <input type="checkbox" name="${question.id}"
                                                   value="c"/>C ${question.c}
                                            <input type="checkbox" name="${question.id}"
                                                   value="d"/>D ${question.d}
                                        </div>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </table>
                    </c:if>

                <input class="btn btn-default" type="submit" value="提交"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>
