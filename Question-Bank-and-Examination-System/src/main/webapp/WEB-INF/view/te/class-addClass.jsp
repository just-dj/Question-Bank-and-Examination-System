<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建班级</title>
	<link href="/static/css/class-addClass.css" rel="stylesheet">
</head>
<body>
	<%@ include file="../head.jsp" %>
	<div class="addClass_body">
		<div class="addClass_box">
			<form id="form_adClass" action="/te/class/add" method="post" onsubmit="return check()">
				<input name="courseId" type="hidden" value="${courseId}"/>
				<div class="left_item">
					<div class="row_type">
						<span>班级名：</span>
						<input type="text" name="name" />
					</div>
					<div class="row_type">
						<span>上课时间：</span>
						<input type="text" name="time" />
					</div>
					<div class="row_type">
						<span>上课地点：</span>
						<input type="text" name="place" />
					</div>
					<div class="row_type" >
						<button id="submit_addClass" type="submit"  value="新建" >新建 </button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
	<script src="/static/lib/layer-v3.1.1/layer/layer.js"></script>
<script>
	function check() {
	    var condition = true;

		var name = $("input[name='name']").val();
        var time = $("input[name='time']").val();
        var place = $("input[name='place']").val();

//        console.log(name + "哈哈");

        if (isEmpty({data: name})){
            layer.msg("班级名不能为空！");
            condition = false;
		}else  if(isEmpty({data: time})){
            layer.msg("上课时间不能为空！")
            condition = false;
		}else  if (isEmpty({data: place})){
		    layer.msg("上课地点不能为空！")
            condition = false;
		}

		return condition;
    }

    function isEmpty(parameters) {
        var data = parameters.data;
	    return data == null || data.toString() == "";
    }


</script>
</body>

</html>