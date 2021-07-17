<%@ page language="java" contentType="text/html; charset=UTF-8"
                pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>景区网站管理</title>
</head>
<body>
<h1>${user.userName},欢迎您进入景区网站后台管理！</h1>
<div>本次登录IP:${user.lastIp}</div>
<div>登录时间：${user.lastVisit}</div>
</body>
</html>