<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.6.18
  Time: 19:51
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>开始考试</title>
    <link href="/static/css/StartExam.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/lib/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
    <script src="/static/lib/layer-v3.1.1/layer/layer.js"></script>
    <%--<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>--%>
</head>
<body>
    <%@ include file="../head.jsp" %>

    <div class="container">
        <div class="row clearfix">
            <div class="col-md-3 column">
                <div class="main-left">
                    <c:if test="${single != null}">
                        <div class="kind">
                            <div class="btn-group-title">单选题</div>
                            <div class="question">
                                <c:forEach items="${single}" var="singleQuestion" varStatus="status">
                                    <button value="${singleQuestion.id}"  class="btn btn-default questionBtn"
                                            type="button">${status.index + 1}</button>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${muti != null}">
                        <div class="kind">
                            <div class="btn-group-title">多选题</div>
                            <div class="question">
                                <c:forEach items="${muti}" var="mutiQuestion" varStatus="status">
                                    <button value="${mutiQuestion.id}"   class="btn btn-default questionBtn"
                                            type="button">${status.index + 1}</button>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${judge != null}">
                        <div class="kind">
                            <div class="btn-group-title">判断题</div>
                            <div class="question">
                                <c:forEach items="${judge}" var="judgeQuestion" varStatus="status">
                                    <button value="${judgeQuestion.id}"  class="btn btn-default questionBtn"
                                            type="button">${status.index + 1}</button>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${emp != null}">
                        <div class="kind">
                            <div class="btn-group-title">填空题</div>
                            <div class="question">
                                <c:forEach items="${emp}" var="emptyQuestion" varStatus="status">
                                    <button value="${emptyQuestion.id}"  class="btn btn-default questionBtn"
                                            type="button">${status.index + 1}</button>
                                </c:forEach>
                            </div>
                        </div>

                    </c:if>

                    <c:if test="${question != null}">
                        <div class="kind">
                            <div class="btn-group-title">简答题</div>
                            <div class="question">
                                <c:forEach items="${question}" var="questionQuestion" varStatus="status">
                                    <button value="${questionQuestion.id}" class="btn btn-default questionBtn"
                                            type="button">${status.index + 1}</button>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>

                </div>
            </div>
            <div class="col-md-9 column" >
                <div class="alert alert-dismissable alert-danger" id="timeDiv">
                    <strong>剩余时间：</strong>
                    <span id="time"></span>
                </div>
                <div class="panel panel-default" >
                    <form id="maxForm" onsubmit="return false">
                        <input type="hidden" name="examId" value="${examId}">
                        <input type="hidden" name="courseId" value="${courseId}">
                        <c:if test="${null != single}">
                            <c:forEach items="${single}" var="question" varStatus="status">
                                <div class="position" >
                                    <div class="panel-heading">
                                        <h3 class="panel-title">
                                            （单选题）${status.index +1} . ${question.question}
                                        </h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="normal"><input type="radio" name="${question.id}"
                                                                   value="a"/>A: ${question.a}
                                        </div>
                                        <div class="normal"><input type="radio" name="${question.id}"
                                                                   value="b"/>B:
                                                ${question.b}
                                        </div>
                                        <div class="normal"><input type="radio" name="${question.id}"
                                                                   value="c"/>C:
                                                ${question.c}
                                        </div>
                                        <div class="normal"><input type="radio" name="${question.id}"
                                                                   value="d"/>D:
                                                ${question.d}
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>

                        <c:if test="${null != muti}">
                            <c:forEach items="${muti}" var="question" varStatus="status">
                                <div class="position" >
                                    <div class="panel-heading">
                                        <h3 class="panel-title">
                                            （多选题）${status.index + 1} . ${question.question}
                                        </h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="normal"><input type="checkbox" name="${question.id}"  value="a"/>A:
                                                ${question.a}</div>
                                        <div class="normal"><input type="checkbox" name="${question.id}"
                                                                   value="b"/>B:
                                                ${question.b}
                                        </div>
                                        <div class="normal"><input type="checkbox" name="${question.id}"
                                                                   value="c"/>C:
                                                ${question.c}
                                        </div>
                                        <div class="normal"><input type="checkbox" name="${question.id}"
                                                                   value="d"/>D:
                                                ${question.d}
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>


                        <c:if test="${null != judge}">
                            <c:forEach items="${judge}" var="question" varStatus="status">
                                <div class="position" >
                                    <div class="panel-heading">
                                        <h3 class="panel-title">
                                            （判断题）${status.index + 1} . ${question.question}
                                        </h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="normal"><input type="radio" name="${question.id}"
                                                                   value="true"/>正确 </div>
                                        <div class="normal"><input type="radio" name="${question.id}"
                                                                   value="false"/>错误</div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>


                        <c:if test="${null != emp}">
                            <c:forEach items="${emp}" var="question" varStatus="status">
                                <div class="position" >
                                    <div class="panel-heading">
                                        <h3 class="panel-title">
                                            （填空题）${status.index + 1} . ${question.question}
                                        </h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="normal"><input class="form-control" type="text"
                                                                   name="${question.id}"/>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>


                        <c:if test="${null != question}">
                            <c:forEach items="${question}" var="questi" varStatus="status">
                                <div class="position" >
                                    <div class="panel-heading">
                                        <h3 class="panel-title">
                                            （简答题）${status.index + 1} . ${questi.question}
                                        </h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="normal">
                                            <textarea class="form-control" name="${questi.id}" rows="5"
                                                      style="width:
                                            600px"
                                              form="maxForm"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>
                    </form>

                    <div class="nextBtns">
                        <button class="btn btn-default next " type="button"  onclick="changeQuestion(1)">下一题>></button>
                        <button class="btn btn-default next" type="button" onclick="changeQuestion(0)"><<上一题</button>
                    </div>
                </div>
                <div class="submitBtn">
                    <button class="btn btn-default submix" type="button" onclick="sendData()">提交</button>
                </div>
            </div>
        </div>
    </div>
