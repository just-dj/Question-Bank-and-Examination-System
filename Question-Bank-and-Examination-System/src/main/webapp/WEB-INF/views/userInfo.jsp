<%--
  Created by IntelliJ IDEA.
  User: 霜
  Date: 18.4.21
  Time: 23:34
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>用户列表</title>
</head>
<body>
<h1>${message }</h1>
<h1>用户列表
    <shiro:hasPermission name="add">---<a href="/user/add">添加用户</a></shiro:hasPermission>
    ---<a href="/logout">退出登录</a>
</h1>
<h2>权限列表</h2>
<table>
    <tr>
        <td><shiro:authenticated>用户已经登录显示此内容</shiro:authenticated> </td>
    </tr>
    <tr>
        <td><font color="red">用户角色</font></td>
    </tr>
    <tr>
        <td><shiro:hasRole name="student">student角色</shiro:hasRole></td>
    </tr>
    <tr>
        <td><shiro:hasRole name="teacher">teacher角色</shiro:hasRole></td>
    </tr>
    <tr>
        <td><shiro:hasRole name="manager">manager角色</shiro:hasRole></td>
    </tr>
    <tr>
        <td><shiro:hasAnyRoles name="manager,teacher">**manager or teacher 角色用户登录显示此内容**</shiro:hasAnyRoles></td>
    </tr>

    <tr>
        <td>当前登录用户:<font color="red"><shiro:principal/></font></td>
    </tr>

    <tr>
        <td><font color="red">用户权限</font></td>
    </tr>


    <tr>
        <td><shiro:hasPermission name="add">add权限用户显示此内容</shiro:hasPermission></td>
    </tr>

    <tr>
        <td><shiro:hasPermission name="query">query权限用户显示此内容</shiro:hasPermission></td>
    </tr>

    <tr>
        <td><shiro:hasPermission name="delete">del权限用户显示此内容</shiro:hasPermission></td>
    </tr>

    <tr>
        <td><shiro:hasPermission name="update">update权限用户显示此内容</shiro:hasPermission></td>
    </tr>

    <tr>
        <td><shiro:hasPermission name="student:query">student:query
            权限用户显示此内容<shiro:principal/></shiro:hasPermission></td>
    </tr>

    <tr>
        <td><shiro:lacksPermission name="student:del"> 不具有user:del权限的用户显示此内容 </shiro:lacksPermission></td>
    </tr>
</table>


<ul>
    <c:forEach items="${userList }" var="user">
        <li>用户名：${user.name }----密码：${user.age }----<a href="/user/edit/${user.id}">修改用户</a>----<a
                href="javascript:;" class="del" ref="${user.id }">删除用户</a></li>
    </c:forEach>
</ul>
<img alt="" src="/static/img/1.jpg">
<script type="text/javascript" src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
<script>
    $(function(){
        $(".del").click(function(){
            var id=$(this).attr("ref");
            $.ajax({
                type:"delete",
                url:"/user/del/"+id,
                success:function(e){

                }
            });
        });
    });
</script>
</body>
</html>
