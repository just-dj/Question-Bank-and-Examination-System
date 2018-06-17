<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.min.css" >
	<link href="/static/css/adminAddUser.css" rel="stylesheet" type="text/css" />
<title>添加用户</title>
</head>
<body>
<%@ include file="../head.jsp" %>

	<div class="containers">
		<div class="main">
			<div class="main-single">
				<div class="main-title">单个添加</div>
				<form id="single" onsubmit="return false" method="post">
					<table>
					<tr>
						<td>账号：</td>
						<td>
							<input type="text" name="account" id="id" class="single-text">
						</td>
					</tr>
					<tr>
						<td>姓名：</td>
						<td>
							<input type="text" name="name" id="name" class="single-text">
						</td>
					</tr>
					<tr>
						<td>性别：</td>
						<td>
							<div style="display: flex;justify-content: flex-start;align-items: center">
								<input name="sex" type="radio" value='男' checked> 男
								<input name="sex" type="radio" value='女' style="margin-left: 15px"> 女
							</div>
						</td>
					</tr>
					<tr>
						<td>密码：</td>
						<td>
							<input name="password" type="password" class="single-text">
						</td>
					</tr>
					<tr>
						<td>邮箱：</td>
						<td><input name="email" type="text" class="single-text"></td>
					</tr>
					<tr>
						<td>角色：</td>
						<td>
							<div style="display: flex;justify-content: flex-start;align-items: center">
								<input type="checkbox" name="role" value="1" checked>学生
								<input type="checkbox" name="role" value="2"  style="margin-left: 15px">教师
								<input type="checkbox" name="role" value="3" style="margin-left:15px">管理员
							</div>
						</td>
					</tr>
					</table>
					<div class="single-btn"><input onclick="addUser()" type="submit" value="添加" class="btn" id="single-add"
												   name="single-add"></div>
				</form>
			</div>
			<div class="main-batch">
				<div class="main-title">批量添加</div>
				<form id="fileForm" onsubmit="return false">
					<table>
						<tr>
							<td>文件：</td>
							<td class="file-ui">
							<input type="file" name="file" id="chooseFile" onchange="checkFileExt(this.value)"></td>
						</tr>
					</table>
					<div class="batch-btn">
						<input type="submit" value="上传" class="btn"  onclick="sendFile()">
					</div>
				</form>
			</div>
		</div>
	</div>

<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
<script src="/static/lib/layer-v3.1.1/layer/layer.js"></script>
<script type="text/javascript">

	function sendFile() {
        if (jQuery("input[type='file']").val()==""){
            layer.msg("请选择文件！");
            return;
        }

        var formData = new FormData(document.getElementById("fileForm"));//表单id

        $.ajax({
            url: '/ma/user/file',
            type: 'POST',
            dataType:'json',
            data:formData,
            cache:false,
            processData:false,
            contentType:false
        })
            .done(function(data) {
                layer.open({
                    type: 1,
                    skin: 'my-layui', //样式类名
                    closeBtn: 0, //不显示关闭按钮
                    anim: 2,
                    shadeClose: true, //开启遮罩关闭
                    content: data.message + "<br>" + data.result+ "<br>" +
                    "<a class='btn btn-primary' href='/ma/userManager' >查看" + "</a>" ,
                });

            })
            .fail(function() {
                console.log("error");
            })
            .always(function() {
                console.log("complete");

            });



    }


	function isEmpty(data) {
		return (data == null || data == "");
    }

    function addUser() {

		var account = $("input[name='account']").val();
        var name = $("input[name='name']").val();
        var password = $("input[name='password']").val();
        var email = $("input[name='email']").val();

		if (isEmpty(account)){
			layer.msg("账号不能为空！");
			return;
		}
        if (isEmpty(name)){
            layer.msg("用户名不能为空！");
            return;
        }
        if (isEmpty(password)){
            layer.msg("密码不能为空！");
            return;
        }
        if (isEmpty(email)){
            layer.msg("邮箱不能为空！");
            return;
        }


        var formData = new FormData(document.getElementById("single"));//表单id

        $.ajax({
            url: '/ma/user/add',
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
                    content: data.toString() + "<br>"+ "<br>"+
                    "<a class='btn btn-primary' href='/ma/userManager' >查看" + "</a>" ,
                });

            })
            .fail(function() {
                console.log("error");
            })
            .always(function() {
                console.log("complete");

            });

    }


    function checkFileExt(filename)
    {
        var flag = false; //状态
        var arr = ["txt"];
        //取出上传文件的扩展名
        var index = filename.lastIndexOf(".");
        var ext = filename.substr(index+1);
        //循环比较
        for(var i=0;i<arr.length;i++) {
            if(ext == arr[i]) {
                flag = true; //一旦找到合适的，立即退出循环
                break;
            }
        }
        //条件判断
        if(flag) {

        }else {
            layer.msg("请选择正确格式的文件！");
//        alert("请选择正确的图片文件！");
            var file = document.getElementById('chooseFile');
            file.value = ''; //虽然file的value不能设为有字符的值，但是可以设置为空值
        }
    }
</script>
</body>
</html>