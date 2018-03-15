<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="">
    
    <title>body</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
a {text-decoration: none;}
</style>
  </head>
  
  <body>
    <h1>欢迎进入网上书城系统</h1>
    <br>
    <p>本网站是刚开始学习JavaWeb的练手项目，采用Servlet + Jsp + MVC设计模式。</p>
    <br>
    <br>
    <br>
     <a id="adminlink"  href="<c:url value='/adminjsps/login.jsp'></c:url>" target="parent">管理员入口</a>
   
  </body>
</html>
