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
        <title>上传试卷</title>
        <script type="text/javascript">
        $(function(){
        	// 点击提交按钮，依次进行表单校验
        	$("form").submit(function(){
        		var bool = true;
        		if(!validatePaperName()) {
        			bool = false;
        		}
        		if(!validateUpFile()) {
        			bool = false;
        		}
        		if(!validateSubject()) {
        			bool = false;
        		}
        		return bool;
        	}); 
        });
        function set(){
        	var filePath = $("#myFile").val();//获取上传文件本地路径
        	$("#upFile").val(filePath);
        }
        function upload(){
        	//alert("text");
        	var filePath = $("#myFile").val();//获取上传文件本地路径
        //	$("#upFile").val(filePath);
	       	 var fileArr=filePath.split('\\'); //分割  
	       	 var fileName=fileArr[fileArr.length-1]; //获取文件名  
	       	 var pointIndex = fileName.lastIndexOf('.'); //获取 . 出现的位置  
	       	 var suffix = fileName.substring(pointIndex, fileName.length).toUpperCase();  //切割 . 获取文件后缀  
	       	 var file = $('#myFile').get(0).files[0]; //获取上传的文件  
	       	 var fileSize = file.size;           //获取上传的文件大小  
	       	var maxSize = 1048576 * 5;              //最大5MB  	
	       	//
	        if(suffix !='.DOC'){ 
	        	$("#upFile").val("");
	           alert('文件类型错误,请上传图片类型');  
	            return false;  
	        }else if(parseInt(fileSize) >= parseInt(maxSize)){  
	        	$("#upFile").val("");
	           alert('上传的文件不能超过5MB');  
	            return false;  
	        }else{	        	
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
	    			url : "/testPaperManager/upload_ajaxUploadTestPaper.action",
	    			success : function(data) {
		    			 if(data){
		    				 var path = data.url.value;
		 	    			$("#filePath").attr("value",path);
		    			//	alert(path);
		    				
		    			} 
	    			}
	        		
	        	});
	        }
        }
        //校验
        function validatePaperName() {
			var bool = true;
			$("#paperNameError").css("display", "none");
			var value = $("#paperName").val();
			var length = value.replace(/\u4e00-\u9fa5/, "aa").length;
			var nospaceVal = value.replace(/\s+/, '');
			if (!nospaceVal) {
				$("#paperNameError").css("display", "");
				$("#paperNameError").text("试卷名称不能为空！");
				bool = false;
			} 
			return bool;
		}
        function validateUpFile() {
			var bool = true;
			$("#upFileError").css("display", "none");
			var value = $("#upFile").val();
			var length = value.replace(/\u4e00-\u9fa5/, "aa").length;
			var nospaceVal = value.replace(/\s+/, '');
			if (!nospaceVal) {
				$("#upFileError").css("display", "");
				$("#upFileError").text("请上传试卷！");
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
      
        </script>
        <style>
        body{overflow:scroll;}
        .box{width:800px;height:1500px;margin-left:15%;margin-top:20px;}
        td{border:1px solid  #9092A6;}
       .tdName{width:150px;font-size:16px;font-weight:bold;}
       .tdValue{}
       img{height:200px;}
       .tdLabel{width:150px;font-size:10px;color:#E51C23}
        </style>
    </head>
    <body>
        <div class="box">
     		<form action="<c:url value="/testPaper_add.action" />" method="post" enctype="multipart/form-data">
     			<input type="hidden" name="filePath" id="filePath" value=""/>
     			<table>
     				<tr>
     					<td class="tdName">试卷名称</td>
     					<td class="tdValue"><input type="text" name="paperName" id="paperName"/></td>
     					<td class="tdLabel"> <label class="error" id="paperNameError">(必填)</label></td>
     				</tr>
     				<tr>
     					<td class="tdName">试卷</td>
     					<td class="tdValue">
     						<input type="text" id="upFile" value="">
     						<input type="button" id="upBtn" value="上传文件" onclick="myFile.click()"/>(仅限doc文件)
     						<input type="file" id="myFile" name="myFile" onchange="set(this);" style="display:none;"/>
     						
     					</td>
     					<td class="tdLabel"> <label class="error" id="upFileError">（必填）</label></td>
     				</tr>
     				<tr>
     					<td></td>
     					<td class="tdValue"><img id="fileImg" src="" /></td>
     					<td></td>
     				</tr>
     				<tr>
     					<td class="tdName">总分</td>
     					<td class="tdValue">
     					<input type="text" name="totalScore" id="totalScore" onkeyup="value=value.replace(/[^\d]/g,'')"/>
     					</td>
     					<td class="tdLabel"> <label class="error" id="totalScoreError"></label></td>
     				</tr>
     				<tr>
     					<td class="tdName">所属科目</td>
     					<td class="tdValue">
     						<select id="gradeSubjectId"  name="gradeSubjectId">
     						<option></option>
	     						<c:forEach items="${sessionScope.user.gradeSubjectSet }" var="gradeSubject">
	     							<option value="${gradeSubject.gradeSubjectId }">${gradeSubject.grade.gradeName} ${gradeSubject.subject.subjectName}</option>
	     						</c:forEach>
	     						</select>
	     					
     					</td>
     					<td class="tdLabel"> <label class="error" id="subjectError">(必填)</label></td>
     				</tr>
     				
     				<tr>
     					<td class="tdName">考试时限</td>
     					<td class="tdValue"><input type="text" name="timeLimit" id="timeLimit" onkeyup="value=value.replace(/[^\d]/g,'')"/></td>
     					<td class="tdLabel"> <label class="error" id="trueAnswerError">（分钟）</label></td>
     				</tr>
     				
     				<tr>
     					<td class="tdName">设置难度</td>
     					<td class="tdValue">
     						超简单 <input type="radio" id="difficulty" name="difficulty" value="1"/>
     						&nbsp;&nbsp;简单 <input type="radio" id="difficulty" name="difficulty" value="2"/>
     						&nbsp;&nbsp;一般 <input type="radio" id="difficulty" name="difficulty" value="3" checked="checked"/>
     						&nbsp;&nbsp;困难 <input type="radio" id="difficulty" name="difficulty" value="4"/>
     						&nbsp;&nbsp;超困难<input type="radio" id="difficulty" name="difficulty" value="5"/>
     					</td>
     					<td class="tdLabel"> <label class="error" id="difficultyError"></label></td>
     				</tr>
     					
     				<tr>
     					<td class="tdName">是否公开</td>
     					<td class="tdValue">
     						否 <input type="radio" id="isPublic" name="isPublic" value="0"/>
     						是 <input type="radio" id="isPublic" name="isPublic" value="1" checked="checked"/>
     					</td>
     					<td></td>
     				</tr>     			
     			</table>
     		<input type="submit" value="提交" onclick="javascript:upload();"/>
     		</form>
            
        </div>
    </body>
</html>