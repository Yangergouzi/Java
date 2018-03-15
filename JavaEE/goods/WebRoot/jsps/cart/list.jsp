<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>cartlist.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="<c:url value='/js/round.js'/>"></script>
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/cart/list.css'/>">
<script type="text/javascript">
	$(function(){
		//计算总计
		showTotal();
		
		//给全选框添加click事件
		$("#selectAll").click(function(){
			var bool = $(this).attr("checked");//获取全选框当前状态
			setAllCheckbox(bool);//所有复选框与全选框状态同步
			jieSuan(bool);//结算按钮同步
			showTotal();
		});
		
		//给每个条目复选框添加click事件
		$(":checkbox[name=checkboxBtn]").click(function(){
			var all = $(":checkbox[name=checkboxBtn]").length;
			var select = $(":checkbox[name=checkboxBtn]:checked").length;
			if(select == all){//如果所有都选上，全选复选框要选上，结算按钮有效
				$("#selectAll").attr("checked",true);
				jieSuan(true);
			}else if(select == 0){
				$("#selectAll").attr("checked",false);
				jieSuan(false);
			}else{
				$("#selectAll").attr("checked",false);
				jieSuan(true);
			}
			//重新计算总计
			showTotal();
		});
		/*添加加号点击事件
		 1.截取出cartItemId
		 2.获取数量
		 3.sendUpdateQuantity
		*/
		$(".jia").click(function(){
			var id = $(this).attr("id").substring(0,32);
			var quantity = $("#" + id +"Quantity").val();
			sendUpdateQuantity(id,Number(quantity)+1);
		});
		/*添加减号点击事件
		 1.截取出cartItemId
		 2.获取数量
		 3.sendUpdateQuantity
		*/
		$(".jian").click(function(){
			var id = $(this).attr("id").substring(0,32);
			var quantity = $("#" + id +"Quantity").val();
			if(quantity == 1){
				if(confirm("确认删除该物品？")){
					location = "/goods/CartItemServlet?method=batchDelete&cartItemIds=" + id;
				}
			}else{
				sendUpdateQuantity(id,Number(quantity)-1);
				}
		});
	
	});
	/*
		ajax请求服务器，修改数量	
	*/
	function sendUpdateQuantity(id,quantity){
		$.ajax({
			async:false,
			cache:false,
			url:"/goods/CartItemServlet",
			data:{method:"updateQuantity",cartItemId:id,quantity:quantity},
			datatype:"json",
			type:"POST",
			success:function(result){
				//修改数量
				json = eval("("+result+")");//那边传的是json对象的格式的一个字符串，在前台首先将字符串转化为一个json格式的js对象
				$("#" + id +"Quantity").val(json.quantity);
				//修改小计
				$("#" + id + "Subtotal").text(json.subtotal);
				//重新计算总计
				showTotal();
			}
		
		});
	}
	
	/*
		计算总计
		1.遍历所有已选的复选框
		2.获得复选框的值，即其他元素的前缀
		3.通过前缀找到小计的文本
		4.计算总值
		5.显示在总计上
	*/
	function showTotal(){
		var total = 0;
		$(":checkbox[name=checkboxBtn]:checked").each(function(){
			var id = $(this).val();
			var text = $("#"+ id + "Subtotal").text();
			total += Number(text);
		});
		$("#total").text(round(total,2));//引用了round.js里的round方法
	}
	//统一设置所有条目的复选按钮
	function setAllCheckbox(bool){
		$(":checkbox[name=checkboxBtn]").each(function(){
			$(this).attr("checked",bool);
		});
	}
	//设置结算按钮是否有效
	function jieSuan(bool){
		if(bool){
			$("#jiesuan").removeClass("kill").addClass("jiesuan");
			$("#jiesuan").unbind("click");
		}else{
			$("#jiesuan").removeClass("jiesuan").addClass("kill");
			$("#jiesuan").click(function(){return false;});
		}
	}
	/*批量删除
		1.遍历所有已选的条目复选框
		2.得到cartItemId，加进数组
		3.指定location,cartItemIds=cartItemIdArray.toString()
	*/
	function batchDelete(){
		var cartItemIdArray = new Array();
		$(":checkbox[name=checkboxBtn]:checked").each(function(){
			cartItemIdArray.push($(this).val()); 
		});
		location = "/goods/CartItemServlet?method=batchDelete&cartItemIds=" + cartItemIdArray;//数组加入字符串自动调用toString()
	}
	//用户点击结算超链接，执行此函数
	function jiesuan(){
		//遍历所有已选的条目复选框，得到cartItemIds
		var cartItemIdArray = new Array();
		$(":checkbox[name=checkboxBtn]:checked").each(function(){
			cartItemIdArray.push($(this).val());
		});
		//给$("#cartItemIds")赋值
		$("#cartItemIds").val("" + cartItemIdArray);
		//得到总计，给$("#hiddeTotal")
		$("#hiddenTotal").val($("#total").text());
		//提交jiesuanForm
		$("#jiesuanForm").submit();
	}
	
