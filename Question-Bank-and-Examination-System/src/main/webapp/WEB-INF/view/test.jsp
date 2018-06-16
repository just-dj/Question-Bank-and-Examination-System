<%--
  Created by IntelliJ IDEA.
  User: shan
  Date: 18.5.16
  Time: 17:53
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传文件</title>
    <link rel="stylesheet" href="/static/lib/bootstrap-3.3.7-dist/css/bootstrap.css" >
</head>
<body>

<button class="test-check-btn" onclick="location.href='/st/course/examInfo'">查看考试信息
</button>

<!--对表格进行包装-->
<form id="uploadForm" action="/upload"  method="post" enctype="multipart/form-data">
    <label>${message}</label>
    <table>
        <tr>
            <td>
                <label>
                    <h2>同步方式上传文件</h2>
                </label>
            </td>
        </tr>
        <tr>
            <td><label>用户名:</label></td>
            <td><input type="text" name="author" id="author"/></td>
        </tr>
        <tr>
            <td><label> 选择文件：</label></td>
            <td><input  type="file"  name="file" value="choose file" /></td>
        </tr>
        <tr>
            <td> <input class="btn btn-primary" type="submit" value="提交并刷新"/></td>
        </tr>
    </table>

</form>


<table>
    <tr><td><h2>异步方式上传文件</h2></td></tr>
    <tr>
        <td><button class="upload btn btn-primary" >通过ajax方式上传</button></td>
    </tr>
    <tr>
        <td><label>上传结果：</label></td>
        <td><div id="result"></div></td>
    </tr>
    <tr>
        <td><label>上传的图片：</label></td>
        <td><img id="testImg" style="width: 200px;height: 200px;"  /></td>
    </tr>
    <tr>
        <td><a class="btn btn-primary" id="download" >下载文件</a></td>
    </tr>
</table>

</body>

<script src="/static/lib/jquery/jquery-3.2.1.min.js"></script>
<!--<link href="css/test.css" rel="stylesheet" type="text/css"/>-->
<script src="/static/js/loadFile.js"></script>


</html>
