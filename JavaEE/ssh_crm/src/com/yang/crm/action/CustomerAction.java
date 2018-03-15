package com.yang.crm.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;



import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.yang.crm.entity.Customer;
import com.yang.crm.entity.CustomerLevel;
import com.yang.crm.entity.User;
import com.yang.crm.pager.PageBean;
import com.yang.crm.pager.PageConstants;
import com.yang.crm.service.CustomerService;

public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{
	private CustomerService customerService;
	private Customer customer = new Customer();
	HttpServletRequest req = ServletActionContext.getRequest();
	
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public Customer getModel() {
		return customer;
	}
	//��ӿͻ�
	public String toAddPage(){
		List<CustomerLevel> levels = customerService.loadAllLevels();
		req.setAttribute("levels", levels);
		return "toAddPage";
	}
	public String add(){
		//��У��
		Map errors = this.validateForm();
		if(errors.size() > 0){
			req.setAttribute("errors", errors);
			return "toAddPage";
		}else{
			String levelName = req.getParameter("levelName");
			CustomerLevel customerLevel = customerService.findLevByName(levelName);
			customer.setCustLevel(customerLevel);
			customerService.add(customer);
			req.setAttribute("msg", "��ӿͻ��ɹ���");
			return "toMsg";
		}
	}
	//�ͻ��б�
	public String list(){
		int pc = getPc();
		PageBean pageBean = customerService.list(pc);		
		pageBean.setUrl(getUrl());
		req.setAttribute("pagebean", pageBean);
		return "list";
	}
	//�õ�����·�������ص�pc��������
	public String getUrl(){
		String url = req.getRequestURI() + "?" + req.getQueryString();//����ȫ·��
		int index = url.lastIndexOf("&pc");
		if(index > 0){
			url = url.substring(0, index);
		}
		return url;
	}
	//�õ���ǰҳ��
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
	//ɾ���ͻ�
	public String delete(){
		String cid = req.getParameter("cid");		
		customerService.delete(Integer.parseInt(cid));
		req.setAttribute("msg", "ɾ���ɹ���");
		return "toMsg";
	}
	//�޸Ŀͻ�
	public String toUpdatePage(){
		String cid = req.getParameter("cid");
		Customer customer = customerService.findById(Integer.parseInt(cid));
		List<CustomerLevel> levels = customerService.loadAllLevels();
		req.setAttribute("levels", levels);
		req.setAttribute("customer", customer);
		return "toUpdatePage";
	}
	public String update(){
		Map errors = this.validateForm();
		if(errors.size() > 0){
			req.setAttribute("errors", errors);
			req.setAttribute("customer", customer);
			return "toUpdatePage";
		}else{
			User sessionUser = (User) req.getSession().getAttribute("user");
			String levelName = (String)req.getParameter("levelName");
			if(!levelName.trim().equals("")){//�����ֵ�ͻ�����Ϊ��
				CustomerLevel customerLevel = new CustomerLevel();
				customerLevel.setLevelName(levelName);
				customer.setCustLevel(customerLevel);
			}
			customerService.update(customer);
			req.setAttribute("msg", "�޸��û��ɹ���");		
			return "toMsg";
		}
	}
	//���ݿͻ����Ʋ���
	public String findByName(){
		if(customer.getCustName() == null || customer.getCustName().trim().equals("")){
			return list();
		}
		int pc = getPc();
		PageBean<Customer> pagebean = customerService.findByName(customer.getCustName(),pc);
		req.setAttribute("pagebean", pagebean);
		return "list";
	}
	//�ͻ���Ϣ��ѯ
	public String toCustomerSelectPage(){
		List<CustomerLevel> levels = customerService.loadAllLevels();
		req.setAttribute("levels", levels);
		return "toCustomerSelectPage";
	}
	public String customerSelect(){
		String levelName = (String)req.getParameter("levelName");
		if(!levelName.trim().equals("")){//�����ֵ�ͻ�����Ϊ��
			CustomerLevel customerLevel = new CustomerLevel();
			customerLevel.setLevelName(levelName);
			customer.setCustLevel(customerLevel);
		}
		int pc = getPc();
		PageBean pagebean = customerService.combinationSelect(customer,pc);
		pagebean.setUrl(getUrl());
		req.setAttribute("pagebean", pagebean);
		return "list";
	}
	//ͳ�ƿͻ���Դ
	public String countSource(){
		List list = customerService.countSourceFind();
		req.setAttribute("list", list);
		return "listSource";
	}
	//ͳ�ƿͻ�����
	public String countLevel(){
		List list = customerService.countLevelFind();
		req.setAttribute("list", list);
		return "listLevel";
	}
	
	//����У��
	public Map validateForm(){
		Map<String, String> errors = new HashMap<String, String>();
		//�ͻ�����У�飬�Ƿ�Ϊ��
		String custName = customer.getCustName();
		String custPhone = customer.getCustPhone();
		String custMobile = customer.getCustMobile();
		if(custName.trim().equals("")){
			errors.put("custNameError", "�ͻ����Ʊ��");
		}
		//�ͻ��̶��绰У�飬�����Ϊ�ո�ʽ�Ƿ���ȷ
		if(!custPhone.trim().equals("")){
			String reg = "^(\\d{3,4}-)?\\d{7,8}$";//�̶��绰��ʽ������ʽ
			if(!Pattern.matches(reg, custPhone)){
				errors.put("custPhoneError", "�����ʽ���ԣ�");
			}
		}
		//�ͻ��ƶ��绰У�飬�����Ϊ�ո�ʽ�Ƿ���ȷ
		if(!custMobile.trim().equals("")){
			String reg = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";//�̶��绰��ʽ������ʽ
			if(!Pattern.matches(reg, custMobile)){
				errors.put("custPhoneError", "�����ʽ���ԣ�");
			}
		}
		return errors;
	}
}
