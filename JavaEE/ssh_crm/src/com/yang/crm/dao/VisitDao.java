package com.yang.crm.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.yang.crm.entity.Customer;
import com.yang.crm.entity.LinkMan;
import com.yang.crm.entity.User;
import com.yang.crm.entity.Visit;
import com.yang.crm.pager.PageBean;
import com.yang.crm.pager.PageConstants;
@Transactional
public class VisitDao extends HibernateDaoSupport {
	private SessionFactory sessionFactory;

	public List<String> loadCustNames() {
		List<String> custNames = (List<String>) this.getHibernateTemplate().find("select custName from Customer");
		return custNames;
	}
	public List<String> loadUsernames() {
		List<String> usernames = (List<String>) this.getHibernateTemplate().find("select username from User");
		return usernames;
	}
	

	public Customer findCustByName(String custName) {
		Customer customer = null;
		List<Customer> customerlist = (List<Customer>) this.getHibernateTemplate().find("from Customer where custName=?", custName);
		if(customerlist != null && customerlist.size() > 0){
			customer = customerlist.get(0);
		}
		return customer;
	}
	public User findUserByName(String username) {
		User user = null;
		List<User> userlist = (List<User>) this.getHibernateTemplate().find("from User where username=?", username);
		if(userlist != null && userlist.size() > 0){
			user = userlist.get(0);
		}
		return user;
	}

	public void add(Visit visit) {
		this.getHibernateTemplate().save(visit);
	}

	public PageBean<Visit> list(int pc) {
		PageBean<Visit> pagebean = new PageBean<Visit>();
		int ps = PageConstants.PAGE_SIZE;
		//底层hibernate实现分页
		sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Visit");
		query.setFirstResult(ps*(pc-1));
		query.setMaxResults(ps);
		List<Visit> visitList = query.list();
		//计算总记录数
		int tr = 0;
		List<Object> objs = (List<Object>) this.getHibernateTemplate().find("select count(*) from Visit"); 
		if(objs != null && objs.size() > 0){
			Object obj = objs.get(0);
			Long l = (Long) obj;
			tr = l.intValue();
		}
		pagebean.setPageCurr(pc);
		pagebean.setPageSize(ps);
		pagebean.setBeanList(visitList);
		pagebean.setTotalRec(tr);
		return pagebean;
	}

	public Visit get(int vid){
		Visit visit = this.getHibernateTemplate().get(Visit.class, vid);
		return visit;
	}
	
	public void delete(int vid) {
		this.getHibernateTemplate().delete(get(vid));
	}

	public void update(Visit visit) {
		this.getHibernateTemplate().update(visit);
	}
	//多条件组合查询
	public PageBean<Visit> combinationSelect(Visit visit, int pc) {
		PageBean<Visit> pagebean = new PageBean<Visit>();
		int ps = PageConstants.PAGE_SIZE;
		String hql = "from Visit where 1=1";
		if(visit.getUser() != null && !visit.getUser().getUsername().equals("")){
			hql = hql + "and user.username like '%"+visit.getUser().getUsername()+"%'";
		}
		if(visit.getCustomer() != null && !visit.getCustomer().getCustName().equals("")){
			hql = hql + "and customer.custName like '%"+visit.getCustomer().getCustName()+"%'";
		}
		List<Visit> visits = (List<Visit>) this.getHibernateTemplate().find(hql);
		//计算总数
		int tr = 0;
		String hqlCount = "select count(*)" + hql;
		List<Object> objs = (List<Object>) this.getHibernateTemplate().find(hqlCount);
		if(objs != null && objs.size() > 0){
			Object obj = objs.get(0);
			Long l = (Long) obj;
			tr = l.intValue();
		}

		pagebean.setBeanList(visits);
		pagebean.setPageCurr(pc);
		pagebean.setPageSize(ps);
		pagebean.setTotalRec(tr);
		return pagebean;
	}

	
	
}
