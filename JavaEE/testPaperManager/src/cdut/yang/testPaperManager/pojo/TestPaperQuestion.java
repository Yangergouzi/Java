package cdut.yang.testPaperManager.pojo;

public class TestPaperQuestion {
	private long testPaperQuestionId;
	private byte bigQuestionOrder;
	private short questionOrder;
	private byte questionScore;
	
	//”≥…‰
	private TestPaper testPaper;
	private Question question;
	public long getTestPaperQuestionId() {
		return testPaperQuestionId;
	}
	public void setTestPaperQuestionId(long testPaperQuestionId) {
		this.testPaperQuestionId = testPaperQuestionId;
	}
	
	public TestPaper getTestPaper() {
		return testPaper;
	}
	public void setTestPaper(TestPaper testPaper) {
		this.testPaper = testPaper;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public byte getBigQuestionOrder() {
		return bigQuestionOrder;
	}
	public void setBigQuestionOrder(byte bigQuestionOrder) {
		this.bigQuestionOrder = bigQuestionOrder;
	}
	public short getQuestionOrder() {
		return questionOrder;
	}
	public void setQuestionOrder(short questionOrder) {
		this.questionOrder = questionOrder;
	}
	public byte getQuestionScore() {
		return questionScore;
	}
	public void setQuestionScore(byte questionScore) {
		this.questionScore = questionScore;
	}
	
	
}
