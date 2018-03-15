package com.yang.crm.service;

import java.util.List;

import com.yang.crm.dao.CustomerDao;
import com.yang.crm.dao.VisitDao;
import com.yang.crm.entity.Customer;
import com.yang.crm.entity.LinkMan;
import com.yang.crm.entity.User;
import com.yang.crm.entity.Visit;
import com.yang.crm.pager.PageBean;

public class VisitService {
	private VisitDao visitDao;

	public void setVisitDao(VisitDao visitDao) {
		this.visitDao = visitDao;
	}
	
	public List<String> loadCustNames() {
		return visitDao.loadCustNames();
	}
	public List<String> loadUsernames() {
		return visitDao.loadUsernames();
	}

	public Customer findCustByName(String custName) {
		return visitDao.findCustByName(custName);
	}
	public User findUserByName(String username) {
		return visitDao.findUserByName(username);
	}

	public void add(Visit visit) {
		visitDao.add(visit);
	}

	public PageBean<Visit> list(int pc) {
		return visitDao.list(pc);
	}

	public void delete(int vid) {
		visitDao.delete(vid);
	}

	public Visit get(int vid) {
		return visitDao.get(vid);
	}

	public void update(Visit visit) {
		visitDao.update(visit);
	}

	public PageBean<Visit> combinationSelect(Visit visit, int pc) {
		return visitDao.combinationSelect(visit,pc);
	}

	

	
}
