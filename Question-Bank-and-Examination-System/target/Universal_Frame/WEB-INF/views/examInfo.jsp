<%--
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.16
  Time: 11:57
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>考试信息</title>
    <link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
</head>
<body>
    <div class="cntainer">
        <div class="row">
            <div class="col-md-8">
                <h2>试卷信息</h2>
                <table class="table table-bordered">
                    <tr>
                        <th>编号</th>
                        <th>试卷编号</th>
                        <th>试卷名称</th>
                        <th>查看试卷</th>
                    </tr>
                    <c:forEach items="${testPaperList}" var="testPaper" varStatus="status">
                        <tr>
                            <td>${status.index}</td>
                            <td>${testPaper.id}</td>
                            <td>${testPaper.name}</td>
                            <td><a class="btn btn-default" href="/testPaper/question?id=${testPaper.id}">查看</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <div class="col-md-12">

                <h2>学生提交情况</h2>
                <table class="table table-bordered">
                    <tr>
                        <th>编号</th>
                        <th>学生账号</th>
                        <th>学生姓名</th>
                        <th>所用试卷编号</th>
                        <th>开始考试时间</th>
                        <th>提交时间</th>
                        <th>是否提交</th>
                        <th>操作</th>
                    </tr>
                    <c:forEach items="${answerList}" var="answer" varStatus="status">
                        <tr>
                            <td>${status.index}</td>
                            <td>${answer.student.account}</td>
                            <td>${answer.student.name}</td>
                            <td>${answer.testPaperId}</td>
                            <td>${answer.startTime}</td>
                            <td>${answer.endTime}</td>
                            <td>${answer.commit}</td>
                            <td><a class="btn btn-default" href="/exam/info/studentTestPaper?id=${answer.id}">查看</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
