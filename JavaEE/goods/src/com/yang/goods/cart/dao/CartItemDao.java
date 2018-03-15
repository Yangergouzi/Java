package com.yang.goods.cart.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.yang.goods.Book.domain.Book;
import com.yang.goods.cart.domain.CartItem;
import com.yang.goods.user.domain.User;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class CartItemDao {
	private QueryRunner qr = new TxQueryRunner();
	
	//将一个Map映射为一个CartItem
	public CartItem toCartItem(Map<String,Object> map){
		CartItem ci = CommonUtils.toBean(map, CartItem.class);
		User u = CommonUtils.toBean(map, User.class);
		Book b = CommonUtils.toBean(map, Book.class);
		ci.setBook(b);
		ci.setUser(u);
		return ci;
	}
	//将List<Map>映射为List<CartItem>
	public List<CartItem> toCartItemList(List<Map<String,Object>> maplist){
		List<CartItem> ciList = new ArrayList<CartItem>();
		for(Map<String,Object> map:maplist){
			CartItem ci = toCartItem(map);
			ciList.add(ci);
		}
		return ciList;
	}
	
	
	/*
	 * 通过uid查找购物车条目
	 * 1.使用多表查询(t_cartitem,t_book)得到List<Map>
	 * 2.对每个Map映射
	 */
	public List<CartItem> findByUser(String uid) throws SQLException {
		String sql = "select * from t_cartitem c,t_book b where c.bid = b.bid and uid = ? order by c.orderBy ";
		List<Map<String,Object>> maplist = qr.query(sql, new MapListHandler(), uid);
		return toCartItemList(maplist);
	}
	
	//通过uid和bid查找购物车条目
	public CartItem findByUidAndBid(String uid,String bid) throws SQLException{
		String sql = "select * from t_cartitem where uid = ? and bid = ?";
		Map<String,Object> map = qr.query(sql, new MapHandler(), uid,bid);
		return toCartItem(map);
	}
	/*
	 * 通过cartItemId查找购物车条目
	 * 	需要多表查询，因为要得到该条目内书的价格来计算小计
	 */
	public CartItem findByCartItemId(String id) throws SQLException {
		String sql = "select * from t_cartitem c,t_book b where c.bid = b.bid and c.cartItemId = ? order by c.orderBy ";
		Map map = qr.query(sql, new MapHandler(), id);
		return toCartItem(map);
	}
	//修改购物车条目的数量
	public void updateQuantity(String cartItemId,int quantity) throws SQLException{
		String sql = "update t_cartitem set quantity = ? where cartItemId = ?";
		qr.update(sql, quantity,cartItemId);
	}
	//添加购物车条目
	public void addCartItem(CartItem cartitem) throws SQLException{
		Object[] params = {cartitem.getCartItemId(),cartitem.getQuantity(),cartitem.getBook().getBid(),
				cartitem.getUser().getUid()};
		String sql = "insert into t_cartitem(cartItemId, quantity, bid, uid) values(?,?,?,?)";
		qr.update(sql, params);	
	}
	
	//生成where子句方法
	public String toWhereSql(int len){
		StringBuilder sb = new StringBuilder("cartItemId in (");
		for(int i = 0;i < len;i++){
			sb.append("?");
			if(i < len -1){
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
	//批量删除
	public void batchDelete(String cartItemIds) throws SQLException {
		// 把cartItemIds分割成数组
		Object[] idArray = cartItemIds.split(",");
		String whereSql = toWhereSql(idArray.length);
		String sql = "delete from t_cartitem where " + whereSql;
		qr.update(sql, idArray);//idArray必须是Object类型数组！
	}
	//查询所有被勾选条目
	public List<CartItem> loadCartItems(String cartItemIds) throws SQLException {
		//把cartItemIds分割成Object数组
		Object[] idArray = cartItemIds.split(",");
		String whereSql = toWhereSql(idArray.length);
		String sql = "select * from t_cartitem c,t_book b where b.bid = c.bid and " + whereSql;
		List<Map<String,Object>> maplist = qr.query(sql, new MapListHandler(), idArray);
		return toCartItemList(maplist);
	}
}
