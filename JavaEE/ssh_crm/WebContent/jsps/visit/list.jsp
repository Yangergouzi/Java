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
	<h2>拜访列表</h2>
	<table>
		<tr>
			<th>用户</th>
			<th>拜访地点</th>
			<th>拜访内容</th>
			<th>拜访客户</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${pagebean.beanList }" var="visit">
			<tr>
				<td>${visit.user.username }</td>
				<td>${visit.vaddress }</td>
				<td>${visit.vcontent }</td>
				<td>${visit.customer.custName}</td>
				<td>
					<c:if test="${visit.user.username eq sessionScope.user.username}">
					<a href="<c:url value='/visit_toUpdatePage.action?vid=${visit.vid }' />">修改</a> 
					&nbsp;
					<a href="<c:url value='/visit_delete.action?vid=${visit.vid }' />" >删除</a> 
					</c:if>
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