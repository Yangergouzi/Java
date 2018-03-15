package com.yang.admin.order.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yang.goods.cart.service.CartItemService;
import com.yang.goods.order.domain.Order;
import com.yang.goods.order.service.OrderService;
import com.yang.goods.pager.PageBean;
import com.yang.goods.user.domain.User;

import cn.itcast.servlet.BaseServlet;

public class AdminOrderServlet extends BaseServlet {

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
	 * findAll
	 * 
	 * 2.获取pc
	 * 3.调用service#findAll,得到PageBean
	 * 4.给PageBean传入url
	 * 4.保存PageBean
	 * 5.return "f:"
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pc = getPc(request);
		PageBean pb = service.findAll(pc);
		pb.setUrl(getUrl(request));
		request.setAttribute("pagebean", pb);
		return "f:/adminjsps/admin/order/list.jsp";
	}
	/*
	 * 按订单状态查询
	 * 1.获取status
	 * 2.调用service#dindByStatus
	 * 3.得到pagebean
	 * 4.保存并转发到list.jsp
	 */
	public String findByStatus(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pc = getPc(request);
		String status = request.getParameter("status");
		PageBean pb = service.findByStatus(status, pc);
		pb.setUrl(getUrl(request));
		request.setAttribute("pagebean", pb);
		return "f:/adminjsps/admin/order/list.jsp";
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
		return "f:/adminjsps/admin/order/desc.jsp";
	}
	/*
	 * 取消订单
	 * 1.得到请求参数oid
	 * 2.得到order，校验状态，只有为1时，才能取消
	 * 3.若失败，保存错误信息，转发到msg.jsp
	 * 4.通过校验，调用service#updateStatus
	 * 5.保存成功信息，转发到/msg.jsp
	 */
	public String cancel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String oid = request.getParameter("oid");
		Order order = service.load(oid);
		if(order.getStatus() != 1){
			request.setAttribute("msg", "抱歉，当前状态不能取消！");
			return "f:/adminjsps/msg.jsp";
		}
		service.updateStatus(oid,5);
		request.setAttribute("msg", "订单已取消！");
		return "f:/adminjsps/msg.jsp";
	}
	/*
	 * 发货
	 * 1.得到请求参数oid
	 * 2.得到order，校验状态，只有为2时，才能发货
	 * 3.若失败，保存错误信息，转发到msg.jsp
	 * 4.通过校验，调用service#updateStatus
	 * 5.保存成功信息，转发到/msg.jsp
	 */
	public String deliver(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String oid = request.getParameter("oid");
		Order order = service.load(oid);
		if(order.getStatus() != 2){
			request.setAttribute("msg", "抱歉，当前状态不能发货！");
			return "f:/adminjsps/msg.jsp";
		}
		service.updateStatus(oid,3);
		request.setAttribute("msg", "订单已发货！");
		return "f:/adminjsps/msg.jsp";
	}

}