</script>
  </head>
  <body>

	<c:choose>
	<c:when test="${empty cartItemList }">
			<table width="95%" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="right">
						<img align="top" src="<c:url value='/images/icon_empty.png'/>"/>
					</td>
					<td>
						<span class="spanEmpty">您的购物车中暂时没有商品</span>
					</td>
				</tr>
			</table>  
		
		<br/>
		<br/>
	</c:when>
	<c:otherwise>
		<table width="95%" align="center" cellpadding="0" cellspacing="0">
			<tr align="center" bgcolor="#efeae5">
				<td align="left" width="50px">
					<input type="checkbox" id="selectAll" checked="checked"/><label for="selectAll">全选</label>
				</td>
				<td colspan="2">商品名称</td>
				<td>单价</td>
				<td>数量</td>
				<td>小计</td>
				<td>操作</td>
			</tr>
		
		
		
			<c:forEach items="${cartItemList }" var="cartItem">
				<tr align="center">
					<td align="left">
						<input value="${cartItem.cartItemId }" type="checkbox" name="checkboxBtn" checked="checked"/>
					</td>
					<td align="left" width="70px">
						<a class="linkImage" href="<c:url value='/BookServlet?method=load&bid=${cartItem.book.bid }'/>"><img border="0" width="54" align="top" src="<c:url value='${cartItem.book.image_b }'/>"/></a>
					</td>
					<td align="left" width="400px">
					    <a href="<c:url value='/BookServlet?method=load&bid=${cartItem.book.bid }'/>"><span>${cartItem.book.bname }</span></a>
					</td>
					<td><span>&yen;<span class="currPrice">${cartItem.book.currPrice }</span></span></td>
					<td>
						<a class="jian" id="${cartItem.cartItemId }Jian"></a><input class="quantity" readonly="readonly" id="${cartItem.cartItemId }Quantity" type="text" value="${cartItem.quantity }"/><a class="jia" id="${cartItem.cartItemId }Jia"></a>
					</td>
					<td width="100px">
						<span class="price_n">&yen;<span class="subTotal" id="${cartItem.cartItemId }Subtotal">${cartItem.subTotal }</span></span>
					</td>
					<td>
						<a href="<c:url value='/CartItemServlet?method=batchDelete&cartItemIds=${cartItem.cartItemId }'/>">删除</a>
					</td>
				</tr>
			</c:forEach>
		
			<tr>
				<td colspan="4" class="tdBatchDelete">
					<a href="javascript:batchDelete();">批量删除</a>
				</td>
				<td colspan="3" align="right" class="tdTotal">
					<span>总计：</span><span class="price_t">&yen;<span id="total"></span></span>
				</td>
			</tr>
			<tr>
				<td colspan="7" align="right">
					<a href="javascript:jiesuan();" id="jiesuan" class="jiesuan"></a>
				</td>
			</tr>
		</table>
			<form id="jiesuanForm" action="<c:url value='/CartItemServlet'/>" method="post">
				<input type="hidden" name="cartItemIds" id="cartItemIds"/>
				<input type="hidden" name="total" id="hiddenTotal"/>
				<input type="hidden" name="method" value="loadCartItems"/>
			</form>
		</c:otherwise>	
	</c:choose>	
  </body>
</html>
