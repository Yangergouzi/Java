package cdut.yang.testPaperManager.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * @author yang
 *
 */
public class User {
	private int uid;
	private String username;
	private String password;
	private String gender;
	private String email;
	private String phone;
	private String school;
	private int userIdentity;
	private String repassword;
	private String verifyCode;
	//与GradeSubject多对多
	private Set<GradeSubject> gradeSubjectSet = new HashSet<GradeSubject>();
	private Set<UserTestPaper> userTestPaperSet;
	private Set<UserQuestion> userQuestionSet;
	//不写入数据库的
	private TestPaperQuestion basket = new TestPaperQuestion();
	
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public int getUserIdentity() {
		return userIdentity;
	}
	public void setUserIdentity(int userIdentity) {
		this.userIdentity = userIdentity;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public Set<GradeSubject> getGradeSubjectSet() {
		return gradeSubjectSet;
	}
	public void setGradeSubjectSet(Set<GradeSubject> gradeSubjectSet) {
		this.gradeSubjectSet = gradeSubjectSet;
	}
	public Set<UserTestPaper> getUserTestPaperSet() {
		return userTestPaperSet;
	}
	public void setUserTestPaperSet(Set<UserTestPaper> userTestPaperSet) {
		this.userTestPaperSet = userTestPaperSet;
	}
	public Set<UserQuestion> getUserQuestionSet() {
		return userQuestionSet;
	}
	public void setUserQuestionSet(Set<UserQuestion> userQuestionSet) {
		this.userQuestionSet = userQuestionSet;
	}
	public TestPaperQuestion getBasket() {
		return basket;
	}
	public void setBasket(TestPaperQuestion basket) {
		this.basket = basket;
	}
	
	
	
	
	
	
	
	
	
}
