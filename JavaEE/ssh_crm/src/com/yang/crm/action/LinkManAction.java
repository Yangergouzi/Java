package com.yang.crm.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.yang.crm.entity.Customer;
import com.yang.crm.entity.LinkMan;
import com.yang.crm.entity.User;
import com.yang.crm.pager.PageBean;
import com.yang.crm.service.LinkManService;

public class LinkManAction extends ActionSupport implements ModelDriven<LinkMan>{
	private LinkManService linkManService;
	private LinkMan linkMan = new LinkMan();
	HttpServletRequest req = ServletActionContext.getRequest();

	public void setLinkManService(LinkManService linkManService) {
		this.linkManService = linkManService;
	}
	
	@Override
	public LinkMan getModel() {
		return linkMan;
	}
	
	//�����ϵ�� 
	public String toAddPage(){
		List<String> custNames = linkManService.loadCustNames();
		req.setAttribute("custNames", custNames);
		return "toAddPage";
	}
	public String add(){
		Map errors = validateForm();
		if(errors.size() > 0){
			req.setAttribute("errors", errors);
			return "toAddPage";
		}else{
			String custName = req.getParameter("custName");
			Customer customer = linkManService.findCustByName(custName);
			if(customer == null){
				return "toAddPage";
			}else{
				linkMan.setCustomer(customer);
				linkManService.add(linkMan);
				req.setAttribute("msg", "�����ϵ�˳ɹ���");
				return "toMsg";
			}
		}
	}
	//�ͻ�����ϵ���б�
	public String list(){
		int pc = getPc();
		PageBean pageBean = linkManService.list(pc);		
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
	//�޸���ϵ��
	public String toUpdatePage(){
		String lid = req.getParameter("lid");
		LinkMan linkMan = linkManService.findById(Integer.parseInt(lid));
		List<String> custNames = linkManService.loadCustNames();
		req.setAttribute("linkMan", linkMan);
		req.setAttribute("custNames", custNames);
		return "toUpdatePage";
	}
	public String update(){
		Map<String,String> errors = validateForm();
		if(errors != null && errors.size() > 0){
			List<String> custNames = linkManService.loadCustNames();
			req.setAttribute("linkMan", linkMan);
			req.setAttribute("custNames", custNames);
			req.setAttribute("errors", errors);
			return "toUpdatePage";
		}else{
			String custName = req.getParameter("custName");
			Customer customer = linkManService.findCustByName(custName);
			if(customer == null){
				return "toUpdatePage";
			}
			linkMan.setCustomer(customer);
			linkManService.update(linkMan);
			req.setAttribute("msg", "�޸���ϵ�˳ɹ���");
			return "toMsg";
		}
	}
	//ɾ����ϵ��
	public String delete(){
		String lid = req.getParameter("lid");
		linkManService.delete(Integer.parseInt(lid));
		req.setAttribute("msg", "ɾ���ɹ���");
		return "toMsg";
	}
	//��ϵ����Ϣ��ѯ
		public String toLinkManSelectPage(){
			List<String> custNames = linkManService.loadCustNames();
			req.setAttribute("custNames", custNames);
			return "toLinkManSelectPage";
		}
		public String linkManSelect(){
			int pc = getPc();
			String custName = req.getParameter("custName");
			if(!custName.trim().equals("")){
				Customer customer = linkManService.findCustByName(custName);
				linkMan.setCustomer(customer);
			}
			PageBean<LinkMan> pagebean = linkManService.combinationSelect(linkMan,pc);
			req.setAttribute("pagebean", pagebean);
			return "list";
		}
	
	//����У��
	public Map validateForm(){
		Map<String, String> errors = new HashMap<String, String>();
		String lkmName = linkMan.getLkmName();
		String lkmGender = linkMan.getLkmGender();
		String lkmPhone = linkMan.getLkmPhone();
		String lkmMobile = linkMan.getLkmMobile();
		//��ϵ������У�飬�Ƿ�Ϊ��
		if(lkmName.trim().equals("")){
			errors.put("lkmNameError", "��ϵ�����Ʊ��");
		}
		//��ϵ���Ա��Ƿ�Ϊ�գ��Ƿ���ȷ
		if(lkmGender.trim().equals("")){
			errors.put("lkmGenderError", "��ϵ���Ա���Ϊ�գ�");
		}else if(!lkmGender.equals("��") && !lkmGender.equals("Ů")){
			errors.put("lkmGenderError", "��ϵ���Ա�ֻ�����л�Ů��");
		}
		//��ϵ�˹̶��绰У�飬�����Ϊ�ո�ʽ�Ƿ���ȷ
		if(!lkmPhone.trim().equals("")){
			String reg = "^(\\d{3,4}-)?\\d{7,8}$";//�̶��绰��ʽ������ʽ
			if(!Pattern.matches(reg, lkmPhone)){
				errors.put("lkmPhoneError", "�����ʽ���ԣ�");
			}
		}
		//��ϵ���ƶ��绰У�飬�����Ϊ�ո�ʽ�Ƿ���ȷ
		if(!lkmMobile.trim().equals("")){
			String reg = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";//�̶��绰��ʽ������ʽ
			if(!Pattern.matches(reg, lkmMobile)){
				errors.put("lkmMobileError", "�����ʽ���ԣ�");
			}
		}
		return errors;
	}
}
