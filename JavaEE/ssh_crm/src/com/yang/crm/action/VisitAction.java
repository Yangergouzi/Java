package com.yang.crm.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.yang.crm.entity.Customer;
import com.yang.crm.entity.LinkMan;
import com.yang.crm.entity.User;
import com.yang.crm.entity.Visit;
import com.yang.crm.pager.PageBean;
import com.yang.crm.service.UserService;
import com.yang.crm.service.VisitService;

public class VisitAction extends ActionSupport implements ModelDriven<Visit>{
	private VisitService visitService;
	private Visit visit = new Visit();
	private HttpServletRequest req = ServletActionContext.getRequest();
	
	public void setVisitService(VisitService visitService) {
		this.visitService = visitService;
	}

	@Override
	public Visit getModel() {
		return visit;
	}
	
	//添加
	public String toAddPage(){
		List<String> custNames = visitService.loadCustNames();
		req.setAttribute("custNames", custNames);
		return "toAddPage";
	}
	public String add(){
		Map<String, String> errors = validateForm();
		if(errors.size() > 0){
			req.setAttribute("errors", errors);
			List<String> custNames = visitService.loadCustNames();
			req.setAttribute("custNames", custNames);
			return "toAddPage";
		}else{
			String custName = req.getParameter("custName");
			Customer customer = visitService.findCustByName(custName);
			if(customer == null){
				errors.put("custNameError", "没有该客户！");
				req.setAttribute("errors", errors);
				List<String> custNames = visitService.loadCustNames();
				req.setAttribute("custNames", custNames);
				return "toAddPage";
			}
			visit.setCustomer(customer);
			User user = (User) req.getSession().getAttribute("user");
			visit.setUser(user);
			visitService.add(visit);
			req.setAttribute("msg", "添加拜访记录成功!");
			return "toMsg";
		}
	}
	
	//列表
	public String list(){
		int pc = getPc();
		PageBean<Visit> pagebean = visitService.list(pc);
		pagebean.setUrl(getUrl());
		req.setAttribute("pagebean", pagebean);
		return "list";
	}
	public String getUrl(){
		String url = req.getRequestURI() + "?" + req.getQueryString();//请求全路径
		int index = url.lastIndexOf("&pc");
		if(index > 0){
			url = url.substring(0, index);
		}
		return url;
	}
	public int getPc(){
		int pc = 1;
		String param = req.getParameter("pc");
		if(param != null && !param.trim().equals("")){
			try{
				pc = Integer.parseInt(param);
			}catch(RuntimeException e){}
		}
		return pc;
	}
	//删除
	public String delete(){
		String vid = req.getParameter("vid");
		visitService.delete(Integer.parseInt(vid));
		req.setAttribute("msg", "成功删除该拜访记录！");
		return "toMsg";
	}
	//修改
	public String toUpdatePage(){
		String vid = req.getParameter("vid");
		List<String> custNames = visitService.loadCustNames();
		req.setAttribute("custNames", custNames);
		Visit visit = visitService.get(Integer.parseInt(vid));
		req.setAttribute("visit", visit);
		return "toUpdatePage";
	}
	public String update(){
		Map<String,String> errors = validateForm();
		if(errors.size() > 0){
			String vid = req.getParameter("vid");
			List<String> custNames = visitService.loadCustNames();
			req.setAttribute("custNames", custNames);
			Visit visit = visitService.get(Integer.parseInt(vid));
			req.setAttribute("visit", visit);
			req.setAttribute("errors", errors);
			return "toUpdatePage";
		}else{
			String custName = req.getParameter("custName");
			Customer customer = visitService.findCustByName(custName);
			if(customer == null){
				errors.put("custNameError", "没有该客户！");
				return "toUpdatePage";
			}
			visit.setCustomer(customer);
			User user = (User) req.getSession().getAttribute("user");
			visit.setUser(user);
			visitService.update(visit);
			req.setAttribute("msg", "成功修改该记录！");
			return "toMsg";
		}
	}
	//拜访信息查询
		public String toVisitSelectPage(){
			List<String> custNames = visitService.loadCustNames();
			List<String> usernames = visitService.loadUsernames();
			req.setAttribute("custNames", custNames);
			req.setAttribute("usernames", usernames);
			return "toVisitSelectPage";
		}
		public String visitSelect(){
			int pc = getPc();
			String custName = req.getParameter("custName");
			String username = req.getParameter("username");
			if(!custName.trim().equals("")){
				Customer customer = visitService.findCustByName(custName);
				visit.setCustomer(customer);
			}
			if(!username.trim().equals("")){
				User user = visitService.findUserByName(username);
				visit.setUser(user);
			}
			PageBean<Visit> pagebean = visitService.combinationSelect(visit,pc);
			req.setAttribute("pagebean", pagebean);
			return "list";
		}
		
	//校验
	public Map validateForm(){
		Map<String, String> errors = new HashMap<String, String>();
		String username = req.getParameter("username");
		String vcontent = visit.getVcontent();
		String custName = req.getParameter("custName");
		//用户名必填，不能改变
		User user = (User) req.getSession().getAttribute("user");
		if(username.trim().equals("")){
			errors.put("usernameError", "用户名必填!");
		}
		else if(!username.equals(user.getUsername())){
			errors.put("usernameError", "用户名不能改变!");
		}
		//拜访内容不能为空
		 if(vcontent.trim().equals("")){
			 errors.put("vcontentError", "拜访内容必填!");
		 }
		 //拜访客户必填
		 if(custName.trim().equals("")){
			 errors.put("custNameError", "拜访客户必填!");
		 }
		
		return errors;
	}
}	
