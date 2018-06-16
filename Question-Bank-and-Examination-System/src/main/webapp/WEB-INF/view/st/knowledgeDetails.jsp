<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知识点详情</title>
<style>
	.containers{
		display:flex;
		flex-direction:column;
		align-items: center;
	}
	.main{
		width:80%;
		/*height:700px;*/
		border:1px solid #CCCCCC;
		margin-top:20px;
		margin-bottom: 50px;
		padding-bottom: 50px;
	}
	.main-title{
		display:flex;
		height:70px;
		align-items:center;
		padding-left:60px;
		color:#000;
		font-size:18px;
		font-weight:bold;
		background-color:#F2F2F2;
	}
	.main-details{

		margin:0 auto;
		padding-top: 30px;
		display:flex;
		flex-direction:column;
		width:80%;
		/*height:60%;*/
		justify-content:space-around;
	}
</style>
</head>
<body>
<%@ include file="../head.jsp"%>

<div class="containers">

	<div class="main">
		<div class="main-title">${knowledge.name}</div>
		<div class="main-details">
			${knowledge.introduce}
		</div>
	</div>
</div>
	
</body>
</html>