package com.yang.crm.entity;

import java.util.Set;

//客户的数据字典表，存储客户的几个级别
public class CustomerLevel {
	private int level_id;
	private String levelName;
	//与客户是一对多关系
	private Set<Customer> customerSet;
	public int getLevel_id() {
		return level_id;
	}
	public void setLevel_id(int level_id) {
		this.level_id = level_id;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public Set<Customer> getCustomerSet() {
		return customerSet;
	}
	public void setCustomerSet(Set<Customer> customerSet) {
		this.customerSet = customerSet;
	}
	
}
