package com.yang.crm.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.yang.crm.entity.User;
import com.yang.crm.service.UserService;

public class UserAction extends ActionSupport implements ModelDriven<User>{
	private UserService userService = null;
	private User user = new User();
	HttpServletRequest req = ServletActionContext.getRequest();
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Override
	public User getModel() {
		return user;
	}
	
	public String login(){
		User userExist = userService.login(user);
		if(userExist != null){
			req.getSession().setAttribute("user", userExist);
			return "loginsuccess";
		}else{		
			return "login";
		}
	}
	public String exit(){
		req.getSession().removeAttribute("user");
		return "exit";
	}
	public String toUpdatePwdPage(){		
		return "toUpdatePwdPage";
	}
	public String updatePwd(){		
		Map errors = validateUpdatePwd();
		if(!errors.isEmpty()){
			req.setAttribute("errors", errors);
			return "toUpdatePwdPage";
		}else{
			User sessionuser = (User) req.getSession().getAttribute("user");
			sessionuser.setPassword(user.getNewpassword());
			userService.updatePwd(sessionuser);	
			req.getSession().removeAttribute("user");
			req.setAttribute("msg", "修改密码成功！请重新登录 ...<a href='/ssh_crm/login.jsp'>登录<a>");
			return "toMsg";
			
		}
	}
	
	
	//各种校验
	//ajax校验原密码
	public void ajaxValidatePwd() throws IOException{
		String password = req.getParameter("password");
		User user = (User) req.getSession().getAttribute("user");
		boolean b = user.getPassword().equals(password);
		ServletActionContext.getResponse().getWriter().print(b);
	}
	//修改密码表单后台校验
	public Map validateUpdatePwd(){
		Map errors = new HashMap<String, String>();
		
		//1.原密码校验，不能为空、长度3-20
		String password = user.getPassword();
		if(password == null || password.trim().equals("")){
			errors.put("passwordError", "密码不能为空！");
		}else if(password.length() < 3 || password.length() >20){
			errors.put("passwordError","密码长度必须在3-20之间！");
		}else{
			User sessionuser = (User) req.getSession().getAttribute("user");
			if(!password.equalsIgnoreCase(sessionuser.getPassword())){
				errors.put("passwordError","原密码错误！");
			}
		}	
		//新密码校验
		String newpassword = user.getNewpassword();
		if(newpassword == null || newpassword.trim().equals("")){
			errors.put("newpasswordError", "新密码不能为空！");
		}else if(newpassword.length() < 3 || newpassword.length() >20){
			errors.put("newpasswordError","新密码长度必须在3-20之间！");
		}
		//确认密码校验
		String repassword = user.getRepassword();
		if(repassword == null || repassword.trim().equals("")){
			errors.put("repasswordError", "确认密码不能为空！");
		}else if(!repassword.equalsIgnoreCase(newpassword)){
			errors.put("repasswordError","确认密码必须一致！");
		}
		return errors;	
	}
}	
