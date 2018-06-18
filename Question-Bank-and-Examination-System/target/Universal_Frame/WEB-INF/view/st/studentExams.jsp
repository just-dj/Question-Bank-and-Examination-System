<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生考试管理</title>
<style>
.containers{
	display:flex;
	flex-direction: column;
	align-items: center;
	height: 100%;
}
.main{
	display: flex;
	flex-wrap:wrap;
	width: 80%;
	margin-top: 40px;
	justify-content: flex-start;;
}
.main-test-part{
	width:33.3333%;
	height:350px;
	display:flex;
	justify-content:center;
	align-items:center;
	float: left;
}
.main-test-items{
	border:1px solid #CCC;
	width:70%;
	height:280px;
	display: flex;
	flex-direction:column;
	justify-content:center;
}
.mytable{
	margin-left:20px;
}
tr{
	height:40px;
}
td{
	min-width:100px;
}
.test-table-bold{
	font-weight:bold;
}
.test-btn-row{
	display:flex;
	justify-content:flex-end;
	padding-right: 10px;
}
.test-btn{
	margin-right:30px;
	width:65px;
	height:35px;
	border:1px solid #CCC;
	border-radius:5px;
	background-color:#fff;
	color:#000;
}
.test-btn:hover{
	margin-right:30px;
	width:65px;
	height:35px;
	border:1px solid #CCC;
	border-radius:5px;
	background-color:#5AC1DF;
	-webkit-transition: all .2s linear;
	transition: all .2s linear;
	color:#fff;
}

body .my-layui .layui-layer-title {
	/*min-width: 450px;*/
	/*width: 450px;*/
	/*max-width: 450px;*/
	padding: 0 80px 0 20px;
	height: 42px;
	line-height: 42px;
	font-size: 14px;
	background:#5bc0de;
	color:#fff;
	border: none;
}

body .my-layui .layui-layer-content {
	padding: 20px;
	min-height: 250px;
	max-height: 250px;
	/*min-width: 450px;*/
	/*width: 450px;*/
	max-width: 450px;
	word-break: break-all;
	white-space:normal;
	OVERFLOW-Y: auto;
	OVERFLOW-X:hidden;
}

.my-layui{
	width: 450px;
}

</style>
	<link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
</head>
<body>

<%
	request.setAttribute("date",new Date());
%>

<%@include file="../head.jsp" %>

	<div class="containers">

		<div class="main">
		<!-- 每个考试模块 -->
		<c:forEach items="${examList}" var="exam" varStatus="status">
			<div class="main-test-part">
			
				<div class="main-test-items">
					<table class="mytable">
						<tr>
							<td>试卷名称：</td>
							<td class="test-table-bold">${exam.name}</td>
						</tr>
						<tr>
							<td>开始时间：</td>
							<td>${exam.startTime}</td>
						</tr>
						<tr>
							<td>结束时间：</td>
							<td>${exam.endTime }</td>
						</tr>
						<tr>
							<td>状态：</td>
							<c:choose>
								<c:when test="${exam.endTime.before(requestScope.date)}">
									<td class="text-success">已结束</td>
								</c:when>
								<c:when test="${exam.startTime.after(requestScope.date)}">
									<td class="text-primary">未开始</td>
								</c:when>
								<c:otherwise >
									<td class="text-danger">进行中</td>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<td>分数：</td>
							<c:choose>
								<c:when test="${answerList.get(status.index).result == null}">
									<td>0</td>
								</c:when>
								<c:otherwise>
									<td>${answerList.get(status.index).result}</td>
								</c:otherwise>
							</c:choose>
						</tr>
					</table>

					<c:choose>
						<c:when test="${exam.endTime.before(requestScope.date)}">
							<div class="test-btn-row">
								<button class="btn btn-success"
										onclick="location.href='/st/course/exam/answer?id=${exam.id}&courseId=${courseId}'">查看
								</button>
							</div>
						</c:when>
						<c:when test="${exam.startTime.after(requestScope.date)}">
							<div class="test-btn-row">
								<button class="btn btn-warn"  >未开始</button>
							</div>
						</c:when>
						<c:when test="${exam.startTime.before(requestScope.date)&& exam.endTime.after(requestScope.date)
						&& (answerList.get(status.index)==
						null || answerList.get(status.index).commit == false) }">
							<div class="test-btn-row">
									<button class="btn btn-danger"
											onclick="location.href='/st/course/exam?id=${exam.id}&courseId=${courseId}'">开始
									</button>
							</div>
						</c:when>
						<c:otherwise >
							<div class="test-btn-row">
								<div class="test-btn-row">
									<button class="btn btn-warn"  >已提交</button>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
					
				</div>
			
			</div>
		</c:forEach>
		
			
		</div>
	</div>
<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
<script src="/static/lib/layer-v3.1.1/layer/layer.js"></script>
<script>
	var message = '${message}';

	$(document).ready(function () {
		if (message == null || message == ""){

		}else{
            layer.open({
                type: 1,
                skin: 'my-layui', //样式类名
                closeBtn: 0, //不显示关闭按钮
                anim: 2,
                shadeClose: true, //开启遮罩关闭
                content: message ,
            });
		}

    });
</script>
</body>
</html>