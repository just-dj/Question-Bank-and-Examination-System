<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看考试</title>
<link rel="stylesheet" type="text/css" href="/static/css/exam-viewTest.css">
</head>
<body>
<div class="top-subtitle">
		<ul class="subtitle-cont">
			<li class="subtitle-cont-li">
				查看考试
			</li>
			<li class="empty-li">
			</li>
		</ul>
 </div>
 <div class="test-title">
 	<table>
 		<tr>
 			<td style="font-weight: bold">
 				考试班级:
 			</td>
 			<td>
 			    <c:forEach items="${exam.clazzList}" var="clazz" varStatus="status">
 				<c:out value="${clazz.name}"></c:out>
 				&nbsp;&nbsp;&nbsp;&nbsp;
 				</c:forEach>
 			</td>
 		</tr>
 		<tr>
 			<td style="font-weight: bold">
 				考试名称:
 			</td>
 			<td>
 				<c:out value="${exam.name}"></c:out>
 			</td>
 		</tr>
 		<tr>
 			<td style="font-weight: bold">
 				考试开始时间:
 			</td>
 			<td>
 				<c:out value="${exam.startTime}"></c:out>
 			</td>
 		</tr>
 		<tr>
 			<td style="font-weight: bold">
 				考试结束时间:
 			</td>
 			<td>
 				<c:out value="${exam.endTime}"></c:out>
 			</td>
 		</tr>
 		<tr>
 			<td style="font-weight: bold">
 				使用试卷:
 			</td>
 			<td>
 				<c:forEach items="${exam.testPaperList}" var="testPaper" varStatus="status">
 				<c:out value="${testPaper.name}"></c:out>
 				&nbsp;&nbsp;&nbsp;&nbsp;
 				</c:forEach>
 			</td>
 		</tr>
 	</table>
 	</div>
 	<div class="test-info-main">
 		<table cellpadding="0" cellspacing="0">
 			<tr style="font-weight: bold">
 				<td>
 					编号
 				</td>
 				<td>
 					账号
 				</td>
 				<td>
 					姓名
 				</td>
 				<td>
 					性别
 				</td>
 				<td>
 				       开始考试时间
 				</td>
 				<td>
 				    提交试卷时间
 				</td>
				<td>
					使用试卷
				</td>
 				<td>
 					是否提交
 				</td>
				<td>
					当前分数
				</td>
 				<td>
 					操作
 				</td>
 			</tr>
 			<c:forEach items="${answerList}" var="answer" varStatus="status">
 			<tr>
 				<td>
 					<c:out value="${status.count}"></c:out>
 				</td>
 				<td>
 					<c:out value="${answer.studentId}"></c:out>
 				</td>
 				<td>
 					<c:out value="${answer.student.name}"></c:out>
 				</td>
 				<td>
 					<c:out value="${answer.student.sex}"></c:out>
 				</td>
 				<td>
 					<c:out value="${answer.startTime}"></c:out>
 				</td>
 				<td>
 				    <c:out value="${answer.endTime}"></c:out>
 				</td>
 				<td>
 				    <c:out value="${answer.testPaper.name}"></c:out>
 				</td>
 				<td>
 				    <c:if test="${answer.commit==true}">
 						<c:out value="提交"></c:out>
 					</c:if>
 					<c:if test="${answer.commit==false}">
 						<c:out value="未提交"></c:out>
 					</c:if>
 				</td>
				<td>${answer.result}</td>
 				<td>
 					<%--<button class="view-bnt" onClick="toViewPaper(<c:out value="${answer.id}"></c:out>,<c:out--%>
 					<%--value="${answer.studentId}"></c:out>">查看</button>--%>
					<button class="view-bnt" onClick="location.href='/te/exam/answer?id=${answer.id}'">查看
					</button>
 				</td>
 			</tr>
 			</c:forEach>
 		</table>
 	</div>
</body>
<script type="text/javascript">
	function a(examId,stuId)
	{
		window.location.href="..?";
	}
</script>
</html>