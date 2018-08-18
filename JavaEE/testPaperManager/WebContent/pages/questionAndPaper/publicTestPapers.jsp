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
     
        <title>公共试卷库</title>
        <script type="text/javascript">
        $(function(){
        	//隐藏高级搜索表单
        	$(".form2").css("display","none");
        	
        	
        });
        //点击出现高级搜索表单，隐藏模糊搜索表单
        function _toGJ(){
        	$(".form1").css("display","none");
        	$(".form2").css("display","");
        }
        //收藏ajax
        function collect(id,obj){
        	var user = "${sessionScope.user}";      	
        	if(user == ""){
        		 $(location).attr("href", "<c:url value='/pages/user/login.jsp'/>");
        	}else{   
	        	$.ajax({
		        		cache : false,
		    			async : false,
		    			type : "POST",
		    			dataType : "json",
		    			data :  {testPaperId : id} ,
		    			url : "/testPaperManager/testPaper_collect.action",
		    			success : function(result) {
		    			if(result){
		    				$(obj).text("已收藏");
		    				$(obj).css("color","#000");
		    				$(obj).attr("onclick","");
		    				alert("题目已收藏，可在我的试题库查看");  
		    			}
		    			}
		        		
		        	}); 
        	}
        	
        }
      
        </script>
        <style>
      	.box{width:90%;text-align:center;}
      	.tb1{margin-top:20px;}
      	.tb2{width:400px;height:200px;margin-top:20px;border:1px solid grey;}
      	.tb2 .tdTitle{width:100px;}
      	.search{width:50%;margin:auto;}
      	.sortRight{margin-left:500px;}
      	.list{margin-top:20px;line-height:30px;}
      	.tbList{margin-left:10px;width:1000px; table-layout: fixed;}
      	.tbList th{width:120px;text-align:center;}
      #tdTime{width:200px;}
       #tdName{width:200px;white-space:nowrap;overflow:hidden;text-overflow: ellipsis;}
      	td{}
      	.tbList tr:nth-child(odd){background: rgba(33, 33, 33, 0.1);}
      	.tbList tr:nth-child(even){background: rgba(255,255,255,0.5);}
      	
        </style>
    </head>
    <body>
        <div class="box">
     	<div class="search">
     		<form class="form1" action="<c:url value='/testPaper_publicFindByName.action'/>" method="post">
	     		<table class="tb1">
				<tr>
					<td>试卷名称：</td>
					<td><input type="text" name="paperName"></td>
					<td> <input type="submit" class="submitBtn" value="查询"></td>
					<td><a href="javascript:_toGJ();">&nbsp;&nbsp;高级查询</a></td>
				</tr>			
				</table>
			</form>
			
			<form class="form2" action="<c:url value='/testPaper_publicAdvancedSearch.action'/>" method="post">
				<table class="tb2">
					<tr>
						<td class="tdTitle">试卷名称：</td>
						<td class="tdInput"><input type="text" id= "paperName" name="paperName"></td>
					</tr>
					
					<tr>
					<c:if test="${not empty sessionScope.user}">
						<c:if test="${not empty sessionScope.user.gradeSubjectSet }">
						<td class="tdTitle">所属科目：</td>
						<td class="tdInput">
							<select id="gradeSubjectId"  name="gradeSubjectId">
     							<option></option>
	     						<c:forEach items="${sessionScope.user.gradeSubjectSet }" var="gradeSubject">
	     							<option value="${gradeSubject.gradeSubjectId }">${gradeSubject.grade.gradeName} ${gradeSubject.subject.subjectName}</option>
	     						</c:forEach>
	     						</select>
	     				</td>
	     				</c:if>
	     				</c:if>
					</tr>
					<tr>
						<td class="tdTitle">难度等级：</td>
						<td class="tdInput">	
						        超简单 <input type="radio" id="difficulty" name="difficulty" value="1"/>
     						&nbsp;&nbsp;简单 <input type="radio" id="difficulty" name="difficulty" value="2"/>
     						&nbsp;&nbsp;一般 <input type="radio" id="difficulty" name="difficulty" value="3"/>
     						&nbsp;&nbsp;困难 <input type="radio" id="difficulty" name="difficulty" value="4"/>
     						&nbsp;&nbsp;超困难<input type="radio" id="difficulty" name="difficulty" value="5"/></td>
					</tr>
				
					<!-- <tr>
						<td class="tdTitle">类别：</td>
						<td class="tdInput">
						<select id="relationship" name="relationship">
							<option value="0" selected="selected">所有</option>
							<option value="1">我录入的</option>
							<option value="2">我收藏的</option>
						</select>
						</td>
					</tr> -->
					<tr>
				     	<td></td>
						<td><input type="submit" class="submitBtn" value="查询"></td>
					</tr>
				</table>
			</form>
     	</div>
     	<div class="sort">
    
     	<span class="sortRight"><a class="byTime" href='<c:url value='/testPaper_publicOrderByTime.action' />'>按时间排序</a>|
     	<a class="byCNum" href='<c:url value='/testPaper_publicOrderByNum.action' />'>按收藏数排序</a></span>	 
	     </div>
	
     	<div class="list">
     		<table class="tbList">
     			<tr>
     				<th class="tdId">试卷序号</th>
     				<th class="tdName">试卷名称</th>
     				<th class="tdSubject">科目</th>
     				<th class="tdDiff">难度</th>
     				<th class="tdTime">上传/录入时间</th>
     				<th class="tdNum">收藏数</th>
     				<th class="tdHandle">操作</th>
     			</tr>
     			<c:forEach items="${pagebean.beanList }" var="testPaper">
     			<tr>
     				<td>${testPaper.testPaperId }</td>
     				<td id="tdName">${testPaper.paperName }</td>
     				<td>${testPaper.gradeSubject.grade.gradeName } ${testPaper.gradeSubject.subject.subjectName }</td>
     				
     				<td>
     				<c:if test="${testPaper.difficulty eq 1}">超简单</c:if> 
     				<c:if test="${testPaper.difficulty eq 2}">简单</c:if> 
     				<c:if test="${testPaper.difficulty eq 3}">一般</c:if> 
     				<c:if test="${testPaper.difficulty eq 4}">困难</c:if> 
     				<c:if test="${testPaper.difficulty eq 5}">超困难</c:if> 
     				</td>
     				<td  id="tdTime">${testPaper.createTime }</td>
     				<td>${testPaper.collectionNum }</td>
     				<td>
     					<a href="<c:url value='/testPaper_load.action?testPaperId=${testPaper.testPaperId }'/>">查看</a>
     					<a onclick="collect(${testPaper.testPaperId },this)" class="collect">收藏</a>
     					<a href="<c:url value='${testPaper.filePath }'/>" download="doc">下载</a>
     				</td>
     			</tr>
     			</c:forEach>
     		
     		</table>    	
     	</div>
     	<div style="float:left; width: 100%; text-align: center;margin-top:30px;">
		<hr/>
		<br/>
		<%@include file="/pages/pager.jsp" %>
		</div>
        </div>
    </body>
</html>