package com.yang.crm.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.yang.crm.entity.Customer;
import com.yang.crm.entity.LinkMan;
import com.yang.crm.pager.PageBean;
import com.yang.crm.pager.PageConstants;
@Transactional
public class LinkManDao extends HibernateDaoSupport{
	private SessionFactory sessionFactory;

	public void add(LinkMan linkMan) {
		this.getHibernateTemplate().save(linkMan);
	}

	public List<String> loadCustNames() {
		List<String> custNames = (List<String>) this.getHibernateTemplate().find("select custName from Customer");
		return custNames;
	}

	public Customer findCustByName(String custName) {
	Customer customer = null;
	List<Customer> customerlist = (List<Customer>) this.getHibernateTemplate().find("from Customer where custName = ?", custName);
	if(customerlist != null && customerlist.size() > 0){
		customer = customerlist.get(0);
	}
	return customer;
	}

	public PageBean list(int pc) {
		int ps = PageConstants.PAGE_SIZE;
		//底层hibernate实现分页
		sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		String sql = "select * from t_linkman order by customer_id limit ?,?";
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameter(0, ps*(pc-1));
		query.setParameter(1, ps);
		List<LinkMan> linkMen = query.addEntity(LinkMan.class).list();
		//计算总记录数
		int tr = 0;
		List<Object> objlist = (List<Object>) this.getHibernateTemplate().find("select count(*) from LinkMan");
		if(objlist != null && objlist.size() > 0){
			Object obj = objlist.get(0);
			Long l = (Long) obj;
			tr = l.intValue();
		}
		//创建pageBean
		PageBean<LinkMan> pageBean = new PageBean<LinkMan>();
		pageBean.setBeanList(linkMen);
		pageBean.setPageCurr(pc);
		pageBean.setPageSize(ps);
		pageBean.setTotalRec(tr);
		return pageBean;
	}

	public LinkMan findById(int lid) {
		LinkMan linkMan = this.getHibernateTemplate().get(LinkMan.class, lid);
		return linkMan;
	}

	public void update(LinkMan linkMan) {
		this.getHibernateTemplate().update(linkMan);
	}

	public void delete(int lid) {
		LinkMan linkMan = findById(lid);
		this.getHibernateTemplate().delete(linkMan);
	}
//多条件组合查询
	public PageBean<LinkMan> combinationSelect(LinkMan linkMan, int pc) {
		PageBean<LinkMan> pagebean = new PageBean<LinkMan>();
		int ps = PageConstants.PAGE_SIZE;
		String hql = "from LinkMan where 1=1";
		if(linkMan.getLkmName() != null && !linkMan.getLkmName().equals("")){
			hql = hql + "and lkmName like '%"+linkMan.getLkmName()+"%'";
		}
		if(linkMan.getCustomer() != null && !linkMan.getCustomer().getCustName().equals("")){
			hql = hql + "and customer.custName like '%"+linkMan.getCustomer().getCustName()+"%'";
		}
		if(linkMan.getLkmGender() != null && !linkMan.getLkmGender().equals("")){
			hql = hql + "and lkmGender like '%"+linkMan.getLkmGender()+"%'";
		}
		List<LinkMan> linkMen = (List<LinkMan>) this.getHibernateTemplate().find(hql);
		//计算总数
		int tr = 0;
		String hqlCount = "select count(*)" + hql;
		List<Object> objs = (List<Object>) this.getHibernateTemplate().find(hqlCount);
		if(objs != null && objs.size() > 0){
			Object obj = objs.get(0);
			Long l = (Long) obj;
			tr = l.intValue();
		}

		pagebean.setBeanList(linkMen);
		pagebean.setPageCurr(pc);
		pagebean.setPageSize(ps);
		pagebean.setTotalRec(tr);
		return pagebean;
	}

	
	
}
