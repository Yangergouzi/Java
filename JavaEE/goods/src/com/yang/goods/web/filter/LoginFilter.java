package com.yang.goods.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.yang.goods.user.domain.User;

/**登录过滤器
 * 1.从session获取user
 * 2.若user为空，保存错误信息，转发到/jsps/msg.jsp
 * 3.若不为空，放行
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {

    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute("user");
		if(user == null){
			req.setAttribute("code", "error");
			req.setAttribute("msg", "对不起，您还没有登录，请先登录！");
			req.getRequestDispatcher("/jsps/msg.jsp").forward(req, response);
		}else{
			chain.doFilter(request, response);//放行
		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
