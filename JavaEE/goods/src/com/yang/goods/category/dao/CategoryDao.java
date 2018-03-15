package com.yang.goods.category.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.yang.goods.category.domain.Category;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class CategoryDao {
	private QueryRunner qr = new TxQueryRunner();
	
	//把一个Map中的数据映射到一个Category中
	public Category toCategory(Map map){
		/*
		 * map{cid,cname,pid,desc,orderby}
		 * Category{cid,cname,parent,children}
		 */
		Category c = CommonUtils.toBean(map, Category.class);
		//如果map的pid!=null,新建一个parent分类，把map的pid赋值给parent.cid，再把parent赋给c.parent
		String mapPid = (String) map.get("pid");
		if(mapPid != null){
			Category parent = new Category();
			parent.setCid(mapPid);
			c.setParent(parent);
		}
		return c;	
	}
	//把多个Map(List<Map<String,Object>>)映射到多个Category(List<Category>)
	public List<Category> toListCategory(List<Map<String,Object>> mapList){
		List<Category> categoryList = new ArrayList<Category>();
		for(Map<String,Object> map:mapList){
			Category c = toCategory(map);
			categoryList.add(c);
		}
		return categoryList;
	}
	
	
	//finAll
	public List<Category> findAll() throws SQLException{
		//查询所有一级分类
		String sql = "select * from t_category where pid is null";
		List<Map<String, Object>> maplist = qr.query(sql, new MapListHandler());
		List<Category> categorylist = toListCategory(maplist);
		//循环遍历所有一级分类，为每个一级分类加载它的二级分类
		for(Category parent:categorylist){
			List<Category> childrenlist = findByParent(parent.getCid());
			parent.setChildren(childrenlist);
		}
		return categorylist;
	}
	
	//通过父分类查找子分类
	public List<Category> findByParent(String pid) throws SQLException{
		String sql = "select * from t_category where pid = ?";
		List<Map<String,Object>> maplist = qr.query(sql, new MapListHandler(),pid);
		List<Category> categorylist = toListCategory(maplist);
		return categorylist;
	}
	//通过cid查找分类
		public Category findCategory(String cid) throws SQLException{
			String sql = "select * from t_category where cid = ?";
			Map<String,Object> map = qr.query(sql, new MapHandler(),cid);
			Category category = toCategory(map);
			return category;
		}
	
	/*
	 * 添加分类
	 */
	public void add(Category category) throws SQLException{
		String sql = "insert into t_category(cid,cname,pid,`desc`) values(?,?,?,?)";
		String pid = null;
		if(category.getParent() != null){
			pid = category.getParent().getCid();
		}
		Object[] params = {category.getCid(), category.getCname(), pid, category.getDesc()};
		qr.update(sql,params);
	}
	/*
	 * 修改分类
	 */
	public void update(Category category) throws SQLException{
		String sql = "update t_category set cname = ?,pid = ?,`desc` = ? where cid = ?";
		String pid = null;
		if(category.getParent() != null){
			pid = category.getParent().getCid();
		}
		Object[] params = { category.getCname(), pid, category.getDesc(), category.getCid()};
		qr.update(sql,params);
	}
	/*
	 * 删除分类
	 */
	public void delete(String cid) throws SQLException {
		String sql = "delete from t_category where cid = ?";
		qr.update(sql, cid);
	}
	
}
