package cdut.yang.testPaperManager.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import cdut.yang.testPaperManager.pojo.User;

@Transactional
public class UserDao extends HibernateDaoSupport{
	private SessionFactory sessionFactory;
	//����û�
	public void add(User user){
		this.getHibernateTemplate().save(user);
	}
	//
	public User findUser(User user){
		String hql = "from User where username = ? and password = ?";
		List<User> userlist = (List<User>) this.getHibernateTemplate().find(hql, user.getUsername(),user.getPassword());
		if(userlist.size() > 0){
			return userlist.get(0);
		}
		return null;
	}
	
	//�����û���,���ڷ���fals
	public boolean findUsername(String username) {
		String hql = "from User where username = ?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username);
		if(list.size() > 0){
			return false;
		}
		return true;
	}
	//��������,���ڷ���fals
		public boolean findEmail(String email) {
			String hql = "from User where email = ?";
			List<User> list = (List<User>) this.getHibernateTemplate().find(hql, email);
			if(list.size() > 0){
				return false;
			}
			return true;
		}
		
		//�޸��û���Ϣ
		public void updateUser(User loginUser) {
			this.getHibernateTemplate().update(loginUser);
		}
	
}
