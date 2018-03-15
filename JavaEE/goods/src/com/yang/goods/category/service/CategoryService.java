package com.yang.goods.category.service;

import java.sql.SQLException;
import java.util.List;

import com.yang.goods.category.dao.CategoryDao;
import com.yang.goods.category.domain.Category;

public class CategoryService {
	private CategoryDao dao = new CategoryDao();
	//调用CategoryDao#findAll
	public List<Category> findAll(){
		try {
			return dao.findAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/*
	 * 添加分类
	 */
	public void add(Category category){
		try {
			dao.add(category);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/*
	 * 修改分类
	 */
	public void update(Category category){
		try {
			dao.update(category);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public void delete(String cid) {
		try {
			dao.delete(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	//通过pid查找子分类
	public List<Category> findChildren(String pid){
		try {
			return dao.findByParent(pid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//通过cid查找分类
	public Category findCategory(String cid){
		try {
			return dao.findCategory(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
