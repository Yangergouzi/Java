package com.yang.goods.category.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yang.goods.category.domain.Category;
import com.yang.goods.category.service.CategoryService;

import cn.itcast.servlet.BaseServlet;

public class CategoryServlet extends BaseServlet {

	private CategoryService service = new CategoryService();
	
	/*查询所有分类
	 * 1.调用Service#findAll，得到所有分类
	 * 2.保存并转发到left.jsp
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Category> parents = service.findAll();
		req.setAttribute("parents", parents);
		return "f:/jsps/left.jsp";
	}

}
