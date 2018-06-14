     function renderQuestions(parameters){
         var ant = parameters.ant;
    	var sum = 0;
    	var ques = ant.length;
	    for(var i=0;i<ant.length;i++)
	    {
	    	// console.log("value:"+ant[i].value);
	    	sum+=ant[i]["score"];
	    	
	    	switch(ant[i]["kindId"])
	    	{
	    	case 1:
	    	case 2:
	    	  renderSingle(ant[i]);
	    	    break;
	    	case 3:
	       	  renderJudge(ant[i]);
	      	  break;
	      	case 4:
	      	case 5:
	      	  renderBlank(ant[i]);
	      	  break;
	    	}
	    }
	    $("#totalQuestion").text("共"+ques+"题");
	    $("#totalScore").text("共"+sum+"分");
	}
    function renderSingle(question)
    {
    	var classA="correct-ans",classB="correct-ans",classC="correct-ans",classD="correct-ans";
    	// console.log(question["answer"]);
    	// console.log(question['answer'].indexOf("a"));
    	// console.log(question['answer'].indexOf("b"));
    	// console.log(question['answer'].indexOf("c"));
    	// console.log(question['answer'].indexOf("d"));
    	if(question['answer'].indexOf("a",0)==-1)
    	{
    		//console.log(question['answer'].indexOf("a"));
    		classA="correct-ans wrong-ans";
    	}
    	if(question['answer'].indexOf("b",0)==-1)
    	{   //console.log(question['answer'].indexOf("b"));
    		classB="correct-ans wrong-ans";
    	}
    	if(question['answer'].indexOf("c",0)==-1)
    	{   //console.log(question['answer'].indexOf("c"));
    		classC="correct-ans wrong-ans";
    	}
    	if(question['answer'].indexOf("d",0)==-1)
    	{
    		//console.log(question['answer'].indexOf("c"));
    		classD="correct-ans wrong-ans";
    	}
    	var score = 5;
    	if(question["score"]==undefined||question["score"]=="")
    		score = 5;
    	else
    		score = question["score"];
    	var text = "<div class='question-item'>"+
		    "<div class='question-title'>"+
			"<span>"+question['question']+"</span>"+
			"<span>("+score+"分)</span>"+
			"</div>"+ 
		    "<p class='"+classA+"'>A."+question['a']+"</p>"+
		    "<p class='"+classB+"'>B."+question['b']+"</p>"+
		    "<p class='"+classC+"'>C."+question['c']+"</p>"+
		    "<p class='"+classD+"'>D."+question['d']+"</p>"+
 	        "</div>";
    	$(".questions-box").append(text);
    }
    function renderJudge(question)
    {
    	// console.log(question["answer"]);
    	var classA="correct-ans",classB="correct-ans";
    	if(question['answer'].indexOf("a")==-1)
    	{
    		classA="correct-ans wrong-ans";
    	}
        if(question['answer'].indexOf("b")==-1)
    	{
    		classB="correct-ans wrong-ans";
    	}
        var score = 5;
    	if(question["score"]==undefined||question["score"]=="")
    		score = 5;
    	else
    		score = question["score"];
    	var text = "<div class='question-item'>"+
		    "<div class='question-title'>"+
			"<span>"+question['question']+"</span>"+
			"<span>("+score+"分)</span>"+
			"</div>"+   
		    "<p class='"+classA+"'>A."+question['a']+"</p>"+
		    "<p class='"+classB+"'>B."+question['b']+"</p>"+
 	        "</div>";

 	    $(".questions-box").append(text);
    }

    function renderBlank(question)
    {
    	// console.log(question["answer"]);
    	var classA="correct-ans";
    	if(question["score"]==undefined||question["score"]=="")
    		score = 0;
    	else
    		score = question["score"];
    	var text = "<div class='question-item'>"+
		    "<div class='question-title'>"+
			"<span>"+question['question']+"</span>"+
			"<span>("+score+"分)</span>"+
			"</div>"+
		    "<p class='"+classA+"'>答案:"+question['answer']+"</p>"+
 	        "</div>";
 	    
    	$(".questions-box").append(text);
    }
// var ant =
// [{
// 	"a": "一层",
// 	"answer": "b",
// 	"b": "两层",
// 	"c": "三层",
// 	"d": "我也不知道",
// 	"id": 1,
// 	"kindId": 1,
// 	"kindName": "单选题",
// 	"question": "Mybatis最高有几级缓存机制？",
// 	"value":5,
// 	"testDatabaseId": 1
// }, {
// 	"a": "一层",
// 	"answer": "b",
// 	"b": "两层",
// 	"c": "三层",
// 	"d": "我也不知道",
// 	"id": 2,
// 	"kindId": 1,
// 	"kindName": "单选题",
// 	"question": "Mybatis最高有几级缓存机制？",
// 	"value":5,
// 	"testDatabaseId": 1
// }, {
// 	"a": "一层",
// 	"answer": "b",
// 	"b": "两层",
// 	"c": "三层",
// 	"d": "我也不知道",
// 	"id": 3,
// 	"kindId": 1,
// 	"kindName": "单选题",
// 	"question": "Mybatis最高有几级缓存机制？",
// 	"value":5,
// 	"testDatabaseId": 1
// }, {
// 	"a": "一层",
// 	"answer": "b",
// 	"b": "两层",
// 	"c": "三层",
// 	"d": "我也不知道",
// 	"value":5,
// 	"id": 4,
// 	"kindId": 1,
// 	"kindName": "单选题",
// 	"question": "Mybatis最高有几级缓存机制？",
// 	"testDatabaseId": 1
// }, {
// 	"a": "一层",
// 	"answer": "b",
// 	"b": "两层",
// 	"c": "三层",
// 	"d": "我也不知道",
// 	"id": 5,
// 	"value":5,
// 	"kindId": 1,
// 	"kindName": "单选题",
// 	"question": "Mybatis最高有几级缓存机制？",
// 	"testDatabaseId": 1
// }, {
// 	"a": "一层",
// 	"answer": "b",
// 	"b": "两层",
// 	"c": "三层",
// 	"d": "我也不知道",
// 	"id": 6,
// 	"kindId": 1,
// 	"value":5,
// 	"kindName": "单选题",
// 	"question": "Mybatis最高有几级缓存机制？",
// 	"testDatabaseId": 1
// }, {
// 	"a": "一层",
// 	"answer": "b",
// 	"b": "两层",
// 	"c": "三层",
// 	"d": "我也不知道",
// 	"id": 7,
// 	"value":5,
// 	"kindId": 1,
// 	"kindName": "单选题",
// 	"question": "Mybatis最高有几级缓存机制？",
// 	"testDatabaseId": 1
// }, {
// 	"a": "一层",
// 	"answer": "b",
// 	"b": "两层",
// 	"c": "三层",
// 	"value":5,
// 	"d": "我也不知道",
// 	"id": 8,
// 	"kindId": 4,
// 	"kindName": "判断",
// 	"question": "Mybatis最高有几级缓存机制？",
// 	"testDatabaseId": 1
// }, {
// 	"a": "1",
// 	"answer": "a",
// 	"value":5,
// 	"b": "2",
// 	"c": "我也不知大",
// 	"d": "太难了",
// 	"id": 9,
// 	"kindId": 1,
// 	"kindName": "单选题",
// 	"question": "1+1=？",
// 	"testDatabaseId": 1
// }];
// renderQuestions(ant);