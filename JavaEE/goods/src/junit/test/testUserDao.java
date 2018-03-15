package junit.test;

import java.sql.SQLException;

import org.junit.Test;

import com.yang.goods.user.dao.UserDao;
import com.yang.goods.user.domain.User;

public class testUserDao {
	@Test
	public void testAjaxValidateLoginname(){
		UserDao dao = new UserDao();
		try {
			boolean b = dao.ajaxValidateLoginname("gdjb");
			System.out.println(b);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Test
	public void testAdd(){
		UserDao dao = new UserDao();
		User user = new User();
		user.setActivationCode("xxx");
		user.setEmail("88@qq.com");
		user.setLoginname("888");
		user.setLoginpass("888");
		user.setStatus(false);
		user.setUid("8888");
		
		try {
			dao.add(user);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
