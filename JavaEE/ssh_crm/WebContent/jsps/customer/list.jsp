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
	.tb2{width: 90%; border-collapse: collapse; margin-left: 5%;}
	.tb2 th,.tb2 td{border: 1px solid black;}
	.tb2 th{ background: #cdcdcd; font-weight: bold;}
	
</style>

</head>
<body>
	<form action="<c:url value='/customer_findByName.action' />" method="post">
		<table class="tb1">
			<tr>
				<td>客户名称：</td>
				<td><input type="text" name="custName"></td>
				<td> <input type="submit" class="btn" name="submit" value="筛选"></td>
			</tr>
		</table>
	</form>
	<h2>客户列表</h2>
	<table class="tb2">
		<tr>
			<th>客户id</th>
			<th>客户名称</th>
			<th>客户级别</th>
			<th>信息来源</th>
			<th>固定电话</th>
			<th>移动电话</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${pagebean.beanList }" var="customer">
			<tr>
				<td>${customer.cid }</td>
				<td>${customer.custName }</td>
				<td>${customer.custLevel.levelName }</td>
				<td>${customer.custSource}</td>
				<td>${customer.custPhone}</td>
				<td>${customer.custMobile}</td>
				<td>
					<a href="<c:url value='/customer_toUpdatePage.action?cid=${customer.cid }' />">修改</a> &nbsp;
					<a href="<c:url value='/customer_delete.action?cid=${customer.cid }' />" >删除</a> 
				</td>
			</tr>		
		</c:forEach>
	</table>
	<div style="float:left; width: 100%; text-align: center;margin-top:30px;">
		<hr/>
		<br/>
	<%@include file="/jsps/pager/pager.jsp" %>
	</div>
</body>
</html>