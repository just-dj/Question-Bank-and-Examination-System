<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>题库管理</title>
	<link rel="stylesheet" type="text/css" href="/static/css/testDatabaseManager.css">
	<link rel="stylesheet" type="text/css" href="/static/lib/font-awesome-4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
</head>
<body>
    <%@ include file="../head.jsp" %>
	<div class="top-subtitle">
		<ul class="subtitle-cont">
			<li class="subtitle-cont-li">
				题库管理
			</li>
			<li class="empty-li">
			</li>
		</ul>
   </div>
   <div class="exams-cont">
   		<div class="exam-add database-item database-add">
   			<div>
   				<i class="fa fa-address-card fa-5x"></i>
   			</div>
   			<div class="exam-add-text">
   				新建题库
   			</div>
   		</div>

   		<c:forEach items="${testDatabaseList}" var="testDatabase" varStatus="status1">
   		<div class="exam-item database-item">
   			<table class="exam-item-table" cellpadding="0" cellspacing="0">
   				<tr>
   					<td>题库名称
   					</td>
   					<td>${testDatabase.getName()}
   					</td>
   				</tr>
				<tr>
					<td>题库标记
					</td>
					<td>${testDatabase.introduce}
					</td>
				</tr>
   				<tr >
   					<td>单选题数
   					</td>
   					<td>${quesNum.get(status1.count-1).get(0)}
   					</td>
   				</tr>
   				<tr >
   					<td>多选题数
   					</td>
   					<td>${quesNum.get(status1.count-1).get(1)}
   					</td>
   				</tr>
   				<tr >
   					<td>判断题数
   					</td>
   					<td>${quesNum.get(status1.count-1).get(2)}
   					</td>
   				</tr>
   				<tr>
   					<td>填空题数
   					</td>
   					<td>${quesNum.get(status1.count-1).get(3)}
   					</td>
   				</tr>
   				<tr>
   					<td>简答题数
   					</td>
   					<td>${quesNum.get(status1.count-1).get(4)}
   					</td>
   				</tr>
   			</table>
   			<div class="database-bnt-box">
	   			<button class="delete-database-bnt" onclick="deletebnt(${testDatabase.getId()})">删除</button>

				<a href="/te/testDatabase/question?id=${testDatabase.id}">
					<button class="view-database" onclick="viewDatabase(${testDatabase.getId()})">查看</button>
				</a>

	   			<button class="get-newQuestion" onclick="getQuestions(${testDatabase.getId()})">导入</button>
   			</div>
   		</div>
   		 </c:forEach>
   		</div>
        <div class="cover cover-add">
		<div class="cover-cont">
			<div class="cover-title" >
				&nbsp&nbsp&nbsp&nbsp导入题库
				<div class="close-alert">
					关闭
				</div>
			</div>
			<div class="cover-input" >
			    <form  method="post" action="" id="dataForm" onsubmit="return false">
				<table class="testbase-alert">
					<input type="hidden" class="new-search" name="id" id="testBaseId">
					<tr>
						<td>题目类型
						</td>
						<td>
							<select class="select-kind" name="kind">
								<option value="单选题">单选题</option>
								<option value="多选题">多选题</option>
								<option value="判断题">判断题</option>
								<option value="填空题">填空题</option>
								<option value="简答题">简答题题</option>
							</select>
						</td>
						<td>
							
						</td>
					</tr>
					<tr>
						<td>
						       试卷名称
						</td>
						<td>
							<input id="chooseFile" type="file" class="new-questions" name="file"
								   onchange="checkFileExt(this.value)">
						</td>
						<td>
							<button type="submit" class="add-submmit" id="creatPaperbnt" onclick="importQuestion()">导入试题</button>
						</td>
					</tr>
				</table>
				</form>
			</div>
		</div>
	</div>
    <div class="cover cover-create">
		<div class="cover-cont">
			<div class="cover-title" >
				&nbsp&nbsp&nbsp&nbsp创建题库
				<div class="close-alert">
					关闭
				</div>
			</div>
			<div class="cover-input" >
			    <form  method="post" action="" id="createForm" onsubmit="return false">
					<input type="hidden" name="courseId" value="${courseId}" />
				<table class="testbase-alert">
					<tr>
						<td>题库名称
						</td>
						<td>
							<input type="text" class="new-search" name="testDatabaseName" id="testBaseName">
						</td>
						<td>
							
						</td>
					</tr>
					
					<tr>
						<td>
						       备注
						</td>
						<td>
							<input type="text" class="new-search" name="introduce">
						</td>
						<td>
							<button type="submit" class="add-submmit" id="creatPaperbnt" onclick="createDatabase()">创建题库</button>
						</td>
					</tr>
				</table>
				</form>
			</div>
		</div>
	</div>
</body>
<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
<script src="/static/lib/layer-v3.1.1/layer/layer.js"></script>
<script type="text/javascript">
	var id ;

	function createDatabase() {

	    var name = $("input[name='testDatabaseName']").val();

		if (isEmpty({"data":name})){
		    layer.msg("题库名不能为空！");
		    return;
		}

        var formData = new FormData(document.getElementById("createForm"));//表单id

        $.ajax({
            url: '/te/testDatabase/new',
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
            $(".cover").attr("style","display:none");

            layer.open({
                type: 1,
                skin: 'my-layui', //样式类名
                closeBtn: 0, //不显示关闭按钮
                anim: 2,
                shadeClose: true, //开启遮罩关闭
                content: data.toString()+ '<br>' +
                "<br><a class='btn btn-primary' style='margin='auto';' href='"+"'>查看" + "</a>" ,
            });

        })
            .fail(function() {
                console.log("error");
            })
            .always(function() {
                console.log("complete");

            });
    }
	
	function importQuestion() {

        if (jQuery("input[type='file']").val()==""){
            layer.msg("请选择文件！");
//        alert("请选择文件！");
            return;
        }

        var formData = new FormData(document.getElementById("dataForm"));//表单id

        $.ajax({
            url: '/te/testDatabase/import',
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
            $(".cover").attr("style","display:none");

            layer.open({
                type: 1,
                skin: 'my-layui', //样式类名
                closeBtn: 0, //不显示关闭按钮
                anim: 2,
                shadeClose: true, //开启遮罩关闭
                content: data.message + '<br>' + data.result +
                "<br><a class='btn btn-primary' style='margin='auto';' href='"+"'>查看" + "</a>" ,
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

    function isEmpty(parameters) {
        var data = parameters.data;
        return data == null || data.toString() == "";
    }



    function deletebnt(databaseId)
	{
    	var element = $(event.srcElement);
		$.ajax({
		    url:"删除databaseURL",
		    type:'POST',
		    data:{
		    	databaseId:databaseId
		    },
		    timeout:5000,    
		    dataType:'json',
		    success:function(data){
		    	if(data.toString().indexOf("失败")>=0)
		        {
		    		alert("失败");
		    		
		        }
		    	else
		    	{
			        $(element).parent().parent().remove();
			      
		    	}
		        
		    },
		    error:function(){
		        alert("数据出现错误");
		    }
		});
	 }
     function viewDatabase(databaseId)
     {
    	 window.location.href="/te/testDatabase/question?databaseId=databaseId";
     }
     function getQuestions(databaseId)
     {
         id = databaseId;
    	 //input file name="newquestions"
    	 $(".cover-add").attr("style","display:block");
    	 $("#testBaseId").val(databaseId);
 		
     }

     $(".close-alert").click(function () {
 		$(".cover").attr("style","display:none");
 	 });
     $(".database-add").click(function(){
    	 $(".cover-create").attr("style","display:block");
     });



</script>
</html>