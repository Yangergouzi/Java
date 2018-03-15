package com.yang.crm.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.yang.crm.entity.User;

public class LoginInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation arg0) throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute("user");
		if(user == null){
			return "login";
		}
		return arg0.invoke();
	}

}
