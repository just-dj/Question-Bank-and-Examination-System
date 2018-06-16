<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生课程页面</title>

	<link href="/static/css/studentCourse.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >

</head>
<body>
<%
	request.setAttribute("date",new Date());
%>

<%@ include file="../head.jsp" %>

	<div class="containers">

		<div class="main">
			<div class="main-course">
				<div class="main-course-title">
					<h2 class="course-title-content">JSP知识点</h2>
				</div>
				<div class="main-course-main">
				
				<!-- 单个知识点详情 -->
					<c:forEach items="${knowledgeList}" var="know" varStatus="status">
					<div class="course-main-knowledge">
						<div class="knowledge-title">${status.index+1}.<a
								href="/te/course/knowledge?id=${know.id}">${know.name }</a></div>
						<div class="knowledge-content">${know.introduce }</div>
					</div>
					</c:forEach>
				</div>
			</div>
			<div class="main-test">
				<div class="test-title"><div class="test-title-img"></div>近期考试</div>
				<div class="test-items">
					<div class="test-items-item">
						<div class="test-items-item-left bold">考试名称</div>
						<span class="bold">状态</span>
					</div>
					<c:forEach items="${examList}" var="exam">

						<div class="test-items-item">
							<c:choose>
								<c:when test="${exam.endTime.before(requestScope.date)}">
									<div class="test-items-item-left">${exam.name}</div>
									<span class="text-success bold">已结束</span>
								</c:when>
								<c:when test="${exam.startTime.after(requestScope.date)}">
									<div class="test-items-item-left">${exam.name}</div>
									<span class="ignore bold">未开始</span>
								</c:when>
								<c:otherwise >
									<div class="test-items-item-left text-danger bold" >${exam.name}</div>
									<span class="text-danger bold">进行中</span>
								</c:otherwise>
							</c:choose>
						</div>
					</c:forEach>
				</div>
				<div class="test-check">
				<button class="test-check-btn" onclick="location.href='/st/course/examInfo?id=${courseId}'">查看详细考试信息
				</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>