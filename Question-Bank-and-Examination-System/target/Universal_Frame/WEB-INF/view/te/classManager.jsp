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
		<c:forEach items="classList" var="clazz">
			<div class="classManager_item" >
				<div class="item_row">
					<span>班级名称：</span>
						${clazz.name}<br>
				</div>
				<div class="item_row">
					<span>班级人数：${clazz.userList}</span>
					<span>人</span><br>
				</div>
				<div class="item_btn_group">
					<div class="item_btn_delete" id="item_btn_delete">
						<span>删除</span>
					</div>
					<div class="item_btn_look" id="item_btn_look">
						<span>查看</span>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</body>
<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
<script src="/static/lib/vue/vue.js"></script>
</html>