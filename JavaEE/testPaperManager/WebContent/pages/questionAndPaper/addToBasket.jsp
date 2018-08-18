<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
       <%--  <script src="<c:url value='/js/updatePwd.js'/>" charset="utf-8"></script> 
          --%>
        <title>组卷试题列表</title>
        <script type="text/javascript">
        $(function(){
   			$(".addIcon").click(function(){
   				$(this).attr("class","fa fa-check finishIcon");
   			});
			
        });
        //点击.addIcon执行的函数，作用是通过ajax添加题目到试题篮
        function add(id,type){
        	var num = 0;
        	var t;
     	   $.ajax({
     		 cache : false,
    			async : false,
    			type : "POST",
    			dataType : "json", 	
    			data: {questionId : id},
    			url : "/testPaperManager/question_addToTempPaper.action",
    			success : function(re) {
    			if(re){
    			
	    			if(type == 1){
	    				t =$("#type1", window.parent.document).text();	
	    				num = parseInt(t);
	    				num++;	    			
	    				$("#p1", window.parent.document).show();
	    				$("#type1", window.parent.document).text(num);
	    			}
	    			else if(type == 2){
	    				t =$("#type2", window.parent.document).text();	
	    				num = parseInt(t);
	    				num++;	    
	    				$("#p2", window.parent.document).show();
	    				$("#type2", window.parent.document).text(num);
	    			}
	    			else if(type == 3){
	    				t =$("#type3", window.parent.document).text();	
	    				num = parseInt(t);
	    				num++;	   
	    				$("#p3", window.parent.document).show();
	    				$("#type3", window.parent.document).text(num);
	    			}
	    			else if(type == 4){
	    				t =$("#type4", window.parent.document).text();	
	    				num = parseInt(t);
	    				num++;	 
	    				$("#p4", window.parent.document).show();
	    				$("#type4", window.parent.document).text(num);
	    			}
	    			else if(type == 5){
	    				t =$("#type5", window.parent.document).text();	
	    				num = parseInt(t);
	    				num++;	
	    				$("#p5", window.parent.document).show();
	    				$("#type5", window.parent.document).text(num);
	    			}
	    			t =$("#typeAll", window.parent.document).text();	
    				num = parseInt(t);
    				num++;	  
    				$("#typeAll", window.parent.document).text(num);
    			}
    			}	        		
        	}); 
     	   
        }
    
      
        </script>
        <style>
        body{overflow:auto;}
      	.box{width:90%;text-align:center;}
     
      	.smallbox{margin-left:15%;margin-top:10px;width:76%;height:200px;background:white;border:2px solid #3CC4C4;}
      	.smallbox p{width:100%;font-family:"微软雅黑","黑体","宋体"; font-size:16px;color:#5f5f5f;height:40px;background:#cccccc;text-align:left;}
        .smallbox textarea{width:100%;height:140px;border:none;}
        .smallbox span{margin-right:20px;}
        .addIcon,.finishIcon{position:absolute;color:#3CC4C4;font-size:40px;left:73%;}
        .addIcon:hover{color:#3c78d8;}
        </style>
    </head>
    <body>
        <div class="box">
     	
     	
     	<div class="list">    			
     			<c:forEach items="${pagebean.beanList }" var="question">
     			<div class="smallbox">
     			<p>
     				<span>题型：
     				<c:if test="${question.type eq 1}">选择题</c:if>
     				<c:if test="${question.type eq 2}">填空题</c:if>
     				<c:if test="${question.type eq 3}">简答题</c:if>
     				<c:if test="${question.type eq 4}">作文</c:if>
     				<c:if test="${question.type eq 0}">其他</c:if>   
     				</span>  			
     				
     				<span>
     				科目：${question.gradeSubject.grade.gradeName } ${question.gradeSubject.subject.subjectName }
     				</span>
     				<span>
     				要点：${question.mainPoint}
     				</span>
     				<span>
     				难度：
     				<c:if test="${question.difficulty eq 1}">超简单</c:if> 
     				<c:if test="${question.difficulty eq 2}">简单</c:if> 
     				<c:if test="${question.difficulty eq 3}">一般</c:if> 
     				<c:if test="${question.difficulty eq 4}">困难</c:if> 
     				<c:if test="${question.difficulty eq 5}">超困难</c:if> 
     				</span>
     			<span>
     		
     				<i class="fa fa-plus-square addIcon" onclick="add(${question.questionId},${question.type })"></i>
     		
     			</span>	
     			
     			<!-- <i class="fa fa-check finishIcon"></i> -->
     				</p>   				
     				<textarea>${question.content}</textarea>
     			</div>
     			</c:forEach>
     		
     		    	
     	</div>
     	
     	<div style="float:left; width: 100%; text-align: center;margin-top:30px;">
		<hr/>
		<br/>
		<%@include file="/pages/pager.jsp" %>
		</div>
        </div>
    </body>
</html>