<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ page import = "justdj.top.pojo.AnswerQuestion" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="/static/css/st-viewExam.css" rel="stylesheet" type="text/css" />
	<script src="/static/lib/jquery/jquery-3.2.1.js"></script>
	<title>查看考试</title>
</head>
<body>

<%@ include file="../head.jsp" %>


<div class="containers">

	<div class="main">
		<div class="main-buttons">
			<button class="btn" name="single" id="single" onclick="chooseSingle()">单选题</button>
			<button class="btn" name="multiple" id="multiple" onclick="chooseMutiple()">多选题</button>
			<button class="btn" name="judge" id="judge" onclick="chooseJudge()">判断题</button>
			<button class="btn" name="fillin" id="fillin" onclick="chooseFillin()">填空题</button>
			<button class="btn" name="essay" id="essay" onclick="chooseEssay()">问答题</button>
		</div>
		<div class="main-questions">
			<div class="questions-title">
				<div class="questions-type">单选题</div>
				<div class="questions-grade">
					<div class="questions-num">共 ${singleList.size()}题</div>
					<div class="questions-totalg">${sum[0]}分</div>
				</div>
			</div>
			<div class="questions-details">
				<%int singleI = 0; %>
				<!-- 单选题 -->
				<c:forEach items="${singleList}" var="single" varStatus="status">
					<div class="question single-choice">
						<div class="question-q">
							<div class="question-id">${status.index+1 }.</div>
							<c:if test="${single.answer ==  single.userAnswer}">
								<div class="question-main question-right">
										${single.question}（${single.questionScore}分）
								</div>
							</c:if>

							<c:if test="${single.answer !=  single.userAnswer}">
								<div class="question-main question-wrong">
										${single.question}（${single.questionScore}分）
								</div>
							</c:if>

						</div>
						<div class="question-items">
							<div class="question-items-option">${single.a}</div>
							<div class="question-items-option">${single.b}</div>
							<div class="question-items-option">${single.c}</div>
							<div class="question-items-option">${single.d}</div>
						</div>
						<div class="answers">
							<div class="true-answer">参考答案：${single.answer}</div>
							<div class="choose-answer">你的答案：${single.userAnswer}</div>
							<div class="answer-score">你的成绩：${single.score}</div>
						</div>
					</div>
				</c:forEach>

				<!-- 多选题 -->
				<%int multipleI = 0; %>
				<c:forEach items="${multipleList}" var="multiple" varStatus="status">
					<div class="multiple-choice question ">
						<div class="question-q">
							<div class="question-id">${status.index+1 }.</div>
							<c:if test="${multiple.answer ==  multiple.userAnswer}">
								<div class="question-main question-right">
										${multiple.question}（${multiple.questionScore}分）
								</div>
							</c:if>

							<c:if test="${multiple.answer != multiple.userAnswer}">
								<div class="question-main question-wrong">
										${multiple.question}（${multiple.questionScore}分）
								</div>
							</c:if>
						</div>
						<div class="question-items">
							<div class="question-items-option">${multiple.a}</div>
							<div class="question-items-option">${multiple.b}</div>
							<div class="question-items-option">${multiple.c}</div>
							<div class="question-items-option">${multiple.d}</div>
						</div>
						<div class="answers">
							<div class="true-answer">参考答案：${multiple.answer}</div>
							<div class="choose-answer">你的答案：${multiple.userAnswer}</div>
							<div class="answer-score">你的成绩：${multiple.score}</div>
						</div>
					</div>
				</c:forEach>
				<!-- 判断题 -->
				<c:forEach items="${judgeList}" var="judge" varStatus="status">
					<div class="question judgement-choice">
						<div class="question-q">
							<div class="question-id">${status.index+1 }.</div>
							<c:if test="${judge.answer ==  judge.userAnswer}">
								<div class="question-main question-right">
										${judge.question}（${judge.questionScore}分）
								</div>
							</c:if>

							<c:if test="${judge.answer !=  judge.userAnswer}">
								<div class="question-main question-wrong">
										${judge.question}（${judge.questionScore}分）
								</div>
							</c:if>
						</div>
						<div class="answers">
							<div class="true-answer">参考答案：${judge.answer}</div>
							<div class="choose-answer">你的答案：${judge.userAnswer}</div>
							<div class="answer-score">你的成绩：${judge.score}</div>
						</div>
					</div>
				</c:forEach>
				<!-- 填空题 -->
				<%int fillInI = 0; %>
				<c:forEach items="${fillInList}" var="fillIn" varStatus="status">
					<div class="question fillin-choice">
						<div class="question-q">
							<div class="question-id">${status.index+1 }.</div>
							<div class="question-main ">
									${fillIn.question}（${fillIn.questionScore}分）
							</div>
						</div>
						<div class="answers">
							<div class="true-answer">参考答案：${fillIn.answer}</div>
							<div class="choose-answer">你的答案：${fillIn.userAnswer}</div>
							<div class="answer-score">你的成绩：${fillIn.score}</div>
						</div>
					</div>
				</c:forEach>
				<!-- 问答题 -->
				<c:forEach items="${essayList}" var="essay" varStatus="status">
					<div class="question essay-choice">
						<div class="question-q">
							<div class="question-id">${status.index+1 }.</div>
							<div class="question-main">
									${essay.question}（${essay.questionScore}分）
							</div>
						</div>
						<div class="answers">
							<div class="true-answer">参考答案：${essay.answer}</div>
							<div class="choose-answer">你的答案：${essay.userAnswer}</div>
							<div class="answer-score">你的成绩：${essay.score}</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
    $(".single-choice").show();
    $(".multiple-choice").hide();
    $(".judgement-choice").hide();
    $(".fillin-choice").hide();
    $(".essay-choice").hide();
    $("#single").css("background-color","#5C885C");
    function chooseSingle(){
        $(".single-choice").show();
        $(".multiple-choice").hide();
        $(".judgement-choice").hide();
        $(".fillin-choice").hide();
        $(".essay-choice").hide();
        $(".questions-type").html("单选题");
        $(".questions-num").html("共${singleList.size()}题");
        $(".questions-totalg").html("共${sum[0]}分");
        $(".btn").css("background-color","#5BC0DE");
        $("#single").css("background-color","#5C885C");
    }
    function chooseMutiple(){
        $(".single-choice").hide();
        $(".multiple-choice").show();
        $(".judgement-choice").hide();
        $(".fillin-choice").hide();
        $(".essay-choice").hide();
        $(".questions-type").html("多选题");
        $(".questions-num").html("共${multipleList.size()}题");
        $(".questions-totalg").html("共${sum[1]}分");
        $(".btn").css("background-color","#5BC0DE");
        $("#multiple").css("background-color","#5C885C");
    }
    function chooseJudge(){
        $(".single-choice").hide();
        $(".multiple-choice").hide();
        $(".judgement-choice").show();
        $(".fillin-choice").hide();
        $(".essay-choice").hide();
        $(".questions-type").html("判断题");
        $(".questions-num").html("共${judgeList.size()}题");
        $(".questions-totalg").html("共${sum[2]}分");
        $(".btn").css("background-color","#5BC0DE");
        $("#judge").css("background-color","#5C885C");
    }
    function chooseFillin(){
        $(".single-choice").hide();
        $(".multiple-choice").hide();
        $(".judgement-choice").hide();
        $(".fillin-choice").show();
        $(".essay-choice").hide();
        $(".questions-type").html("填空题");
        $(".questions-num").html("共${fillInList.size()}题");
        $(".questions-totalg").html("共${sum[3]}分");
        $(".btn").css("background-color","#5BC0DE");
        $("#fillin").css("background-color","#5C885C");
    }
    function chooseEssay(){
        $(".single-choice").hide();
        $(".multiple-choice").hide();
        $(".judgement-choice").hide();
        $(".fillin-choice").hide();
        $(".essay-choice").show();
        $(".questions-type").html("问答题");
        $(".questions-num").html("共${essayList.size()}题");
        $(".questions-totalg").html("共${sum[4]}分");
        $(".btn").css("background-color","#5BC0DE");
        $("#essay").css("background-color","#5C885C");
    }
</script>
</body>
</html>