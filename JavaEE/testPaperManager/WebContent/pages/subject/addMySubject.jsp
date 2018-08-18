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
            	var subjectId;
            	var gradeId;
            	//先隐藏科目
            	$(".subjects").hide();
            	//当年级被点击时
               $(".gradeName").click(
            		function(){           			
            			$(".subjects").hide();
            			gradeId = $(this).attr("id");
            			//为隐藏input加值
            			$("form .hidden1").val(gradeId);
            			//显示对应科目栏
            			 $(this).siblings(".subjects").show();
            			//先去掉其他gradeName背景颜色
            			 $(".gradeName").css({"background" : "none"});  
            			//添加背景颜色
            			$(this).css({"background" : "#CCCC00"});
            			//先清除.subjects内容
            			$(".subjects").html("");
            			//点击年级，出现对应科目，ajax实现
            			$.ajax({
            				cache : false,
            				async : false,
            				type : "POST",
            				dataType : "json",
            				data : {
            					gradeId : gradeId
            				},
            				url : "/testPaperManager/subject_findSubjectsByGradeId.action",
            				success : function(data) {
            					if (data) {
           
            						$.each(data.subjects,function(index,item){
            							$(".subjects").append( "<a id = '" + item.subjectId + "'>" + item.subjectName + "</a>"	);
            							$(".subjects").trigger('create');
            							//	alert(item.subjectName);
            						});
            					}
            				}
            			});
            		}	                
               );
             //当科目被点击时
  			 $(".subjects").on('click','a',function(){
  				// alert("test");
  				 subjectId = $(this).attr("id");
  				// alert(subjectId);
  				//为隐藏input加值
  				 $("form .hidden2").val(subjectId);
  				//先去掉其他gradeName背景颜色
    			 $(".subjects a").css({"background" : "none"});  
  				//添加背景颜色
     			$(this).css({"background" : "#CCCC00"});
  				
  			 });                          
            });
         
        </script>
	<style>
		form{width:500px;margin-left:20%;margin-top:30px;}
		.gradeParents(font-size:20px;)
		.gradeName{text-decoration: none;}
		.gradeName:hover{color:red}
		.subjects{ width:600px; height: 30px; border:2px solid grey;margin-top:10px;border-radius:5px;}
		.subjects a{ margin-left:20px;}
		.subjects a:hover{color:red}
	</style>
</head>
<body>
	 <div class="box">
     	<form action="<c:url value='/subject_addMySubject.action'/>" method="post">
     		<input class="hidden1" type="hidden" name="gradeId" value=""/>
     		<input class="hidden2" type="hidden" name="subjectId" value=""/>
   		<c:forEach items="${gradeParents }" var="gradeParent">
   		<div>
   				<p class="gradeParents">${gradeParent.gradeName }</p>
   				<c:forEach items="${gradeParent.children }" var="child">
   				<a class="gradeName" id="${child.gradeId }">${child.gradeName }</a>
   				</c:forEach>  	
   				<div class="subjects"> 
	   				
   				</div>	
   		</div>
   		</c:forEach>
   		<br>
   	
   		<input type="submit" value="添加" id="submitBtn" >
 
     	</form>
    </div>

</body>
</html>
