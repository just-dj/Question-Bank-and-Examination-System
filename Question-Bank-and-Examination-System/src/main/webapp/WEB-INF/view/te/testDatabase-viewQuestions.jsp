<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/static/css/testDatabase-viewQuestions.css">
<title>查看题库</title>
</head>
<body>
    <%@ include file="../head.jsp" %>
	<div class="top-subtitle">
		<ul class="subtitle-cont">
			<li class="subtitle-cont-li">
				题库试题查看
			</li>
			<li class="empty-li">
			</li>
		</ul>
   </div>
   <div class="get-question-body">
   		<div class="get-question-left">
   			<ul class="paper-catalog">

   				<li class="basic-li">
   					<div class="question-type">
   						<span>单选题</span>
   					</div>
   				</li>
   				<li class="basic-li">
   					<div class="question-type">
   						<span>多选题</span>
   					</div>
   				</li>
   				<li class="basic-li">
   					<div class="question-type">
   						<span>判断题</span>

   					</div>
   				</li>
   				<li class="basic-li">
   					<div class="question-type">
   						<span>填空题</span>
   					</div>
   				</li>
   				<li class="basic-li">
   					<div class="question-type">
   						<span>简答题</span>
   					</div>
   				</li>
   			</ul>
   		</div>
   		<div class="get-question-right text-danger">
   			<div class="grayborder">
   				<div class="choose-div-top question-top">
   					<div id="kindName">
   						选择题
   					</div>
   					<div>
   						<span id="totalQuestion">共10题</span>&nbsp;&nbsp;&nbsp;&nbsp;
   						<span id="totalScore"></span>
   					</div>
   				</div>
			    <div class="questions-box">

				</div>
   			</div>
   		</div>
   </div>
	<input type="hidden" name="id" value="${testDatabaseId}"/>
</body>
<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
<script src="/static/lib/layer-v3.1.1/layer/layer.js"></script>
<script type="text/javascript" src="/static/js/testDatabase-viewQuestions.js"></script>
<script>

    var testDatabaseId;

	$(document).ready(function () {
        testDatabaseId = $("input[name='id']").val();
        $(".get-question-right").hide();
    })

	$(".basic-li").click(function(){
   		//console.log("kind"+kind);
   		$("#kindName").text($(this).find("span").text());
   		var kindName = $(this).find("span").text();
   		$.ajax({
		    url:'/te/testDatabase/ques',
		    type:'POST',
		    data:{
		        id:testDatabaseId,
		    	kindName:kindName//id
		    },
		    timeout:5000,    
		    dataType:'json',
		    success:function(data){
                $(".get-question-right").show();
				$(".questions-box").empty();
				renderQuestions(data);
		    },
		    error:function(){
		        layer.msg("获取数据失败，请稍后重试!")
		    }
		});
	});
	
</script>
</html>