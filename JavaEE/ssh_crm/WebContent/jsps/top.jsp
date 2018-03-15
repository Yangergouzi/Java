<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>top</title>
	<style>
	body {
		margin: 0;
		padding: 0;
	}
	
	span {
		font-size: 10px;
		margin-left: 78%;
	}
	</style>
</head>
<body>
	<div >
       <h1 style="text-align: center;">客户关系管理系统</h1>
       <span>
          	      当前用户：${sessionScope.user.username } &nbsp;&nbsp;&nbsp;
                <a href="<c:url value='/user_toUpdatePwdPage' />" target="parent">修改密码</a>&nbsp;&nbsp;&nbsp;
                <a href="<c:url value='/user_exit' />" target="parent">安全退出</a>
            </span>
        </div>
		
</body>
</html>