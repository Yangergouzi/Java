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
	<base href="<%=basePath%>">
	
	<title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta charset="UTF-8">
	 <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
          <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
      
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
	<style>
	body {
		background: url("image/top-bg.png") no-repeat;
		background-size: 100%;
		margin-top: o;
		width: 100%;
		height: 100%;
		overflow: hidden;
	}
	
	h1 {
		color: #DE0B0B;
		font-family: Arial, Helvetica, sans-serif;
		font: bold;
		text-align: center;
	}
	
	p{
		color: #D6C2A5;
		margin-left: 80%;
	}
	.main{
		float:left;
		top:20px;
		color: #D6C2A5;
		margin-left: 50px;;
	}
	.btn:hover{background:#fedcd2;}
	</style>
</head>
<body>
	 <div class="box">
        <h1>在线试卷管理系统</h1>
      
    <c:choose>
        <c:when test="${empty sessionScope.user }"><p> 您好，游客</p></c:when>
        <c:otherwise> 
        	 <a href="<c:url value='/pages/user/main.jsp'/>" target="body" class="main"><button class="btn">首页</button> </a> 
        	<p>您好，${sessionScope.user.username }  &nbsp&nbsp
        	<a href="<c:url value='/user_exit.action'/>" target="parent">退出</a></p>     
        	
        </c:otherwise>
    </c:choose>  
    </div>

</body>
</html>
