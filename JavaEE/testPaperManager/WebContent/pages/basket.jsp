<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!doctype html>
<html>
<head>
	
	
	<title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta charset="UTF-8">
	<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
          <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
           <link rel="stylesheet" type="text/css" href="<c:url value='/css/mainStyle.css'/>">
	<script type="text/javascript">
	$(function(){		
		if($("#type1").text() == "0"){
			$("#p1").hide();
		}
		if($("#type2").text() == "0"){
			$("#p2").hide();
		}
		if($("#type3").text() == "0"){
			$("#p3").hide();
		}
		if($("#type4").text() == "0"){
			$("#p4").hide();
		}
		if($("#type5").text() == "0"){
			$("#p5").hide();
		}
	});
	
	function del(id){
		if(confirm("您还没有组卷完成，真的要删除吗？")){ 
			
			$.ajax({
	        		cache : false,
	    			async : false,
	    			contentType : false,
	    			type : "POST",
	    			dataType : "json",	    	
	    			url : "/testPaperManager/testPaper_delete.action",
	    			success : function(re) {
	    			if(re){
	    				alert("已删除该试题篮，要组卷请重新创建");
	    				window.location.reload();	    			
	    			}
	    			}	        		
	        	}); 
		}
		
	}
	
	</script>
	<style>
	 p{font-family:"微软雅黑","黑体","宋体"; font-size:16px;color:#5f5f5f;line-height:20px; }
		.basket{position:fixed;width:160px;top:30px;left:85%;border:3px groove #2BD5D5;border-radius:8%;background:#F2E0CB;}
  		.myTP{width:100%;background:#2BD5D9;}
  		button{color:#3c78d8;}
  		button:hover{background:#2BD5D9;}
  		a:hover{text-decoration: none;}
  		i{color:orange;}
	</style>
</head>
<body>
	 <div class="basket">
		         	<p class="myTP">我的试题篮</p>      	
		         	<p class="subject">
		         	${tempPaper.gradeSubject.grade.gradeName } ${tempPaper.gradeSubject.subject.subjectName }
		         	</p> 
		         	<%-- <c:forEach items="${typeNumStrs }" var="str">
		         	<p>${str }	</p> --%>	
		         	
		         	<p id="p1">选择题: <i id="type1">${typeNums[1]}</i> 道</p>		        		         	
		         	<p id="p2">填空题: <i id="type2">${typeNums[2]}</i> 道</p>		         	
		         	<p id="p3">简答题: <i id="type3">${typeNums[3]}</i> 道</p>	         	
		         	<p id="p4">作文: <i id="type4">${typeNums[4]}</i> 道</p>		         	
		         	<p id="p5">其他: <i id="type5">${typeNums[5]}</i> 道</p>		         
		         	<p id="pAll">总共: <i id="typeAll">${typeNums[0]}</i> 道</p>
		         	 
		         <a href="<c:url value='/testPaper_generate.action?testPaperId=${tempPaper.testPaperId}' />">   
		         	 <button class="btn genBtn">生成试卷</button>     
		          </a> 
		         	<button class="btn delBtn" onclick="del(${tempPaper.testPaperId})">删除</button>    	
		    </div>
</body>
</html>
