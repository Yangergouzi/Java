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
	
	<style>
		.registAndLogin{
                position: absolute;
                margin-left: 10%;
                width: 25%;
                height: 400px;
                text-align: center;
            }
            .circle{
                list-style: none;
                width: 100px;
                height: 100px;
                background-color: #AAAAAA;
                border: 3px solid #CCCC00;
                border-radius: 50%;
                margin-top: 100px;
            }
            .circle p{
                margin-top: 15px;
                font-size: 20px;
                color: #DE0B0B;
                line-height: 30px;
            }
            a{  text-decoration:none;}
            .circle:hover{
                background-color: #CCCC00;
            }
        
            .info1{
                position: absolute;
                margin-left: 50%;
                margin-top:5%;
                width: 30%;
               
            }
            .info2{
                position: absolute;
                background: url(image/index-talk.png) no-repeat;
                background-size: 100%;
                margin-left: 48%;
                margin-top: 23%;
                width: 35%;
                height: 300px;
            }
            .talk{
                margin-left: 120px;
                font-size: 12px;
                color: #3F51B5;
                margin-top: 35px;
            }
            .studenttalk{
               margin-top: 48px;
            }
	</style>
</head>
<body>
	 <div class="box">
      <ul class="registAndLogin">
          <li class="circle login"><a target="parent" href="<c:url value='/pages/user/login.jsp'/>"><p>我要<br>登录</p></a></li>
          <li class="circle regist"><a target="body" href="<c:url value='/pages/user/regist.jsp'/>"><p>我要<br>注册</p></a></li>
      </ul>
          <div class="info1">
              <p><img src="image/icon1.png"/>多功能在线试卷管理系统云平台<img src="image/icon1.png"/><br><br>
                  专为各学校服务的试卷管理系统，包括科目精<br>准分类、试卷上传、试卷拆分、智能组卷、在<br>线评论功能。
              </p>
          </div>
          <div class="info2" >
              <p class="talk teachertalk">我是老师，自主出题、私有题库、<br>灵活组卷、难度定级、评论互动</p>
              <p class="talk studenttalk">我是学生，海量题库、精确找卷、<br>发表意见、收藏下载、应有尽有</p>
          </div>
    </div>

</body>
</html>
