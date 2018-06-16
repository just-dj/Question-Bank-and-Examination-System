<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>班级管理</title>
	<link rel="stylesheet"  href="/static/css/classManager.css">
</head>
<body>
	<%@ include file="../head.jsp" %>

	<div class="classManager_box">
		<div class="classManager_box_body">
			<!-- 新建班级  -->
			<div class="classAdd">
				<a class="classAdd_box" href="/te/class/add?courseId=${courseId}">
					<img src="/static/img/classManager.png" height="76px" width="76px"/>
					<span>新建班级</span>
				</a>
			</div>

			<c:forEach items="${classList}" var="clazz">
				<div class="classManager_item">
					<div class="item_row">
						<span>班级名称：</span>
						${clazz.name}<br>
					</div>
					<div class="item_row">
						<span>班级人数：</span>
						${clazz.userList.size()}
						<span>人</span><br>
					</div>
					<div class="item_btn_group">
						<div class="item_btn_look" id="item_btn_look">
							<a href="/te/class/student?id=${clazz.id}&courseId=${courseId}">
								<span style="color: #ffffff">查看</span>
							</a>

						</div>

						<div class="item_btn_delete" id="item_btn_delete">
							<span>删除</span>
						</div>

					</div>
				</div>
			</c:forEach>
			<!-- 班级情况  -->
		</div>
	</div>
</body>
	<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
	<script src="/static/lib/vue/vue.js"></script>
</html>