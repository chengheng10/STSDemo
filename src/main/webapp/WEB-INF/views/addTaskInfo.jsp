<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();

    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>タスク追加</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
        <link rel="stylesheet" type="text/css" href="styles.css">
        -->

</head>

<body>
<a href="<%=basePath%>start.do">ホームページに戻る</a>
<br>
<br>
タスク追加画面

<br>
<form action="<%=request.getContextPath()%>/addTask.do" method="post">
    <input type="hidden" name="blueToothMac" value="${blueToothMac}"> <br>
    タスク ID：<input type="text" name="id"> ※特殊なIDを入れないでください。{0:出勤 99:退勤} <br>
    タスク 詳細 ：<input type="text" name="detail"> <br>
    タスクス テータス ：<input type="text" name="state"> ※追加するとき、初期で「4」にしてください。 <br>
    タスク TID：<input type="text" name="tid"> <br>
    <input type="submit" value="提交数据">

</form>
</body>
</html>
