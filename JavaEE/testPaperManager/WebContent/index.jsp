<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>在线试卷管理系统首页</title>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type"/>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
      <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/mainStyle.css'/>">

	<style type="text/css">
	  
           
	</style>
  </head>
  
  <body>
     <div class="box">             
            <div class="top">
                <iframe frameborder="0" src="<c:url value='/pages/top.jsp'/>" name="top" id="top"></iframe>
            </div>
    
            <div class="left">
                <iframe frameborder="0" src="<c:url value='/pages/left.jsp'/>" name="left" id="left"></iframe>
            </div>
            
            <div class="main">
            <c:choose>
        <c:when test="${empty sessionScope.user }">
        	<iframe frameborder="0" src="<c:url value='/pages/body.jsp'/>" name="body" id="body"></iframe>
        </c:when>
        <c:otherwise> 
        	<iframe frameborder="0" src="<c:url value='/pages/user/main.jsp'/>" name="body" id="body"></iframe>
        </c:otherwise>
        </c:choose>
            	
            </div>
    </div>

  </body>
</html>
