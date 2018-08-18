package cdut.yang.testPaperManager.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cdut.yang.testPaperManager.dao.UserDao;
import cdut.yang.testPaperManager.pojo.User;

public class UserService {
	private UserDao userDao = null;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	//注册
	public void add(User user){
		userDao.add(user);
	}
	//登录
	public User find(User user){
		return userDao.findUser(user);
	}
	
	//修改用户信息
	public void updateUser(User loginUser) {
		userDao.updateUser(loginUser);
	}
	//ajax校验用户名是否存在
	public boolean ajaxValidateUsername(String username){
		return userDao.findUsername(username);
	}
	//ajax校验邮箱是否存在
		public boolean ajaxValidateEmail(String email){
			return userDao.findEmail(email);
		}
	//后台注册表单校验
	public Map validateRegist(User user , HttpServletRequest req){
		Map errors = new HashMap<String, String>();
		//1.用户名校验：不能为空、是否已存在
		String username = user.getUsername();
		if(username == null ||username.trim().equals("")){
			errors.put("usernameError", "用户名不能为空！");
		}else{
			if(!ajaxValidateUsername(username)){
				errors.put("usernameError", "用户名已被注册！");
			}
		}
		
		//2.密码校验：不能为空
		String password = user.getPassword();
		if(password == null || password.trim().equals("")){
			errors.put("passwordError", "密码不能为空！");
		}		
		//3.确认密码校验:不能为空、不区分大小写与密码一致
		String repassword = user.getRepassword();
		if(repassword == null || repassword.trim().equals("")){
			errors.put("repasswordError", "密码不能为空！");
		}else if(!repassword.equalsIgnoreCase(user.getPassword())){
			errors.put("repasswordError","密码必须一致！");
		}
		//4.邮箱校验:不能为空、格式正确、是否已存在
		String email = user.getEmail();
		if(email == null || email.trim().equals("")){
			errors.put("emailError", "邮箱不能为空！");
		}else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")){
			errors.put("emailError","邮箱格式错误！");
		}else{
			if(!ajaxValidateEmail(email)){
				errors.put("emailError","邮箱已被注册！");
			}
		}		
		//5.验证码校验：不能为空、正确
		String verifyCode = user.getVerifyCode();
		if(verifyCode == null || verifyCode.trim().equals("")){
			errors.put("verifyCodeError", "验证码不能为空！");
		}else {
			if(!verifyCode.equalsIgnoreCase((String) req.getSession().getAttribute("vCode"))){
				errors.put("verifyCodeError", "验证码错误！");
			}
		}
		return errors;
	}
	//后台登录表单校验

	

	
	
	
}
