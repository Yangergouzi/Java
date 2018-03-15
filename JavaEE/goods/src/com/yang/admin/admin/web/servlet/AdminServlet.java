package com.yang.admin.admin.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yang.admin.admin.domain.Admin;
import com.yang.admin.admin.service.AdminService;

import cn.itcast.servlet.BaseServlet;

public class AdminServlet extends BaseServlet {
	AdminService service = new AdminService();
	/*
	 * 管理员登录
	 * 1.获取表单adminname和admainpwd
	 * 2.调用service#findByNameAndPwd
	 * 3.若admin为空,保存错误信息，转发/adminjsps/msg.jsp
	 * 4.否则，保存admin到session，转发或重定向到/adminjsps/admin/index.jsp
	 */
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 String adminname = request.getParameter("adminname");
		 String adminpwd = request.getParameter("adminpwd");
		 Admin admin = service.findByNameAndPwd(adminname,adminpwd);
		 if(admin == null){
			 request.setAttribute("msg", "管理员登录名或密码错误！");
			 return "f:/adminjsps/msg.jsp";
		 }
		 request.getSession().setAttribute("admin", admin);
		 return "f:/adminjsps/admin/index.jsp";
	}
	/*
	 * 管理员退出
	 */
	public String logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute("admin");
		return "f:/adminjsps/login.jsp";
	}

}
