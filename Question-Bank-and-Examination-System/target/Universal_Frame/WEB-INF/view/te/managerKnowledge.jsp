<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知识点管理</title>
	<link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.min.css" >
	<link href="/static/css/managerKnowledge.css" rel="stylesheet">
</head>
<body>
	<%@ include file="../head.jsp" %>
	<!-- 添加弹出框  -->
	<div class="add_layer_box" style="display:none">
		<div class="add_layer_box_body">
			<form id="form_add_knowledge" name="form_add_knowledge" method="post" action="" onsubmit="return false">
				<input type="hidden" name="courseId" value="${courseId}">
				<div class="add_layer_box_body_row">
					<div class="knowledge_name">
						<span>知识点名称</span>
					</div>
					<input type="text" name="name" />
				</div>
				<div class="add_layer_box_body_row">
					<div class="knowledge_context">
						<span>知识点内容</span>
					</div>
					<input type="text" name="introduce" />
				</div>
				<div class="add_layer_box_btn">
					<input type="submit" name="submit_add" value="添加" onclick="addKnowledge()" />
				</div>
			</form>
		</div>
	</div>
	<!-- 添加弹出框 -->
	
	<!-- 主体 -->
	<div class="JSP_knowledge_box">
		<%
			
		%>
		<div class="JSP_knowledge_box_body">
			<div>
				<table class="table table-bordered">
					<tr>
						<th >序号</th>
						<th >知识点名称</th>
						<th >知识点详情</th>
						<th >操作</th>
					</tr>
					<c:forEach  items="${knowledgeList}" var="knowledge" varStatus="status">
						<tr>
							<td >${status.index + 1}</td>
							<td ><div class="iname">${knowledge.name}</div></td>
							<td ><div class="introduce">${knowledge.introduce}</div></td>
							<td >
								<div class="child_center">
									<div class="btn btn-warning"
										  onclick="location.href='/te/knowledge/delete?id=${knowledge.id}&courseId=${courseId}'">删除</div>
									<div class="btn btn-primary" style="margin-right: 8px"
										 onclick="location.href='/te/course/knowledge?id=${knowledge.id}'">查看
									</div>
								</div>
							</td>
						</tr>
					</c:forEach>

				</table>
			</div>
			
			<div class="add_knowledge_btn" id="add_knowledge_btn">
				<span>添加知识点</span>
			</div>
		</div>
		
	</div>
</body>
<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
<script src="/static/lib/layer-v3.1.1/layer/layer.js"></script>
<script>
$(function(){  
	$('#add_knowledge_btn').on('click', function(){  
        layer.open({  
            type: 1 
            ,title: 'JSP添加知识点'
            ,resize: false
            ,skin: 'layui-layer-lan' 
            ,shadeClose: true   
            ,area : ['600px' , '300px']
            ,content: $('.add_layer_box')

        });
    });

});


function addKnowledge() {
    var name  =$("input[name='name']").val();
    var introduce = $("input[name='introduce']").val();

    if (name = null || name == ""){
        layer.msg("请输入知识点名称");
        return;
    }else if(introduce == null  || introduce == ""){
        layer.msg("请输入知识点内容");
        return;
	}

    var formData = new FormData(document.getElementById("form_add_knowledge"));//表单id

    $.ajax({
        url: '/te/knowledge/add',
        //type是无所谓的 但是get只能传递1kb数据
        type: 'POST',
//            dataType:'json',
        // dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
        data:formData,
        //上传文件不需要缓存
        cache:false,
        //data值是FormData对象，不需要对数据做处理
        processData:false,
        // 且已经声明了属性enctype="multipart/form-data"，所以这里设置为false
        contentType:false
    })
        .done(function(data) {

            layer.open({
                type: 1,
                skin: 'my-layui', //样式类名
                closeBtn: 0, //不显示关闭按钮
                anim: 2,
                shadeClose: true, //开启遮罩关闭
                content: data.toString() + "<br>" +
                "<a class='btn btn-primary' href='"+"'>查看" + "</a>" ,
            });

        })
        .fail(function() {
            console.log("error");
        })
        .always(function() {
            console.log("complete");

        });
}
</script>
</html>