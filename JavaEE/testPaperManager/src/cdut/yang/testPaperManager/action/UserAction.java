package cdut.yang.testPaperManager.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.struts2.ServletActionContext;

import cdut.yang.testPaperManager.pojo.User;
import cdut.yang.testPaperManager.service.UserService;
import cdut.yang.tools.wordToHtml.DocToHtml;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends ActionSupport implements ModelDriven<User>{
	private UserService userService;
	private User user = new User();
	HttpServletRequest req = ServletActionContext.getRequest();
	HttpServletResponse resp = ServletActionContext.getResponse();

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public User getModel() {
		return user;
	}
	
	//ע��
	public String regist(){
		//��User�����ݽ��з�������У�飬�Ѵ�����Ϣ����Map�У�����Map��User����session
		Map errors = userService.validateRegist(user,req);
		if(errors != null && errors.size() > 0){//���У��ʧ�ܣ�����ע���������ʧ��ԭ���User����
			req.setAttribute("errors", errors);
			req.setAttribute("userForm", user);
			return "toRegist";
		}
		userService.add(user); 
		req.setAttribute("msg", "ע��ɹ���<a target=\"parent\" href=\"/testPaperManager/pages/user/login.jsp\">��¼</a>");
		return "toMsg";
	}
	//��¼
	public String login(){
		User u = userService.find(user);
		if(u == null){
			req.setAttribute("loginError", "�ǳƻ��������");
			req.setAttribute("userForm", user);
			return "toLogin";
		}
		req.getSession().setAttribute("user", u);
		return "loginOk";
	}
	//�˳���¼
	public String exit(){
		//
		
		req.getSession().removeAttribute("user");
		return "exit";
	}
	//�޸�����
	public String toUpdatePwd(){
		return "toUpdatePwd";
	}
	public String updatePwd(){
		User loginUser = (User) req.getSession().getAttribute("user");
		String newPwd = req.getParameter("newPassword");
		loginUser.setPassword(newPwd);
		userService.updateUser(loginUser);	
		req.getSession().removeAttribute("user");
		req.setAttribute("msg", "�޸�����ɹ��������µ�¼ ...<a target=\"parent\" href=\"/testPaperManager/pages/user/login.jsp\">��¼<a>");
		return "toMsg";
	}
	//�޸ĸ�����Ϣ
	
	public String updateUsername(){
		User loginUser = (User) req.getSession().getAttribute("user");
		loginUser.setUsername(user.getUsername());
		userService.updateUser(loginUser);
		req.getSession().setAttribute("user", loginUser);
		return "toEdit";
	}
	public String updateGender(){
		User loginUser = (User) req.getSession().getAttribute("user");
		loginUser.setGender(user.getGender());
		userService.updateUser(loginUser);
		req.getSession().setAttribute("user", loginUser);
		return "toEdit";
		}
	public String updateEmail(){
		User loginUser = (User) req.getSession().getAttribute("user");
		loginUser.setEmail(user.getEmail());
		userService.updateUser(loginUser);
		req.getSession().setAttribute("user", loginUser);
		return "toEdit";
	}
	public String updatePhone(){
		User loginUser = (User) req.getSession().getAttribute("user");
		loginUser.setPhone(user.getPhone());
		userService.updateUser(loginUser);
		req.getSession().setAttribute("user", loginUser);
		return "toEdit";
	}
	public String updateSchool(){
		User loginUser = (User) req.getSession().getAttribute("user");
		loginUser.setSchool(user.getSchool());
		userService.updateUser(loginUser);
		req.getSession().setAttribute("user", loginUser);
		return "toEdit";
	}
	//ajaxУ��
	public String ajaxValidateUsername(){
		boolean b = userService.ajaxValidateUsername(user.getUsername());
		try {
			resp.getWriter().print(b + "");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return NONE;
	}
	public String ajaxValidateEmail(){
		boolean b = userService.ajaxValidateEmail(user.getEmail());
		try {
			resp.getWriter().print(b + "");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return NONE;
	}
	public String ajaxValidateVcode(){
		String trueVCode = (String) req.getSession().getAttribute("vCode");
		boolean b = trueVCode.equalsIgnoreCase(user.getVerifyCode());
		try {
			resp.getWriter().print(b + "");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return NONE;
	}
	//�޸�����ԭ����ajaxУ��
	public String ajaxValidatePwd(){
		boolean b = true;
		User loginUser = (User) req.getSession().getAttribute("user");
		if(!loginUser.getPassword().equals(user.getPassword())){//�������ԭ���벻��ȷ
			b = false;
		}
		try {
			resp.getWriter().print(b + "");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return NONE;
	}
	
}
