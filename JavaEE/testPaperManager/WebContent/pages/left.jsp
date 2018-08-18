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
	
	  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
        <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
	
	<script type="text/javascript">
 
	</script> 
	  <script>
            $(function(){
                var flag = false;
                $(".items li").hide();//先隐藏二级菜单
                $("i").click(function(){//当点击某菜单条目时
                    $(".items li").hide();//隐藏所有二级菜单
                    //如果当前条目显示加号即没有打开，则显示其下的二级菜单并显示减号
                    if( $(this).attr("class") == "fa fa-plus"){
                        $("i").attr("class","fa fa-plus");
                        $(this).siblings().children().show();
                        $(this).attr("class","fa fa-minus");
                    }else{//否则，隐藏当前条目下的二级菜单并显示加号
                        $(this).siblings().children().hide();
                        $(this).attr("class","fa fa-plus");
                    }
                });

            });
        </script>
	<style>
	   body{
              margin: 0;
           	padding:0;
          }
          .menu{
          	margin-top:20px;
          }

          .items{ width: 80%;  margin-top:10px; background: #9092A6; border-radius:6px;line-height:38px;}
          i{color:grey; margin-left:15px;}
          i span{color:#efaa72;}
          i:hover,span:hover{color: #de6b0b; }
          ul{margin-top:5px;}
          li{list-style: none; font-size: 13px;  margin-left: 25px; line-height: 200%; }
          li a{color: #E51C23;text-decoration: none;}
	</style>
</head>
<body>
	 <div class="menu">  
           
             <c:choose>
		        <c:when test="${empty sessionScope.user }"></c:when>
		        <c:otherwise>
		        <div class="items">  
                <i class="fa fa-plus">&ensp;&ensp;&ensp;&ensp;<span>个人中心</span></i>    
                <ul>
                    <li><a href="<c:url value='/user_toUpdatePwd.action' />" target="body">修改密码</a></li>
                    <li><a href="<c:url value='/pages/user/edit.jsp'/>" target="body">编辑资料</a></li>
                </ul>
             </div> 
             <div class="items">  
                <i class="fa fa-plus">&ensp;&ensp;&ensp;&ensp;<span>科目管理</span></i>    
                <ul>
                    <li><a href="<c:url value='/subject_findMySubjects.action' />" target="body">我的科目</a></li>
                    <li><a href="<c:url value='/subject_toAddMySubject.action' />" target="body">添加我的科目</a></li>
                </ul>
             </div> 
             <div class="items"> 
                <i class="fa fa-plus">&ensp;&ensp;&ensp;&ensp;<span>我的题库</span></i>
                <ul>
                    <li><a href="<c:url value='/testPaper_toMyTestPapers.action' />" target="body">我的试卷</a></li>
                    <li><a href="<c:url value='/question_toMyQuestions.action' />" target="body">我的试题</a> </li>
                </ul>
            </div>
             <div class="items"> 
                <i class="fa fa-plus">&ensp;&ensp;&ensp;&ensp;<span>录入试卷/试题</span></i>
                <ul>
                    <li><a href="<c:url value='/question_toAddQuestion.action' />" target="body">试题录入</a></li>
                    <li><a href="<c:url value='/testPaper_toAddTestPaper.action' />" target="body">试卷上传</a></li>
                </ul>
            </div>
              <div class="items"> 
                <i class="fa fa-plus">&ensp;&ensp;&ensp;&ensp;<span>在线组卷</span></i>
                <ul>
                     <li><a href="<c:url value='/testPaper_combine.action' />" target="body">人工组卷</a> </li>
                   <!--  <li><a href="#">自动组卷</a> </li> -->
                </ul>
            </div>
            
		        
		        </c:otherwise>
		       </c:choose> 
            
            <div class="items"> 
                <i class="fa fa-plus">&ensp;&ensp;&ensp;&ensp;<span>公共题库中心</span></i>
                <ul>       
                    <li><a href="<c:url value='/testPaper_toPublicTestPapers.action' />" target="body">试卷库</a></li>
                    <li><a href="<c:url value='/question_toPublicQuestions.action' />" target="body">试题库</a> </li>
                </ul>
            </div>
           
           
        </div>

      
</body>
</html>
