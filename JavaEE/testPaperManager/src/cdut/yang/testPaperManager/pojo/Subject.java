package cdut.yang.testPaperManager.pojo;

import java.util.Set;

public class Subject {
	private int subjectId;
	private String subjectName;
	private int orderBy;
	
	//与GradeSubject多对一
	private Set<GradeSubject> gradeSubjectSet;
	
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	public int getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}
	public Set<GradeSubject> getGradeSubjectSet() {
		return gradeSubjectSet;
	}
	public void setGradeSubjectSet(Set<GradeSubject> gradeSubjectSet) {
		this.gradeSubjectSet = gradeSubjectSet;
	}
	
	
	
}
