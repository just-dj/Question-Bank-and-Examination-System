<%@ page import="justdj.top.pojo.Exam" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考试管理</title>
	<link rel="stylesheet" href="/static/lib/font-awesome-4.7.0/css/font-awesome.min.css" >
	<link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
	<link rel="stylesheet"  href="/static/css/examManager.css">
</head>
<body>
	<%@ include file="../head.jsp" %>

	<%
		request.setAttribute("date",new Date());
	%>
	<div class="top-subtitle">
		<ul class="subtitle-cont">
			<li class="subtitle-cont-li">
				考试管理
			</li>
			<li class="empty-li">
			</li>
		</ul>
   </div>

   <div class="exams-cont">
   		<div class="exam-add" id="getNewExam">
   			<div>
                <a href="/te/exam/new?courseId=${courseId}">
                    <i class="fa fa-plus-circle  fa-5x" style="font-size: 10em"></i>
                </a>
   			</div>
   			<div class="exam-add-text">
   				新建考试
   			</div>
   		</div>

   		<c:forEach items="${examList}" var="exam" varStatus="status">
		<div class="exam-item">
   			<table class="exam-item-table" cellpadding="0" cellspacing="0">
   				<tr >
   					<td>考试名称
   					</td>
   					<td class="next-td">${exam.getName()}
   					</td>
   				</tr>
   				<tr >
   					<td>开始时间
   					</td>
   					<td class="next-td">${exam.getStartTime()}
   					</td>
   				</tr>
   				<tr >
   					<td>结束时间
   					</td>
   					<td class="next-td">${exam.getStartTime()}
   					</td>
   				</tr>
   				<tr >
   					<td>状态
   					</td>
   					<td class="next-td">
						<c:choose>
							<c:when test="${exam.endTime.before(requestScope.date)}">
								<span class="text-success">已结束</span>
							</c:when>
							<c:when test="${exam.startTime.after(requestScope.date)}">
								<span class="text-primary">未开始</span>
							</c:when>
							<c:otherwise >
								<span class="text-danger">进行中</span>
							</c:otherwise>
						</c:choose>
   					</td>
   				</tr>
   				
   				<tr >
   					<td>参加人数
   					</td>
   					<td class="next-td">${studentNum.get(status.count-1)}
   					</td>
   				</tr>
   			</table>
   			<div class="exam-cont-see">
				<button class="see-bnt" style="background: #d9534f">删除</button>
   				<button class="see-bnt">查看</button>
   			</div>
   		</div>
       </c:forEach>
   </div>
   
</body>
<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
//	$("#getNewExam").click(function(){
//		window.location.href="newTest.jsp";
//	})
</script>
</html>