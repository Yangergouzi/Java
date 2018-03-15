<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="cLevel" value="${customer.custLevel }"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>修改客户</title>
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
    	$(function(){
    		var custLevel = "${cLevel}";
    		$("select[name='custLevel']").children("option").each(function(){
    			if($(this).val() == custLevel){
    				$(this).attr("selected","selected");
    			}
    		}); 		
    	});
    </script>
   </head>
    <body>
        <div class="box">
            <div class="header">
                <h2>修改客户</h2>
            </div>
            <form action="${pageContext.request.contextPath}/customer_update.action" method="post">
            <input type="hidden" name="cid" value="${customer.cid }" />
            <table class="registerform">
                <tr>
                    <td class="td1">客户名称：</td>
                    <td> <input class="userinput" type="text" name="custName" value="${customer.custName }"> </td>
                    <td> <label class="error" id="custNameError" >* </label>${errors.custNameError}</td>
                </tr>
                <tr>
                	
                    <td class="td1">客户级别：</td>
                     <td> <select class="userinput" type="text" name="levelName" > 
                    	<c:forEach items="${levels }" var="level">
                    		<option value="${level.levelName }">---------- ${level.levelName } ----------</option>
                    	</c:forEach>
                    </select></td>
                    <td> <label class="error" id="custLevelError" >* </label></td>
                </tr>
                <tr>
                    <td class="td1">信息来源：</td>
                    <td> <input class="userinput" type="text" name="custSource" value="${customer.custSource }"> </td>
                </tr>
                <tr>
                    <td class="td1">客户固定电话：</td>
                    <td> <input class="userinput" type="text" name="custPhone" value="${customer.custPhone }"> </td>
                    <td> <label class="error" id="custPhoneError" ></label>${errors.custPhoneError}</td>
                </tr>
                <tr>
                    <td class="td1">客户移动电话：</td>
                    <td> <input class="userinput" type="text" name="custMobile" value="${customer.custMobile }"> </td>
                     <td> <label class="error" id="custMobileError" ></label>${errors.custMobileError}</td>
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