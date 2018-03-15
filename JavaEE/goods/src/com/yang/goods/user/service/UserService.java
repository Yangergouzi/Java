package com.yang.goods.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.Session;

import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;

import com.sun.faces.util.MessageFactory;
import com.yang.goods.user.dao.UserDao;
import com.yang.goods.user.domain.User;
import com.yang.goods.user.service.exception.UserException;

public class UserService {
	private UserDao dao = new UserDao();

	// ajax校验注册用户名是否已存在，调用dao层方法
	public boolean ajaxValidateLoginname(String loginname) throws SQLException {
		return dao.ajaxValidateLoginname(loginname);
	}

	// ajax校验注册邮箱是否已存在，调用dao层方法
	public boolean ajaxValidateEmail(String email) throws SQLException {
		return dao.ajaxValidateEmail(email);
	}

	//用户登录，用UserDao#findByNameAndPass 
	public User login(User formbean){
		try {
			User user = dao.findByNameAndPass(formbean.getLoginname(), formbean.getLoginpass());
			return user;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// 注册用户
	public void register(User user) throws SQLException {
		// 1.对User进行数据补全
		user.setUid(CommonUtils.uuid());
		user.setActivationCode(CommonUtils.uuid() + CommonUtils.uuid());
		user.setStatus(false);

		// 2.向数据库添加用户
		dao.add(user);
		// 3.向用户邮箱发送激活邮件
		// 读取email模板文件中的数据
		Properties props = new Properties();
		try {
			props.load(this.getClass().getClassLoader()
					.getResourceAsStream("email_template.properties"));

			// 通过类加载器得到文件并加载
			String subject = props.getProperty("subject");
			String content = MessageFormat.format(props.getProperty("content"),
					user.getActivationCode());
			String from = props.getProperty("from");
			String host = props.getProperty("host");
			String username = props.getProperty("username");
			String password = props.getProperty("password");

			// session
			Session session = MailUtils.createSession(host, username, password);
			// Mail对象
			String to = user.getEmail();
			Mail mail = new Mail(from, to, subject, content);

			// 发送
			MailUtils.send(session, mail);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	//激活
	public void activation(String activationCode) throws UserException {
		// 根据激活码查找用户，若不存在，抛UserException异常；判断status状态，若为true,抛异常，否则修改数据库改用户的status为true
		try {
			User user = dao.findByActivationCode(activationCode);
			if (user == null)
				throw new UserException("无效的激活码！");
			if (user.isStatus())
				throw new UserException("您已激活成功！不能二次激活！");
			dao.updateStatus(user.getUid(), true);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	//修改密码
	public void updatePassword(String uid,String newpass){
		//调用UserDao#updatePassword
		dao.updatePassword(uid, newpass);
	}
}
