package com.yang.crm.entity;

import java.util.Set;

public class Customer{
	private Integer cid;
	private String custName;
//	private String custLevel;
	private String custSource;
	private String custPhone;
	private String custMobile;
	//客户级别，多对一
	private CustomerLevel custLevel;
	//联系人集合
	private Set<LinkMan> linkMen = null;
	public Integer getCid() {
		return cid;
	}
	//每个客户被拜访的记录
	private Set<Visit> userVisitSet;
	
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	public String getCustSource() {
		return custSource;
	}
	public void setCustSource(String custSource) {
		this.custSource = custSource;
	}
	public String getCustPhone() {
		return custPhone;
	}
	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}
	public String getCustMobile() {
		return custMobile;
	}
	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}
	public Set<LinkMan> getLinkMen() {
		return linkMen;
	}
	
	
	public CustomerLevel getCustLevel() {
		return custLevel;
	}
	public void setCustLevel(CustomerLevel custLevel) {
		this.custLevel = custLevel;
	}
	public void setLinkMen(Set<LinkMan> linkMen) {
		this.linkMen = linkMen;
	}
	public Set<Visit> getUserVisitSet() {
		return userVisitSet;
	}
	public void setUserVisitSet(Set<Visit> userVisitSet) {
		this.userVisitSet = userVisitSet;
	}
	
	
	
}
