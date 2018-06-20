     function renderQuestions(ant){
	    for(var i=0;i<ant.length;i++)
	    {
	    	console.log("score:"+ant[i].score);
	    	switch(ant[i].kindId)
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
	}
    function renderSingle(question)
    {
    	var classA="correct-ans",classB="correct-ans",classC="correct-ans",classD="correct-ans";
    	console.log(question["answer"]);
    	console.log(question['answer'].indexOf("a"));
    	console.log(question['answer'].indexOf("b"));
    	console.log(question['answer'].indexOf("c"));
    	console.log(question['answer'].indexOf("d"));
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
    	var score = 0;
    	if(question.score==undefined||question.score=="")
    		score = 2
    	else
    		score = question["score"];
    	var text = 
    		"<div class='question-big-box'>"+
			"<div class='choose-box-div'>"+
				"<input type='checkbox' name='questionId' class='choose-box choose-one' value='"+question["id"]+"'>"+
			"</div>"+
  			"<div class='question-box'>"+
	        "<p>"+question['question']+"</p>"+    
		    "<p class='"+classA+"'>A."+question['a']+"</p>"+
		    "<p class='"+classB+"'>B."+question['b']+"</p>"+
		    "<p class='"+classC+"'>C."+question['c']+"</p>"+
		    "<p class='"+classD+"'>D."+question['d']+"</p>"+
		    "分值：<input type='number' name='score' value='"+score+"'>"+
 	        "</div>"+
 	        "</div>";
 	    $(".choose-div-bottom").append(text);
    }
    function renderJudge(question)
    {
    	console.log(question["answer"]);
    	var classA="correct-ans",classB="correct-ans";
    	if(question['answer'].indexOf("a")==-1)
    	{
    		classA="correct-ans";
    	}
        if(question['answer'].indexOf("b")==-1)
    	{
    		classB="correct-ans";
    	}
        var score = 0;
    	if(question.score==undefined||question.score=="")
    		score = 2;
    	else
    		score = question["score"];
    	var text = 
    		"<div class='question-big-box'>"+
			"<div class='choose-box-div'>"+
				"<input type='checkbox' name='questionId' class='choose-box choose-one' value='"+question["id"]+"'>"+
			"</div>"+
  			"<div class='question-box'>"+
	        "<p>"+question['question']+"</p>"+    
		    "<p class='"+classB+"'>答案."+question['answer']+"</p>"+
		    "分值：<input type='number' name='score' value='"+score+"'>"+
 	        "</div>"
 	        "</div>";
 	    
 	    $(".choose-div-bottom").append(text);
    }
    function renderBlank(question)
    {
    	console.log(question["answer"]);
    	var classA="correct-ans";
    	if(question.score==undefined||question.score=="")
    		score = 2;
    	else
    		score = question["score"];
    	var text = 
    		"<div class='question-big-box'>"+
			"<div class='choose-box-div'>"+
				"<input type='checkbox' name='questionId' class='choose-box choose-one' value='"+question["id"]+"'>"+
			"</div>"+
  			"<div class='question-box'>"+
	        "<p>"+question['question']+"</p>"+    
		    "<p class='"+classA+"'>答案:"+question['answer']+"</p>"+
		    "分值：<input type='number' name='score' value='"+score+"'>"+
 	        "</div>"+
 	        "</div>";
 	    
 	    $(".choose-div-bottom").append(text);
    }