<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加学生</title>
	<link href="/static/css/class-addStudent.css" rel="stylesheet">
	<link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
	<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
	<script src="/static/lib/layer-v3.1.1/layer/layer.js"></script>
</head>
<body>
	<%@ include file="../head.jsp" %>
	
	<div class="add_student_box">
		<div class="single_add_student_box">
			<div class="add_box_title">
				<span>单个添加</span>
			</div>
			<input name="classId2" type="hidden" value="${classId}"/>
			<form id="one" action="/te/class/student/add" enctype="multipart/form-data"  method="Post"
				  onsubmit="return false">
				<div class="add_box_body">
					<span>账号：</span>
					<input name="kind" type="hidden" value="0"/>
					<input name="classId" type="hidden" value="${classId}"/>
					<input name="account" class="add_box_input" type="text"  />
					<input onclick=" return sendData({kind : 0})" class="add_box_btn_type" type="submit"
						   name="submit_single_add_student" value="添加"/>
				</div>
			</form>

		</div>
		<div class="more_add_student_box">
			<div class="add_box_title" >
				<span>批量添加</span>
			</div>

			<form id="two" action="/te/class/student/add" enctype="multipart/form-data" method="Post"
				  onsubmit="return false">
				<input name="courseId" type="hidden" value="${courseId}">
				<input name="kind" type="hidden" value="1"/>
				<input name="classId" type="hidden" value="${classId}"/>
				<div class="add_box_body">
					<span >文件：</span>
					<div class="add_box_file">
						<input id="chooseFile" type="file" name="file"
							   onchange="checkFileExt({filename : this.value})"/>
					</div>
					<input onclick="return sendData({kind : 1})" class="add_box_btn_type" type="submit"
						   name="submit_more_add_studnet"
						   value="上传" />
				</div>
			</form>

			<div style="margin-left: 20px;">
				<span style="font-weight:bold;">提醒：</span>
				<span class="text-warning">目前只支持txt文件上传，识别。最大10M</span>
			</div>
		</div>
	</div>

</body>
<script >
    function sendData(parameters) {
        var kind = parameters.kind;

		var account = $("input[name='account']").val();

        var id = $("input[name='classId2']").val();

        var courseId = $("input[name='courseId']").val();

		if (1 == kind ){
            if (jQuery("input[type='file']").val()==""){
                layer.msg("请先选择文件！");
                //        alert("请选择文件！");
                return;
            }


            var formData = new FormData(document.getElementById("two"));//表单id
            $.ajax({
                url: '/te/class/student/add',
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
            })
                .done(function(data) {
					layer.open({
						type: 1,
						skin: 'my-layui', //样式类名
						closeBtn: 0, //不显示关闭按钮
						anim: 2,
						shadeClose: true, //开启遮罩关闭
						content: data.message + '<br>' +
						data.data.toString().replace(new RegExp(",","gm")," ")  + '<br>' +
						"<a class='btn btn-primary' href='/te/class/student?id="+id + "&courseId=" + courseId+" '>查看" +
						"</a>" ,
					});
                })
                .fail(function() {
                    console.log("error");
                })
                .always(function() {
                    console.log("complete");

                });
		}else {
		    if ( null == account || "" == account || ""==account.trim()){
                layer.msg("请输入学生账号！");
                return;
			}

            var formData = new FormData(document.getElementById("one"));//表单id
            $.ajax({
                url: '/te/class/student/add',
                //type是无所谓的 但是get只能传递1kb数据
                type: 'POST',
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
                        content: data.toString() +"<br>"+
                        "<a class='btn btn-primary' href='/te/class/student?id="+id+ "&courseId=" + courseId+"'>查看" + "</a>" ,
                    });

                })
                .fail(function() {
                    console.log("error");
                })
                .always(function() {
                    console.log("complete");

                });


		}


	return false;

    };

//    function replaceAll(parameters) {
//        var string = parameters.string;
//        var oldChar = parameters.oldChar;
//        var newChar = parameters.newChar;
//        while(string.indexOf(oldChar) >= 0){
//            string.replace(oldChar,newChar);
//		}
//		return string;
//    }

    function checkFileExt(parameters)
    {
        var filename = parameters.filename;
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
</html>