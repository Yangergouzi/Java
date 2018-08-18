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
       
        <title>试题查看</title>
        <script type="text/javascript">
        $(function(){
        	//先隐藏答案
        	$("#hidePart").css("visibility","hidden");
        	$("#btnShow").click(function(){
        		$("#hidePart").css("visibility","visible");
        	});
        	// 为每一个textarea绑定事件使其高度自适应
            $.each($("textarea"), function(i, n){
                autoTextarea($(n)[0],0,500);
            });

        	//如果是选择题在题目与选项间加空格
        	/*  var type="${question.type}";
        	if(type == 1){
        		var content = $("#content").val();
        		var index = content.lastIndexOf("A.");
        		var c1 = content.substring(0,index);
        		var c2 = content.substring(index,content.length);
        		var content = c1 + "<br>" + c2;
        		$("#content").html(content);
        	}  */
        });
        /**
         * 文本框根据输入内容自适应高度
         * {HTMLElement}   输入框元素
         * {Number}        设置光标与输入框保持的距离(默认0)
         * {Number}        设置最大高度(可选)
         */
        var autoTextarea = function (elem, extra, maxHeight) {
            extra = extra || 0;
            var isFirefox = !!document.getBoxObjectFor || 'mozInnerScreenX' in window,
            isOpera = !!window.opera && !!window.opera.toString().indexOf('Opera'),
                addEvent = function (type, callback) {
                    elem.addEventListener ?
                        elem.addEventListener(type, callback, false) :
                        elem.attachEvent('on' + type, callback);
                },
                getStyle = elem.currentStyle ? 
                function (name) {
                    var val = elem.currentStyle[name];
                    if (name === 'height' && val.search(/px/i) !== 1) {
                        var rect = elem.getBoundingClientRect();
                        return rect.bottom - rect.top -
                               parseFloat(getStyle('paddingTop')) -
                               parseFloat(getStyle('paddingBottom')) + 'px';       
                    };
                    return val;
                } : function (name) {
                    return getComputedStyle(elem, null)[name];
                },
            minHeight = parseFloat(getStyle('height'));
            elem.style.resize = 'both';//如果不希望使用者可以自由的伸展textarea的高宽可以设置其他值

            var change = function () {
                var scrollTop, height,
                    padding = 0,
                    style = elem.style;

                if (elem._length === elem.value.length) return;
                elem._length = elem.value.length;

                if (!isFirefox && !isOpera) {
                    padding = parseInt(getStyle('paddingTop')) + parseInt(getStyle('paddingBottom'));
                };
                scrollTop = document.body.scrollTop || document.documentElement.scrollTop;

                elem.style.height = minHeight + 'px';
                if (elem.scrollHeight > minHeight) {
                    if (maxHeight && elem.scrollHeight > maxHeight) {
                        height = maxHeight - padding;
                        style.overflowY = 'auto';
                    } else {
                        height = elem.scrollHeight - padding;
                        style.overflowY = 'hidden';
                    };
                    style.height = height + extra + 'px';
                    scrollTop += parseInt(style.height) - elem.currHeight;
                    document.body.scrollTop = scrollTop;
                    document.documentElement.scrollTop = scrollTop;
                    elem.currHeight = parseInt(style.height);
                };
            };

            addEvent('propertychange', change);
            addEvent('input', change);
            addEvent('focus', change);
            change();
        };

     
        </script>
        <style>
        body{padding:0;overflow:auto;}
        .box{margin-top:20px;}
        #question{width:60%;margin-left:100px;background:white;}
        #content{font-size:18px;width:100%;border:none;}
        img{width:500px;margin-bottom:20px;}
        #property{position:absolute;left:75%;top:100px}
       #hidePart{width:60%;margin-left:100px;border:1px solid red;margin-top:30px;}
       #hidePart p{margin-top:10px;margin-bottom:10px;}

        </style>
    </head>
    <body>
        <div class="box">
     		<div id="question">
     		<textarea id="content">${question.content }</textarea>
     		
     		<c:if test="${not empty question.imagePath }">
     		<img id="image" src='<c:url value='${question.imagePath }'/>'/>
     		</c:if>
 
     		</div>
            <div id="property">
            <p id="subject">${question.gradeSubject.grade.gradeName }${question.gradeSubject.subject.subjectName }</p>
            <p id="type">
          			 <c:if test="${question.type eq 1}">选择题</c:if>
     				<c:if test="${question.type eq 2}">填空题</c:if>
     				<c:if test="${question.type eq 3}">简答题</c:if>
     				<c:if test="${question.type eq 4}">作文</c:if>
     				<c:if test="${question.type eq 0}">其他</c:if> 
     		<p>		
            <p id="difficulty">难度：
          	    <c:if test="${question.difficulty eq 1}">超简单</c:if> 
  				<c:if test="${question.difficulty eq 2}">简单</c:if> 
  				<c:if test="${question.difficulty eq 3}">一般</c:if> 
  				<c:if test="${question.difficulty eq 4}">困难</c:if> 
  				<c:if test="${userQuestion.question.difficulty eq 5}">超困难</c:if> 
  			</p>
  			<br><input type="button" value="查看答案" class="btn" id="btnShow"/>
            </div>
            <div id="hidePart">
            	<p id="trueAnswer">答案：${question.trueAnswer}</p>
            	<c:if test="${not empty question.analysis }">
            	<p id="analysis">解析：${question.analysis}</p>
            	</c:if>
            	<c:if test="${not empty question.mainPoint }">
            	<p id="mainPoint">知识点：${question.mainPoint}</p>
            	</c:if>
            </div>
         <c:if test="${sessionScope.tempPaper.gradeSubject.gradeSubjectId eq question.gradeSubject.gradeSubjectId }">
     		<%@include file="/pages/basket.jsp" %>
     	</c:if>
        </div>
    </body>
</html>