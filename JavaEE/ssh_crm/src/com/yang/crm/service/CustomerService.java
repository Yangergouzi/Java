package com.yang.crm.service;

import java.util.List;
import java.util.Map;

import com.yang.crm.dao.CustomerDao;
import com.yang.crm.entity.Customer;
import com.yang.crm.entity.CustomerLevel;
import com.yang.crm.pager.PageBean;

public class CustomerService {
	private CustomerDao customerDao;

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public void add(Customer customer) {
		customerDao.add(customer);
	}
	
	public PageBean list(int pc){
		return customerDao.loadCustomers(pc);
	}

	public void delete(Integer cid) {
		customerDao.delete(cid);
	}
	
	public Customer findById(Integer cid){
		return customerDao.findById(cid);
	}

	public void update(Customer customer) {
		customerDao.update(customer);
	}

	public PageBean combinationSelect(Customer customer, int pc) {
		return customerDao.combinationSelect(customer,pc);
	}
//根据名字模糊查询
	public PageBean<Customer> findByName(String custName, int pc) {
		return customerDao.findByName(custName,pc);
	}
//加载所有级别
	public List<CustomerLevel> loadAllLevels() {
		return customerDao.loadAllLevels();
	}
//统计客户来源
	public List countSourceFind() {
		return customerDao.countSourceFind();
	}
//统计客户级别
	public List countLevelFind() {
		return customerDao.countLevelFind();
	}
	
//通过名字查找级别
	public CustomerLevel findLevByName(String levelName) {
		return customerDao.findLevByName(levelName);
	}
}
