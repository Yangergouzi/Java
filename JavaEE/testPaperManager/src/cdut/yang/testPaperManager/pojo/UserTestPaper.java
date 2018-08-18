package cdut.yang.testPaperManager.pojo;

public class UserTestPaper {
	private long userTestPaperId;
	private byte relationship;
	private byte difficulty;
	//
	private User user;
	private TestPaper testPaper;
	public long getUserTestPaperId() {
		return userTestPaperId;
	}
	public void setUserTestPaperId(long userTestPaperId) {
		this.userTestPaperId = userTestPaperId;
	}
	public byte getRelationship() {
		return relationship;
	}
	public void setRelationship(byte relationship) {
		this.relationship = relationship;
	}
	public byte getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(byte difficulty) {
		this.difficulty = difficulty;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public TestPaper getTestPaper() {
		return testPaper;
	}
	public void setTestPaper(TestPaper testPaper) {
		this.testPaper = testPaper;
	}
	
	
}
