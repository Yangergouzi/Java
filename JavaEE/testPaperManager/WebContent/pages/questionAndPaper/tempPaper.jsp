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
       
        <title>组合试卷查看</title>
        <script type="text/javascript">
        $(function(){
        	//添加大题标题      	
        	$("p[id='1']").text("选择题");
        	$("p[id='2']").text("填空题");
        	$("p[id='3']").text("简答题");
        	$("p[id='4']").text("作文");
        	$("p[id='5']").text("其他");
        	//添加小题序号
        	var i = 1
        	$(".smallbox .serial").each(function(){
        		$(this).text(i + '.');
        		i++;
        	});
        	
        });
        //保存试卷
        function savePaper(id){
        	//校验试卷名是否填写
        	var paperName = $("#paperName").val();
        	var nospaceVal = paperName.replace(/\s+/, '');
        	if(!nospaceVal){
        		alert("试卷名称不能为空！");
        		return;
        	}
        	var paper = $("#paper").html();
        	var noTop = paper.replace(/<p class="questionTop">[\s\S]*?<\/p>/g,'');
        	
        	var html = '<!doctype html><html><head><meta http-equiv="Content-Type" content="text/html; charset=gb2312">';
        	html += ' <style>  #paper{}';
        	html += '.smallbox{width:96%;margin-left:10px;}.content{font-size:18px;width:90%;';
        	html += 'white-space:pre-wrap;text-align:left;margin-left:5%;}';
        	html += ' .type{font-size:23px;text-align:left;margin-left:20px;margin-top:40px;}';
        	html += '.smallbox p{visibility:hidden;}';
        	html += '.smallbox .serial{position:absolute;left:46px;font-size:18px;}</style></head><body><div id="paper">';
        	html += '<h1>' + $("#paperName").val() + '</h1>';        	
        	html += noTop;
        	html += '</div> </body></html>';
        //	console.log(html);
        $("#htmlInput").val(html);
        	//提交表单
        	$("form").submit();
        }
   
     
        </script>
        <style>
        body{overflow:auto;}
        .box{margin-top:10px;width:100%;height:2000px;}
        #paper{width:76%;;background:white;border:1px solid grey;margin-left:20px;padding:0 0 30px 0;}
        #property{position:fixed;left:80%;top:30px;text-align:right;padding: 10px 5px 10px 5px;border:3px inset #3c78d8;border-radius:10%;background:#dfd;}
 		 #property p{font-size:18px;text-align:center;}      
        #property div{margin-top:15px;}
        #property input,select {width:120px;margin-left:10px;}
      	.smallbox{width:96%;margin-left:10px;}
      	.smallbox p{visibility:hidden;width:100%;font-family:"微软雅黑","黑体","宋体"; font-size:16px;color:#5f5f5f;height:30px;background:#cccccc;text-align:left;}
       	.smallbox span{margin-left:20px;}
        .smallbox:hover{border:1px solid #3CC4C4;}
        .smallbox:hover p{visibility:visible;}
    //	 textarea{font-size:18px;width:90%;overflow:hidden;outline:none;resize:none;border:none;}
    	.content{font-size:18px;width:90%;white-space:pre-wrap;text-align:left;margin-left:5%;}
    	 .type{font-size:23px;text-align:left;margin-left:20px;margin-top:40px;}
    	 .smallbox .serial{position:absolute;left:46px;font-size:18px;}
    	 .remove{position:absolute;color:#3CC4C4;left:70%;}
    	 .remove:hover {color:#3c78d8}
    	 .btn{color:#3c78d8;border:1px solid #3c78d8;margin-top:10px;}
    	 .btn:hover{color:#fff;background:#3c78d8;}
        </style>
    </head>
    <body>
        <div class="box">
     		<div id="paper">
     		<c:forEach items="${map }" var="m">
     			<p class="type" id="${m.key}"></p>
	     		<c:forEach items="${m.value }" var="question">
	     			<div class="smallbox">
	     			<p class="questionTop">
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
	     					<a href="<c:url value='/testPaper_removeQuestion.action?questionId=${question.questionId }'/>"><i class="fa fa-trash remove">移除</i></a>
	     				</span>
	     				</p>  
	     				<i class="serial"></i>				
	     				<div class="content">${question.content}</div>
	     			</div>
	     		
	     		</c:forEach>
     		</c:forEach>
     		</div>
            <div id="property">
             <form action="<c:url value="/testPaper_finishCombine.action" />" method="post" >  
             	<input type="hidden" name="html" id="htmlInput" />
             	<input type="hidden" name="testPaperId" value="${tempPaper.testPaperId }" />
           			  <p>试卷属性设置</p>
             			<div>  
             					
     			  		试卷名称
     					<input type="text" name="paperName" id="paperName"  placeholder="必填"/>
     					
     					</div> 
     					<div> 
     				          总分
     					<input type="text" name="totalScore" id="totalScore" onkeyup="value=value.replace(/[^\d]/g,'')"/>
     					</div> 
     					<div> 
     					考试时限
     					<input type="text" name="timeLimit" id="timeLimit" onkeyup="value=value.replace(/[^\d]/g,'')"/>
     					</div> 
     				<div> 
     					难度
     					<select id="difficulty" name="difficulty">
     						<option value="1">超简单</option>
     						<option value="2">简单</option>
     						<option value="3" selected="selected">一般</option>
     						<option value="4">困难</option>
     						<option value="5">超困难</option>
     					</select>
     				</div> 
     				<div> 
     					是否公开
     					<select id="isPublic" name="isPublic">
     						<option value="1">是</option>
     						<option value="0">否</option>
     					</select>
     				</div>    					
     		</form>
     		<button class="btn saveBtn" onclick="savePaper(${tempPaper.testPaperId})">保存试卷</button>
     		<a href="<c:url value='/testPaper_combine.action' />"><button class="btn contBtn">继续选题</button></a>
            </div>
          
        </div>
    </body>
</html>