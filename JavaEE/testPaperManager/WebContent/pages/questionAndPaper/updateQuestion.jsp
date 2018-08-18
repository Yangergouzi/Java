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
        <title>修改试题</title>
        <script type="text/javascript">
        $(function(){
        	// 点击提交按钮，依次进行表单校验
        	$("form").submit(function(){
        		var bool = true;
        		if(!validateContent()) {
        			bool = false;
        		}
        		if(!validateType()) {
        			bool = false;
        		}
        		if(!validateTrueAnswer()) {
        			bool = false;
        		}
        		if(!validateSubject()) {
        			bool = false;
        		}
        		return bool;
        	}); 
        });
        //实现本地图片预览
        function handleImg(){
        	//alert("text");
        	var filePath = $("#myFile").val();//获取上传图片本地路径
        	$("#filePath").val(filePath);
	       	 var imgArr=filePath.split('\\'); //分割  
	       	 var imgName=imgArr[imgArr.length-1]; //获取图片名  
	       	 var pointIndex = imgName.lastIndexOf('.'); //获取 . 出现的位置  
	       	 var suffix = imgName.substring(pointIndex, imgName.length).toUpperCase();  //切割 . 获取文件后缀  
	       	 var file = $('#myFile').get(0).files[0]; //获取上传的文件  
	       	 var fileSize = file.size;           //获取上传的文件大小  
	       	var maxSize = 1048576;              //最大1MB  	
	       	//
	        if(suffix !='.PNG' && suffix !='.GIF' && suffix !='.JPG' && suffix !='.JPEG' && suffix !='.BMP'){ 
	        	$("#filePath").val("");
	           alert('文件类型错误,请上传图片类型');  
	            return false;  
	        }else if(parseInt(fileSize) >= parseInt(maxSize)){  
	        	$("#filePath").val("");
	           alert('上传的文件不能超过1MB');  
	            return false;  
	        }else{	   
	        	//fileReader实现本地图片预览
	        	//创建一个FileReader对象
				var reader = new FileReader();
				//读取file文件；
				reader.readAsDataURL(file);
				//当文件读取成功后，将结果保存到url变量里；
				reader.onload = function(evt) {
				    var url = evt.target.result;
				    $("#questionImg").attr("src",url);
				};
	        }
        }
        //上传图片ajax
        function upload(){
         	
 	         	var formData = new FormData();
 	        	formData.append('myFile', $("#myFile")[0].files[0]);
 	        	$.ajax({
 	        		cache : false,
 	    			async : false,
 	    			// 告诉jQuery不要去处理发送的数据
 	    			processData : false, 
 	    			// 告诉jQuery不要去设置Content-Type请求头
 	    			contentType : false,
 	    			type : "POST",
 	    			dataType : "json",
 	    			data :  formData,
 	    			url : "/testPaperManager/upload_ajaxUploadQuestionImg.action",
 	    			success : function(data) {
 	    			if(data){
 	    				alert("图片上传成功");
 	    				var path = data.url.value;
 	    				var src = "/testPaperManager" + path;
 	    				$("#imagePath").attr("value",path);
 	    			//	$("#questionImg").attr("src",src);
 	    			}
 	    			}
 	        		
 	        	}); 
        }
        //校验
        function validateContent() {
			var bool = true;
			$("#contentError").css("display", "none");
			var value = $("#content").val();
			var length = value.replace(/\u4e00-\u9fa5/, "aa").length;
			var nospaceVal = value.replace(/\s+/, '');
			if (!nospaceVal) {
				$("#contentError").css("display", "");
				$("#contentError").text("题目内容不能为空！");
				bool = false;
			} 
			return bool;
		}
        function validateType() {
			var bool = true;
			$("#typeError").css("display", "none");
			var value = $("#type").val();
			var length = value.replace(/\u4e00-\u9fa5/, "aa").length;
			var nospaceVal = value.replace(/\s+/, '');
			if (!nospaceVal) {
				$("#typeError").css("display", "");
				$("#typeError").text("题型不能为空！");
				bool = false;
			} 
			return bool;
		}
        function validateSubject() {
			var bool = true;
			$("#subjectError").css("display", "none");
			var value = $("#gradeSubjectId").val();
			var length = value.replace(/\u4e00-\u9fa5/, "aa").length;
			var nospaceVal = value.replace(/\s+/, '');
			if (!nospaceVal) {
				$("#subjectError").css("display", "");
				$("#subjectError").text("科目不能为空！");
				bool = false;
			} 
			return bool;
		}
        function validateTrueAnswer() {
			var bool = true;
			$("#trueAnswerError").css("display", "none");
			var value = $("#trueAnswer").val();
			var length = value.replace(/\u4e00-\u9fa5/, "aa").length;
			var nospaceVal = value.replace(/\s+/, '');
			if (!nospaceVal) {
				$("#trueAnswerError").css("display", "");
				$("#trueAnswerError").text("答案不能为空！");
				bool = false;
			} 
			return bool;
		}
        </script>
        <style>
        body{overflow:scroll;}
        .box{width:800px;height:1500px;margin-left:15%;margin-top:20px;}
        tr{}
        td{border:1px solid  #9092A6;}
       .tdName{width:150px;font-size:16px;font-weight:bold;}
       .tdValue{}
       img{width:300px;}
       .tdLabel{width:150px;font-size:10px;color:#E51C23}
        textarea{width:400px;}
        </style>
    </head>
    <body>
        <div class="box">
     		<form action="<c:url value="/question_update.action" />" method="post" enctype="multipart/form-data">
     		<input type="hidden" name="questionId" value="${question.questionId }"/>
     			<input type="hidden" name="imagePath" id="imagePath" value=""/>
     			<table>
     				<tr>
     					<td class="tdName">题目内容</td>
     					<td class="tdValue"><textarea rows="15" id="content" name="content">${question.content }</textarea></td>
     					<td class="tdLabel"> <label class="error" id="contentError">(必填)</label></td>
     				</tr>
     				<tr>
     					<td class="tdName">题目图片</td>
     					<td class="tdValue">
     						<input type="text" id="filePath" value="">
     						<input type="button" id="upBtn" value="修改图片" onclick="myFile.click()"/>(不超过1MB)
     						<input type="file" id="myFile" name="myFile" onchange="handleImg(this);" style="display:none;"/>
     					</td>
     					<td class="tdLabel"> <label class="error" id="imagePathError"></label></td>
     				</tr>
     				<tr>
     					<td></td>
     					<td class="tdValue"><img id="questionImg" src="<c:url value='${question.imagePath }'/>" /></td>
     					<td></td>
     				</tr>
     				<tr>
     					<td class="tdName">题型</td>
     					<td class="tdValue">
     					<select name="type" id="type">
     					<option></option>
     					<option value='1' <c:if test="${question.type eq 1}">selected="selected"</c:if> >---- 选择题 ----</option>
     					<option value='2' <c:if test="${question.type eq 2}">selected="selected"</c:if>>---- 填空题 ----</option>
     					<option value='3' <c:if test="${question.type eq 3}">selected="selected"</c:if>>---- 简答题 ----</option>
     					<option value='4' <c:if test="${question.type eq 4}">selected="selected"</c:if>>---- 作    文  ----</option>
     					<option value='0' <c:if test="${question.type eq 0}">selected="selected"</c:if>>---- 其    他  ----</option>
     					</select>
     					</td>
     					<td class="tdLabel"> <label class="error" id="typeError">(必填)</label></td>
     				</tr>
     				<tr>
     					<td class="tdName">所属科目</td>
     					<td class="tdValue">
     						<select id="gradeSubjectId"  name="gradeSubjectId">
     						<option></option>
	     						<c:forEach items="${sessionScope.user.gradeSubjectSet }" var="gradeSubject">
	     							<option value="${gradeSubject.gradeSubjectId }" <c:if test="${question.gradeSubject.gradeSubjectId eq gradeSubject.gradeSubjectId }">selected="selected"</c:if> >
	     								${gradeSubject.grade.gradeName} ${gradeSubject.subject.subjectName}
	     							</option>
	     						</c:forEach>
	     						</select>
	     					
     					</td>
     					<td class="tdLabel"> <label class="error" id="subjectError">(必填)</label></td>
     				</tr>
     				
     				<tr>
     					<td class="tdName">正确答案</td>
     					<td class="tdValue"><textarea rows="5" id="trueAnswer" name="trueAnswer">${question.trueAnswer }</textarea></td>
     					<td class="tdLabel"> <label class="error" id="trueAnswerError">(必填)</label></td>
     				</tr>
     				<tr>
     					<td class="tdName">题目解析</td>
     					<td class="tdValue"><textarea rows="5" id="analysis" name="analysis">${question.analysis }</textarea></td>
     					<td class="tdLabel"> <label class="error" id="analysisError"></label></td>
     				</tr>
     				<tr>
     					<td class="tdName">知识要点</td>
     					<td class="tdValue"><textarea rows="5" id="mainPoint" name="mainPoint">${question.mainPoint }</textarea></td>
     					<td></td>
     				</tr>
     				<tr>
     					<td class="tdName">设置难度</td>
     					<td class="tdValue">
     						超简单 <input type="radio" id="difficulty" name="difficulty" value="1" <c:if test="${question.difficulty eq 1}">checked="checked"</c:if> />
     						&nbsp;&nbsp;简单 <input type="radio" id="difficulty" name="difficulty" value="2" <c:if test="${question.difficulty eq 2}">checked="checked"</c:if> />
     						&nbsp;&nbsp;一般 <input type="radio" id="difficulty" name="difficulty" value="3" <c:if test="${question.difficulty eq 3}">checked="checked"</c:if> />
     						&nbsp;&nbsp;困难 <input type="radio" id="difficulty" name="difficulty" value="4" <c:if test="${question.difficulty eq 4}">checked="checked"</c:if> />
     						&nbsp;&nbsp;超困难<input type="radio" id="difficulty" name="difficulty" value="5" <c:if test="${question.difficulty eq 5}">checked="checked"</c:if> />
     					</td>
     					<td class="tdLabel"> <label class="error" id="difficultyError"></label></td>
     				</tr>
     					
     				<tr>
     					<td class="tdName">是否公开</td>
     					<td class="tdValue">
     						否 <input type="radio" id="isPublic" name="isPublic" value="0" <c:if test="${question.isPublic eq 0}">checked="checked"</c:if> />
     						是 <input type="radio" id="isPublic" name="isPublic" value="1" <c:if test="${question.isPublic eq 1}">checked="checked"</c:if> />
     					</td>
     					<td></td>
     				</tr>     			
     			</table>
     		<input type="button" value="下一题"/>
     		<input type="submit" onclick="javascript:upload();" value="提交" />
     		</form>
            
        </div>
    </body>
</html>