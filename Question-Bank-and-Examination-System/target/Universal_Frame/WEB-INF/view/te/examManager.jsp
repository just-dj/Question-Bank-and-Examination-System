<%@ page import="justdj.top.pojo.Exam" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考试管理</title>
	<link rel="stylesheet" href="/static/lib/font-awesome-4.7.0/css/font-awesome.min.css" >
	<link rel="stylesheet"  href="/static/css/examManager.css">
</head>
<body>
	<%@ include file="../head.jsp" %>

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
   				<i class="fa fa-address-card fa-5x"></i>
   			</div>
   			<div class="exam-add-text">
   				考试管理
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
   					    <c:if test="${exam.getUse()==true}">
    						可用
						</c:if>
						<c:if test="${exam.getUse()==false}">
    						可用
						</c:if>
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
   			<button class="see-bnt">查看</button>
   			</div>
   		</div>
       </c:forEach>
   </div>
   
</body>
<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	$("#getNewExam").click(function(){
		window.location.href="newTest.jsp";
	})
</script>
</html>