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
       <%--  <script src="<c:url value='/js/updatePwd.js'/>" charset="utf-8"></script> 
          --%>
        <title>修改密码</title>
        <script type="text/javascript">
        $(function(){
        	//没有错误信息，隐藏
        	$(".error").each(function(){
        		var val = $(this).text();
        		if(val == ""){
        			$(this).css("display","none");
        		}
        	});
        	// 点击提交按钮，依次进行表单校验
        	$("form").submit(function(){
        	//	return false;
        		 var bool = true;
        		if(!validatePassword()) {
        			bool = false;
        		}
        		if(!validateNewPassword()) {
        			bool = false;
        		}
        		return bool; 
        	}); 
        	// 输入框失去焦点，进行校验
        	$("input").blur(function() {
        		var inputName = $(this).attr("name");
        		invokeValidateFunction(inputName);
        	});
        	// 得到焦点，隐藏错误信息
        	$("input").focus(function() {
        		var inputName = $(this).attr("name");
        		$("#" + inputName + "Error").css("display", "none");
        	});
        });
    //  输入input name，调用相应的校验方法
        function invokeValidateFunction(inputName) {
        	inputName = inputName.substring(0, 1).toUpperCase()
        			+ inputName.substring(1);
        	var functionName = "validate" + inputName;
        	return eval(functionName + "()");
        }
        // 密码校验
        function validatePassword() {
    		 var value = $("#password").val();
        	var bool = true;
        	$("#passwordError").css("display", "none");

        //	var length = value.length;
        	var nospaceVal = value.replace(/\s+/, '');
        	if (!nospaceVal) {
        		$("#passwordError").css("display", "");
        		$("#passwordError").text("密码不能为空");
        		bool = false;
        	} else{//
        		$.ajax({
        			cache : false,
        			async : false,
        			type : "POST",
        			dataType : "json",
        			data : {
        				password : value
        			},
        			url : "/testPaperManager/user_ajaxValidatePwd.action",
        			success : function(result) {
        				if (!result) {
        					$("#passwordError").css("display", "");
        					$("#passwordError").text("原密码错误！");
        					bool = false;
        				}
        			}
        		});
        	}
        	return bool;      
    	}
        // 新密码校验
        function validateNewPassword() {
    		 var value = $("#newPassword").val();
        	var bool = true;
        	$("#newPasswordError").css("display", "none");

        //	var length = value.length;
        	var nospaceVal = value.replace(/\s+/, '');
        	if (!nospaceVal) {
        		$("#newPasswordError").css("display", "");
        		$("#newPasswordError").text("新密码不能为空");
        		bool = false;
        	} 
        	return bool;      
    	}

        </script>
        <style>
       
          .updatePwdForm{
              width: 560px;    
              margin-top:6%;
              margin-left: 15%;
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
     
            <div class="updatePwdForm">
                    <form action="<c:url value='/user_updatePwd.action'/>" method="post" id="updatePwdForm" >
                    <input type="hidden" name="method"  value="updatePwd"/>
                    <label name="updatePwdError" class="error" id="updatePwdError">${updatePwdError }</label>
                    <table>
                        <tr>
                            <td class="tdTitle">原密码</td>
                            <td class="tdInput"><input type="password" name="password" id="password" ></td>
                            <td class="tdLabel"><label name="passwordError" class="error" id="passwordError">${errors.usernameError }</label></td>
                        </tr>
                        <tr>
                            <td class="tdTitle">新密码</td>
                            <td class="tdInput"><input type="password" name="newPassword" id="newPassword" "></td>
                            <td class="tdLabel"><label name="newPasswordError" class="error" id="newPasswordError">${errors.newPasswordError }</label></td>
                        </tr>
                     
                    
                        <tr>
                            <td></td>
                            <td class="tdInput"><input id="submitBtn" type="submit" value="修改"/></td>
                            <td></td>
                        </tr>
                    
                    </table>
                </form>

            </div>
        </div>
    </body>
</html>