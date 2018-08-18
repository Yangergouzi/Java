package cdut.yang.testPaperManager.pojo;

import java.sql.Timestamp;
import java.util.Set;

public class TestPaper {
	private int testPaperId;
	private String paperName;
	private short totalScore;
	private short timeLimit;
	private int collectionNum;
	private Timestamp createTime;
	private byte difficulty;
	private byte isPublic;
	private String filePath;
	private byte status = 1;
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	//private User creater;
	//”≥…‰
	private GradeSubject gradeSubject;
	private Set<TestPaperQuestion> testPaperQuestionSet;
	private Set<UserTestPaper> userTestPaperSet;
	
	
	public int getTestPaperId() {
		return testPaperId;
	}
	public void setTestPaperId(int testPaperId) {
		this.testPaperId = testPaperId;
	}
	public String getPaperName() {
		return paperName;
	}
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	
	public int getCollectionNum() {
		return collectionNum;
	}
	public void setCollectionNum(int collectionNum) {
		this.collectionNum = collectionNum;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	public Set<TestPaperQuestion> getTestPaperQuestionSet() {
		return testPaperQuestionSet;
	}
	public void setTestPaperQuestionSet(Set<TestPaperQuestion> testPaperQuestionSet) {
		this.testPaperQuestionSet = testPaperQuestionSet;
	}
	public Set<UserTestPaper> getUserTestPaperSet() {
		return userTestPaperSet;
	}
	public void setUserTestPaperSet(Set<UserTestPaper> userTestPaperSet) {
		this.userTestPaperSet = userTestPaperSet;
	}
	public GradeSubject getGradeSubject() {
		return gradeSubject;
	}
	public void setGradeSubject(GradeSubject gradeSubject) {
		this.gradeSubject = gradeSubject;
	}
	public short getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(short totalScore) {
		this.totalScore = totalScore;
	}
	public short getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(short timeLimit) {
		this.timeLimit = timeLimit;
	}
	public byte getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(byte difficulty) {
		this.difficulty = difficulty;
	}
	public byte getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(byte isPublic) {
		this.isPublic = isPublic;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	
	
	
	
	
}
