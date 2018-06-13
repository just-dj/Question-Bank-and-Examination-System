<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人中心</title>
	<link rel="stylesheet"  href="/static/css/teacherPersonalCenter.css">
	<link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
</head>
<body>
	<%@ include file="../head.jsp" %>

	<div class="personalCenter_box" >
		<div class="personalCenter_body" >
			<div class="person_info_btn_box">
				<div class="box_type person_info" >
					<label>个人信息</label>
				</div>
				<div class="box_type base_info" id="base_info">
					<p>基本信息</p>
				</div>
				<div class="box_type change_pwd" id="change_pwd">
					<p>修改密码</p>
				</div>
			</div>
			
			<div class="person_info_box" id="vue_person_info_box" >
				<div class="box_part_pic">
					<div class="box_part_title">
						<span>头像：</span>
					</div>
					<div class="box_part_pic_img">
						<c:choose>
							<c:when test="${user.img == null || user.img == ''}">
								<img id="headImg" class="circle_img" src="/static/img/teacher.png"/>
							</c:when>
							<c:otherwise>
								<img id="headImg" class="circle_img" src="${user.img}"/>
							</c:otherwise>
						</c:choose>
						<span></span>
					</div>
					<div class="box_part_pic_upload">
						<div class="temp">
							<form action="/changeImg" method="post" id="uploadForm" enctype="multipart/form-data" style="width:
							100%;height: 100%">
								<input id="chooseFile" name="file" type="file" value="选择文件"  onchange="checkFileExt(this.value)">
								<span>支持jpg、jpeg和png格式，最大1M</span>

							</form>
						</div>

						<div class="box_part_pic_upload_btn" id="pic_upload">
							<span>上传头像</span>
						</div>
						<div class="box_part_pic_upload_btn" id="pic_default">
							<a href="/defaultImg">
								<span style="color: black;">默认头像</span>
							</a>
						</div>
					</div>
				</div>
				<div class="box_part_type name">
					<div class="box_part_title_other">
						<span>姓名：</span>
					</div>
					<div class="box_part_context">
						<p>${user.name}</p>
					</div>
				</div>
				<div class="box_part_type e_mail">
					<div class="box_part_title_other">
						<span>邮箱：</span>
					</div>
					<div class="box_part_context">
						<p>${user.email}</p>
					</div>
				</div>
				<div class="box_part_type sex">
					<div class="box_part_title_other">
						<span>性别：</span>
					</div>
					<div class="box_part_context">
						<p>${user.sex}</p>
					</div>
				</div>
				<div class="box_part_type account_num">
					<div class="box_part_title_other">
						<span>账号：</span>
					</div>
					<div class="box_part_context ">
						<p>${user.account}</p>
					</div>
				</div>
			</div>
			
			<div class="change_pwd_box" style="display:none">
				<form id="from_change_pwd" name="from_change_pwd" method="post" onsubmit="return false">
					<div class="pwd_box_row">
						<div class="context_type">
							<span>验证码：</span>
						</div>
						<input id="identify_code_type" type="text" placeholder="请输入验证码" name="identifyCode"/>
						<div id="btn_get_idcode">
							<span id="sendEmail" onclick="sendEmail()">获取验证码</span>
						</div>
					</div>
					<div class="pwd_box_row">
						<div class="context_type">
							<span>新密码：</span>
						</div>
						<input type="password" placeholder="请输入新密码" name="password"/>
					</div>
					<div class="pwd_box_row">
						<div class="context_type">
							<span>确认密码：</span>
						</div>
						<input type="password" placeholder="请确认新密码" name="check_pwd"/>
					</div>
					<div class="btn_box" onclick="changePassword()">
						<input type="submit" name="submit_change" value="确认修改"/>
					</div>
				</form>
			</div>
		</div>
	</div>

</body>
<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
<script src="/static/lib/vue/vue.js"></script>
<script src="/static/lib/layer-v3.1.1/layer/layer.js"></script>
<%--<script src="../js/personalCenter.js"></script>--%>
<script>

	function changePassword() {
	    if (check()){
            var code = $("input[name='identifyCode']").val();
            var password = $("input[name='password']").val();

            $.ajax({
                url: '/changePassword',
                //type是无所谓的 但是get只能传递1kb数据
                type: 'Post',
                // dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
                data:({"identifyCode":code,"password":password})
            })
                .done(function(data) {
                    if (data.toString().indexOf("失败") >=0){
                        layer.msg(data.toString());
                        return;
                    }else {
                        window.location.href="localhost:8080/logout";
                        layer.msg(data.toString());
                    }
                })
                .fail(function() {
                    console.log("error");
                })
                .always(function() {
                    console.log("complete");
                });

		}
    }
	function check() {
        var condition = false;

        var code = $("input[name='identifyCode']").val();
        var password1 = $("input[name='password']").val();
        var password2 = $("input[name='check_pwd']").val();

        if ( empty(code) || code.length > 6){
            layer.msg("验证码不合法！");
            condition = true;
        }else  if (empty(password1) || password1.length < 6){
            layer.msg("密码不合法");
//            alert("密码不合法！");
            condition = true;
        }else  if(empty(password2) || password2 < 6){
            layer.msg("两次输入的密码不匹配！")
//            alert("密码不合法！");
            condition=true
        }else if (password1 != password2){
            layer.msg("两次输入的密码不匹配！")
//            alert("两次输入的密码不匹配！");
            condition = true;
        }
        return (!condition);
    }

    function empty(a) {
        return a==null || a=="" || a.trim()=="";
    }

$("#base_info").click(function() {
	$(".change_pwd_box").hide();
	$(".person_info_box").show();
});

$("#change_pwd").click(function() {
	$(".person_info_box").hide();
	$(".change_pwd_box").show();
});


var timer = 65;
var t;
function sendEmail(){
    if (timer < 60){
        layer.msg("邮件发送过于频繁，请 "+ (60 - timer) +" S之后重试");
//        alert("邮件发送过于频繁，请 "+ (60 - timer) +" S之后重试")
        return;
    }

    $.ajax({
        url: '/sendEmail',
        //type是无所谓的 但是get只能传递1kb数据
        type: 'Post',
    })
        .done(function(data) {
            console.log("success");
            layer.msg("已发送邮件，请查收！ 如遇网络延迟请耐心等待或重试。");
//            alert("已发送邮件，请查收！ 如遇网络延迟请耐心等待或重试。");
        })
        .fail(function() {
            console.log("error");
            layer.msg("邮件发送失败，请稍后重试！。");
//            alert("邮件发送失败，请稍后重试！。");
        })
        .always(function() {
            console.log("complete");
        });
    timer = 0;
    timedCount();
}

function timedCount()
{
    timer += 1;
    if (timer >= 62){
        clearTimeout(t);
    }
    else {
        t=setTimeout("timedCount()",1000);
    }


}

$("#pic_upload").click(function () {

    var formData = new FormData($('#uploadForm')[0]);

	if (jQuery("input[type='file']").val()==""){
	    layer.msg("请选择文件！");
//        alert("请选择文件！");
        return;
	}

    console.log(formData);
    $.ajax({
        url: '/changeImg',
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
            if (data.toString().indexOf("失败") >=0){
                layer.msg(data.toString());
//                alert(data.toString());
                return;
			}else {
                $("#headImg").attr('src',data.toString());
			}
        })
        .fail(function() {
            console.log("error");
        })
        .always(function() {
            console.log("complete");

        });


});


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


</script>
</html>