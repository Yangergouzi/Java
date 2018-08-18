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
       
        <title>试卷查看</title>
        <script type="text/javascript">
        $(function(){
        	var docPath = "${testPaper.filePath}";
        	var index = docPath.lastIndexOf(".");
        	var htmlPath = docPath.substring(0,index) + ".html";
        	var src = "/testPaperManager" + htmlPath;
        	$("#htmlTestPaper").attr("src",src);
        	
        });
        
     
        </script>
        <style>
        body{width:100%;height:100%;padding:0}
        .box{margin-top:10px;width:100%;height:100%}
        #paper{width:76%;;background:white;border:1px solid grey;margin-left:20px;height:560px;}
        #property{position:absolute;left:85%;top:100px}
      
        </style>
    </head>
    <body>
        <div class="box">
     		<div id="paper">
     		<iframe frameborder="0" src="" name="htmlTestPaper" id="htmlTestPaper"></iframe>
     		</div>
            <div id="property">
            <p id="name">${testPaper.paperName }</p>
            <p id="subject">${testPaper.gradeSubject.grade.gradeName }${question.gradeSubject.subject.subjectName }</p>           
            <p id="difficulty">难度：
          	    <c:if test="${testPaper.difficulty eq 1}">超简单</c:if> 
  				<c:if test="${testPaper.difficulty eq 2}">简单</c:if> 
  				<c:if test="${testPaper.difficulty eq 3}">一般</c:if> 
  				<c:if test="${testPaper.difficulty eq 4}">困难</c:if> 
  				<c:if test="${testPaper.difficulty eq 5}">超困难</c:if> 
  			</p>
  			<p id="score">总分：${testPaper.totalScore}</p>
  			<p id="time">限时：${testPaper.timeLimit} 分钟</p>
            </div>
          
        </div>
    </body>
</html>