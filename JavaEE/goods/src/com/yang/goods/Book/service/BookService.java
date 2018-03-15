package com.yang.goods.Book.service;

import java.sql.SQLException;

import com.yang.goods.Book.dao.BookDao;
import com.yang.goods.Book.domain.Book;
import com.yang.goods.pager.PageBean;

public class BookService {
	private BookDao dao = new BookDao();
	
	//按分类查询
	public PageBean<Book> findByCategory(String cid,int pc){
		try {
			return dao.findByCategory(cid, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//按作者查询
	public PageBean<Book> findByAuthor(String author,int pc){
		try {
			return dao.findByAuthor(author, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//按出版社查询
	public PageBean<Book> findByPress(String press,int pc){
		try {
			return dao.findByPress(press, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//按书名模糊查询
	public PageBean<Book> findByBname(String bname,int pc){
		try {
			return dao.findByBname(bname, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//按id查询
		public Book findByBid(String bid){
			try {
				return dao.findByBid(bid);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	//多条件组合查询
	public PageBean<Book> findByCombination(Book criteria,int pc){
		try {
			return dao.findByCombination(criteria, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//添加图书（后台用）
	public void add(Book book){
		try {
			dao.add(book);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//修改图书（后台用）
	public void edit(Book book){
		try {
			dao.edit(book);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//删除图书（后台用）
	public void delete(String bid){
		try {
			dao.delete(bid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
