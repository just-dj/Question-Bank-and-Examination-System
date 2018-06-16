<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>班级详情</title>
	<link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
	<%--<script src="/static/lib/bootstrap-3.3.7-dist/js/bootstrap.js"></script>--%>
<%--<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">--%>
	<link rel="stylesheet"  href="/static/css/classDetail.css">
</head>
<body>
	<%@ include file="../head.jsp" %>
	<!-- 主体 -->
	<div class="class_detail_box">
		<div class="class_detail_box_body">
			<div class="table_box">
				<table class="table-hover ">
					<tr>
						<th>头像</th>
						<th >帐号</th>
						<th >姓名</th>
						<th >邮箱</th>
						<th >性别</th>
						<th >状态</th>
						<th >操作</th>
					</tr>

					<c:forEach items="${studentList}" var="student">
						<tr>
							<td>
								<c:choose>
									<c:when test="${student.img == null || student.img == ''}">
										<img id="headImg" class="img-circle" src="/static/img/teacher.png"
											 style="width: 35px;height: 35px;"/>
									</c:when>
									<c:otherwise>
										<img id="headImg" class="img-circle" src="${student.img}"
											 style="width: 35px;height: 35px;"/>
									</c:otherwise>
								</c:choose>
							</td>
							<td >${student.account}</td>
							<td >${student.name}</td>
							<td>${student.email}</td>
							<td >${student.sex}</td>
							<c:choose >
								<c:when test="${student.use}">
									<td class="text-success"> 正常</td>
								</c:when>
								<c:otherwise>
									<td class="text-danger"> 停用</td>
								</c:otherwise>
							</c:choose>
							<td class="action_td">
								<div class="btn_delete"><a id="delete"
														   href="/te/class/student/delete?studentId=${student.id}&classId=${classId}&courseId=${courseId}"
										style="color:#ffffff">删除</a></div>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="add_student_btn" id="add_student_btn">
				<a href="/te/class/student/add?classId=${classId}&courseId=${courseId}">添加学生</a>
			</div>
		</div>
	</div>
</body>
<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
<script src="/static/lib/layer-v3.1.1/layer/layer.js"></script>
</html>