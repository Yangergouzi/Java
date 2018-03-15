package com.yang.goods.order.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

import com.yang.goods.Book.domain.Book;
import com.yang.goods.order.domain.Order;
import com.yang.goods.order.domain.OrderItem;
import com.yang.goods.pager.Expression;
import com.yang.goods.pager.PageBean;
import com.yang.goods.pager.PageConstants;

public class OrderDao {
	private QueryRunner qr = new TxQueryRunner();
	/*
	 * 查找所有订单
	 */
	public PageBean findAll(int pc) throws SQLException{
		List<Expression> exprlist = new ArrayList<Expression>();
		return findByCriteria(exprlist, pc);
	}
	/*
	 * 通过status查找订单
	 */
	public PageBean findByStatus(String status,int pc) throws SQLException{
		List<Expression> exprlist = new ArrayList<Expression>();
		exprlist.add(new Expression("status", "=",status));
		return findByCriteria(exprlist, pc);
	}
	/*
	 * 通过用户查找订单，返回pagebean
	 */
	public PageBean fingByUser(String uid,int pc) throws SQLException{
		List<Expression> exprlist = new ArrayList<Expression>();
		exprlist.add(new Expression("uid", "=", uid));
		return findByCriteria(exprlist, pc);
	}
	
	/*
	 * 通用查询方法，返回一个PageBean
	 * 1.得到每页记录数ps
	 * 2.得到总记录数tr
	 * 3.得到beanList
	 * 4.创建PageBean
	 */
	public PageBean<Order> findByCriteria(List<Expression> exprList,int pc) throws SQLException{
		//得到ps
		int ps = PageConstants.ORDER_PAGE_SIZE;
		//通过exprList生成where子句
		List<Object> params = new ArrayList<Object>();
		StringBuilder whereSql = new StringBuilder("where 1=1");
		for(Expression expr : exprList){
			whereSql.append(" and ").append(expr.getName() + " ").append(expr.getOperator() + " ");
			if(!expr.getOperator().equals("is null")){
				whereSql.append("?").append(" ");
			}
			params.add(expr.getValue());
		}
		
		//得到tr
		String sql = "select count(*) from t_order " + whereSql;
		Number num = (Number) qr.query(sql, new ScalarHandler(),params.toArray());
		int tr = num.intValue();
		
		//得到beanList
		sql = "select * from t_order " + whereSql + " limit ?,?";
		params.add(ps * (pc-1));//当前页面首行记录的下标
		params.add(ps);//一个查询几行，即ps
		List<Order> orderList = qr.query(sql, new BeanListHandler<Order>(Order.class), params.toArray());
		
		//为每个order加载orderItems
		for(Order order : orderList){
			List<OrderItem> oiList = loadOrderItem(order.getOid());
			order.setOrderItemList(oiList);
		}
		
		//创建PageBean，传入参数
		PageBean<Order> pb = new PageBean<Order>();
		pb.setPageCurr(pc);
		pb.setPageSize(ps);
		pb.setTotalRec(tr);
		pb.setBeanList(orderList);

		return pb;
	}
	/*
	 * 为订单加载OrderItem
	 */
	public List<OrderItem> loadOrderItem(String oid) throws SQLException{
		String sql = "select * from t_orderitem o,t_book b where o.bid = b.bid and o.oid = ?";
		List<Map<String,Object>> maplist = qr.query(sql, new MapListHandler(), oid);
		return toOrderItemList(maplist);
	}
	
	//一个map到orderItem的映射
	public OrderItem toOrderItem(Map map){
		OrderItem oi = CommonUtils.toBean(map, OrderItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		Order order = CommonUtils.toBean(map, Order.class);
		oi.setBook(book);
		oi.setOrder(order);
		return oi;
	}
	//多个map到多个orderitem的映射
	public List<OrderItem> toOrderItemList(List<Map<String,Object>> maplist){
		List<OrderItem> oiList = new ArrayList<OrderItem>();
		for(Map<String,Object> map : maplist){
			oiList.add(toOrderItem(map));
		}
		return oiList;
	}
	
	//添加订单（分别添加order和它的orderItemList）
	public void add(Order order) throws SQLException{
		String sql = "insert into t_order(oid, ordertime, total, status,address,uid) values(?,?,?,?,?,?)";
		Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),
				order.getStatus(),order.getAddress(),order.getOwner().getUid()};
		qr.update(sql, params);
		//遍历orderItemList，将每个插入数据库
		sql = "insert into t_orderitem(orderItemId, quantity, subtotal, bid, bname, " +
						"currPrice, image_b, oid) values(?,?,?,?,?,?,?,?)";
		int len = order.getOrderItemList().size();
		Object[][] batchParams = new Object[len][];	
		OrderItem oItem = new OrderItem();
		for(int i = 0;i < len;i++){
			oItem = order.getOrderItemList().get(i);
			batchParams[i] = new Object[]{oItem.getOrderItemId(), oItem.getQuantity(), oItem.getSubtotal(),
					oItem.getBook().getBid(), oItem.getBook().getBname(), oItem.getBook().getCurrPrice(), 
					oItem.getBook().getImage_b(), oItem.getOrder().getOid()};
		}
		qr.batch(sql, batchParams);
	}

	/*
	 * 通过oid查找订单
	 */
	public Order findByOid(String oid) throws SQLException {
		String sql = "select * from t_order where oid = ?";
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
		//加载orderitems
		List<OrderItem> oiList = loadOrderItem(oid);
		order.setOrderItemList(oiList);
		return order;
	}
	/*
	 * 修改订单状态
	 */
	public void updateStatus(String oid, int status) throws SQLException {
		String sql = "update t_order set status = ? where oid = ?";
		qr.update(sql, status,oid);	
	}
	
}
