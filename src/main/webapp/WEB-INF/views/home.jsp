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

    <title>ホームページ</title>

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
ユーザ情報一覧
<br/>
<table border="1">
    <tr>
        <td>ユーザ名</td>
        <td>タスク一覧</td>
        <td>タスク履歴一覧</td>
        <td>タスク追加</td>
        <td>センサー情報一覧</td>
        <td>イベント</td>
    </tr>
    <c:forEach var="list" items="${masterList}">
        <tr>
            <td>${list.name}</td>
            <td><a href="getTaskPage.do?blueToothMac=${list.blueToothMac}">タスク一覧</a></td>
            <td><a href="getTaskHistory.do?blueToothMac=${list.blueToothMac}">タスク状況</a></td>
            <td><a href="taskPage.do?blueToothMac=${list.blueToothMac}">タスク追加</a></td>
            <td><a href="getSensorHistory.do?blueToothMac=${list.blueToothMac}">センサー情報</a></td>
            <td><a href="pushNotification.do?typeinfo=1&message=Sleep Alarm">Sleep Alarm</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
