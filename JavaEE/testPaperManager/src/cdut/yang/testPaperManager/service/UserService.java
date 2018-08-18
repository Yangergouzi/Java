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
	
	//ע��
	public void add(User user){
		userDao.add(user);
	}
	//��¼
	public User find(User user){
		return userDao.findUser(user);
	}
	
	//�޸��û���Ϣ
	public void updateUser(User loginUser) {
		userDao.updateUser(loginUser);
	}
	//ajaxУ���û����Ƿ����
	public boolean ajaxValidateUsername(String username){
		return userDao.findUsername(username);
	}
	//ajaxУ�������Ƿ����
		public boolean ajaxValidateEmail(String email){
			return userDao.findEmail(email);
		}
	//��̨ע���У��
	public Map validateRegist(User user , HttpServletRequest req){
		Map errors = new HashMap<String, String>();
		//1.�û���У�飺����Ϊ�ա��Ƿ��Ѵ���
		String username = user.getUsername();
		if(username == null ||username.trim().equals("")){
			errors.put("usernameError", "�û�������Ϊ�գ�");
		}else{
			if(!ajaxValidateUsername(username)){
				errors.put("usernameError", "�û����ѱ�ע�ᣡ");
			}
		}
		
		//2.����У�飺����Ϊ��
		String password = user.getPassword();
		if(password == null || password.trim().equals("")){
			errors.put("passwordError", "���벻��Ϊ�գ�");
		}		
		//3.ȷ������У��:����Ϊ�ա������ִ�Сд������һ��
		String repassword = user.getRepassword();
		if(repassword == null || repassword.trim().equals("")){
			errors.put("repasswordError", "���벻��Ϊ�գ�");
		}else if(!repassword.equalsIgnoreCase(user.getPassword())){
			errors.put("repasswordError","�������һ�£�");
		}
		//4.����У��:����Ϊ�ա���ʽ��ȷ���Ƿ��Ѵ���
		String email = user.getEmail();
		if(email == null || email.trim().equals("")){
			errors.put("emailError", "���䲻��Ϊ�գ�");
		}else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")){
			errors.put("emailError","�����ʽ����");
		}else{
			if(!ajaxValidateEmail(email)){
				errors.put("emailError","�����ѱ�ע�ᣡ");
			}
		}		
		//5.��֤��У�飺����Ϊ�ա���ȷ
		String verifyCode = user.getVerifyCode();
		if(verifyCode == null || verifyCode.trim().equals("")){
			errors.put("verifyCodeError", "��֤�벻��Ϊ�գ�");
		}else {
			if(!verifyCode.equalsIgnoreCase((String) req.getSession().getAttribute("vCode"))){
				errors.put("verifyCodeError", "��֤�����");
			}
		}
		return errors;
	}
	//��̨��¼��У��

	

	
	
	
}
