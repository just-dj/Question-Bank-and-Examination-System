<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/static/css/testPaper-viewQuestion.css">
<title>查看试卷</title>
</head>
<body>
    <%@ include file="../head.jsp" %>
	<input name="testPaperId" type="hidden" value="${testPaperId}">
	<div class="top-subtitle">
		<ul class="subtitle-cont">
			<li class="subtitle-cont-li">
				试题查看
			</li>
			<li class="empty-li">
			</li>
		</ul>
   </div>
   <div class="get-question-body">
   		<div class="get-question-left">
   			<ul class="paper-catalog">
				<c:forEach items="${kinList}" var="name" varStatus="status">
					<li class="basic-li">
						<div class="question-type" onclick="getQuestion({kind : '${name}'})">
							<span>${name}</span>
						</div>
					</li>
				</c:forEach>
   			</ul>
   		</div>

   		<div class="get-question-right">
   			<div class="grayborder" >
   				<div class="choose-div-top question-top">
					<div class="question-title-container text-warning">
						<div id="kindName">
							选择题
						</div>
						<div>
							<span id="totalQuestion">共10题</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<span id="totalScore">共40分</span>
						</div>
					</div>
   				</div>
			    <div class="questions-box">
					<%--<div class="question-item">--%>
						<%--<div class="question-title">--%>
							<%--<span>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</span>--%>
							<%--<span>(2分)</span>--%>
						<%--</div>--%>
						<%--<p class="correct-ans">A.xxxxx</p>--%>
						<%--<p class="correct-ans wrong-ans" >B.xxxxx</p>--%>
						<%--<p class="correct-ans">C.xxxxx</p>--%>
						<%--<p class="correct-ans">D.xxxxx</p>--%>
					<%--</div>--%>
				</div>
   			</div>
   		</div>
   </div>
</body>
<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="/static/js/testPaper-viewQuestions.js"></script>
<script src="/static/lib/layer-v3.1.1/layer/layer.js"></script>
<script>

	var paperId
	var nowKind;


    $(document).ready(function () {
        paperId = $("input[name='testPaperId']").val();
        $(".grayborder").hide();
    });

	function getQuestion(parameters) {
        $(".grayborder").show();
        var kind = parameters.kind;

	    nowKind = kind;

        //console.log("kind"+kind);
        $("#kindName").text(kind);
        $.ajax({
            url:'/te/testPaper/question',
            type:'POST',
            data:{
                "id":paperId,
                "kind":kind//id
            },
            timeout:5000,
            dataType:'json',
            success:function(data){
//                layer.open({
//                    type: 1,
//                    skin: 'my-layui', //样式类名
//                    closeBtn: 0, //不显示关闭按钮
//                    anim: 2,
//                    shadeClose: true, //开启遮罩关闭
//                    content: data +
//                    "<a class='btn btn-primary' href='/te/class/student?id="+kind+"'>查看" + "</a>" ,
//                });
                $(".questions-box").empty();
                renderQuestions({ant: data});
            },
            error:function(){
                console.log("error");
            }
        });

    }



	
</script>
</html>