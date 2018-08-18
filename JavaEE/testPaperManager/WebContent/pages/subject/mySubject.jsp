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
	<base href="<%=basePath%>">
	
	<title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta charset="UTF-8">
	<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>

	  <script>
	 $(function(){
		$(".del").on('click',function(){
			if(confirm("确认删除吗？")){
				
			  }else{
				return false;
			  }
			
		});
		
		}); 
	
		 
	
        </script>
	<style>
		.box{width:200px;margin-left:30%;margin-top:20px;}
	</style>
</head>
<body>
	 <div class="box">
     	<h2>我的科目</h2>
     	<div class="subjects">
     		<c:forEach items="${mySubjects }" var="gradeSubject">
     		<a>${gradeSubject.grade.gradeName } ${gradeSubject.subject.subjectName}</a>
     		&nbsp&nbsp&nbsp&nbsp 
     		<a class="del" href="<c:url value='/subject_deleteMySubject.action?gradeSubjectId=${gradeSubject.gradeSubjectId}'/>">删除</a>
     		<br><br><br>
     		</c:forEach>
     	</div>
    </div>

</body>
</html>