<script>

    var arr =new Array()

    var question = '${questionList}';

    var time = ${time};

    var interval;

    var up = 0;
    var now = 0;

    var tempup ;
    var tempnow;
    function sendData() {
        tempnow = now;
        tempup = up;
        for (var i = 0; i< question.length;++i){
            now = i;
            check();
        }
        for (var i = 0; i < question.length;++i){
            if (arr[i] == 0){
                layer.msg("有题目未完成！");
                now = tempnow;
                up=tempup;
                return;
            }
        }

        var formData = new FormData(document.getElementById("maxForm"));//表单id
        $.ajax({
            url: '/st/course/exam',
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
        })
            .done(function(data) {
                layer.open({
                    type: 1,
                    skin: 'my-layui', //样式类名
                    closeBtn: 0, //不显示关闭按钮
                    anim: 2,
                    shadeClose: true, //开启遮罩关闭
                    content: data.toString() +  '<br>' +
                    "<a class='btn btn-primary' href='/st/course/examInfo?id=${courseId} '>查看</a>" ,
                });
            })
            .fail(function() {
                console.log("error");
            })
            .always(function() {
                console.log("complete");

            });
    }



    $(".questionBtn").click(function () {
        $(".position").hide();

        var id = $(this).val();
       var index = $(".questionBtn").index(this);
        $(".position").eq(index).show();
       now = index;

        check();

       console.log(index + " 计算" + getQuestionById(id));

    });

