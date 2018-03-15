package com.yang.goods.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.yang.goods.user.domain.User;

import cn.itcast.jdbc.TxQueryRunner;

public class UserDao {
	private TxQueryRunner qr = new TxQueryRunner();

	// ajax校验注册用户名是否已存在，若不存在返回true，否则返回false
	public boolean ajaxValidateLoginname(String loginname) throws SQLException {
		String sql = "select count(1) from t_user where loginname=?";
		Number count = (Number) qr.query(sql, new ScalarHandler(), loginname);
		return count.intValue() == 0;
	}

	// ajax校验注册邮箱是否已存在，若不存在返回true，否则返回false
	public boolean ajaxValidateEmail(String email) throws SQLException {
		String sql = "select count(1) from t_user where email=?";
		Number count = (Number) qr.query(sql, new ScalarHandler(), email);
		return count.intValue() == 0;
	}
	//根据用户名和密码查找数据库
	public User findByNameAndPass(String name,String password) throws SQLException {
		String sql = "select * from t_user where loginname = ? and loginpass = ?";
		return qr.query(sql, new BeanHandler<User>(User.class), name,password);
	}
	// 向数据库添加用户
	public void add(User user) throws SQLException {
		String sql = "insert into t_user values(?,?,?,?,?,?)";
		Object[] params = { user.getUid(), user.getLoginname(),
				user.getLoginpass(), user.getEmail(), user.isStatus(),
				user.getActivationCode() };
		qr.update(sql, params);
	}

	// 根据激活码查找用户
	public User findByActivationCode(String activationCode) throws SQLException {
		String sql = "select * from t_user where activationCode = ?";
		return qr.query(sql, new BeanHandler<User>(User.class), activationCode);
	}

	// 修改status状态
	public void updateStatus(String uid, boolean b) {
		String sql = "update t_user set status = ? where uid = ?";
		try {
			qr.update(sql, b, uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	//修改密码
	public void updatePassword(String uid, String newpass) {
		String sql = "update t_user set loginpass = ? where uid = ?";
		try {
			qr.update(sql, newpass, uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
