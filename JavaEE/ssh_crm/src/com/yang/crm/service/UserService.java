package com.yang.crm.service;

import com.yang.crm.dao.UserDao;
import com.yang.crm.entity.User;

public class UserService {
	private UserDao userDao = null;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public User login(User user){
		return userDao.load(user);
	}

	public void updatePwd(User user) {
		userDao.updatePwd(user);
	}
}
