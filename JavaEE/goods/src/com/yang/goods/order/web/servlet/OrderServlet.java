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
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String payment(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("payment.properties"));
		/*
		 * 1. 准备13个参数
		 */
		String p0_Cmd = "Buy";//业务类型，固定值Buy
		String p1_MerId = props.getProperty("p1_MerId");//商号编码，在易宝的唯一标识
		String p2_Order = req.getParameter("oid");//订单编码
		String p3_Amt = "0.01";//支付金额
		String p4_Cur = "CNY";//交易币种，固定值CNY
		String p5_Pid = "";//商品名称
		String p6_Pcat = "";//商品种类
		String p7_Pdesc = "";//商品描述
		String p8_Url = props.getProperty("p8_Url");//在支付成功后，易宝会访问这个地址。
		String p9_SAF = "";//送货地址
		String pa_MP = "";//扩展信息
		String pd_FrpId = req.getParameter("yh");//支付通道
		String pr_NeedResponse = "1";//应答机制，固定值1
		
		/*
		 * 2. 计算hmac
		 * 需要13个参数
		 * 需要keyValue
		 * 需要加密算法
		 */
		String keyValue = props.getProperty("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);
		
		/*
		 * 3. 重定向到易宝的支付网关
		 */
		StringBuilder sb = new StringBuilder("https://www.yeepay.com/app-merchant-proxy/node");
		sb.append("?").append("p0_Cmd=").append(p0_Cmd);
		sb.append("&").append("p1_MerId=").append(p1_MerId);
		sb.append("&").append("p2_Order=").append(p2_Order);
		sb.append("&").append("p3_Amt=").append(p3_Amt);
		sb.append("&").append("p4_Cur=").append(p4_Cur);
		sb.append("&").append("p5_Pid=").append(p5_Pid);
		sb.append("&").append("p6_Pcat=").append(p6_Pcat);
		sb.append("&").append("p7_Pdesc=").append(p7_Pdesc);
		sb.append("&").append("p8_Url=").append(p8_Url);
		sb.append("&").append("p9_SAF=").append(p9_SAF);
		sb.append("&").append("pa_MP=").append(pa_MP);
		sb.append("&").append("pd_FrpId=").append(pd_FrpId);
		sb.append("&").append("pr_NeedResponse=").append(pr_NeedResponse);
		sb.append("&").append("hmac=").append(hmac);
		
		resp.sendRedirect(sb.toString());
		return null;
	}
	
	/**
	 * 回馈方法
	 * 当支付成功时，易宝会访问这里
	 * 用两种方法访问：
	 * 1. 引导用户的浏览器重定向(如果用户关闭了浏览器，就不能访问这里了)
	 * 2. 易宝的服务器会使用点对点通讯的方法访问这个方法。（必须回馈success，不然易宝服务器会一直调用这个方法）
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String back(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获取12个参数
		 */
		String p1_MerId = req.getParameter("p1_MerId");
		String r0_Cmd = req.getParameter("r0_Cmd");
		String r1_Code = req.getParameter("r1_Code");
		String r2_TrxId = req.getParameter("r2_TrxId");
		String r3_Amt = req.getParameter("r3_Amt");
		String r4_Cur = req.getParameter("r4_Cur");
		String r5_Pid = req.getParameter("r5_Pid");
		String r6_Order = req.getParameter("r6_Order");
		String r7_Uid = req.getParameter("r7_Uid");
		String r8_MP = req.getParameter("r8_MP");
		String r9_BType = req.getParameter("r9_BType");
		String hmac = req.getParameter("hmac");
		/*
		 * 2. 获取keyValue
		 */
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader().getResourceAsStream("payment.properties"));
		String keyValue = props.getProperty("keyValue");
		/*
		 * 3. 调用PaymentUtil的校验方法来校验调用者的身份
		 *   >如果校验失败：保存错误信息，转发到msg.jsp
		 *   >如果校验通过：
		 *     * 判断访问的方法是重定向还是点对点，如果要是重定向
		 *     修改订单状态，保存成功信息，转发到msg.jsp
		 *     * 如果是点对点：修改订单状态，返回success
		 */
		boolean bool = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId,
				r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType,
				keyValue);
		if(!bool) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "无效的签名，支付失败！（你不是好人）");
			return "f:/jsps/msg.jsp";
		}
		if(r1_Code.equals("1")) {
			service.updateStatus(r6_Order, 2);
			if(r9_BType.equals("1")) {
				req.setAttribute("code", "success");
				req.setAttribute("msg", "恭喜，支付成功！");
				return "f:/jsps/msg.jsp";				
			} else if(r9_BType.equals("2")) {
				resp.getWriter().print("success");
			}
		}
		return null;
	}
	  

}
