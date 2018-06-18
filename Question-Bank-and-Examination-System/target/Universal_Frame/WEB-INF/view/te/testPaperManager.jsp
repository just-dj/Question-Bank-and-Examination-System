<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>试卷管理</title>
	<%--<link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >--%>
	<link rel="stylesheet" type="text/css" href="/static/css/testPaperManager.css">
</head>
<body>
    <%@ include file="../head.jsp" %>

	<div class="top-subtitle">
		<ul class="subtitle-cont">
			<li class="subtitle-cont-li">
				试卷管理
			</li>
			<li class="empty-li">
			</li>
		</ul>
	</div>
	<div class="search-cont">
	    <ul class="search-cont-tab">
			<li class="empty-li">
			</li>
			<li>
				<button class="basic-bnt add-paper-bnt" id="add_bnt">添加</button>
			</li>
		</ul>	
	</div>

	<%--<%--%>
		<%--//后面遍历我要用到的题目类型的数组--%>
		<%--//你不用添加--%>
		<%--String[] kindName = {"单择题","多选题","判断题","填空题","简答题"};--%>
	    <%--request.setAttribute("kindName", kindName);--%>
	<%--%>--%>
	<div class="paper-cont">
		<table class="paper-title"  cellpadding="0" cellspacing="0">
			<tr>
				<td class="paper-tab-title">
					 试卷名称
				</td>
				<td class="paper-tab-title">
					 总题数
				</td>
				<td class="paper-tab-title">
					 总分值
				</td>
				<td class="paper-tab-title">
					 试卷状态
				</td>
				<td class="paper-tab-title paper-tab-title-last">
					 操作
				</td>
			</tr>
		</table>
		<c:forEach items="${testPaperList}" var="testPaper" varStatus="status">
		<table class="paper-tab"  cellpadding="0" cellspacing="0">
			<tr class="paper-tab-cont-tr">
				<td class="paper-tab-cont">
					 ${testPaper.getName()}
				</td>
				<td class="paper-tab-cont">
				    <!-- 计算总题数 -->
					<c:out value="${ testPaperInfo[status.count-1].get(0).get(kindName.size())}">0</c:out>
				</td>
				<td class="paper-tab-cont">
					<c:out value="${testPaperInfo[status.count-1].get(1).get(kindName.size())}"></c:out>
				</td>
				<td class="paper-tab-cont">
					<c:choose>
						<c:when test="${testPaper.use}">
							<span  style=" color: #8a6d3b;">已用</span>
						</c:when>
						<c:otherwise>
							<span  style="color: #3c763d;">未用</span>
						</c:otherwise>
					</c:choose>
				</td>
				<td class="paper-tab-cont paper-tab-cont-last">
					<c:if test="${!testPaper.use}">
						<button class="basic-bnt delete-bnt" id="addPaperQuestion" onclick="deletePaper(${testPaper.getId()})">删除试卷</button>
						<a href="/te/testPaper/import?courseId=${courseId}&testPaperId=${testPaper.id}">
							<button class="basic-bnt" id="addPaperQuestion" onclick="toViewPaper()" >导入试题</button>
						</a>
					</c:if>
					<a href="/te/testPaper/question?id=${testPaper.id}">
						<button class="basic-bnt" id="viewPapper" onclick="toViewPaper()">浏览</button>
					</a>
				</td>
			</tr>
		</table>
		<div class='paper-detail'>
			<table class='paper-detail-tab' cellpadding='0' cellspacing='0'>
				<tr class='paper-detail-title'>
					<td>
						 序号
					</td>
					<td>
						题目类型
					</td>
					<td>
						总分值
					</td>
				</tr>
				<c:forEach items="${testPaperInfo[status.count-1].get(1)}" var="score" varStatus="status1">
			    <tr class='paper-detail-content'>
					<c:if test="${status1.count -1 < kindName.size() }">
						<td>
								${status1.count}
						</td>
						<td>
								${kindName.get(status1.count-1)} (共 ${testPaperInfo[status.count-1].get(0).get(status1.count -1)} 道)
						</td>
						<td>
								${score}
						</td>
					</c:if>

				</tr>
				</c:forEach>
			</table>
		</div>
		</c:forEach>
	</div>
	<div class="cover">
		<div class="cover-cont">
			<div class="cover-title" >
				&nbsp&nbsp&nbsp&nbsp创建试卷
				<div class="close-alert">
					关闭
				</div>
			</div>
			<div class="cover-input">
				<form action="" onsubmit="return false" id="addTestPaper">
					<input type="hidden" value="${courseId}" name="courseId"/>
					<table>
						<tr>
							<td>试卷名称
							</td>
							<td>
								<input name="name" type="text"class="paper-name-input">
							</td>
							<td>
								<button class="add-submmit" id="creatPaperbnt">创建试卷</button>
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
<script>
    function addQuestion()
    {
//    	window.location.href="getQuestions.jsp?paperId="+id;
    	event.stopPropagation();
    }
    function toViewPaper()
    {
//    	window.location.href="localhost:8080/te/testPaper/question?id="+id;
    	event.stopPropagation();

    }
    //删除试卷
    function deletePaper(id)
    {
    	event.stopPropagation();
    	layer.confirm('你真的要删除这套试卷吗？', {
    	 btn: ['确定','取消'] 
    	}, function(){
    		//删除试卷
    		$.ajax({
    		    url:'/te/testPaper/delete',
    		    type:'POST',
    		    data:{
    		    	"testPaperId":id,
    		    },
    		    timeout:5000,    
//    		    dataType:'json',
    		    success:function(data){
    		        layer.msg(data.toString());
    		    		window.location.reload();
    		    },
    		    error:function(){
    		        layer.msg("数据出现错误");
    		    }
    		});
    		
    	}, function(){
    		  
    	});
    }
	$("#add_bnt").click(function(){
		$(".cover").attr("style","display:block");
	});
	$(".close-alert").click(function () {
		$(".cover").attr("style","display:none");
	});
	var vis=false;
	$(".paper-tab").click(function() {
		
		console.log($(this).next(".paper-detail"));
		if($(this).next(".paper-detail:visible").length>0)
		   $(".paper-detail").attr("style","display:none");
		else
		{
			$(".paper-detail").attr("style","display:none");
			$(this).next(".paper-detail").attr("style","display:block");
		}
	});
	$("#creatPaperbnt").click(function()
	{

        var formData = new FormData(document.getElementById("addTestPaper"));//表单id
		var name = $(".paper-name-input").val();
		if (name==""){
		    layer.msg("试卷名不能为空！");
		    return;
		}

		event.stopPropagation();
        $.ajax({
            url: '/te/testPaper/new',
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
                content: data+ '<br>' +
                "<a class='btn btn-primary' style='margin='auto' ;'  href='' " + " >查看" + "</a>" ,
            });
        })
            .fail(function() {
                console.log("error");
            })
            .always(function() {
                console.log("complete");

            });
		
	
	});
</script>
</html>