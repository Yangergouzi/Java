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
       <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
        <title>手动组卷</title>
        <script type="text/javascript">
       $(function(){
    	   //创建界面点击样式
    	   $(".create form").hide();
    	   $(".create i").click(function(){
           	$(".create").css("background","#F2E0CB");
           	$(".create form").show();
           	$(this).hide();
           });
    	   //设置分类栏元素初始背景
    	   $(".search .all").css("background","#3c78d8");
    	   $(".search .all").css("color","#fff");
    	   
       });
       //
       function refreshList(){
    	   var type = $("#typeInput").val();	 
   		  var diff = $("#diffInput").val();
   		  var range = $("#rangeInput").val();
   		  var src = "<c:url value='/question_addToBasket.action?type=" + type + "&diff=" + diff + "&range=" + range + "'/>";
   		  $(".iframe").attr("src",src);
   		
       }
       //点击选择题型
       function clickType(value,obj){
     	  $("#typeInput").val(value);
     	 refreshList();//刷新框架的列表
     	//设置点击背景
     	$(".typeDiv a").css("background","");
     	 $(obj).css("background","#3c78d8");
     	 $(obj).css("color","#fff");
       }
       
       //点击选择难度
        function clickDiff(value,obj){
     	  $("#diffInput").val(value);
     	 refreshList();
     	//设置点击背景
      	$(".diffDiv a").css("background","");
      	 $(obj).css("background","#3c78d8");
      	 $(obj).css("color","#fff");
       }
       
       //点击选择范围
        function clickRange(value,obj){
        	 $("#rangeInput").val(value);
        	 refreshList();
        	//设置点击背景
          	$(".rangeDiv a").css("background","");
          	 $(obj).css("background","#3c78d8");
          	 $(obj).css("color","#fff");
       }

       
        </script>
        <style>
        body{overflow:scroll;} 
        .box{width:100%;height:2000px;}
        .create{width:200px;height:200px;margin-top:100px;margin-left:30%;border:5px inset #3c78d8;border-radius:10%;}
        p{font-family:  font-family:"微软雅黑","黑体","宋体"; font-size:16px;color:#5f5f5f;line-height:30px; }
     	.create i{color:#F2E0CB;}
     	.create i:hover{color:#3c78d8;}
  		.list{width:80%;height:2000px;}
  		.search{width:54.5%;height:130px;margin-top:20px;margin-left:10.7%;padding:10px 15px 10px 15px;font-weight:bold;text-align:left;border:1px solid #F2E0CB;}
  		.search div{margin:15px 25px;}
  		.search a{text-decoration: none;color:#5f5f5f;margin-right:15px;border-radius:15%;padding:5px 5px 3px 5px;font-weight:normal;}
  		.search a:hover{text-decoration: none;background:#3c78d8;color:#fff;}
        </style>
    </head>
    <body>
        <div class="box">    
        <c:choose>
        <c:when test="${empty tempPaper }">
	        <div class="create">
	        	<p>新建一个试题篮</p>
	        	<br>
	         <i class="fa fa-plus fa-5x"></i>
	          <form action="<c:url value='/testPaper_create.action'/>" method="post">					
						选择科目：<select id="gradeSubjectId"  name="gradeSubjectId">
	   							<option></option>
	    						<c:forEach items="${sessionScope.user.gradeSubjectSet }" var="gradeSubject">
	    							<option value="${gradeSubject.gradeSubjectId }">${gradeSubject.grade.gradeName} ${gradeSubject.subject.subjectName}</option>
	    						</c:forEach>
	    						</select>	
	    						<br><br><br><br>	
	    			<button class="btn" type="submit">创建</button>			     			
	         </form>
	         </div>
	         </c:when>
	         <c:otherwise>
			 <div class="search">
			         <input type="hidden" id="typeInput" value="0" />
					<input type="hidden" id="diffInput" value="0" />
					<input type="hidden" id="rangeInput" value="0" />
					<div class="typeDiv">题型：
						<a onclick="clickType(0,this)" class="all">全部</a>
						<a onclick="clickType(1,this)">选择题</a>
						<a onclick="clickType(2,this)">填空题</a>
						<a onclick="clickType(3,this)">简答题</a>
						<a onclick="clickType(4,this)">作文</a>
						<a onclick="clickType(5,this)">其他</a>
					</div>
					<div class="diffDiv">难度：
						<a onclick="clickDiff(0,this)" class="all">全部</a>
						<a onclick="clickDiff(1,this)">超简单</a>
						<a onclick="clickDiff(2,this)">简单</a>
						<a onclick="clickDiff(3,this)">一般</a>
						<a onclick="clickDiff(4,this)">困难</a>
						<a onclick="clickDiff(5,this)">超困难</a>
					</div>
					<div class="rangeDiv">范围：
						<a onclick="clickRange(0,this)" class="all">公共题库</a>
						<a onclick="clickRange(1,this)">我的题库</a>
					</div>
		     	</div>
			         
	         
	        
	        	<div class="basket">
		       <%@include file="/pages/basket.jsp" %> 
		       </div>
		         <div class="list">	         
		         <iframe frameborder="0" class="iframe" src="<c:url value='/question_addToBasket.action?type=0&diff=0&range=0'/>" ></iframe>		         
		         </div>
	         
	         </c:otherwise>
   </c:choose>
        </div>
    </body>
</html>