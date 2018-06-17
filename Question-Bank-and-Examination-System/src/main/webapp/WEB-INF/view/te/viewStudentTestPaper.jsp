<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/static/css/viewStudentTestPaper.css">
<title>查看学生答卷</title>
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
   		<div class="get-question-right">
   			<div class="grayborder">
   				<div class="choose-div-top question-top">
   					<div id="kindName">
   						选择题
   					</div>
   					<div>
   						<span id="totalQuestion">共10题</span>&nbsp;&nbsp;&nbsp;&nbsp;
   						<span id="totalScore">共40分</span>
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
				<button class="commit-bnt">提交修改</button>
   			</div>
   		</div>
   </div>
   
</body>
<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
<script src="/static/lib/layer-v3.1.1/layer/layer.js"></script>
<script type="text/javascript" src="/static/js/viewStudentTestPaper.js"></script>
<script>
    //获取url参数
    function GetRequest() {  
	   	var url = location.search; //获取url中"?"符后的字串  
	   	var theRequest = new Object();  
	   	if (url.indexOf("?") != -1) {  
		      var str = url.substr(1);  
		      strs = str.split("&");  
		      for(var i = 0; i < strs.length; i ++) {  
		         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);  
	      		}  
   		}  
     	return theRequest;  
	}

    var request ;

    $(document).ready(function () {
//        request = GetRequest();

    });

    var ans ="";
    var kindName;
    var answerId = '${answer.id}';

	$(".basic-li").click(function(){

        kindName = $(this).find("span").text();

   		$("#kindName").text(kindName);

        $.ajax({
            url: '/te/exam/getAnswer',
            //type是无所谓的 但是get只能传递1kb数据
            type: 'POST',
            dataType:'json',
            // dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
            data:{
                "answerId":answerId,
                "kind":kindName
            },
            cache:false,
            processData:true
        })
            .done(function(data) {
                ans=data;

                $(".questions-box").empty();

                renderQuestions(data);
            })
            .fail(function() {
                layer.msg("和服务器连接失败，请稍后重试");
            })
            .always(function() {
                console.log("complete");

            });


	});

	$(".commit-bnt").click(function(){
		var id = "";
		for(var i=0;i<ans.length;i++)
		{
			id+=ans.id;
			id+=" ";
		}
		var scorelist = "",anslist="";
		$(".scorelist").each(function(){
			scorelist+=$(this).val();
			scorelist+=" ";
		});
		$(".anslist").each(function(){
			anslist+=$(this).val();
			anslist+=" ";
		});
		console.log(anslist);
		console.log(scorelist);
		$.ajax({
		    url:'读取数据',
		    type:'POST',
		    data:{
		        id:id,
		        scorelist:scorelist,
		        anslist:anslist
		    },
		    timeout:5000,    
		    dataType:'json',
		    success:function(data){
		    	if(data.toString().indexOf("失败")>=0)
		        {
		    		alert("失败");
		        }
		    	else
		    	{   ans=data;
			        $(".questions-box").empty();
			        renderQuestions(data);
		    	}
		        
		    },
		    error:function(){
		        alert("数据出现错误");
		    }
		});
	});
	
</script>
</html>