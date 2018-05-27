<%@ page import="java.util.List" %>
<%@ page import="justdj.top.pojo.Clazz" %>
<%@ page import="java.util.LinkedList" %><%--
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.13
  Time: 23:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>课程信息</title>
    <link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-8">
            <h2>教学班信息</h2>
            <table class="table table-bordered">
                <tr>
                    <th>编号</th>
                    <th>教学班名称</th>
                    <th>查看该教学班学生</th>
                </tr>
                <c:forEach items="${classList}" var="clazz" varStatus="status">
                    <tr>
                        <td>${status.index}</td>
                        <td>${clazz.name}</td>
                        <td><a class="btn btn-default" href="/course/student?id=${clazz.id}">查看</a></td>
                    </tr>
                </c:forEach>
            </table>


            <h2>知识点信息</h2>
            <table class="table table-bordered">
                <tr>
                    <th>编号</th>
                    <th>知识点名称</th>
                    <th>知识点描述</th>
                </tr>
                <c:forEach items="${knowledgeList}" var="knowledge" varStatus="status">
                    <tr>
                        <td>${status.index}</td>
                        <td>${knowledge.name}</td>
                        <td>${knowledge.introduce}</td>
                    </tr>
                </c:forEach>
            </table>
            <a class="btn btn-default">添加知识点</a>


            <h2>题库信息</h2>
            <table class="table table-bordered">
                <tr>
                    <th>编号</th>
                    <th>题库名称</th>
                    <th>查看题库信息</th>
                </tr>
                <c:forEach items="${testDatabaseList}" var="testDatabase" varStatus="status">
                    <tr>
                        <td>${status.index}</td>
                        <td>${testDatabase.name}</td>
                        <td><a class="btn btn-default" href="/course/testDatabase?id=${testDatabase.id}">查看</a></td>
                    </tr>
                </c:forEach>
            </table>


            <h2>试卷信息</h2>
            <table class="table table-bordered">
                <tr>
                    <th>编号</th>
                    <th>试卷名称</th>
                    <th>查看试卷</th>
                </tr>
                <c:forEach items="${testPaperList}" var="testPaper" varStatus="status">
                    <tr>
                        <td>${status.index}</td>
                        <td>${testPaper.name}</td>
                        <td><a class="btn btn-default" href="/testPaper/question?id=${testPaper.id}">查看</a></td>
                    </tr>
                </c:forEach>
            </table>

        </div>

        <div class="col-md-12">
            <h2>考试信息</h2>
            <table class="table table-bordered">
                <tr>
                    <th>编号</th>
                    <th>测试名称</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>是否启用</th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${examList}" var="exam" varStatus="status">
                    <tr>
                        <td>${status.index}</td>
                        <td>${exam.name}</td>
                        <td>${exam.startTime}</td>
                        <td>${exam.endTime}</td>
                        <td>${exam.use}</td>
                        <td class="center-pill">
                            <a class="btn btn-default" href="">删除</a>
                            <a class="btn btn-default" href="/exam/info?id=${exam.id}">查看</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>


</body>
</html>
