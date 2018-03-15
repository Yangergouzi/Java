<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<style type="text/css">
	.tb2{width: 60%; border-collapse: collapse; margin-left: 5%;}
	 th,.tb2 td{border: 1px solid black;}
	 th{ background: #cdcdcd; font-weight: bold;}
	 td{text-align: center;}
</style>

</head>
<body>

	<h2>客户来源统计</h2>
	<table class="tb2">
		<tr>
			<th>客户级别</th>
			<th>数量</th>
		</tr>
		<c:forEach items="${list }" var="map">
			<tr>
				<td>${map.levelName }</td>
				<td>${map.num }</td>
			</tr>		
		</c:forEach>
	</table>
	
</body>
</html>