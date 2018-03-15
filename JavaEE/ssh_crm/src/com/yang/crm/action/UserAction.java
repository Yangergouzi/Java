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
			req.setAttribute("msg", "�޸�����ɹ��������µ�¼ ...<a href='/ssh_crm/login.jsp'>��¼<a>");
			return "toMsg";
			
		}
	}
	
	
	//����У��
	//ajaxУ��ԭ����
	public void ajaxValidatePwd() throws IOException{
		String password = req.getParameter("password");
		User user = (User) req.getSession().getAttribute("user");
		boolean b = user.getPassword().equals(password);
		ServletActionContext.getResponse().getWriter().print(b);
	}
	//�޸��������̨У��
	public Map validateUpdatePwd(){
		Map errors = new HashMap<String, String>();
		
		//1.ԭ����У�飬����Ϊ�ա�����3-20
		String password = user.getPassword();
		if(password == null || password.trim().equals("")){
			errors.put("passwordError", "���벻��Ϊ�գ�");
		}else if(password.length() < 3 || password.length() >20){
			errors.put("passwordError","���볤�ȱ�����3-20֮�䣡");
		}else{
			User sessionuser = (User) req.getSession().getAttribute("user");
			if(!password.equalsIgnoreCase(sessionuser.getPassword())){
				errors.put("passwordError","ԭ�������");
			}
		}	
		//������У��
		String newpassword = user.getNewpassword();
		if(newpassword == null || newpassword.trim().equals("")){
			errors.put("newpasswordError", "�����벻��Ϊ�գ�");
		}else if(newpassword.length() < 3 || newpassword.length() >20){
			errors.put("newpasswordError","�����볤�ȱ�����3-20֮�䣡");
		}
		//ȷ������У��
		String repassword = user.getRepassword();
		if(repassword == null || repassword.trim().equals("")){
			errors.put("repasswordError", "ȷ�����벻��Ϊ�գ�");
		}else if(!repassword.equalsIgnoreCase(newpassword)){
			errors.put("repasswordError","ȷ���������һ�£�");
		}
		return errors;	
	}
}	
