package com.yang.crm.entity;

import java.util.Set;

public class User {
	//要写入数据库的属性
	private Integer uid;
	private String username;
	private String password;
	private String address;
	//不写入数据库的属性
	private String newpassword;
	private String repassword;
	//每个用户对应的拜访记录
	private Set<Visit> visitCustomerSet;
	
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public Set<Visit> getVisitCustomerSet() {
		return visitCustomerSet;
	}
	public void setVisitCustomerSet(Set<Visit> visitCustomerSet) {
		this.visitCustomerSet = visitCustomerSet;
	}
	
	
}
