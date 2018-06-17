<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>タスク履歴一覧</title>

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
タスク履歴
<br/>
<br>
<br>
ユーザ名：<input type="text" name="userName" value="${userName}" disabled="disabled">
<br>
<br>
<table border="1">
    <tr>
        <td>日付</td>
        <td>タスク情報</td>
        <td>タスク詳細</td>
        <td>タスクステータス</td>
    </tr>
    <c:forEach var="list" items="${historyLists}">
        <tr>
            <td>${list.date}</td>
            <td>${list.tid}</td>
            <td>${list.detail}</td>
            <td>${list.state}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
