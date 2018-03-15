<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>拜访客户信息查询</title>

        <style>
        *{
            margin: 0;
            padding: 0;
        }
        body{
            text-align: center;
            font-family: sans-serif;
        }
        .box{
            width: 35%;
            padding: 20px;
            margin-left: 15%;
            margin-top: 10%;
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
        .error{color: red;}
        .userinput,.btn{
            border: 1px;
            background: #292323;
            color: wheat;
        }
        .btn{
            margin-left: 16px;
            margin-top:10px;
            width: 45px;
            font-size: 12px;
        }
        </style>
    </head>
    <body>
        <div class="box">
            <div class="header">
                <h2>拜访客户信息查询</h2>
            </div>
            <form action="${pageContext.request.contextPath}/visit_visitSelect.action" method="post">
            <table class="registerform">
         	   <tr>
                    <td class="td1">用户：</td>
                    <td><select class="userinput" type="text" name="username">
                    	<option></option>
                    	<c:forEach items="${usernames }" var="username">
                    		<option value="${username }">---------- ${username } ----------</option>
                    	</c:forEach>       
                    </select></td>
                    <td> <label class="error" id="userNameError" >* </label>${errors.usernameError}</td>
                </tr>
           
                <tr>
                    <td class="td1">拜访客户：</td>
                     <td> <select class="userinput" type="text" name="custName" > 
                  	   <option></option>
                     	<c:forEach items="${custNames }" var="custName">
                    	<option value="${custName }">---------- ${custName } ----------</option>
                    	</c:forEach>
                    </select></td>
                    <td> <label class="error" id="custNameError" >* </label></td>
                </tr>
        
            </table>
            <div class="formsubmit">
                <span><input type="submit" class="btn" name="submit" value="查询"></button></span>
            </div>
            </form>
         </div>
    </body>

</html>