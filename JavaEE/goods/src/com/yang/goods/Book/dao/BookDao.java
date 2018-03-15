package com.yang.goods.Book.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.yang.goods.Book.domain.Book;
import com.yang.goods.category.domain.Category;
import com.yang.goods.order.domain.Order;
import com.yang.goods.pager.Expression;
import com.yang.goods.pager.PageBean;
import com.yang.goods.pager.PageConstants;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class BookDao {
	private QueryRunner qr = new TxQueryRunner();
	
	//按分类查询
	public PageBean<Book> findByCategory(String cid,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("cid","=",cid));
		return findByCriteria(exprList, pc);
	}
	//按书名模糊查询
	public PageBean<Book> findByBname(String bname,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("bname","like","%" + bname + "%"));
		return findByCriteria(exprList, pc);
	}
	//按作者查询
	public PageBean<Book> findByAuthor(String author,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("author","=",author));
		return findByCriteria(exprList, pc);
	}
	//按出版社查询
	public PageBean<Book> findByPress(String press,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("press","=",press));
		return findByCriteria(exprList, pc);
	}
	//多条件模糊查询（书名，作者，出版社）
	public PageBean<Book> findByCombination(Book criteria,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("bname","like","%" + criteria.getBname() + "%"));
		exprList.add(new Expression("author","like","%" + criteria.getAuthor() + "%"));
		exprList.add(new Expression("press","like","%" + criteria.getPress() + "%"));
		return findByCriteria(exprList, pc);
	}
	//按id查询
		public Book findByBid(String bid) throws SQLException{
			String sql = "select * from t_book where bid = ?";
			Map<String,Object> map = qr.query(sql, new MapHandler(), bid);
			//把map中除cid外的其他部分映射到book中
			Book book = CommonUtils.toBean(map, Book.class);
			//把map中的cid映射到一个category中，此category只有这一个参数
			Category category = CommonUtils.toBean(map, Category.class);
			//把category加入book
			book.setCategory(category);
			return book;
		}
	/*
	 * 通用查询方法，返回一个PageBean
	 * 1.得到每页记录数ps
	 * 2.得到总记录数tr
	 * 3.得到beanList
	 * 4.创建PageBean
	 */
	public PageBean<Book> findByCriteria(List<Expression> exprList,int pc) throws SQLException{
		//得到ps
		int ps = PageConstants.BOOK_PAGE_SIZE;
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
		String sql = "select count(*) from t_book " + whereSql;
		Number num = (Number) qr.query(sql, new ScalarHandler(),params.toArray());
		int tr = num.intValue();
		
		//得到beanList
		sql = "select * from t_book " + whereSql + "order by orderBy limit ?,?";
		params.add(ps * (pc-1));//当前页面首行记录的下标
		params.add(ps);//一个查询几行，即ps
		List<Book> bookList = qr.query(sql, new BeanListHandler<Book>(Book.class), params.toArray());
		
		//创建PageBean，传入参数
		PageBean<Book> pb = new PageBean<Book>();
		pb.setPageCurr(pc);
		pb.setPageSize(ps);
		pb.setTotalRec(tr);
		pb.setBeanList(bookList);

		return pb;
	}
	//添加图书
	public void add(Book book) throws SQLException {
		String sql = "insert into t_book (bid,bname,author,price,currPrice,discount,press,publishtime,edition,pageNum," +
				"wordNum,printtime,booksize,paper,cid,image_w,image_b) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params={book.getBid(), book.getBname(), book.getAuthor(), book.getPrice(), book.getCurrPrice(), book.getDiscount(),
				book.getPress(), book.getPublishtime(),book.getEdition(), book.getPageNum(), book.getWordNum(), book.getPrinttime(),
				book.getBooksize(), book.getPaper(), book.getCategory().getCid(), book.getImage_w(), book.getImage_b()};
		qr.update(sql, params);
		
	}
	//修改图书
	public void edit(Book book) throws SQLException {
		String sql = "update t_book set bname=?,author=?,price=?,currPrice=?,discount=?,press=?,publishtime=?,edition=?,pageNum=?," +
				"wordNum=?,printtime=?,booksize=?,paper=?,cid=?  where bid = ?";
		Object[] params={book.getBname(), book.getAuthor(), book.getPrice(), book.getCurrPrice(), book.getDiscount(),
				book.getPress(), book.getPublishtime(),book.getEdition(), book.getPageNum(), book.getWordNum(), book.getPrinttime(),
				book.getBooksize(), book.getPaper(), book.getCategory().getCid(), book.getBid()};
		qr.update(sql, params);
		
	}
	//删除图书
	public void delete(String bid) throws SQLException {
		String sql = "delete from t_book where bid = ?";
		qr.update(sql, bid);
	}
	
}
