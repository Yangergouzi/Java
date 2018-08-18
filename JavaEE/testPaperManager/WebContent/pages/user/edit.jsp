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

        <title>编辑资料</title>
        <script type="text/javascript">
        $(function(){  
        	  $(".updateForm").hide();//先隐藏所有修改表单
        	  //点击出现修改表单
        	  $("a").click(function(){
        		  $("span").show();
        		  $(".updateForm").hide();
        		 $(this).parent().siblings(".td2").children("span").hide(); 
        		 $(this).parent().siblings(".td2").children(".updateForm").show(); 
        	  });
        	// 点击提交按钮，依次进行表单校验
          	$(".updateForm").submit(function(){
          	//	return false;
          		 var bool = true;
          		var inputname = $(this).children("input").attr("name");
          		bool = invokeValidateFunction(inputname);
          		return bool; 
          	}); 
        });
    //  输入input name，调用相应的校验方法
        function invokeValidateFunction(inputName) {
        	inputName = inputName.substring(0, 1).toUpperCase()
        			+ inputName.substring(1);
        	var functionName = "validate" + inputName;
        	return eval(functionName + "()");
        }
    //  登录名校验
        function validateUsername() {
        	var bool = true;
        	var value = $("#username").val();
        
	        	var length = value.replace(/\u4e00-\u9fa5/, "aa").length;
	        	var nospaceVal = value.replace(/\s+/, '');
	        	if (!nospaceVal) {
	        		$("#error").text("昵称不能为空！");
	        		bool = false;
	        	} 
   		
        	return bool;
        }
     // 邮箱校验
        function validateEmail() {
        	var bool = true;
        	var value = $("#email").val();
        	
	        	var length = value.length;
	        	var nospaceVal = value.replace(/\s+/, '');
	        	if (!nospaceVal) {
	        		$("#error").css("display", "");
	        		$("#error").text("邮箱不能为空");
	        		bool = false;
	        	} else if (!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value)) {
	        		$("#error").css("display", "");
	        		$("#error").text("邮箱格式错误");
	        		bool = false;
	        	}else{//邮箱重复校验
	        		$.ajax({
	        			cache : false,
	        			async : false,
	        			type : "POST",
	        			dataType : "json",
	        			data : {
	        				email : value
	        			},
	        			url : "/testPaperManager/user_ajaxValidateEmail.action",
	        			success : function(result) {
	        				if (!result) {
	        					$("#error").css("display", "");
	        					$("#error").text("该邮箱已被注册！");
	        					bool = false;
	        				}
	        			}
	        		});
	        	}
        	
        	return bool;
        }
        // 电话校验
        function validatePhone() {
        	var bool = true;
        	var value = $("#phone").val();
        		        	 
	        	 if (!/^1[34578]\d{9}$/.test(value)) {
	        		$("#error").css("display", "");
	        		$("#error").text("电话格式错误");
	        		bool = false;
	        	}  
        	
        	return bool;
        }
       
        </script>
        <style>
       .box{width:500px;margin-left:22%;margin-top:6%;}
       table{line-height:50px;}
       .td1{width:150px;}
       .td2{width:300px;}
       .updateForm{line-height:20px;}
      .td3{width:100px;}
        #error{font-size:120x;color:#E51C23;}
        
        </style>
    </head>
    <body>
        <div class="box">
        <label id="error"></label>
        <table>
        	<tr>
        		<td class="td1">我的昵称：</td>
        		<td class="td2">
        		<span>${sessionScope.user.username}</span>
        		<form class="updateForm usernameForm" action="<c:url value='/user_updateUsername.action'/>" method="post">
        			<input type="text" name="username" id="username"/>
        			<button type="submit">确认</button>
        		</form>
        		</td>
        		<td class="td3"><a href="javascript:;">修改</a></td>
        	</tr>
        	<tr>
        		<td class="td1">我的性别：</td>
        		<td class="td2">
        		<span>${sessionScope.user.gender}</span>
        		<form class="updateForm genderForm" action="<c:url value='/user_updateGender.action'/>" method="post">
        			男 <input type="radio" value="男" name="gender" id="gender"/>
        			女 <input type="radio" value="女" name="gender" id="gender"/>
        			<button type="submit">确认</button>
        		</form>
        		</td>
        		<td class="td3">
        		<c:choose>
        		<c:when test="${sessionScope.user.gender eq null}">
        			<a href="javascript:;">添加</a>
        		</c:when>
        		<c:otherwise>
        			<a href="javascript:;">修改</a>
        		</c:otherwise>   
        		</c:choose>    		
        		</td>
        	</tr>
        	<tr>
        		<td class="td1">我的邮箱：</td>
        		<td class="td2">
        		<span>${sessionScope.user.email}</span>
        		<form class="updateForm emailForm" action="<c:url value='/user_updateEmail.action'/>" method="post">
        			<input type="text" name="email" id="email"/>
        			<button type="submit">确认</button>
        		</form>
        		</td>
        		<td class="td3"><a href="javascript:;">修改</a></td>
        	</tr>
        	<tr>
        		<td class="td1">我的电话：</td>
        		<td class="td2">
        		<span>${sessionScope.user.phone}</span>
        		<form class="updateForm phoneForm" action="<c:url value='/user_updatePhone.action'/>" method="post">
        			<input type="text" name="phone" id="phone"/>
        			<button type="submit">确认</button>
        		</form>
        		</td>
        		<td class="td3">
        		<c:choose>
        		<c:when test="${sessionScope.user.phone eq null}">
        			<a href="javascript:;">添加</a>
        		</c:when>
        		<c:otherwise>
        			<a href="javascript:;">修改</a>
        		</c:otherwise>
        		</c:choose>
				</td>
        	</tr>
        	<tr>
        		<td class="td1">我的身份：</td>
        		<td class="td2">
        			<c:choose>
        			<c:when test="${sessionScope.user.userIdentity eq 1 }">老师</c:when>
        			<c:otherwise>学生</c:otherwise>
        			</c:choose>
        		</td>
        		<td class="td3"></td>
        	</tr>
        	<tr>
        		<td class="td1">我的学校：</td>
        		<td class="td2">
        		<span>${sessionScope.user.school}</span>
        		<form class="updateForm schoolForm" action="<c:url value='/user_updateSchool.action'/>" method="post">
        			<input type="text" name="school" id="school"/>
        			<button type="submit">确认</button>
        		</form>
        		</td>
        		<td class="td3"><a href="javascript:;">修改</a></td>
        	</tr>
        </table>
        </div>
    </body>
</html>