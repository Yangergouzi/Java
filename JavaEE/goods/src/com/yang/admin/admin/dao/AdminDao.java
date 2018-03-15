package com.yang.admin.admin.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.yang.admin.admin.domain.Admin;

import cn.itcast.jdbc.TxQueryRunner;

public class AdminDao {
	QueryRunner qr = new TxQueryRunner();
	//通过管理员登录名和密码查询数据库
	public Admin findByNameAndPwd(String adminname, String adminpwd) throws SQLException {
		String sql = "select * from t_admin where adminname = ? and adminpwd = ?";
		Admin admin = qr.query(sql, new BeanHandler<Admin>(Admin.class), adminname,adminpwd);
		return admin;
	}
}
