<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>用户登录</title>

        <style>
        *{
            margin: 0;
            padding: 0;
        }
        body{
            background-color: #06C9BF;
            text-align: center;
            font-family: sans-serif;
        }
        .box{
            width: 35%;
            padding: 20px;
            margin: auto;
            margin-top: 13%;
        }
        .header{
            margin-top: 5%;
            color: #061931;
        }
        form{
            margin: auto;
            margin-top: 5%;
            color:#061931;
            width: 580px;
        }
        table{
            margin-left: 108px; 
        }
        table tr{
            height: 25px;
            font-size: 14px;
        }
        .userinput,.btn{
            border: 1px;
            background: #292323;
            color: wheat;
        }
       
        .btn{
            margin-left: 14px;
            margin-top:10px;
            width: 45px;
            font-size: 12px;
        }
        
        .ps{
      	  color: grey;
         font-size: 10px;
        }
        </style>
    </head>
    <body>
        <div class="box">
            <div class="header">
                <h1>用户登录</h1>
            </div>
            <form action="${pageContext.request.contextPath}/user_login.action" method="post">
            <table class="registerform">
                <tr>
                    <td class="td1">用户账号：</td>
                    <td> <input class="userinput" type="text" name="username"> </td>
                </tr>
                <tr>
                    <td class="td1">用户密码：</td>
                    <td> <input class="userinput" type="password" name="password"> </td>
                </tr>
            
            </table>
            <div class="formsubmit">
                <span><input type="reset" class="btn" name="reset" value="重置"></button></span>
                <span><input type="submit" class="btn" name="submit" value="登录"></button></span>
            </div>
            </form>
            <br>
            <br>
            <br>
            <br>
            <br>
            <div class="ps">
            <p>基于SSH框架的客户关系管理系统</p>
            <br>
            <p>ps:没有做注册功能，登录  -> 用户账号：yang 用户密码:666<br>或lily,233或haha,123</p>
            </div>
         </div>
    </body>

</html>