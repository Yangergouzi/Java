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
          <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="<c:url value='/css/mainStyle.css'/>">
         <script src="<c:url value='/js/login.js'/>" charset="utf-8"></script>
        <title>用户登录</title>
        <style>
       
          .loginForm{
              width: 560px;    
              margin-top:6%;
              margin-left: 32%;
              line-height: 20px;
              background:#F2E0CB;
               border-radius: 5px;
          }
            .tdTitle{
              width: 150px;
              text-align: right;
          }
          .tdInput{
              width: 260px;
              height: 40px;
              margin-left:60%;
          }
           .error{
      	    width:150px;
          	font-size:12px;
          	color:#E51C23;
          }
          #submitBtn{
          	width:120px;
          	height:46px;
          	background:#FF9900;
          	border-radius:10px;
          	color:#E51C23;
          	font-size:18px;
          }
           #submitBtn:hover{
           	background:#CCCC00;
           }
         
        
        </style>
    </head>
    <body>
        <div class="box">
       <div class="top">
                <iframe frameborder="0" src="<c:url value='/pages/top.jsp'/>" name="top"></iframe>
            </div>   
            <div class="loginForm">
                    <form action="<c:url value='/user_login.action'/>" method="post" id="loginForm">
                    <input type="hidden" name="method"  value="login"/>
                    <label name="loginError" class="error" id="loginError">${loginError }</label>
                    <table>
                        <tr>
                            <td class="tdTitle">昵称或姓名</td>
                            <td class="tdInput"><input type="text" name="username" id="username" value="${userForm.username }"></td>
                            <td class="tdLabel"><label name="usernameError" class="error" id="usernameError">${errors.usernameError }</label></td>
                        </tr>
                        <tr>
                            <td class="tdTitle">密码</td>
                            <td class="tdInput"><input type="password" name="password" id="password" value="${userForm.password }"></td>
                            <td class="tdLabel"><label name="passwordError" class="error" id="passwordError">${errors.passwordError }</label></td>
                        </tr>
                       <tr>
                            <td class="tdTitle">图形验证码</td>
                            <td class="tdInput"><input type="text" name="verifyCode" id="verifyCode" value="${userForm.verifyCode }"></td>
                            <td class="tdLabel"><label name="verifyCodeError" class="error" id="verifyCodeError">${errors.verifyCodeError }</label></td>
                        </tr>
                        <tr>
                            <td ></td>
                            <td class="tdInput"><span class="spanImg"><img id="vcodeImg" src="<c:url value='/vCodeImg.action'/>"/></span></td>
                            <td class="tdLabel"><a id="verifyCode" href='javascript:_hyz();'>换一张</a></td>
                        </tr>
                    
                        <tr>
                            <td></td>
                            <td class="tdInput"><input id="submitBtn" type="submit" value="登录"/></td>
                            <td></td>
                        </tr>
                    
                    </table>
                </form>

            </div>
        </div>
    </body>
</html>