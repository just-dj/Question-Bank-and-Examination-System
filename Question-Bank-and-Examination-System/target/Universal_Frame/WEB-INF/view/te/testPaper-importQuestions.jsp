<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>试题导入</title>
<link rel="stylesheet" type="text/css" href="/static/css/testPaper-importQuestions.css">
</head>
<body>
    
    <%@ include file="../head.jsp" %>
	<div class="top-subtitle">
		<ul class="subtitle-cont">
			<li class="subtitle-cont-li">
				试题导入
			</li>
			<li class="empty-li">
			</li>
		</ul>
   </div>
   <input type="text" value="${testPaperId}" hidden="hidden" id="paperId">
   <div class="get-question-body">
   		<div class="get-question-left">
   			<ul class="paper-catalog">
   				<li class="basic-li">
   					当前试卷
   				</li>
   				<li class="basic-li">
   					<div class="question-collect">
   						<span>当前单选题:</span>
   						<span class="questions-num">
   						<c:out value="${quesNum[0]}">
                        </c:out>
                        </span>
   					</div>
   				</li>
   				<li class="basic-li">
   					<div class="question-collect">
   						<span>当前多选题:</span>
   						<span class="questions-num">
   						<c:out value="${quesNum[1]}">
                        </c:out>
                        </span>
   					</div>
   				</li>
   				<li class="basic-li">
   					<div class="question-collect">
   						<span>当前判断题:</span>
   						<span class="questions-num">
   						<c:out value="${quesNum[2]}">
                        </c:out>
                        </span>
   					</div>
   				</li>
   				<li class="basic-li">
   					<div class="question-collect">
   						<span>当前填空题:</span>
   						<span class="questions-num">
   						<c:out value="${quesNum[3]}">
                        </c:out>
                        </span>
   					</div>
   				</li>
   				<li class="basic-li">
   					<div class="question-collect">
   						<span>当前简答题:</span>
   						<span class="questions-num">
   						<c:out value="${quesNum[4]}">
                        </c:out>
                        </span>
   					</div>
   				</li>
   			</ul>
   		</div>
   		<div class="get-question-right">
   			<div class="grayborder choose-cont">
				<form id="searchForm" onsubmit="return false">
					<input type="hidden" name="courseId" value="${courseId}">
					<table class="choose-tab">
						<tr>
							<td class="choose-item">
								<span>题库名称</span>
								<select name="id" class="choose-select" id="testDatabaseid">
									<option value="-1">全部题库</option>
									<c:forEach items="${testDatabaseList}" var="testDataItem" varStatus="status">
										<option value="${testDataItem.getId()}">${testDataItem.getName()}</option>
									</c:forEach>
								</select>
							</td>
							<td class="choose-item">
								<span>题目类型</span>
								<select name="kind" class="choose-select" id = "kindName">
									<option value="all">所有题型</option>
									<option value="单选题">单选题</option>
									<option value="多选题">多选题</option>
									<option value="判断题">判断题</option>
									<option value="填空题">填空题</option>
									<option value="简答题">简答题</option>
								</select>
							</td>
							<td class="choose-item">
								<span>关键词</span>
								<input type="text" name="key" id="keyWord">
							</td>
						</tr>
						<tr>
							<td class="choose-item">
							</td>
							<td class="choose-item">
                                <button  class="search-bnt" id="searchQuestion">搜索</button>
							</td>
							<td class="choose-item">
							</td>
						</tr>
					</table>
				</form>
   			</div>
   			<div id="questionBody" class="grayborder " >
   				<div class="choose-div-top" >
   					<input type="checkbox" name="chooseAll" id="chooseAllCheck" class="choose-box choose-all-box">
   					<button class="search-bnt add-bnt" id="quesBnt">添加</button>
   				</div>
                <form>
                    <div class="choose-div-bottom">

                    </div>
                </form>
   				</div>
   			</div>
   		</div>
   </div>
