package cdut.yang.testPaperManager.service;

import java.util.Set;

import cdut.yang.testPaperManager.dao.QuestionDao;
import cdut.yang.testPaperManager.pager.PageBean;
import cdut.yang.testPaperManager.pojo.GradeSubject;
import cdut.yang.testPaperManager.pojo.Question;
import cdut.yang.testPaperManager.pojo.TestPaper;
import cdut.yang.testPaperManager.pojo.User;
import cdut.yang.testPaperManager.pojo.UserQuestion;

public class QuestionService {
	private QuestionDao questionDao= null;

	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	public void add(Question question, int gradeSubjectId, User user) {
		questionDao.add(question,gradeSubjectId,user);
	}

	public User loadUser(int uid) {
		return questionDao.loadUser(uid);
	}
//�鿴�ҵ���Ŀ
	public PageBean<UserQuestion> findMyQuestion(int uid,int pc) {
		return questionDao.findMyQuestion(uid,pc);
	}
	//���ݹ�ϵ����
	public PageBean<UserQuestion> findByRelationship(int uid,int pc,int relationship) {
		return questionDao.findByRelationship(uid,pc,relationship);
	}
//�������ݲ���
	public PageBean<UserQuestion> findByContent(int uid, int pc, String content) {
		return questionDao.findByContent(uid,pc,content);
	}
//��������ѯ
	public PageBean<UserQuestion> combinationFind(int uid, int pc,
			Question question) {
		return questionDao.combinationFind(uid,pc,question);
	}
//��������
	public Question loadQuestion(long questionId) {
		return questionDao.loadQuestion(questionId);
	}
//�޸�
	public void update(Question question, int gradeSubjectId) {
		questionDao.update(question,gradeSubjectId);
	}
//ɾ��
	public void delete(Question question) {
		questionDao.delete(question);
	}

	public PageBean<Question> findPublicQuestion(int pc) {
		return questionDao.findPublicQuestion(pc);
	}
//�ղ�
	public void collect(User user, Question question) {
		questionDao.collect(user,question);
	}

	

	public PageBean<Question> findQuestionToBasket(TestPaper tp,
			int uid,int pc,int type,int diff,int range) {
		return questionDao.findQuestionToBasket(tp,uid,pc,type,diff,range);
	}
	public void addToTempPaper(TestPaper testPaper,Question question){
		questionDao.addToTempPaper(testPaper, question);
	}
	
	public TestPaper loadTempPaper(int tid){
		return questionDao.loadTempPaper(tid);
	}

	public void remove(long questionId, int uid) {
		questionDao.remover(questionId,uid);
	}
	
}
