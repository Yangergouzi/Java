<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="cLevel" value="${customer.custLevel }"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>修改拜访记录</title>
		<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
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
    <script type="text/javascript">
    </script>
   </head>
    <body>
        <div class="box">
            <div class="header">
                <h2>修改拜访记录</h2>
            </div>
            <form action="${pageContext.request.contextPath}/visit_update.action" method="post">
            <input type="hidden" name="vid" value="${visit.vid }">
            <table class="registerform">
                 <tr>
                    <td class="td1">用户名称：</td>
                    <td> <input class="userinput" type="text" name="username" readonly="readonly" value="${sessionScope.user.username }"> </td>
                    <td> <label class="error" id="userNameError" >* </label>${errors.usernameError}</td>
                </tr>
                <tr>
                    <td class="td1">拜访地点：</td>
                    <td> <input class="userinput" type="text" name="vaddress" value="${visit.vaddress }"> </td>
                </tr>
     
                <tr>
                    <td class="td1">拜访内容：</td>
                    <td> <input class="userinput" type="text" name="vcontent" value="${visit.vcontent}"> </td>
                     <td> <label class="error" id="vcontentError" >* </label></td>
                </tr>
                <tr>
                    <td class="td1">拜访客户：</td>
                     <td> <select class="userinput" type="text" name="custName" > 
                     	<c:forEach items="${custNames }" var="custName">
                    	<option value="${custName }" <c:if test="${custName eq visit.customer.custName }">selected='selected'</c:if>>---------- ${custName } ----------</option>
                    	</c:forEach>
                    </select></td>
                    <td> <label class="error" id="custNameError" >* </label></td>
                </tr>
        
            
            </table>
            <div class="formsubmit">
                <span><input type="reset" class="btn" name="reset" value="重置"></button></span>
                <span><input type="submit" class="btn" name="submit" value="修改"></button></span>
            </div>
            </form>
         </div>
    </body>

</html>