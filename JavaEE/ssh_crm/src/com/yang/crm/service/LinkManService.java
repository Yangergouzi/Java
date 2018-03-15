package com.yang.crm.service;

import java.util.List;

import com.yang.crm.dao.LinkManDao;
import com.yang.crm.entity.Customer;
import com.yang.crm.entity.LinkMan;
import com.yang.crm.pager.PageBean;

public class LinkManService {
	public LinkManDao linkManDao;

	public void setLinkManDao(LinkManDao linkManDao) {
		this.linkManDao = linkManDao;
	}

	public void add(LinkMan linkMan) {
		linkManDao.add(linkMan);
	}
	
	public List<String> loadCustNames() {
		return linkManDao.loadCustNames();
	}

	public Customer findCustByName(String custName) {
		return linkManDao.findCustByName(custName);
	}

	public PageBean list(int pc) {
		return linkManDao.list(pc);
	}

	public LinkMan findById(int lid) {
		return linkManDao.findById(lid);
	}

	public void update(LinkMan linkMan) {
		linkManDao.update(linkMan);
	}

	public void delete(int lid) {
		linkManDao.delete(lid);
	}

	public PageBean<LinkMan> combinationSelect(LinkMan linkMan, int pc) {
		return linkManDao.combinationSelect(linkMan,pc);
	}
	
}