//    0上1下
    function changeQuestion(kind) {
        if (kind== 0){
            if (now > 0){
                $(".position").hide();
                now--;
                $(".position").eq(now).show();
                check();
            }else {
                layer.msg("到底了！");
            }
        }else if (kind==1){
            if (now < question.length -1){
                $(".position").hide();
                now++;
                $(".position").eq(now).show();
                check();
            }else {
                layer.msg("到底了");
            }
        }
    }

    function check(para) {
        console.log("up" + up + "  now" + now  );
        if (up != now){
            if (question[up]['kindName'] =="单选题"){
                var names = question[up]["id"];
                var val=$('input:radio[name='+ names+']:checked').val();
                if (null == val || val ==undefined){
                    arr[up] = 0;
                    $(".questionBtn").eq(up).css("background-color","#ffffff").css("color","#333");
                }else{
                    $(".questionBtn").eq(up).css("background-color","#5cb85c").css("color","#ffffff");
                    arr[up] = 1;
                }
            }else  if (question[up]['kindName'] =="多选题"){
                var names =  question[up]["id"];
                var val=$('input:checkbox[name='+ names+']:checked').val();
//                console.log(val)
                if (null == val || val ==undefined){
                    arr[up] = 0;
                    $(".questionBtn").eq(up).css("background-color","#ffffff").css("color","#333");
                }else{
                    arr[up] = 1;
                    $(".questionBtn").eq(up).css("background-color","#5cb85c").css("color","#ffffff");
                }
            }else  if (question[up]['kindName'] =="判断题"){
                var names =  question[up]["id"];
                var val=$('input:radio[name='+ names+']:checked').val();
                if (null == val || val ==undefined){
                    arr[up] = 0;
                    $(".questionBtn").eq(up).css("background-color","#ffffff").css("color","#333");
                }else{
                    arr[up] = 1;
                    $(".questionBtn").eq(up).css("background-color","#5cb85c").css("color","#ffffff");
                }
            }else  if (question[up]['kindName'] =="填空题"){
                var names =  question[up]["id"];
                var val=$('input[name='+ names+']').val();
                if (null == val || val ==undefined || val.trim()==""){
                    arr[up] = 0;
                    $(".questionBtn").eq(up).css("background-color","#ffffff").css("color","#333");
                }else{
                    arr[up] = 1;
                    $(".questionBtn").eq(up).css("background-color","#5cb85c").css("color","#ffffff");
                }
            }else  if (question[up]['kindName'] =="简答题"){
                var names =  question[up]["id"];
                var val=$('textarea[name='+ names+']').val();
                if (null == val || val ==undefined || val.trim()==""){
                    arr[up] = 0;
                    $(".questionBtn").eq(up).css("background-color","#ffffff").css("color","#333");
                }else{
                    arr[up] = 1;
                    $(".questionBtn").eq(up).css("background-color","#5cb85c").css("color","#ffffff");
                }
            }
        }
        up = now;
    }


    function getQuestionById(questionId) {

        for(var i = 0; i < question.length; ++i){
            if (question[i]["id"] == questionId){
                return i;
            }
        }

    }


    window.onload = function () {
        var pos =  $('.main-left').offset();// offset() 获得div1当前的位置，左上角坐标(x,y)
        $(window).scroll(function () { //滚动条滚动事件
            if ($(this).scrollTop() > pos.top ) {
                $('.main-left').css('top', 0).css('position', 'fixed');
            } else if ($(this).scrollTop() <=  pos.top ) {
                $('.main-left').css('top',0).css('position', 'relative');
            }

        })

        var pos =  $('#timeDiv').offset();// offset() 获得div1当前的位置，左上角坐标(x,y)
        $(window).scroll(function () { //滚动条滚动事件
            if ($(this).scrollTop() > pos.top ) {
                $('#timeDiv').css('top', 0).css('position', 'fixed');
            } else if ($(this).scrollTop() <=  pos.top ) {
                $('#timeDiv').css('top',0).css('position', 'relative');
            }

        })
    };

    $(document).ready(function () {
        question = $.parseJSON(question);

        console.log(SecondToDate(time));
        console.log(question.length);

        interval = setInterval('updateTime()',1000);

        $(".position").hide();
        $(".position").eq(0).show();

        for (var i = 0; i <question.length ;++i){
            arr[i] = 0;
        }
    })



    function updateTime() {

        time--;
        if (0 >= time ){
            //提交函数
            clearInterval(interval);
            sendData();
            return;
        }

        $("#time").html(SecondToDate(time));
    }

    function SecondToDate(msd) {
        var time =msd
        if (null != time && "" != time) {
            if (time > 60 && time < 60 * 60) {
                time = parseInt(time / 60.0) + "分钟" + parseInt((parseFloat(time / 60.0) -
                    parseInt(time / 60.0)) * 60) + "秒";
            }
            else if (time >= 60 * 60 && time < 60 * 60 * 24) {
                time = parseInt(time / 3600.0) + "小时" + parseInt((parseFloat(time / 3600.0) -
                    parseInt(time / 3600.0)) * 60) + "分钟" +
                    parseInt((parseFloat((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60) -
                        parseInt((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60)) * 60) + "秒";
            } else if (time >= 60 * 60 * 24) {
                time = parseInt(time / 3600.0/24) + "天" +parseInt((parseFloat(time / 3600.0/24)-
                    parseInt(time / 3600.0/24))*24) + "小时" + parseInt((parseFloat(time / 3600.0) -
                    parseInt(time / 3600.0)) * 60) + "分钟" +
                    parseInt((parseFloat((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60) -
                        parseInt((parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60)) * 60) + "秒";
            }
            else {
                time = parseInt(time) + "秒";
            }
        }
        return time;
    }




</script>
</body>
</html>
