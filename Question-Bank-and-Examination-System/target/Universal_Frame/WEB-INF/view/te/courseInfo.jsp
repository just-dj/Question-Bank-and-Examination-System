<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.Calendar,java.sql.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课程信息</title>
	<link rel="stylesheet" href="/static/lib/font-awesome-4.7.0/css/font-awesome.min.css" >
	<%--<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.12/css/all.css" integrity="sha384-G0fIWCsCzJIMAVNQPfjH08cyYaUtMwjJwqiRKxxE/rx96Uroj1BtIQ6MLJuheaO9" crossorigin="anonymous">--%>
	<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
	<script src="/static/lib/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<style>
	.body{
		border: 1px solid #CCCCCC; 
		width: 75%;
		height: 450px;
		margin: auto;
		margin-top: 50px;
	}
	.body-left{
		border-right: 1px solid #CCCCCC;
		width: 60%;
		height: 100%;
		float: left;
	}
	.body-right{
		/*background-color: #CCCCCC;*/
		margin-top: 1%;
		width: 39%;
		height: 98%;
		float: right;
	}
	.add{
		width: 45%;
		height: 44%;
		float: left;
		margin-top: 3%;
		margin-left: 3%;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}
	#add1{
		background-color: #5bc0de;
	}
	#add2{
		background-color: #f0ad4e;
	}
	#add3{
		background-color: #5cb85c;
	}
	#add4{
		background-color: #d9534f;
	}
	.bofy-right{
		width: 30%;
		height: 100%;
		background-color: gray;
	}
	.add-pic{
		/*margin-left: 35%;*/
		color: white;
		margin-top: 20px;
	}
	.right-pic{
		/*margin:auto;*/
		/*margin-left: 28%;*/
		color: #5bc0de;
		/*margin-top: 33%;*/
	}
	.word{
		color: white;
		font-weight:bold;
		text-align: center;
	}
	.right-word{
		/*font-weight:bold;*/
		text-align: center;
	}
	.add-right{
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		border: 1px solid #CCCCCC;
		width: 98%;
		height: 98%;
	}
</style>

</head>
<body>

<%@ include file="../head.jsp" %>

<div class="body">
    	<div class="body-left">
    		<div class="add" id="add1">
    			<a href="/te/class?id=${courseId}"><i class="fa fa-user-circle fa-5x add-pic"></i></a>
    			<p class="word">${classList.size()}个</p>
    			<p class="word">班级管理</p>
    		</div>
    		<div class="add" id="add2">
    			<a href="/te/testPaper?id=${courseId}"><i class="fa fa-file fa-5x add-pic"></i></a>
    			<p class="word">${testPaperList.size()}个</p>
    			<p class="word">试卷管理</p>
    		</div>
    		<div class="add" id="add3">
    			<a href="/te/exam?id=${courseId}"><i class="fa fa-address-card fa-5x add-pic"></i></a>
    			<p class="word">${examList.size()}个</p>
    			<p class="word">考试管理</p>
    		</div>
    		<div class="add" id="add4">
    			<a href="/te/testDatabase?id=${courseId}"><i class="fa fa-database fa-5x add-pic"></i></a>
    			<p class="word">${testDatabaseList.size()}个</p>
    			<p class="word">题库管理</p>
    		</div>
    	</div>


    	<div class="body-right">
    		<div class="add-right">
    			<a href="/te/knowledge?id=${courseId}"><i class="fa fa-plus-circle fa-5x right-pic" style="font-size: 10em"></i></a>
    			<p class="right-word">知识点管理</p>
    		</div>
    	</div>
    </div>
</body>
</html>