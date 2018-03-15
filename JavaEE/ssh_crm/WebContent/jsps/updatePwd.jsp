<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>修改密码</title>
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
        </style>
        <script type="text/javascript">
        	$(function(){
        		// 点击提交按钮，依次进行表单校验
        		$("form").submit(function(){
        			var bool = true;
        			if(!validatePassword()) {
        				bool = false;
        			}
        			if(!validateNewpassword()) {
        				bool = false;
        			}
        			if(!validateRepassword()) {
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
        	
        	
        	// 输入input name，调用相应的校验方法
        	function invokeValidateFunction(inputName) {
        		inputName = inputName.substring(0, 1).toUpperCase()
        				+ inputName.substring(1);
        		var functionName = "validate" + inputName;
        		return eval(functionName + "()");
        	}
        	// 原密码校验
        	function validatePassword() {
        		var bool = true;
        		$("#passwordError").css("display", "none");
        		var value = $("#password").val();
        		var length = value.length;
        		var nospaceVal = value.replace(/\s+/, '');
        		if (!nospaceVal) {
        			$("#passwordError").css("display", "");
        			$("#passwordError").text("密码不能为空！");
        			bool = false;
        		} else if (length < 3 || length > 20) {
        			$("#passwordError").css("display", "");
        			$("#passwordError").text("长度必须在3-20之间！");
        			bool = false;
        		}else {// 验证原密码是否正确
        			$.ajax({
        				cache: false,
        				async: false,
        				type: "POST",
        				dataType: "json",
        				data: { password: value},
        				url: "/ssh_crm/user_ajaxValidatePwd",
        				success: function(flag) {
        					if(!flag) {
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
        	function validateNewpassword() {
        		var bool = true;
        		$("#newpasswordError").css("display", "none");
        		var value = $("#newpassword").val();
        		var length = value.length;
        		var nospaceVal = value.replace(/\s+/, '');
        		if (!nospaceVal) {
        			$("#newpasswordError").css("display", "");
        			$("#newpasswordError").text("新密码不能为空！");
        			bool = false;
        		} else if (length < 3 || length > 20) {
        			$("#newpasswordError").css("display", "");
        			$("#newpasswordError").text("长度必须在3-20之间！");
        			bool = false;
        		} 
        		return bool;
        	}
        	// 确认密码校验
        	function validateRepassword() {
        		var bool = true;
        		$("#repasswordError").css("display", "none");
        		var value = $("#repassword").val();
        		var length = value.length;
        		var nospaceVal = value.replace(/\s+/, '');
        		if (!nospaceVal) {
        			$("#repasswordError").css("display", "");
        			$("#repasswordError").text("确认密码不能为空！");
        			bool = false;
        		} else if (value != $("#newpassword").val()) {
        			$("#repasswordError").css("display", "");
        			$("#repasswordError").text("密码不一致！");
        			bool = false;
        		}
        		return bool;
        	}
        
        </script>
    </head>
    <body>
        <div class="box">
            <div class="header">
                <h1>用户修改密码</h1>
            </div>
            <form action="${pageContext.request.contextPath}/user_updatePwd.action" method="post">
            <table class="registerform">
                <tr>
                    <td class="td1">原始密码：</td>
                    <td> <input class="userinput" id="password" type="password" name="password"> </td>
                    <td> <label class="error" id="passwordError" ></label>${errors.passwordError }</td>
                </tr>
                <tr>
                    <td class="td1">新密码：</td>
                    <td> <input class="userinput" id="newpassword" type="password" name="newpassword"> </td>
                    <td> <label class="error" id="newpasswordError" ></label>${errors.newpasswordError }</td>
                </tr>
            	<tr>
                    <td class="td1">确认密码：</td>
                    <td> <input class="userinput" id="repassword" type="password" name="repassword"> </td>
                    <td> <label class="error" id="repasswordError" ></label>${errors.repasswordError }</td>
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