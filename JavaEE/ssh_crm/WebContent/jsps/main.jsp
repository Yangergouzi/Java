<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<style type="text/css">
	body {
		margin: 0;
		padding: 0;
		overflow: hidden;
	}
	
	.top {
		width: 100%;
		height: 10%;
		border: 1px solid #000000;
	}
	
	.menu {
		float: left;
		width: 25%;
		height: 600px;
		border: 1px solid #000000;
	}
	
	.body {
		position: absolute;
		margin-left: 25%;
		width: 75%;
		height: 90%;
		border: 1px solid #000000;
	}
	
	iframe {
		width: 100%;
		height: 100%;
	}
	</style>
</head>
<body>
	<div>
		<div class="top">
			<iframe frameborder="0" src="<c:url value='/jsps/top.jsp' />" name="top"></iframe>
		</div>

		<div class="menu">
			<iframe frameborder="0" src="<c:url value='/jsps/menu.jsp' />" name="menu"></iframe>
		</div>
		<div class="body">
			<iframe frameborder="0" src="<c:url value='/jsps/body.jsp' />" name="body"></iframe>
		</div>
	</div>

</body>
</html>