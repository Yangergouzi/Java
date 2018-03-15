package com.yang.goods.cart.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yang.goods.Book.domain.Book;
import com.yang.goods.cart.domain.CartItem;
import com.yang.goods.cart.service.CartItemService;
import com.yang.goods.user.domain.User;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class CartItemServlet extends BaseServlet {

	private CartItemService service = new CartItemService();
	
	/*
	 * 我的购物车
	 * 1.从session得到uid
	 * 2.调用service#findByUid，得到购物车条目列表
	 * 3.将购物车条目列表保存到req，转发到/jsps/cart/list.jsp
	 */
	public String myCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		List<CartItem> ciList = service.findByUser(user.getUid());
		request.setAttribute("cartItemList", ciList);
		return "f:/jsps/cart/list.jsp";
	}
	/*
	 * 添加购物车条目
	 * 1.从session获取user，
	 * 2.从表单获取book的bid，创建book对象，把bid设置到book
	 * 3.从表单获取数量
	 * 4.创建CartItem对象
	 * 5.调用service#addCartItem
	 * 6.调用myCart，查询出条目并转发到/jsps/cart/list.jsp
	 */
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		User user = (User) request.getSession().getAttribute("user");
		String bid = request.getParameter("bid");
		Book book = new Book();
		book.setBid(bid);
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		String cartItemId = CommonUtils.uuid();
		CartItem cartitem = new CartItem(cartItemId, quantity, book, user);
		
		service.add(cartitem);
		return myCart(request, response);

	}
	
	/*
	 * 批量删除
	 * 1.获取cartItemIds
	 * 2.调用service#batchDelete()
	 * 3.return myCart()
	 */
	public String batchDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String cartItemIds = request.getParameter("cartItemIds");
		service.batchDelete(cartItemIds);
		return myCart(request, response);
	}
	/*
	 * ajax修改条目数量
	 * */
	public void updateQuantity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String cartItemId = request.getParameter("cartItemId");
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		CartItem ci = service.updateQuantity(cartItemId, quantity);
		//返回json对象
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"quantity\"").append(":").append(ci.getQuantity());
		sb.append(",");
		sb.append("\"subtotal\"").append(":").append(ci.getSubTotal());
		sb.append("}");
		response.getWriter().print(sb);
	}
	/*
	 * 查询所有被勾选条目（用于结算页面显示）
	 * 1.得到cartItemIds和total，以及cartItemIds
	 * 2.调用service#loadCartItems(String)
	 * 3.得到List<CartItem>，保存
	 * 4.return "f:/jsps/cart/showitem.jsp"
	 * */
	public String loadCartItems(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String cartItemIds = request.getParameter("cartItemIds");
		String total = request.getParameter("total");
		List<CartItem> cartItems = service.loadCartItems(cartItemIds);
		request.setAttribute("cartItemList", cartItems);
		request.setAttribute("total", total);
		request.setAttribute("cartItemIds", cartItemIds);
		return "f:/jsps/cart/showitem.jsp";
	}
}
