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
	
	//添加联系人 
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
				req.setAttribute("msg", "添加联系人成功！");
				return "toMsg";
			}
		}
	}
	//客户的联系人列表
	public String list(){
		int pc = getPc();
		PageBean pageBean = linkManService.list(pc);		
		pageBean.setUrl(getUrl());
		req.setAttribute("pagebean", pageBean);
		return "list";
	}
	//得到请求路径，并截掉pc参数部分
	public String getUrl(){
		String url = req.getRequestURI() + "?" + req.getQueryString();//请求全路径
		int index = url.lastIndexOf("&pc");
		if(index > 0){
			url = url.substring(0, index);
		}
		return url;
	}
	//得到当前页码
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
	//修改联系人
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
			req.setAttribute("msg", "修改联系人成功！");
			return "toMsg";
		}
	}
	//删除联系人
	public String delete(){
		String lid = req.getParameter("lid");
		linkManService.delete(Integer.parseInt(lid));
		req.setAttribute("msg", "删除成功！");
		return "toMsg";
	}
	//联系人信息查询
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
	
	//各种校验
	public Map validateForm(){
		Map<String, String> errors = new HashMap<String, String>();
		String lkmName = linkMan.getLkmName();
		String lkmGender = linkMan.getLkmGender();
		String lkmPhone = linkMan.getLkmPhone();
		String lkmMobile = linkMan.getLkmMobile();
		//联系人名称校验，是否为空
		if(lkmName.trim().equals("")){
			errors.put("lkmNameError", "联系人名称必填！");
		}
		//联系人性别，是否为空，是否正确
		if(lkmGender.trim().equals("")){
			errors.put("lkmGenderError", "联系人性别不能为空！");
		}else if(!lkmGender.equals("男") && !lkmGender.equals("女")){
			errors.put("lkmGenderError", "联系人性别只能是男或女！");
		}
		//联系人固定电话校验，如果不为空格式是否正确
		if(!lkmPhone.trim().equals("")){
			String reg = "^(\\d{3,4}-)?\\d{7,8}$";//固定电话格式正则表达式
			if(!Pattern.matches(reg, lkmPhone)){
				errors.put("lkmPhoneError", "号码格式不对！");
			}
		}
		//联系人移动电话校验，如果不为空格式是否正确
		if(!lkmMobile.trim().equals("")){
			String reg = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";//固定电话格式正则表达式
			if(!Pattern.matches(reg, lkmMobile)){
				errors.put("lkmMobileError", "号码格式不对！");
			}
		}
		return errors;
	}
}
