package com.yang.goods.cart.service;

import java.sql.SQLException;
import java.util.List;

import com.yang.goods.cart.dao.CartItemDao;
import com.yang.goods.cart.domain.CartItem;

public class CartItemService {
	private CartItemDao dao = new CartItemDao();
	//通过user查找购物车条目
	public List<CartItem> findByUser(String uid){
		try {
			return dao.findByUser(uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/*add(CartItem)
	 * 1.调用dao#findByUidAndBid(String,String)根据uid和bid查找购物车条目，返回CartItem
	 * 2.如果CartItem不为空，调用dao#updateQuantity(String cartItemId,int quantity)把表单数量加入CartItem
	 * 3.反之，调用dao#addCartItem(CartItem cartitem)
	 */
	public void add(CartItem cartitem){
		try {
			CartItem ci = dao.findByUidAndBid(cartitem.getUser().getUid(), cartitem.getBook().getBid());
			if(ci.getCartItemId() != null){
				int quantity = ci.getQuantity() + cartitem.getQuantity();
				dao.updateQuantity(ci.getCartItemId(), quantity);
			}else{
				dao.addCartItem(cartitem);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//批量删除
	public void batchDelete(String cartItemIds){
		try {
			dao.batchDelete(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//修改条目数量
	public CartItem updateQuantity(String id,int quantity){
		try {
			dao.updateQuantity(id, quantity);
			return dao.findByCartItemId(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//查询所有被勾选条目
	public List<CartItem> loadCartItems(String cartItemIds){
		try {
			return dao.loadCartItems(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
