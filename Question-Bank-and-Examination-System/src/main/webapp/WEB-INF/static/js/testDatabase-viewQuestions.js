     function renderQuestions(ant){
    	var sum = 0;
    	var ques = ant.length;
	    for(var i=0;i<ant.length;i++)
	    {
	    	console.log("score:"+ant[i].score);
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
	    // $("#totalScore").text("共"+sum+"分");
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
    	var score = 5;
    	if(question["score"]==undefined||question["score"]=="")
    		score = 5;
    	else
    		score = question["score"];
    	var text = "<div class='question-item'>"+
		    "<div class='question-title'>"+
			"<span>"+question['question']+"</span>"+
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
    	if(question["score"]==undefined||question["score"]=="")
    		score = 0;
    	else
    		score = question["score"];
    	var text = "<div class='question-item'>"+
		    "<div class='question-title'>"+
			"<span>"+question['question']+"</span>"+
			"</div>"+   
		    "<p class='"+classA+"'>答案:"+question['answer']+"</p>"+
 	        "</div>";
 	    
 	    $(".questions-box").append(text);
    }
    function renderBlank(question)
    {
    	console.log(question["answer"]);
    	var classA="correct-ans";
    	if(question["score"]==undefined||question["score"]=="")
    		score = 5;
    	else
    		score = question["score"];
    	var text = "<div class='question-item'>"+
		    "<div class='question-title'>"+
			"<span>"+question['question']+"</span>"+
			"</div>"+
		    "<p class='"+classA+"'>答案:"+question['answer']+"</p>"+
 	        "</div>";
 	    
    	$(".questions-box").append(text);
    }
