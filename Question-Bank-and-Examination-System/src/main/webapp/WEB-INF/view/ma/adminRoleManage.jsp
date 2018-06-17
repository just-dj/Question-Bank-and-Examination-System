<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
	<link href="/static/css/adminRoleManage.css" rel="stylesheet" type="text/css" />
<title>角色管理</title>
</head>
<body>


<%@ include file="../head.jsp" %>

	<div class="containers">

		<div class="main">
			<div class="role-table">
				<table class="table">
					<tr>
						<th>角色ID</th>
						<th>角色名</th>
						<th>角色权限</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${roleList}" var="role" varStatus="status">
						<tr>
							<td>${role.id}</td>
							<td>${role.name}</td>
							<td>${role.permission.toString()}</td>
							<td><button class="role-delete">删除</button></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="role-add">
				<form action="/ma/role" method="post" id="form" onsubmit="return false">
					<div class="role-add-text" style="font-weight: bold">角色名</div>
					<input type="text" name="name" id="role-name">
					<div class="role-add-text">
						<span style="font-weight: bold">权限</span>
						<br>
						<br>
						<input type="checkbox" value="insert" name="permission">insert<br>
						<input type="checkbox" value="delete" name="permission">delete<br>
						<input type="checkbox" value="update" name="permission">update<br>
						<input type="checkbox" value="select" name="permission">select<br>
					</div>
					<input class="btn btn-primary" type="submit" value="添加角色" id="role-submit" onclick="addUser()" >
					
				</form>
			</div>
		</div>
		
	</div>

</body>



<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
<script src="/static/lib/layer-v3.1.1/layer/layer.js"></script>
<script type="text/javascript">


    function addUser() {

        var name  =$("#role-name").val();

        if (name = null || name == ""){
            layer.msg("请输入角色名");
            return;
		}

        var formData = new FormData(document.getElementById("form"));//表单id

        $.ajax({
            url: '/ma/role',
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