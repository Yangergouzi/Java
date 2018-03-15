package com.yang.goods.order.service;

import java.sql.SQLException;

import com.yang.goods.order.dao.OrderDao;
import com.yang.goods.order.domain.Order;
import com.yang.goods.pager.PageBean;

public class OrderService {
	private OrderDao dao = new OrderDao();
	/*
	 * 查找所有订单（后台）
	 */
	public PageBean findAll(int pc){
		try {
			return dao.findAll(pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/*
	 * 通过状态查订单（后台）
	 */
	public PageBean findByStatus(String status,int pc){
		try {
			return dao.findByStatus(status, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/*
	 * 我的订单
	 */
	public PageBean myOrders(String uid,int pc){
		try {
			return dao.fingByUser(uid, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/*
	 * 添加订单
	 */

	public void add(Order order) {
		try {
			dao.add(order);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/*
	 * 通过oid查询order
	 */
	public Order load(String oid) {
		try {
			return dao.findByOid(oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/*
	 * 修改订单状态
	 */
	public void updateStatus(String oid, int status) {
		try {
			dao.updateStatus(oid,status);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
