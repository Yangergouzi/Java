$(function() {
	// 默认隐藏所有错误信息
//	$(".error").css("display", "none");
	//没有错误信息，隐藏
	$(".error").each(function(){
		var val = $(this).text();
		if(val == ""){
			$(this).css("display","none");
		}
	});
	// 提交按钮图片切换
	$("#submit").hover(function() {
		$(this).attr("src", "/goods/images/regist2.jpg");
	}, function() {
		$(this).attr("src", "/goods/images/regist1.jpg");
	});

	// 点击提交按钮，依次进行表单校验
	$("form").submit(function(){
		var bool = true;
		if(!validateLoginname()) {
			bool = false;
		}
		if(!validateLoginpass()) {
			bool = false;
		}
		if(!validateReloginpass()) {
			bool = false;
		}
		if(!validateEmail()) {
			bool = false;
		}
		if(!validateVerifyCode()) {
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

// 换一张验证码.1.获取img元素 2.重新设置src 3.添加毫秒参数
function _hyz() {
	$("#vcodeImg").attr("src",
			"/goods/VerifyCodeServlet?a=" + new Date().getTime());
}
// 输入input name，调用相应的校验方法
function invokeValidateFunction(inputName) {
	inputName = inputName.substring(0, 1).toUpperCase()
			+ inputName.substring(1);
	var functionName = "validate" + inputName;
	return eval(functionName + "()");
}
// 登录名校验
function validateLoginname() {
	var bool = true;
	$("#loginnameError").css("display", "none");
	var value = $("#loginname").val();
	var length = value.replace(/\u4e00-\u9fa5/, "aa").length;
	var nospaceVal = value.replace(/\s+/, '');
	if (!nospaceVal) {
		$("#loginnameError").css("display", "");
		$("#loginnameError").text("用户名不能为空！");
		bool = false;
	} else if (length < 3 || length > 20) {
		$("#loginnameError").css("display", "");
		$("#loginnameError").text("长度必须在3-20之间！");
		bool = false;
	} else {// 是否被注册过
		$.ajax({
			cache : false,
			async : false,
			type : "POST",
			dataType : "json",
			data : {
				method : "validateLoginname",
				loginname : value
			},
			url : "/goods/UserServlet",
			success : function(result) {
				if (!result) {
					$("#loginnameError").css("display", "");
					$("#loginnameError").text("用户名已被注册！");
					bool = false;
				}
			}
		});
	}
	return bool;
}
// 登录密码校验
function validateLoginpass() {
	var bool = true;
	$("#loginpassError").css("display", "none");
	var value = $("#loginpass").val();
	var length = value.length;
	var nospaceVal = value.replace(/\s+/, '');
	if (!nospaceVal) {
		$("#loginpassError").css("display", "");
		$("#loginpassError").text("密码不能为空！");
		bool = false;
	} else if (length < 3 || length > 20) {
		$("#loginpassError").css("display", "");
		$("#loginpassError").text("长度必须在3-20之间！");
		bool = false;
	}
	return bool;
}
// 确认密码校验
function validateReloginpass() {
	var bool = true;
	$("#reloginpassError").css("display", "none");
	var value = $("#reloginpass").val();
	var length = value.length;
	var nospaceVal = value.replace(/\s+/, '');
	if (!nospaceVal) {
		$("#reloginpassError").css("display", "");
		$("#reloginpassError").text("确认密码不能为空！");
		bool = false;
	} else if (value != $("#loginpass").val()) {
		$("#reloginpassError").css("display", "");
		$("#reloginpassError").text("密码不一致！");
		bool = false;
	}
	return bool;
}
// 邮箱校验
function validateEmail() {
	var bool = true;
	$("#emailError").css("display", "none");
	var value = $("#email").val();
	var length = value.length;
	var nospaceVal = value.replace(/\s+/, '');
	if (!nospaceVal) {
		$("#emailError").css("display", "");
		$("#emailError").text("邮箱不能为空！");
		bool = false;
	} else if (!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value)) {
		$("#emailError").css("display", "");
		$("#emailError").text("邮箱格式错误！");
		bool = false;
	}else{//邮箱是否被注册过
		$.ajax({
			cache : false,
			async : false,
			type : "POST",
			dataType : "json",
			data : {
				method : "validateEmail",
				email : value
			},
			url : "/goods/UserServlet",
			success : function(result) {
				if (!result) {
					$("#emailError").css("display", "");
					$("#emailError").text("邮箱已被注册！");
					bool = false;
				}
			}
		});
	}
	return bool;
}
// 验证码校验
function validateVerifyCode() {
	var bool = true;
	$("#verifyCodeError").css("display", "none");
	var value = $("#verifyCode").val();
	var length = value.length;
	var nospaceVal = value.replace(/\s+/, '');
	if (!nospaceVal) {
		$("#verifyCodeError").css("display", "");
		$("#verifyCodeError").text("验证码不能为空！");
		bool = false;
	} else if (length != 4) {// 长度不为4就是错误的
		$("#verifyCodeError").css("display", "");
		$("#verifyCodeError").text("验证码错误！");
		bool = false;
	}else{//验证码校验
		$.ajax({
			cache : false,
			async : false,
			type : "POST",
			dataType : "json",
			data : {
				method : "validateVerifyCode",
				verifyCode : value
			},
			url : "/goods/UserServlet",
			success : function(result) {
				if (!result) {
					$("#verifyCodeError").css("display", "");
					$("#verifyCodeError").text("验证码错误！");
					bool = false;
				}
			}
		});
	}
	return bool;
}
