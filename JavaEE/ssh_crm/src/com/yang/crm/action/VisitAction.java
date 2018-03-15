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
	
	//���
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
				errors.put("custNameError", "û�иÿͻ���");
				req.setAttribute("errors", errors);
				List<String> custNames = visitService.loadCustNames();
				req.setAttribute("custNames", custNames);
				return "toAddPage";
			}
			visit.setCustomer(customer);
			User user = (User) req.getSession().getAttribute("user");
			visit.setUser(user);
			visitService.add(visit);
			req.setAttribute("msg", "��Ӱݷü�¼�ɹ�!");
			return "toMsg";
		}
	}
	
	//�б�
	public String list(){
		int pc = getPc();
		PageBean<Visit> pagebean = visitService.list(pc);
		pagebean.setUrl(getUrl());
		req.setAttribute("pagebean", pagebean);
		return "list";
	}
	public String getUrl(){
		String url = req.getRequestURI() + "?" + req.getQueryString();//����ȫ·��
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
	//ɾ��
	public String delete(){
		String vid = req.getParameter("vid");
		visitService.delete(Integer.parseInt(vid));
		req.setAttribute("msg", "�ɹ�ɾ���ðݷü�¼��");
		return "toMsg";
	}
	//�޸�
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
				errors.put("custNameError", "û�иÿͻ���");
				return "toUpdatePage";
			}
			visit.setCustomer(customer);
			User user = (User) req.getSession().getAttribute("user");
			visit.setUser(user);
			visitService.update(visit);
			req.setAttribute("msg", "�ɹ��޸ĸü�¼��");
			return "toMsg";
		}
	}
	//�ݷ���Ϣ��ѯ
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
		
	//У��
	public Map validateForm(){
		Map<String, String> errors = new HashMap<String, String>();
		String username = req.getParameter("username");
		String vcontent = visit.getVcontent();
		String custName = req.getParameter("custName");
		//�û���������ܸı�
		User user = (User) req.getSession().getAttribute("user");
		if(username.trim().equals("")){
			errors.put("usernameError", "�û�������!");
		}
		else if(!username.equals(user.getUsername())){
			errors.put("usernameError", "�û������ܸı�!");
		}
		//�ݷ����ݲ���Ϊ��
		 if(vcontent.trim().equals("")){
			 errors.put("vcontentError", "�ݷ����ݱ���!");
		 }
		 //�ݷÿͻ�����
		 if(custName.trim().equals("")){
			 errors.put("custNameError", "�ݷÿͻ�����!");
		 }
		
		return errors;
	}
}	
