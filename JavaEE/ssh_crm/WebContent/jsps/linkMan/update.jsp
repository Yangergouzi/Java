<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="gender" value="${linkMan.lkmGender }"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>修改联系人</title>
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
    		var lkmGender = "${gender}";
    		$("select[name='lkmGender']").children("option").each(function(){
    			if($(this).val() == lkmGender){
    				$(this).attr("selected","selected");
    			}
    		}); 		
    	});
    </script>
    </head>
    <body>
        <div class="box">
            <div class="header">
                <h2>修改联系人</h2>
            </div>
            <form action="${pageContext.request.contextPath}/linkMan_update.action" method="post">
            <input type="hidden" name="lid" value="${linkMan.lid }" />
            <table class="registerform">
                <tr>
                    <td class="td1">联系人姓名：</td>
                    <td> <input class="userinput" type="text" name="lkmName" value="${linkMan.lkmName }"> </td>
                    <td> <label class="error" id="lkmNameError" >* </label>${errors.lkmNameError}</td>
                </tr>
                <tr>
                    <td class="td1">联系人性别：</td>
                    <td> <select class="userinput" type="text" name="lkmGender" >        
                    	<option value="男" >----------   男    ----------</option>
                    	<option value="女" >----------   女    ----------</option>
                    </select></td>
                    <td> <label class="error" id="lkmGenderError" >* </label></td>
                </tr>
             
                <tr>
                    <td class="td1">联系人固定电话：</td>
                    <td> <input class="userinput" type="text" name="lkmPhone" value="${linkMan.lkmPhone }"> </td>
                    <td> <label class="error" id="lkmPhoneError" ></label>${errors.lkmPhoneError}</td>
                </tr>
                <tr>
                    <td class="td1">联系人移动电话：</td>
                    <td> <input class="userinput" type="text" name="lkmMobile" value="${linkMan.lkmMobile }"> </td>
                     <td> <label class="error" id="custMobileError" ></label>${errors.lkmMobileError}</td>
                </tr>
            	<tr>
            		<td class="td1">所属客户：</td>
            		<td>
            			<select class="userinput" type="text" name="custName" >       
            			<c:forEach items="${custNames }" var="custName"> 
                    	<option value="${custName }" <c:if test='${linkMan.customer.custName eq custName }' >selected="selected"</c:if>>---------- ${custName } ----------</option>
                  	 	</c:forEach>
                  	  </select>
            		</td>
            	</tr>
            </table>
            <div class="formsubmit">
                <span><input type="submit" class="btn" name="submit" value="修改"></button></span>
            </div>
            </form>
         </div>
    </body>

</html>