package cdut.yang.testPaperManager.pojo;

import java.util.Set;

public class Question {
	private long questionId;
	private String type;
	private String mainPoint;
	private String content;
	private String imagePath;
	private byte isObjective;
	private String trueAnswer;
	private String analysis;
	private byte difficulty;
	private byte isPublic;

	//”≥…‰
	private GradeSubject gradeSubject;
	private Set<UserQuestion> userQuestionSet;
	private Set<TestPaperQuestion> testPaperQuestionSet;
	
	public long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMainPoint() {
		return mainPoint;
	}
	public void setMainPoint(String mainPoint) {
		this.mainPoint = mainPoint;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public String getTrueAnswer() {
		return trueAnswer;
	}
	public void setTrueAnswer(String trueAnswer) {
		this.trueAnswer = trueAnswer;
	}
	public String getAnalysis() {
		return analysis;
	}
	
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	
	public byte getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(byte difficulty) {
		this.difficulty = difficulty;
	}

	public GradeSubject getGradeSubject() {
		return gradeSubject;
	}
	public void setGradeSubject(GradeSubject gradeSubject) {
		this.gradeSubject = gradeSubject;
	}
	public Set<UserQuestion> getUserQuestionSet() {
		return userQuestionSet;
	}
	public void setUserQuestionSet(Set<UserQuestion> userQuestionSet) {
		this.userQuestionSet = userQuestionSet;
	}
	public Set<TestPaperQuestion> getTestPaperQuestionSet() {
		return testPaperQuestionSet;
	}
	public void setTestPaperQuestionSet(Set<TestPaperQuestion> testPaperQuestionSet) {
		this.testPaperQuestionSet = testPaperQuestionSet;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	public byte getIsObjective() {
		return isObjective;
	}
	public void setIsObjective(byte isObjective) {
		this.isObjective = isObjective;
	}
	public byte getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(byte isPublic) {
		this.isPublic = isPublic;
	}
	
	
	
}
