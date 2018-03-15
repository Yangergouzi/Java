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
	table{width: 90%; border-collapse: collapse; margin-left: 5%;}
	th,td{border: 1px solid black;}
	th{ background: #cdcdcd; font-weight: bold;}
</style>

</head>
<body>
	<h2>客户的联系人列表</h2>
	<table>
		<tr>
			<th>联系人姓名</th>
			<th>联系人性别</th>
			<th>联系人固定电话</th>
			<th>联系人移动电话</th>
			<th>所属客户</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${pagebean.beanList }" var="linkMan">
			<tr>
				<td>${linkMan.lkmName }</td>
				<td>${linkMan.lkmGender }</td>
				<td>${linkMan.lkmPhone }</td>
				<td>${linkMan.lkmMobile}</td>
				<td>${linkMan.customer.custName }</td>
				<td>
					<a href="<c:url value='/linkMan_toUpdatePage.action?lid=${linkMan.lid }' />">修改</a> &nbsp;
					<a href="<c:url value='/linkMan_delete.action?lid=${linkMan.lid }' />">删除</a> 
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