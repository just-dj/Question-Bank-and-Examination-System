<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看考试</title>
	<link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
	<link rel="stylesheet" href="/static/lib/layui/css/layui.css">
	<link rel="stylesheet" type="text/css" href="/static/css/exam-viewTest.css">
	<%--<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>--%>
	<script src="/static/lib/layui/layui.all.js"></script>
</head>
<body>

<%@ include file="../head.jsp" %>

<div class="top-subtitle">
		<ul class="subtitle-cont">
			<li class="subtitle-cont-li">
				查看考试
			</li>
			<li class="empty-li">
			</li>
		</ul>
 </div>
 <div class="test-title">
 	<table>
 		<tr>
 			<td style="font-weight: bold">
 				考试班级:
 			</td>
 			<td>
 			    <c:forEach items="${exam.clazzList}" var="clazz" varStatus="status">
 				<c:out value="${clazz.name}"></c:out>
 				&nbsp;&nbsp;&nbsp;&nbsp;
 				</c:forEach>
 			</td>
 		</tr>
 		<tr>
 			<td style="font-weight: bold">
 				考试名称:
 			</td>
 			<td>
 				<c:out value="${exam.name}"></c:out>
 			</td>
 		</tr>
 		<tr>
 			<td style="font-weight: bold">
 				考试开始时间:
 			</td>
 			<td>
 				<c:out value="${exam.startTime}"></c:out>
 			</td>
 		</tr>
 		<tr>
 			<td style="font-weight: bold">
 				考试结束时间:
 			</td>
 			<td>
 				<c:out value="${exam.endTime}"></c:out>
 			</td>
 		</tr>
 		<tr>
 			<td style="font-weight: bold">
 				使用试卷:
 			</td>
 			<td>
 				<c:forEach items="${exam.testPaperList}" var="testPaper" varStatus="status">
 				<c:out value="${testPaper.name}"></c:out>
 				&nbsp;&nbsp;&nbsp;&nbsp;
 				</c:forEach>
 			</td>
 		</tr>
 	</table>
 	</div>

	<div class="container">
		<div class="row">
			<div class="col-md-5 col-md-offset-1" id="chart1" style="height: 400px">

			</div>
			<div class="col-md-5 col-md-offset-1" id="chart2" style="height: 400px">

			</div>
		</div>
	</div>


 	<div class=" test-info-main ">
 		<table class="layui-table"  lay-filter="demo">
			<colgroup>
				<col width="100">
				<col width="100">
				<col width="100">
				<col width="100">
				<col width="210">
				<col width="210">
				<col width="150">
				<col width="100">
				<col width="100">
				<col>
			</colgroup>

			<thead>
				<tr >
				<th lay-data="{field:'index',width:100}">
					编号
				</th>
				<th lay-data="{field:'account',width:100}">
					账号
				</th>
				<th lay-data="{field:'name',width:100}">
					姓名
				</th>
				<th lay-data="{field:'sex',  sort:true,width:100}">
					性别
				</th>
				<th lay-data="{field:'start',  sort:true,width:210}">
					开始考试时间
				</th>
				<th lay-data="{field:'end',  sort:true,width:210}">
					提交试卷时间
				</th>
				<th lay-data="{field:'kind',  sort:true,width:150}">
					使用试卷
				</th>
				<th lay-data="{field:'status',width:100, sort:true}">
					状态
				</th>
				<th lay-data="{field:'score',  sort:true,width:100}">
					当前分数
				</th>
				<th lay-data="{field:'option',width:110}">
					操作
				</th>
			</tr>
			</thead>

 			<c:forEach items="${answerList}" var="answer" varStatus="status">
 			<tr>
 				<td>
 					<c:out value="${status.count}"></c:out>
 				</td>
 				<td>
 					<c:out value="${answer.student.account}"></c:out>
 				</td>
 				<td>
 					<c:out value="${answer.student.name}"></c:out>
 				</td>
 				<td>
 					<c:out value="${answer.student.sex}"></c:out>
 				</td>
 				<td>
 					<c:out value="${answer.startTime}"></c:out>
 				</td>
 				<td>
 				    <c:out value="${answer.endTime}"></c:out>
 				</td>
 				<td>
 				    <c:out value="${answer.testPaper.name}"></c:out>
 				</td>

				<c:if test="${answer.commit==true}">
					<td class="text-success">提交</td>
				</c:if>
				<c:if test="${answer.commit==false}">
					<td class="text-danger">未参加</td>
				</c:if>

				<td>${answer.result}</td>
 				<td>
 					<%--<button class="view-bnt" onClick="toViewPaper(<c:out value="${answer.id}"></c:out>,<c:out--%>
 					<%--value="${answer.studentId}"></c:out>">查看</button>--%>
					 <c:if test="${answer.commit==true}">
						<button class="view-bnt" onClick="location.href='/te/exam/answer?id=${answer.id}'">查看
						</button>
					</c:if>

 				</td>
 			</tr>
 			</c:forEach>
 		</table>
 	</div>

</body>
<script src="/static/lib/echart/echarts.common.min.js"></script>

<script type="text/javascript">

    // 基于准备好的dom，初始化echarts图表
    var chart1 = echarts.init(document.getElementById('chart1'));

    option1 = {
        title : {
            text: '本次考试目前提交情况',
            subtext: '考试截至之前的数据可能有偏差',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'left',
            data:['已提交','未提交']
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'left',
                            max: 1548
                        }
                    }
                },
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        series : [
            {
                name:'提交情况',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:${submitAnswer.size()}, name:'已提交'},
                    {value:${answerList.size() - submitAnswer.size()}, name:'未提交'}
                ]
            }
        ]
    };


    // 为echarts对象加载数据
    chart1.setOption(option1);



    // 基于准备好的dom，初始化echarts图表
    var chart2 = echarts.init(document.getElementById('chart2'));

    var option2 = {
        title : {
            text: '成绩分布',
            subtext: '已提交考生的'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['成绩']
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : ${nameList.toString()}
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value} 分'
                }
            }
        ],
        series : [
            {
                name:'最高成绩',
                type:'line',
                data:${scoreList.toString()},
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name: '平均值'}
                    ]
                }
            }
        ]
    };


    // 为echarts对象加载数据
    chart2.setOption(option2);




    function a(examId,stuId)
	{
		window.location.href="..?";
	}


    var table = layui.table;

    //转换静态表格
    table.init('demo', {
        height: 700 //设置高度
        ,limit: '${answerList.size()}',
        cellMinWidth:'100',
        //支持所有基础参数
    });
</script>
</html>