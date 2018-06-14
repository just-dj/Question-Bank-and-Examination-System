<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建课程</title>
	<link href="/static/css/course-addCourse.css" rel="stylesheet" >
	<link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
</head>
<body>
	<%@ include file="../head.jsp" %>
	<div class="addCourse_body_box">
		<div class="addCourse_box">
			<form id="form_addCourse" action="" method="post" onsubmit="return false">
				<div class="left_item">
					<div class="row_type">
						<span>课程名称：</span>
						<input type="text" name="name" />
					</div>
					<div class="row_type">
						<span>课程介绍：</span>
						<input type="text" name="introduce" />
					</div>
					<div class="row_type">
						<span>封面：</span>
						<input  id="chooseFile" type="file" name="file" onchange="checkFileExt(this.value)"  style="border-color: #ffffff"/>

					</div>
					<div class="row_type" style="height: 30px">
						<div class="text-warning" >支持jpg、jpeg和png格式，最大1M</div>
					</div>
					<div class="row_type">
						<input id="submit_addCourse" type="submit" onclick="sendData()" name="submit_addCourse" value="新建"/>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
	<script src="/static/lib/layer-v3.1.1/layer/layer.js"></script>
<script>

    function checkFileExt(filename)
    {
        var flag = false; //状态
        var arr = ["jpg","png","jpeg"];
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
            layer.msg("请选择正确的图片文件！");
//        alert("请选择正确的图片文件！");
            var file = document.getElementById('chooseFile');
            file.value = ''; //虽然file的value不能设为有字符的值，但是可以设置为空值
        }
    }

    function sendData() {

        var name = $("input[name='name']").val();
        var introduce = $("input[name='introduce']").val();

        if (isEmpty(name)){
            layer.msg("请输入课程名！");
            return;
		}else if(isEmpty(introduce)){
            layer.msg("请输课程介绍！");
			return;
		}


        if (jQuery("input[type='file']").val()==""){
            layer.msg("请选择文件！");
//        alert("请选择文件！");
            return;
        }

        var formData = new FormData(document.getElementById("dataForm"));//表单id

        $.ajax({
            url: '/te/course/add',
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
        }).done(function(data) {
            layer.open({
                type: 1,
                skin: 'my-layui', //样式类名
                closeBtn: 0, //不显示关闭按钮
                anim: 2,
                shadeClose: true, //开启遮罩关闭
                content: data.toString() +
                "<a class='btn btn-primary' href='/te/class/student?id="+12+"'>查看" + "</a>" ,
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

</body>
</html>