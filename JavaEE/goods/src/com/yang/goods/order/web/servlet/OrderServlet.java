package com.yang.goods.order.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yang.goods.cart.domain.CartItem;
import com.yang.goods.cart.service.CartItemService;
import com.yang.goods.order.domain.Order;
import com.yang.goods.order.domain.OrderItem;
import com.yang.goods.order.service.OrderService;
import com.yang.goods.pager.PageBean;
import com.yang.goods.user.domain.User;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class OrderServlet extends BaseServlet {
	private OrderService service = new OrderService();
	private CartItemService cartItemService = new CartItemService();
	/*
	 * 得到截取后url
	 */
	public String getUrl(HttpServletRequest request){
		String url = request.getRequestURI() + "?" + request.getQueryString();
		int index = url.lastIndexOf("&pc");//若url存在&pc，index为"&pc"前一个字符的下标；反之，index = -1
		if(index != -1){
			url = url.substring(0, index);
		}
		return url;
	}
	/*
	 * 得到pc，若没有，设为1
	 */
	public int getPc(HttpServletRequest request){
		int pc =1;
		String param = request.getParameter("pc");
		if(param != null && !param.trim().isEmpty()){
			try{
				pc = Integer.parseInt(param);
			}catch(RuntimeException e){}
		}
		return pc;
	}
	
	
	/*
	 * myOrder
	 * 1.从session获取用户
	 * 2.获取pc
	 * 3.调用service#myOrders,得到PageBean
	 * 4.给PageBean传入url
	 * 4.保存PageBean
	 * 5.return "f:"
	 */
	public String myOrders(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User owner = (User) request.getSession().getAttribute("user");
		int pc = getPc(request);
		PageBean pb = service.myOrders(owner.getUid(), pc);
		pb.setUrl(getUrl(request));
		request.setAttribute("pagebean", pb);
		return "f:/jsps/order/list.jsp";
	}
	
	/*
	 * 生成订单
	 * 1.从请求参数获取cartItemIds、total、address，从session获取user
	 * 2.新建订单，设置oid,ordertime,total,status=1,address和owner
	 * 3.加载orderItemList:用cartItemIds调用cartItemService#loadCartItems(cartItemIds)得到List<CartItem>，
	 * 		再遍历之，根据其实例变量创建orderItem
	 * 4.调用service#add完成添加
	 * 5.保存order,转发到/jsps/order/ordersucc.jsp
	 */
	public String createOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cartItemIds = request.getParameter("cartItemIds");
		String total = request.getParameter("total");
		String address = request.getParameter("address");
		User owner = (User) request.getSession().getAttribute("user");
		Order order = new Order();
		order.setOid(CommonUtils.uuid());
		order.setOrdertime(String.format("%tF %<tT", new Date()));
		order.setTotal(Double.parseDouble(total));
		order.setStatus(1);
		order.setAddress(address);
		order.setOwner(owner);
		//生成ordetItemList
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds);
		for(CartItem cartitem : cartItemList){
			OrderItem orderitem = new OrderItem();
			orderitem.setOrderItemId(CommonUtils.uuid());
			orderitem.setBook(cartitem.getBook());
			orderitem.setQuantity(cartitem.getQuantity());
			orderitem.setSubtotal(cartitem.getSubTotal());
			Order o = new Order();
			o.setOid(order.getOid());
			orderitem.setOrder(o);
			orderItemList.add(orderitem);
		}
		order.setOrderItemList(orderItemList);
		service.add(order);
		request.setAttribute("order", order);
		return "f:/jsps/order/ordersucc.jsp";
	}
	/*
	 * 通过oid加载订单信息
	 * 1.获取请求参数oid和btn
	 * 2.调用service#load，返回order
	 * 3.保存order和btn,并转发/jsps/order/desc.jsp
	 */
	public String load(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String oid = request.getParameter("oid");
		String btn = request.getParameter("btn");
		Order order = service.load(oid);
		request.setAttribute("order", order);
		request.setAttribute("btn", btn);
		return "f:/jsps/order/desc.jsp";
	}
	/*
	 * 取消订单
	 * 1.得到请求参数oid
	 * 2.得到order，校验状态，只有为1时，才能取消
	 * 3.若失败，保存错误信息，转发到/jsps/msg.jsp
	 * 4.通过校验，调用service#updateStatus
	 * 5.保存成功信息，转发到/jsps/msg.jsp
	 */
	public String cancel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String oid = request.getParameter("oid");
		Order order = service.load(oid);
		if(order.getStatus() != 1){
			request.setAttribute("code", "error");
			request.setAttribute("msg", "抱歉，当前状态不能取消！");
			return "f:/jsps/msg.jsp";
		}
		service.updateStatus(oid,5);
		request.setAttribute("code", "success");
		request.setAttribute("msg", "订单已取消！");
		return "f:/jsps/msg.jsp";
	}
	/*
	 * 确认收货
	 * 1.得到请求参数oid
	 * 2.得到order，校验状态，只有为3时，才能取消
	 * 3.若失败，保存错误信息，转发到/jsps/msg.jsp
	 * 4.通过校验，调用service#updateStatus
	 * 5.保存成功信息，转发到/jsps/msg.jsp
	 */
	public String confirm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String oid = request.getParameter("oid");
		Order order = service.load(oid);
		if(order.getStatus() != 3){
			request.setAttribute("code", "error");
			request.setAttribute("msg", "抱歉，当前状态不能确认收货！");
			return "f:/jsps/msg.jsp";
		}
		service.updateStatus(oid,4);
		request.setAttribute("code", "success");
		request.setAttribute("msg", "恭喜，交易成功！");
		return "f:/jsps/msg.jsp";
	}
	/*
	 * 支付准备
	 * 1.调用service#load
	 * 2.保存order，转发/jsp/order/pay.jsp
	 */
	public String paymentPre (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String oid = request.getParameter("oid");
		Order order = service.load(oid);
		request.setAttribute("order", order);
		return "f:/jsps/order/pay.jsp";
	}
	
	
	/**
	 * 支付方法
	 * 
	 */
	public String payment(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//没有支付宝商户帐号。。。直接支付成功。。。
		
		String oid = req.getParameter("oid");
		service.updateStatus(oid, 2);
		req.setAttribute("code", "success");
		req.setAttribute("msg", "付款成功！");
		return "f:/jsps/msg.jsp";
	}
	
	  

}
