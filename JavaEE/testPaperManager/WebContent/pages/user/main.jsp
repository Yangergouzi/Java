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
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/mainStyle.css'/>">
	<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>   
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript">
    	$(function(){
    		
    	});
    </script>
	<style>
	body{ background-color: #D6C2A5;}
		.box{margin-left:15%;margin-top:8%;}
		.model1{width:120px;height:160px;margin-left:20px;border:1px solid #D6C2A5;}
		.model2{width:260px;height:110px;margin-left:20px;border:1px solid #D6C2A5;}
		.top{background:red;}
		.div3{margin-top:-50px;}
		.div4{margin-top:20px;}
		.div5,.div6{margin-top:-30px;}
		.model1 p{width:20px; margin:0 auto;margin-top:25px;line-height:24px;font-size:20px;}
		.model2 p{margin-top:35px;font-size:20px;}
		.div1{background:#f8e9a1;}
		.div2{background:#f76c6c;}
		.div3{background:#a8d0e6;}
		.div4{background:#c2cad0;}
		.div5{background:#374785;}
		.div6{background:#e7717d;}
		a{text-decoration:none; color:#000;}
		a:hover{text-decoration:none;}
		table p{}
		.model1 .btn{margin-top:30px;}
		.model2 .btn{margin-top:35px;margin-right:10px;margin-left:10px;}
		.btn{display:none;}
		table div:hover{
			background:#bfd8d2;
			transform:rotateY(180deg);
			-ms-transform:rotateY(180deg); 	/* IE 9 */
			-moz-transform:rotateY(180deg); 	/* Firefox */
			-webkit-transform:rotateY(180deg); /* Safari 和 Chrome */
			-o-transform:rotateY(180deg); 	/* Opera */
			transition: 0.6s;
		}
		table div:hover p{
		 	display:none;	
		}
		table div:hover .btn{		
			
			transform:rotateY(180deg);
			-ms-transform:rotateY(180deg); 	/* IE 9 */
			-moz-transform:rotateY(180deg); 	/* Firefox */
			-webkit-transform:rotateY(180deg); /* Safari 和 Chrome */
			-o-transform:rotateY(180deg); 	/* Opera */
			display:inline;	
			transition: display 0.6s;
		}
		.btn:hover{background:#fedcd2;}
	</style>
</head>
<body>
	 <div class="box">
	 	<table>
	 		<tr>
	 		<td><div class="div1 model1">
	 		<p>个人中心</p>
	 		<span>
	 		<a href="<c:url value='/user_toUpdatePwd.action' />" target="body"><button class="btn">修改密码</button></a>
	 		<a href="<c:url value='/pages/user/edit.jsp'/>" target="body"><button class="btn">编辑资料</button></a>
	 		</span>
	 		</div></td>
	 		<td><div class="div2 model1" >
	 		<p>科目管理</p>
	 		<span>
	 		<a href="<c:url value='/subject_findMySubjects.action' />" target="body"><button class="btn">我的科目</button></a>
	 		<a href="<c:url value='/subject_toAddMySubject.action' />" target="body"><button class="btn">添加科目</button></a>
	 		</span>
	 		</div></td>
	 		<td colspan="2"><div class="div3 model2">
	 		<p>录入/上传</p>
	 		<span>
	 		<a href="<c:url value='/question_toAddQuestion.action' />" target="body"><button class="btn">录入试题</button></a>
	 		<a href="<c:url value='/testPaper_toAddTestPaper.action' />" target="body"><button class="btn">上传试卷</button></a>
	 		</span>
	 		</div></td>
	 		</tr>
	 		<tr>
	 		<td colspan="2"><div class="div4 model2">
	 		<p>公共题库中心</p>
	 		<span>
	 		<a href="<c:url value='/testPaper_toPublicTestPapers.action' />" target="body"><button class="btn">试卷库</button></a>
	 		<a href="<c:url value='/question_toPublicQuestions.action' />" target="body"><button class="btn">试题库</button></a>
	 		</span>
	 		</div></td>
	 		
	 		<td><div class="div5 model1">
	 		<p>在线组卷</p>
	 		<span>
	 		<a href="<c:url value='/testPaper_combine.action' />" target="body"><button class="btn">手动组卷</button></a>
	 		</span>
	 		</div></td>
	 		<td><div class="div6 model1">
	 		<p>我的题库</p>
	 		<span>
	 		<a href="<c:url value='/testPaper_toMyTestPapers.action' />" target="body"><button class="btn">我的试卷</button></a>
	 		<a href="<c:url value='/question_toMyQuestions.action' />" target="body"><button class="btn">我的试题</button></a>
	 		</span>
	 		</div></td>
	 		</tr>
	 	</table>
    </div>

</body>
</html>
