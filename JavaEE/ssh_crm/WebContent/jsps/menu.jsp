<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>menu</title>
	<style type="text/css">
	body {
		margin: 0;
		padding: 30px 20px;
	}
	
	.items {
		width: 70%;
		border-bottom: 2px dotted darkcyan;
	}
	
	i:hover,li a:hover {
		color: darkcyan;
	}
	
	li {
		list-style: none;
		font-size: 13px;
		margin-left: 25px;
		line-height: 200%;
	}
	
	li a {
		color: #C92F17;
		text-decoration: none;
	}
	</style>
 	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
</head>
<body>
	 <div class="menu">  
            <div class="items">  
                <i class="fa fa-plus">客户管理</i>    
                <ul>
                    <li><a href="<c:url value='/customer_list.action'/>" target="body">客户列表</a></li>
                    <li><a href="<c:url value='/customer_toAddPage.action'/>" target="body">添加客户</a></li>
                </ul>
             </div> 
             <div class="items"> 
                <i class="fa fa-plus">联系人管理</i>
                <ul>
                    <li><a href="<c:url value='/linkMan_list.action'/>" target="body">联系人列表</a></li>
                    <li><a href="<c:url value='/linkMan_toAddPage.action'/>" target="body">新增联系人</a> </li>
                </ul>
            </div>
            <div class="items"> 
                <i class="fa fa-plus">客户拜访管理</i>
                <ul>
                    <li><a href="<c:url value='/visit_list.action'/>" target="body">客户拜访列表</a></li>
                    <li><a href="<c:url value='/visit_toAddPage.action'/>" target="body">新增客户拜访</a> </li>
                </ul>
            </div>
            <div class="items"> 
                <i class="fa fa-plus">综合查询</i>
                <ul>
                    <li><a href="<c:url value='/customer_toCustomerSelectPage.action'/>" target="body">客户信息查询</a></li>
                    <li><a href="<c:url value='/linkMan_toLinkManSelectPage.action'/>" target="body">联系人信息查询</a></li>
                    <li><a href="<c:url value='/visit_toVisitSelectPage.action'/>" target="body">客户拜访信息查询</a></li>
                </ul>
            </div>
            <div class="items"> 
                <i class="fa fa-plus">统计分析</i>
                <ul>
                     <li><a href="<c:url value='/customer_countSource.action'/>" target="body">根据客户来源统计</a> </li>
                    <li><a href="<c:url value='/customer_countLevel.action'/>" target="body">根据客户级别统计</a> </li>
                </ul>
            </div>
            <div class="items"> 
                <i class="fa fa-plus">系统管理</i>
                <ul></ul>
            </div>
        </div>

        <script>
            $(function(){
                var flag = false;
                $(".items li").hide();
                $("i").click(function(){
                    $(".items li").hide();
                    if( $(this).attr("class") == "fa fa-plus"){
                        $("i").attr("class","fa fa-plus");
                        $(this).siblings().children().show();
                        $(this).attr("class","fa fa-minus");
                    }else{
                        $(this).siblings().children().hide();
                        $(this).attr("class","fa fa-plus");
                    }
                });

            });
        </script>
</body>
</html>