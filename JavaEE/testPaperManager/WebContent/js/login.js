$(function() {
	
	//没有错误信息，隐藏
	$(".error").each(function(){
		var val = $(this).text();
		if(val == ""){
			$(this).css("display","none");
		}
	});

	// 点击提交按钮，依次进行表单校验
	$("form").submit(function(){
		var bool = true;
		if(!validateUsername()) {
			bool = false;
		}
		if(!validatePassword()) {
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
			"/testPaperManager/vCodeImg.action?a=" + new Date().getTime());
}
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
	$("#usernameError").css("display", "none");
	var value = $("#username").val();
	var length = value.replace(/\u4e00-\u9fa5/, "aa").length;
	var nospaceVal = value.replace(/\s+/, '');
	if (!nospaceVal) {
		$("#usernameError").css("display", "");
		$("#usernameError").text("昵称不能为空！");
		bool = false;
	} 
	return bool;
}
// 密码校验
function validatePassword() {
	var bool = true;
	$("#passwordError").css("display", "none");
	var value = $("#password").val();
	var length = value.length;
	var nospaceVal = value.replace(/\s+/, '');
	if (!nospaceVal) {
		$("#passwordError").css("display", "");
		$("#passwordError").text("密码不能为空");
		bool = false;
	} 
	return bool;
}

// 校验验证码
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
	} else if (length != 4) {
		$("#verifyCodeError").css("display", "");
		$("#verifyCodeError").text("验证码错误！");
		bool = false;
	}else{//
		$.ajax({
			cache : false,
			async : false,
			type : "POST",
			dataType : "json",
			data : {
				verifyCode : value
			},
			url : "/testPaperManager/user_ajaxValidateVcode.action",
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
