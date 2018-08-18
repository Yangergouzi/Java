package cdut.yang.testPaperManager.pojo;

public class UserQuestion {
	private long userQuestionId;
	private byte relationship;//1是创造者，2是收藏者
	private byte difficulty;
	//
	private User user;
	private Question question;
	public long getUserQuestionId() {
		return userQuestionId;
	}
	public void setUserQuestionId(long userQuestionId) {
		this.userQuestionId = userQuestionId;
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
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	
	
}
