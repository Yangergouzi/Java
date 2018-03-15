package com.yang.admin.category.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import com.yang.goods.category.domain.Category;
import com.yang.goods.category.service.CategoryService;

public class AdminCategoryServlet extends BaseServlet {
	CategoryService service = new CategoryService();
	/*
	 * 查找所有分类，转发到/adminjsps/admin/category/list.jsp
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Category> parents = service.findAll();
		req.setAttribute("parents", parents);
		return "f:/adminjsps/admin/category/list.jsp";
	}
	/*
	 * 添加一级分类
	 */
	public String addOneLevel(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cname = req.getParameter("cname");
		String desc = req.getParameter("desc");
		Category category = new Category();
		category.setCid(CommonUtils.uuid());
		category.setCname(cname);
		category.setDesc(desc);
		service.add(category);
		return findAll(req, resp);
	}
	/*
	 * 添加子类准备
	 * 主要为了给jsp页面传递数据
	 */
	public String addTwoLevelPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pid = req.getParameter("pid");
		List<Category> parents = service.findAll();
		req.setAttribute("pid", pid);
		req.setAttribute("parents", parents);
		return "f:/adminjsps/admin/category/add2.jsp";
	}
	/*
	 * 添加二级分类
	 */
	public String addTwoLevel(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cname = req.getParameter("cname");
		String desc = req.getParameter("desc");
		String pid = req.getParameter("pid");
		Category category = new Category();
		category.setCid(CommonUtils.uuid());
		category.setCname(cname);
		category.setDesc(desc);
		Category parent = new Category();
		parent.setCid(pid);
		category.setParent(parent);
		service.add(category);
		return findAll(req, resp);
	}
	
	/*
	 * 修改一级分类准备
	 */
	public String updateOneLevelPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("cname", req.getParameter("cname"));
		req.setAttribute("desc", req.getParameter("desc"));
		req.setAttribute("cid", req.getParameter("cid"));
		return "f:/adminjsps/admin/category/edit.jsp";
	}
	/*
	 * 修改一级分类
	 */
	public String updateOneLevel(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cname = req.getParameter("cname");
		String desc = req.getParameter("desc");
		String cid = req.getParameter("cid");
		Category category = new Category();
		category.setCid(cid);
		category.setCname(cname);
		category.setDesc(desc);
		service.update(category);
		return findAll(req, resp);
	}
	/*
	 * 修改二级分类准备
	 */
	public String updateTwoLevelPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("cname", req.getParameter("cname"));
		req.setAttribute("desc", req.getParameter("desc"));
		req.setAttribute("cid", req.getParameter("cid"));
		req.setAttribute("pid", req.getParameter("pid"));
		List<Category> parents = service.findAll();
		req.setAttribute("parents", parents);
		return "f:/adminjsps/admin/category/edit2.jsp";
	}
	/*
	 * 修改二级分类
	 */
	public String updateTwoLevel(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cname = req.getParameter("cname");
		String desc = req.getParameter("desc");
		String cid = req.getParameter("cid");
		String pid = req.getParameter("pid");
		Category category = new Category();
		category.setCid(cid);
		category.setCname(cname);
		category.setDesc(desc);
		Category parent = new Category();
		parent.setCid(pid);
		category.setParent(parent);
		service.update(category);
		return findAll(req, resp);
	}
	/*
	 * 删除分类
	 */
	public String delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cid = req.getParameter("cid");
		service.delete(cid);
		return findAll(req, resp);
	}
}
