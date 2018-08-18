package cdut.yang.testPaperManager.pojo;

import java.util.List;
import java.util.Set;

public class Grade {
	private int gradeId;
	private String gradeName;
	private int parentId;
	private int orderBy;
	private List<Grade> children;
	//与GradeSubject多对一
	private Set<GradeSubject> gradeSubjectSet;
	private Set<Subject> subjects;
	
	public int getGradeId() {
		return gradeId;
	}
	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public int getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}
	
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public List<Grade> getChildren() {
		return children;
	}
	public void setChildren(List<Grade> children) {
		this.children = children;
	}
	public Set<GradeSubject> getGradeSubjectSet() {
		return gradeSubjectSet;
	}
	public void setGradeSubjectSet(Set<GradeSubject> gradeSubjectSet) {
		this.gradeSubjectSet = gradeSubjectSet;
	}
	public Set<Subject> getSubjects() {
		return subjects;
	}
	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}
	
	
}
