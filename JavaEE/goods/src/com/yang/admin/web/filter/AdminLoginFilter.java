package com.yang.admin.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.yang.admin.admin.domain.Admin;

/**
 * Servlet Filter implementation class AdminLoginFilter
 */
public class AdminLoginFilter implements Filter {

	/**管理员登录过滤器
	 * 1.从session获取admin
	 * 2.若admin为空，保存错误信息，转发到/adminjsps/msg.jsp
	 * 3.若不为空，放行
	 * Servlet Filter implementation class LoginFilter
	 */
    public AdminLoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		Admin admin = (Admin) req.getSession().getAttribute("admin");
		if(admin == null){
			req.setAttribute("msg", "对不起，请先登录管理员！");
			req.getRequestDispatcher("/adminjsps/msg.jsp").forward(req, response);
			return;
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
