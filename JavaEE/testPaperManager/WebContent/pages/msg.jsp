<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
    <head>
            <meta charset="UTF-8">
         
        <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="<c:url value='/css/mainStyle.css'/>">
        <title></title>
        <style>
       	.msg{
       		margin-top:50px;
       		font-size:25px;
       		
       	}
        
        
        </style>
    </head>
    <body>
        <div class="box">
	      <%--   <div class="top">
	                <iframe frameborder="0" src="<c:url value='/pages/top.jsp'/>" name="top"></iframe>
	         </div> --%>
	         <div class="msg">	         
	         	${msg }
	         </div>
           
        </div>
    </body>
</html>