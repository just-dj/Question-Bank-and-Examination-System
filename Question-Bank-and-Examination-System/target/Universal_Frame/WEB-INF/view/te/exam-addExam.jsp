<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建考试</title>
	<link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
	<link rel="stylesheet"  href="/static/css/exam-addExam.css">
</head>
<body>

<%@ include file="../head.jsp" %>

<div class="top-subtitle">
		<ul class="subtitle-cont">
			<li class="subtitle-cont-li">
				新建考试
			</li>
			<li class="empty-li">
			</li>
		</ul>
 </div>
 <form id="dataForm" action="/te/exam/new" method="post" onsubmit="return false">
 <div class="test-title">
	 <input type="hidden" name="courseId" value="${courseId}"/>
 	<table>
 		<tr>
 			<td>
 				考试班级
 			</td>

 			<td >
				<div style="display: flex;align-items: center;justify-content: flex-start">
					<c:forEach items="${classList}" var="classItem" varStatus="status">
						<div style="display: flex;align-items: center;justify-content: flex-start">
							<input name="classList" type="checkbox" style="width:20px;height:20px;margin-top: 0px"
								   value="${classItem.getId()}">
							<span style="height: 20px; margin-left:4px;margin-right: 15px">${classItem.getName()}</span>
						</div>
					</c:forEach>
				</div>

 			</td>
 		</tr>
 		<tr>
 			<td>
 				考试名称
 			</td>
 			<td>
 				<input name="examName" class="examName" type="text" style="width:166px;height:19px;">
 			</td>
 		</tr>
 		<tr>
 			<td>
 				考试开始时间
 			</td>
 			<td>
 				<input name="startTime" class="startTime" type="text" id="date1"
						data-options="{'type':'YYYY-MM-DD hh:mm:ss','beginYear':2018,'endYear':2088}" style="width:166px;height:19px;">
 			</td>
 		</tr>
 		<tr>
 			<td>
 				考试结束时间
 			</td>
 			<td>
 				<input name="endTime" class="endTime" type="text" id="date2"
						data-options="{'type':'YYYY-MM-DD hh:mm:ss','beginYear':2018,'endYear':2088}" style="width:166px;height:19px;">
 			</td>
 		</tr>
 	</table>
 	</div>
 	<div class="test-info-main">
 		<table cellpadding="0" cellspacing="0" class="table-hover">
 			<tr class="paperlist">
 				<td>
 					试卷名称
 				</td>
 				<td>
 					单选题数
 				</td>
 				<td>
 					多选题数
 				</td>
 				<td>
 					填空题数
 				</td>
 				<td>
 					判断题数
 				</td>
 				<td>
 				       问答题数
 				</td>
 				<td>
 				      试卷状态
 				</td>
 			</tr>
 			<c:forEach items="${testPaperList}" var="paperitem" varStatus="status1">
 			<tr>
 				<td>
					<div style="display: flex;justify-content: center;align-items: center">
						<input  type="checkbox" style="width:20px;height:20px;margin-top: 0px"
							   value="${paperitem.id}"
							   name="testPaperList">
						<span style="margin-left:4px;height: 20px">${paperitem.name}</span>
					</div>
 				</td>
 				<c:forEach items="${testPaperInfo.get(status1.count-1)}" var="questionItem" varStatus="status2">
					<td>
						${questionItem}
					</td>
 				</c:forEach>
 				<td>
					<c:choose>
						<c:when test="${paperitem.use}">
							<span class="text-success">已用</span>
						</c:when>
						<c:otherwise>
							<span class="text-danger">不可用</span>
						</c:otherwise>
					</c:choose>
 				</td>
 			</tr>
 			</c:forEach>
 		</table>
 	</div>
 	<div style="margin:auto;margin-top:30px;width:85%;text-align: center;">
 	<button onclick="sendData()" type="submit" class="view-bnt"  style="width:160px;height:40px;">添加考试</button>
 	</div>
 	</form>
</body>
<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="/static/lib/jquery/jquery.date.js"></script>
<script src="/static/lib/layer-v3.1.1/layer/layer.js"></script>
<script type="text/javascript">
$.date('#date1');
$.date('#date2');

function sendData() {

	if(check())
	    return false;

	var formData = new FormData(document.getElementById("dataForm"));//表单id

	$.ajax({
		url: '/te/exam/new',
		//type是无所谓的 但是get只能传递1kb数据
		type: 'POST',
		dataType:'json',
		// dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
		data:formData,
		//上传文件不需要缓存
		cache:false,
		//data值是FormData对象，不需要对数据做处理
		processData:false,
		// 且已经声明了属性enctype="multipart/form-data"，所以这里设置为false
		contentType:false
	}).done(function(data) {
			layer.open({
				type: 1,
				skin: 'my-layui', //样式类名
				closeBtn: 0, //不显示关闭按钮
				anim: 2,
				shadeClose: true, //开启遮罩关闭
				content: data.message +
				"<a class='btn btn-primary' href='/te/class/student?id="+12+"'>查看" + "</a>" ,
			});
		})
	.fail(function() {
		console.log("error");
	})
	.always(function() {
		console.log("complete");

	});
    return false;
}


function check (){
    var condition = false;

    var examName = $("input[name='examName']").val();
    var startTime = $("input[name='startTime']").val();
    var endTime = $("input[name='endTime']").val();

	var classListLength = $("input[name='classList']:checked").length;
    var testPaperListLength = $("input[name='testPaperList']:checked").length;

    if (classListLength <= 0 ){
        layer.msg("至少选择一个班级！");
        condition = true;
	}else if ( empty(examName) || examName.length > 100){
        layer.msg("考试名不合法！");
        condition = true;
    }else  if (empty(startTime) || startTime.length != 19){
        layer.msg("开始时间不合法！");
        condition = true;
    }else  if(empty(endTime) || endTime.length != 19){
        layer.msg("结束时间不合法！");
        condition = true;
    }else if (testPaperListLength <= 0){
        layer.msg("至少选择一张试卷！");
        condition = true;
	}

    return condition;

}

function empty(a) {
    return a==null || a=="" || a.trim()=="" || a ==0 ;
}



</script>
</html>