package com.yang.goods.user.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yang.goods.user.domain.User;
import com.yang.goods.user.service.UserService;
import com.yang.goods.user.service.exception.UserException;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class UserServlet extends BaseServlet {

	private UserService userservice = new UserService();

	// ajax校验注册用户名是否已存在，调用service层方法
	public void validateLoginname(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		String loginname = req.getParameter("loginname");
		try {
			boolean b = userservice.ajaxValidateLoginname(loginname);// 如果用户名不存在，返回true;反之返回false
			resp.getWriter().print(b + "");

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ajax校验注册邮箱是否已存在，调用service层方法
	public void validateEmail(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		try {
			boolean b = userservice.ajaxValidateEmail(email);// 如果邮箱不存在，返回true;反之返回false
			resp.getWriter().print(b + "");

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	//ajax校验验证码，在session中获取正确校验码
	public void validateVerifyCode(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException,IOException{
		String verifyCode = req.getParameter("verifyCode");
		String vCode = (String) req.getSession().getAttribute("vCode");
		boolean b = verifyCode.equalsIgnoreCase(vCode);
		resp.getWriter().print(b + "");
	}
	
	//ajax校验登录密码，用于修改密码校验
	public void validateLoginpass(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException,IOException{
		//得到session中的user
		User user = (User) req.getSession().getAttribute("user");
		//得到表单中的密码
		String loginpass = req.getParameter("loginpass");
		//比较
		boolean b = loginpass.equalsIgnoreCase(user.getLoginpass());
		resp.getWriter().print(b);
	}
	//表单提交后，调用register()方法
	public void register(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException,IOException{
		//1.将表单数据存入User中：调用CommonUtils#toBean方法
		User user = CommonUtils.toBean(req.getParameterMap(), User.class);
		//2.对User中数据进行服务器端校验，把错误信息存入Map中，并把Map和User存入session
		Map errors = validateRegist(user, req);
		if(errors != null && errors.size() > 0){//3.如果校验失败，跳回注册表单，回显失败原因和User数据
			req.setAttribute("errors", errors);
			req.setAttribute("user", user);
			req.getRequestDispatcher("/jsps/user/register.jsp").forward(req, resp);
			return;
		}
		//4.校验成功，调用UserService的register(User)方法
		try {
			userservice.register(user);
		} catch (SQLException e) {	
			//5.如果UserService#register(User)失败，跳到msg.jsp显示友好错误消息
			req.setAttribute("msg", "对不起，注册失败！");
			req.setAttribute("code", "error");
			req.getRequestDispatcher("/jsps/msg.jsp").forward(req, resp);
			return;
		}
	
		//6.注册成功，跳到msg.jsp显示成功消息
		req.setAttribute("msg", "恭喜您，注册成功！");
		req.setAttribute("code", "success");
		req.getRequestDispatcher("/jsps/msg.jsp").forward(req, resp);
		
	}

	/*login方法
	 * 1.把表单数据存到User(formbean)
	 * 2.校验表单数据，若存在错误信息，通过req回显到login.jsp
	 * 3.若校验通过，调用UserService#login
	 * 4.若返回User为空，说明不存在，将错误信息存进req中，回显到login.jsp
	 * 5.若status=false，说明未激活，将错误信息存进req，回显到login.jsp
	 * 6.登录成功，保存User到session，将用户名存入cookie,重定向到首页
	 */
	public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
		User formbean = CommonUtils.toBean(req.getParameterMap(), User.class);
		Map errors = validateLogin(formbean, req);
		if(errors.size() > 0){
			req.setAttribute("errors", errors);
			req.setAttribute("formbean", formbean);
			req.getRequestDispatcher("/jsps/user/login.jsp").forward(req, resp);
			return;
		}
		User user = userservice.login(formbean);
		if(user == null){
			req.setAttribute("msg","用户名或密码错误！");
			req.setAttribute("formbean", formbean);
			req.getRequestDispatcher("/jsps/user/login.jsp").forward(req, resp);
			return;
		}
		if(!user.isStatus()){
			req.setAttribute("msg","您还没有激活，请立即前往邮箱激活！");
			req.setAttribute("formbean", formbean);
			req.getRequestDispatcher("/jsps/user/login.jsp").forward(req, resp);
			return;
		}
		req.getSession().setAttribute("user", user);
		//获取用户名，保存到cookie
		String loginname = formbean.getLoginname();
		loginname = URLEncoder.encode(loginname, "utf-8");
		Cookie cookie = new Cookie("loginname", loginname);
		cookie.setMaxAge(1000 * 60 * 60 * 24 * 10);//保存十天
		resp.addCookie(cookie);
		//重定向到首页
		resp.sendRedirect(req.getContextPath() + "/index.jsp");
	}
	
	//退出登录
	public void quit(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
		req.getSession().removeAttribute("user");
		//重定向到登录页面
		resp.sendRedirect(req.getContextPath() + "/jsps/user/login.jsp");
	}
	
	/*修改密码updatePassword()
	 * 1.把表单数据存进formbean
	 * 2.后台表单校验,若有错误信息，则回显到pwd.jsp
	 * 3.通过session得到uid,调用UserService#updatePassword方法修改密码
	 * 4.修改成功，移除session,将成功信息保存到req并转发到msg.jsp
	 */
	public void updatePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		Map errors = validateUpdatepass(formUser, req);
		if(errors.size() > 0){
			req.setAttribute("errors", errors);
			req.setAttribute("formUser", formUser);
			req.getRequestDispatcher("/jsps/user/pwd.jsp").forward(req, resp);
			return;
		}
		User user = (User)req.getSession().getAttribute("user");
		userservice.updatePassword(user.getUid(), formUser.getNewpass());
		req.getSession().removeAttribute("user");
		req.setAttribute("code", "success");
		req.setAttribute("msg", "修改密码成功！请重新登陆");
		req.getRequestDispatcher("/jsps/msg.jsp").forward(req, resp);
	}
	
	//邮件点击激活后，处理激活
	//实现邮箱激活功能：用户在自己邮箱中点击激活邮件的链接后跳转到UserServlet调用此方法
	public void activation(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException,IOException{
		//1.获取req带过来的激活码
		String code = (String) req.getParameter("activationCode");
		//2.调用UserService#findByActivationCode方法,若抛出UserException异常，把异常信息保存到req,转发到msg.jsp显示
		try {
			userservice.activation(code);
			//3.转发到msg.jsp显示激活成功
			req.setAttribute("msg", "恭喜您，激活成功！请登录");
			req.setAttribute("code","success");
		} catch (UserException e) {
			req.setAttribute("msg", e.getMessage());
			req.setAttribute("code", "error");
		}
		req.getRequestDispatcher("/jsps/msg.jsp").forward(req, resp);
	}
	
	
	//注册表单后台验证
	public Map validateRegist(User user,HttpServletRequest req) {
		Map errors = new HashMap<String, String>();
		//1.用户名校验：不能为空、长度3-20、是否已存在
		String loginname = user.getLoginname();
		if(loginname == null ||loginname.trim().equals("")){
			errors.put("loginnameError", "用户名不能为空！");
		}else if(loginname.length() < 3 || loginname.length() >20){
			errors.put("loginnameError","用户名长度必须在3-20之间！");
		}else{
			try {
				if(!new UserService().ajaxValidateLoginname(loginname)){
					errors.put("loginname", "用户名已被注册！");
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		//2.密码校验：不能为空，长度3-20
		String loginpass = user.getLoginpass();
		if(loginpass == null || loginpass.trim().equals("")){
			errors.put("loginpassError", "密码不能为空！");
		}else if(loginpass.length() < 3 || loginpass.length() >20){
			errors.put("loginpassError","密码长度必须在3-20之间！");
		}
		
		//3.确认密码校验:不能为空、不区分大小写与密码一致
		String reloginpass = user.getReloginpass();
		if(reloginpass == null || reloginpass.trim().equals("")){
			errors.put("reloginpassError", "密码不能为空！");
		}else if(!reloginpass.equalsIgnoreCase(loginpass)){
			errors.put("reloginpassError","密码必须一致！");
		}
		
		//4.邮箱校验:不能为空、格式正确、是否已存在
		String email = user.getEmail();
		if(email == null || email.trim().equals("")){
			errors.put("emailError", "邮箱不能为空！");
		}else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")){
			errors.put("emailError","邮箱格式错误！");
		}else{
			try {
				if(!new UserService().ajaxValidateEmail(email)){
					errors.put("emailError","邮箱已被注册！");
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		//5.验证码校验：不能为空、正确
		String verifyCode = user.getVerifyCode();
		if(verifyCode == null || verifyCode.trim().equals("")){
			errors.put("verifyCodeError", "验证码不能为空！");
		}else {
			if(!verifyCode.equalsIgnoreCase((String) req.getSession().getAttribute("vCode"))){
				errors.put("verifyCodeError", "验证码错误！");
			}
		}
		return errors;
	}
	
	//登录表单后台校验
	public Map validateLogin(User user,HttpServletRequest req) {
		Map errors = new HashMap<String, String>();
		//1.用户名校验，不能为空、长度3-20
		String loginname = user.getLoginname();
		if(loginname == null ||loginname.trim().equals("")){
			errors.put("loginnameError", "用户名不能为空！");
		}else if(loginname.length() < 3 || loginname.length() >20){
			errors.put("loginnameError","用户名长度必须在3-20之间！");
		}
		//2.密码校验，不能为空、长度3-20
		String loginpass = user.getLoginpass();
		if(loginpass == null || loginpass.trim().equals("")){
			errors.put("loginpassError", "密码不能为空！");
		}else if(loginpass.length() < 3 || loginpass.length() >20){
			errors.put("loginpassError","密码长度必须在3-20之间！");
		}
		//3.验证码校验：不能为空、正确
		String verifyCode = user.getVerifyCode();
		if(verifyCode == null || verifyCode.trim().equals("")){
			errors.put("verifyCodeError", "验证码不能为空！");
		}else {
			if(!verifyCode.equalsIgnoreCase((String) req.getSession().getAttribute("vCode"))){
				errors.put("verifyCodeError", "验证码错误！");
			}
		}
		return errors;
	}
	//修改密码表单后台校验
	public Map validateUpdatepass(User user,HttpServletRequest req) {
		Map errors = new HashMap<String, String>();
		
		//密码校验，不能为空、长度3-20
		String loginpass = user.getLoginpass();
		if(loginpass == null || loginpass.trim().equals("")){
			errors.put("loginpassError", "密码不能为空！");
		}else if(loginpass.length() < 3 || loginpass.length() >20){
			errors.put("loginpassError","密码长度必须在3-20之间！");
		}else{
			User sessionuser = (User) req.getSession().getAttribute("user");
			if(!loginpass.equalsIgnoreCase(sessionuser.getLoginpass())){
				errors.put("loginpassError","原密码错误！");
			}
		}
		//新密码校验
		String newpass = user.getNewpass();
		if(newpass == null || newpass.trim().equals("")){
			errors.put("newpassError", "新密码不能为空！");
		}else if(newpass.length() < 3 || newpass.length() >20){
			errors.put("newpassError","新密码长度必须在3-20之间！");
		}
		//确认密码校验
		String reloginpass = user.getReloginpass();
		if(reloginpass == null || reloginpass.trim().equals("")){
			errors.put("reloginpassError", "确认密码不能为空！");
		}else if(!reloginpass.equalsIgnoreCase(newpass)){
			errors.put("reloginpassError","确认密码必须一致！");
		}
		//验证码校验：不能为空、正确
		String verifyCode = user.getVerifyCode();
		if(verifyCode == null || verifyCode.trim().equals("")){
			errors.put("verifyCodeError", "验证码不能为空！");
		}else {
			if(!verifyCode.equalsIgnoreCase((String) req.getSession().getAttribute("vCode"))){
				errors.put("verifyCodeError", "验证码错误！");
			}
		}
		
		return errors;
	}

}

