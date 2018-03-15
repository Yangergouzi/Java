package com.yang.goods.cart.domain;

import java.math.BigDecimal;

import org.apache.commons.dbutils.QueryRunner;

import com.yang.goods.Book.domain.Book;
import com.yang.goods.user.domain.User;

import cn.itcast.jdbc.TxQueryRunner;

public class CartItem {
	private String cartItemId;
	private int quantity;
	private Book book;
	private User user;
	
	//购物车条目小计
	public Double getSubTotal(){
		BigDecimal b1 = new BigDecimal(book.getCurrPrice() + "");
		BigDecimal b2 = new BigDecimal(quantity + "");
		BigDecimal b3 = b1.multiply(b2);
		return b3.doubleValue();
	}
	
	public String getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartItem(String cartItemId, int quantity, Book book, User user) {
		super();
		this.cartItemId = cartItemId;
		this.quantity = quantity;
		this.book = book;
		this.user = user;
	}
	
}
