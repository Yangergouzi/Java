package com.yang.admin.admin.service;

import java.sql.SQLException;

import com.yang.admin.admin.dao.AdminDao;
import com.yang.admin.admin.domain.Admin;

public class AdminService {
	AdminDao dao = new AdminDao();

	/*
	 * 调用dao的方法，通过管理员登录名和密码查询数据库
	 */
	public Admin findByNameAndPwd(String adminname, String adminpwd) {
		try {
			return dao.findByNameAndPwd(adminname,adminpwd);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
