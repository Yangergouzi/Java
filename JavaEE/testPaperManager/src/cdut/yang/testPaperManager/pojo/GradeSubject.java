package cdut.yang.testPaperManager.pojo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GradeSubject {
	private int gradeSubjectId;
	//��Gradeһ�Զ�
	private Grade grade;
	//��Subjectһ�Զ�
	private Subject subject;
	//��User��Զ�
	private Set<User> userSet = new HashSet<User>();
	//��Questionһ�Զ�
	private Set<Question> questionSet = new HashSet<Question>();
	
	public int getGradeSubjectId() {
		return gradeSubjectId;
	}
	public void setGradeSubjectId(int gradeSubjectId) {
		this.gradeSubjectId = gradeSubjectId;
	}
	public Grade getGrade() {
		return grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public Set<User> getUserSet() {
		return userSet;
	}
	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}
	public Set<Question> getQuestionSet() {
		return questionSet;
	}
	public void setQuestionSet(Set<Question> questionSet) {
		this.questionSet = questionSet;
	}
	
	
	
}
