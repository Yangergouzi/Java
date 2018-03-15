package com.yang.crm.dao;



import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.yang.crm.entity.User;

@Transactional
public class UserDao extends HibernateDaoSupport{
	private SessionFactory sessionFactory;

	public User load(User user) {
		if(user == null) return null;
		String hql = "from User where username = ? and password = ?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, user.getUsername(),user.getPassword());
		if(list.size() == 0){
			return null;
		}else{
			User userExist = list.get(0);
			return userExist;
		}
	}

	public void updatePwd(User user) {
		this.getHibernateTemplate().update(user);
	}
	
}