</body>
<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="/static/js/testPaper-importQuestions.js"></script>
<script src="/static/lib/layer-v3.1.1/layer/layer.js"></script>
<script>

	var courseId = $("input[name='courseId']").val();
	var kindNow;

	$(document).ready(function () {
		$("#questionBody").hide();
        $("#chooseAllCheck").prop('checked',false);
    });

    var questions;
    var paperId =$("#paperId").val();

    console.log("paperId"+paperId);
    $("#chooseAllCheck").click(function()
    {
    	if($("#chooseAllCheck").is(':checked'))
    	{
    		$('.choose-one').prop('checked',true);
    	}
    else
    	{
    		$('.choose-one').prop('checked',false);
    	}
    });

    //搜索点击

	$("#searchQuestion").click(function(){
	    console.log("111")
		$("#quesBnt").html("添加");
//		var testDatabaseId = $("#testDatabaseid").val();
//		var kindName = $("#kindName").val();
//		var keyWord = $("#keyWord").val();

        var formData = new FormData(document.getElementById("searchForm"));//表单id

//		console.log(testDatabaseId+" "+kindName);
        $.ajax({
            url:'/te/testDatabase/search',
            type:'POST',
            data:formData,
            timeout:5000,
            dataType:'json',
            //上传文件不需要缓存
            cache:false,
            //data值是FormData对象，不需要对数据做处理
            processData:false,
            // 且已经声明了属性enctype="multipart/form-data"，所以这里设置为false
            contentType:false,
            success:function(data){

                $("#questionBody").show();
                $("#chooseAllCheck").prop('checked',false);
//                layer.open({
//                    type: 1,
//                    skin: 'my-layui', //样式类名
//                    closeBtn: 0, //不显示关闭按钮
//                    anim: 2,
//                    shadeClose: true, //开启遮罩关闭
//                    content: data.toString(),
//                });


				$(".choose-div-bottom").empty();
				renderQuestions(data);
            },
            error:function(){
                layer.msg("数据出现错误!");
            }
        });

	});


    //导出对应系列的题目
    $(".question-collect").click(function(){
    	$("#quesBnt").html("删除");
    	var kind = $(this).index(".question-collect");
    	kindNow = kind +1;
   		console.log("kind"+kind);
   		$.ajax({
		    url:'/te/testPaper/question',
		    type:'POST',
		    data:{
		    	"id":paperId,
		    	"kindId":(kind+1)//id
		    },
		    timeout:5000,    
		    dataType:'json',
		    success:function(data){

                	$("#questionBody").show();
                    $("#chooseAllCheck").prop('checked',false);

			        $(".choose-div-bottom").empty();
			        renderQuestions(data);
		    },
		    error:function(){
		        alert("数据出现错误");
		    }
		});
    });
//    console.log(ant);
//	renderQuestions(ant);

   	//添加或者删除的操作
   	$("#quesBnt").click(function(){

   	 var questionId="",value="",operation=0,url="/te/testPaper/import";
   	 var bntText = $(this).html();
   	 if(bntText.indexOf("添加")!=-1)
   	 {
   		operation = 1;
   		url="/te/testPaper/import";
   	 }
   	 console.log(operation);
   	 $.each( $(".choose-one:checked"),function(){
   		 var dom = $(this);
   		 questionId += ($(this).val());//id
   		var  index = $(this).index(".choose-one");
   		questionId +=" ";
        console.log(questionId);
        value+=$("input[name='score']").eq(index).val();//值

        value+=" ";
     });

        $.ajax({
            url:url,
            type:'POST',
            data:{
                questionId:questionId,
                value:value,
                paperId:paperId,
                operation:operation,
				kindId:kindNow,
            },
            timeout:5000,
            dataType:'json',
            success:function(data){
                $("#chooseAllCheck").prop('checked',false);
                if (0 ==  operation){
                    layer.msg("删除成功");
                    $(".choose-div-bottom").empty();
                    renderQuestions(data);
				}else {
						layer.open({
                        type: 1,
                        skin: 'my-layui', //样式类名
                        closeBtn: 0, //不显示关闭按钮
                        anim: 2,
                        shadeClose: true, //开启遮罩关闭
                        content: data.message
                    	});
				}

				setNum();

            },
            error:function(){
                layer.msg("数据出现错误,请稍后重试！(可能有题目没有输入分值)");
            }
        });

   	});


   	function setNum() {
        $.ajax({
            url:"/te/testPaper/info",
            type:'POST',
            data:{
                "id":paperId
            },
            timeout:5000,
            dataType:'json',
            success:function(data){
//                layer.msg(data)
				for (var i = 0; i < data.length; ++i ){
				    $(".questions-num").eq(i).html(data[i]);
				}


            },
            error:function(){
                layer.msg("数据出现错误,请稍后重试！");
            }
        });
    }
</script>
</html>