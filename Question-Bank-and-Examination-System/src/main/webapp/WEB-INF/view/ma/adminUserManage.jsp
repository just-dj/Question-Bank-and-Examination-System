<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
	<link href="/static/css/adminUserManage.css" rel="stylesheet" type="text/css" />
<title>用户管理</title>
</head>
<body>

	<%@ include file="../head.jsp"%>

	<div class="containers">

		<div class="main">
			<!-- 检索行 -->
			<div class="row main-search">
				<form id="searchForm" class="search-form" onsubmit="return false">
					<div class="row-left main-search-text">
						<div>账号</div>
						<input name="account" type="text" class="input" id="id">
						<div class="search-name">姓名</div>
						<input name="name" type="text" class="input" id="name">
					</div>
					<div class="row-right main-search-btn">
						<input type="submit" class="search-btn mybtn" onclick="searchUser()" value="检索">
					</div>
				</form>
			</div>
			<!-- table行 -->
			<div class="row main-table">
				<div class="row-left table-left">
					<table id="user-manage-tb" class="table ">
						<tr>
							<th>账号</th>
							<th>姓名</th>
							<th>性别</th>
							<th>邮箱</th>
							<th>账号状态</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${users}" var="user" varStatus="status">
							<tr onclick = "roleShow({id : ${user.id}})">
								<td>${user.account}</td>
								<td>${user.name}</td>
								<td>${user.sex }</td>
								<td>${user.email }</td>
								<c:if test="${user.use}">
									<td class="text-success">正常</td>
								</c:if>
								<c:if test="${!user.use}">
									<td class="text-danger">冻结</td>
								</c:if>
								<td>
									<div class="temp">
										<c:if test="${user.use}">
											<button class="btn btn-danger" onclick="changeStatus({kind :
													0,id:${user.id},this:this})">停用
											</button>
										</c:if>
										<c:if test="${!user.use}">
											<button class="btn btn-warning" onclick="changeStatus({kind :
													1,id:${user.id},this:this})">启用
											</button>
										</c:if>

										<button class="btn btn-warning" style="margin-left: 10px"
												onclick="changeStatus({kind : 2,id:${user.id},this:this})">重置密码</button>
									</div>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="row-right table-right">
					<a class="mybtn add-user-btn" href="/ma/user/add">添加用户</a>
					<div class="now-user-role" style="font-weight: bold">当前用户角色</div>
					<div class="user-role-list">
						<form id="changeUserRole" class="temp2" action="" onsubmit="return false">
							<input id="userNow" type="hidden" name="id" value="">
							<div class="roleItem">
								<input  class="nothing" type="checkbox" value="student" name="role">学生<br>
							</div>
							<div class="roleItem">
								<input class="nothing" type="checkbox" value="teacher" name="role">教师<br>
							</div>
							<div class="roleItem">
								<input class="nothing"  type="checkbox" value="manager" name="role">管理员<br>
							</div>
							<div class="role-btn-row">
							<input type="submit" value="确定修改" class="mybtn role-change-btn" onclick="changeUserRole()">
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>






	<script type="text/x-jsrender" id="head">
  <tr>
							<th>账号</th>
							<th>姓名</th>
							<th>性别</th>
							<th>邮箱</th>
							<th>账号状态</th>
							<th>操作</th>
						</tr>
</script>

	<script type="text/x-jsrender" id="item">
  <tr onclick = "roleShow({id : {{:id}}})">
		<td>{{:account}}</td>
		<td>{{:name}}</td>
		<td>{{:sex}}</td>
		<td>{{:email}}</td>
		{{if use}}
			<td class="text-success">正常</td>
		{{/if}}
		{{if !use}}
			<td class="text-danger">冻结</td>
		{{/if}}

		<td>
			<div class="temp">
				{{if use}}
				<button class="btn btn-danger" onclick="changeStatus({kind :0,id:{{:id}},this:this})">停用
				</button>
				{{/if}}

				{{if !use}}
				<button class="btn btn-warning" onclick="changeStatus({kind :1,id:{{:id}},this:this})">启用
				</button>
				{{/if}}

				<button class="btn btn-warning" style="margin-left: 10px"
						onclick="changeStatus({kind : 2,id:{{:id}},this:this})">重置密码</button>
			</div>
		</td>
	</tr>
</script>

	<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
	<script src="/static/lib/layer-v3.1.1/layer/layer.js"></script>
	<script src="/static/lib/jsrender-master/jsrender.min.js"></script>

	<script>

		var nname;

		var kkind;

        var userId = -1;

        function searchUser() {


//        $(".now-user-role").html("教师");


            var formData = new FormData(document.getElementById("searchForm"));//表单id

            $.ajax({
                url: '/ma/search',
                //type是无所谓的 但是get只能传递1kb数据
                type: 'POST',
                dataType:'json',
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

                    for (var i = 0; i < 3; ++i){
                        $("input[name='role']").eq(i).prop('checked',false);
                    }
                    userId = -1;

                    //获取模板
                    var head = $.templates('#head');
                    var item = $.templates('#item');
                    //模板与数据结合
                    var items = item.render(eval(data));


                    $("#user-manage-tb tbody").empty("");//清空表的内容

                    $("#user-manage-tb tbody").append(head);
                    $('#user-manage-tb tbody').append(items);

                })
                .fail(function() {
                    console.log("error");
                })
                .always(function() {
                    console.log("complete");

                });

        }


        function changeUserRole() {
            if (userId == -1)
                return;

            var formData = new FormData(document.getElementById("changeUserRole"));//表单id

            $.ajax({
                url: '/ma/changeUserRole',
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

                    layer.msg(data.toString());

                    if (data.toString()=="success"){
                        roleShow({"id":userId});
                    }


                })
                .fail(function() {
                    console.log("error");
                })
                .always(function() {
                    console.log("complete");

                });



        }

        function roleShow(parameters){

            var id = parameters.id;

            userId = id;

            $("#userNow").val(userId);



            $.ajax({
                url: '/ma/getUserRole',
                //type是无所谓的 但是get只能传递1kb数据
                type: 'POST',
                dataType:'json',
                // dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
                data:{"id":id}
            })
                .done(function(data) {
                    $("input[name='role']").prop('checked',false);
                    for (var i = 0; i < data.length; ++i){
                        for (var j = 0; j < 3;++j){
                            if ($("input[name='role']").eq(j).val()==data[i].toString()){
                                $("input[name='role']").eq(j).prop('checked',true);
                            }
                        }
//                console.log(data[i].toString());
                    }

//            layer.msg(data);

                })
                .fail(function() {

                    layer.msg("数据获取失败，请稍后重试");
                })
                .always(function() {
                    console.log("complete");

                });

        };

        function changeStatus(parameters) {

            event.stopPropagation();

            var kind = parameters.kind;
            var id = parameters.id;
			var point = parameters.this;

			console.log(	1111 + point);
//    var formData = new FormData(document.getElementById("two"));//表单id
            $.ajax({
                url: '/ma/stop',
                //type是无所谓的 但是get只能传递1kb数据
                type: 'POST',
                dataType:'json',
                // dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
                data:({
                    "kind":kind,
                    "id":id,
                }),
            })
                .done(function(data) {

                    layer.msg(data.message);

                    if (kind == 0 || kind == 1){
                        searchUser();
					}


                })
                .fail(function() {
                    console.log("error");
                })
                .always(function() {
                    console.log("complete");

                });



        }

	</script>

</body>
</html>