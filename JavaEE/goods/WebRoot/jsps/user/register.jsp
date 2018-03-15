<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>注册</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/user/register.css'/>">
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="<c:url value='/jsps/js/user/register.js'/>"></script>

  </head>
  
  <body>
    <div id="divBody">
    	<div id="divTitle"><span>新用户注册</span></div>
    	<div id="divCenter">
	    	<form action="<c:url value="/UserServlet"/>" method="post" id="registForm">
	    		<input type="hidden" name="method"  value="register"/>
	    		<table>
	    			<tr>
	    				<td class="tdTitle">用户名：</td>
	    				<td class="tdInput"><input type="text" name="loginname" id="loginname" value="${user.loginname }"></td>
	    				<td class="tdLabel"><label name="loginnameError" class="error" id="loginnameError">${errors.loginnameError }</label></td>
	    			</tr>
	    			<tr>
	    				<td class="tdTitle">登陆密码：</td>
	    				<td class="tdInput"><input type="password" name="loginpass" id="loginpass" value="${user.loginpass }"></td>
	    				<td class="tdLabel"><label name="loginpassError" class="error" id="loginpassError">${errors.loginpassError }</label></td>
	    			</tr>
	    			<tr>
	    				<td class="tdTitle">确认密码：</td>
	    				<td class="tdInput"><input type="password" name="reloginpass" id="reloginpass" value="${user.reloginpass }"></td>
	    				<td class="tdLabel"><label name="reloginpassError" class="error" id="reloginpassError">${errors.reloginpassError }</label></td>
	    			</tr>
	    			<tr>
	    				<td class="tdTitle">Email：</td>
	    				<td class="tdInput"><input type="text" name="email" id="email" value="${user.email }"></td>
	    				<td class="tdLabel"><label name="emailError" class="error" id="emailError">${errors.emailError }</label></td>
	    			</tr>
	    			<tr>
	    				<td class="tdTitle">图形验证码：</td>
	    				<td class="tdInput"><input type="text" name="verifyCode" id="verifyCode" value="${user.verifyCode }"></td>
	    				<td class="tdLabel"><label name="verifyCodeError" class="error" id="verifyCodeError">${errors.verifyCodeError }</label></td>
	    			</tr>
	    			<tr>
	    				<td ></td>
	    				<td class="tdInput"><span class="spanImg"><img width="100" id="vcodeImg" src="<c:url value='/VerifyCodeServlet'/>"/></span></td>
	    				<td class="tdLabel"><a id="verifyCode" href='javascript:_hyz();'>换一张</a></td>
	    			</tr>
	    			<tr>
	    				<td></td>
	    				<td class="tdInput"><input type="image" id="submit" name="imgSubmit" src="<c:url value="/images/regist1.jpg"/>"></td>
	    				<td></td>
	    			</tr>
	    		
	    		</table>
	    	</form>
    	</div>
    
    </div>
  </body>
</html>
