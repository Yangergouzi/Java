package com.yang.crm.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.apache.xml.internal.security.encryption.Transforms;
import com.yang.crm.entity.Customer;
import com.yang.crm.entity.CustomerLevel;
import com.yang.crm.entity.User;
import com.yang.crm.pager.PageBean;
import com.yang.crm.pager.PageConstants;
@Transactional
public class CustomerDao extends HibernateDaoSupport {
	private SessionFactory sessionFactory;
	
	

	public void add(Customer customer) {
		this.getHibernateTemplate().save(customer);
	}
	
	
	
	public PageBean loadCustomers(int pc){
		int ps = PageConstants.PAGE_SIZE;
		//底层hibernate实现分页
		SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Customer");
		query.setFirstResult(ps*(pc-1));
		query.setMaxResults(ps);
		List<Customer> customers = query.list();
		//总记录数
		int tr = 0;
		List<Object> list = (List<Object>) this.getHibernateTemplate().find("select count(*) from Customer");
		if(list != null && list.size() > 0){
			Object obj = list.get(0);
			Long l = (Long) obj;
			tr = l.intValue();
		} 
		
		PageBean pageBean = new PageBean<Customer>();
		pageBean.setPageCurr(pc);
		pageBean.setPageSize(ps);
		pageBean.setTotalRec(tr);
		pageBean.setBeanList(customers);
		return pageBean;
	}

	public Customer findById(Integer cid) {
		return this.getHibernateTemplate().get(Customer.class, cid);		
	}
	public void delete(Integer cid){
		Customer customer = findById(cid);
		this.getHibernateTemplate().delete(customer);
	}

	public void update(Customer customer) {
		List<CustomerLevel> custLevel = (List<CustomerLevel>) this.getHibernateTemplate().find("from CustomerLevel where levelName=?", customer.getCustLevel().getLevelName());
		customer.setCustLevel(custLevel.get(0));
		this.getHibernateTemplate().update(customer);
	}

	//多条件组合查询
	public PageBean combinationSelect(Customer customer,int pc) {
		PageBean<Customer> pagebean = new PageBean<Customer>();
		int ps = PageConstants.PAGE_SIZE;
  		String hql = "from Customer c where 1=1";
		if(customer.getCustName() != null && !customer.getCustName().equals("")){
			hql = hql + " and custName like '%"+customer.getCustName()+"%' ";
		}
		if(customer.getCustLevel() != null && !customer.getCustLevel().getLevelName().equals("")){
			hql = hql + " and custLevel.levelName like '%"+customer.getCustLevel().getLevelName()+"%' ";
		}
		if(customer.getCustSource() != null && !customer.getCustSource().equals("")){
			hql = hql + " and custSource like '%"+customer.getCustSource()+"%' ";
		}
		List<Customer> customers = (List<Customer>) this.getHibernateTemplate().find(hql);
		//计算总数
		int tr = 0;
		String hqlCount = "select count(*)" + hql;
		List<Object> objs = (List<Object>) this.getHibernateTemplate().find(hqlCount);
		if(objs != null && objs.size() > 0){
			Object obj = objs.get(0);
			Long l = (Long) obj;
			tr = l.intValue();
		}
		pagebean.setBeanList(customers);
		pagebean.setPageCurr(pc);
		pagebean.setPageSize(ps);
		pagebean.setTotalRec(tr);
		return pagebean;
	}
	//根据名字模糊查询
	public PageBean<Customer> findByName(String custName,int pc) {
		int ps = PageConstants.PAGE_SIZE;
		//底层hibernate实现分页
				SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
				Session session = sessionFactory.getCurrentSession();
				Query query = session.createQuery("from Customer where custName like '%"+custName+"%' ");
				query.setFirstResult(ps*(pc-1));
				query.setMaxResults(ps);
				List<Customer> customers = query.list();
				//总记录数
				int tr = 0;
				List<Object> list = (List<Object>) this.getHibernateTemplate().find("select count(*) from Customer where custName like '%"+custName+"%'");
				if(list != null && list.size() > 0){
					Object obj = list.get(0);
					Long l = (Long) obj;
					tr = l.intValue();
				} 
				
				PageBean pageBean = new PageBean<Customer>();
				pageBean.setPageCurr(pc);
				pageBean.setPageSize(ps);
				pageBean.setTotalRec(tr);
				pageBean.setBeanList(customers);
				return pageBean;
	}


//加载所有级别
	public List<CustomerLevel> loadAllLevels() {
		List<CustomerLevel> levels = (List<CustomerLevel>) this.getHibernateTemplate().find("from CustomerLevel");
		return levels;
	}


//客户来源统计
	public List countSourceFind() {
		//底层sql实现多表分组统计
		sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		String sql = "select count(*) as num,custSource from t_customer group by custSource";
		SQLQuery query = session.createSQLQuery(sql);
		//默认返回对象数组集合，设置返回Map集合
		query.setResultTransformer(Transformers.aliasToBean(HashMap.class));
		List list = query.list();
		return list;
	}
//客户级别统计
	public List countLevelFind() {
		//底层sql实现多表分组统计
				sessionFactory = this.getHibernateTemplate().getSessionFactory();
				Session session = sessionFactory.getCurrentSession();
				String childSql = "select count(*) as num,level_id from t_customer group by level_id";
				String sql = "select c.num,d.levelName from ("+childSql+") as c,t_dictcustlevel as d where c.level_id=d.level_id";
				SQLQuery query = session.createSQLQuery(sql);
				//默认返回对象数组集合，设置返回Map集合
				query.setResultTransformer(Transformers.aliasToBean(HashMap.class));
				List list = query.list();
				return list;
	}



	public CustomerLevel findLevByName(String levelName) {
		CustomerLevel customerLevel = null;
		List<CustomerLevel> list = (List<CustomerLevel>) this.getHibernateTemplate().find("from CustomerLevel where levelName=?", levelName);
		if(list != null && list.size() > 0){
			customerLevel = list.get(0);
		}
		return customerLevel;
	}
}
