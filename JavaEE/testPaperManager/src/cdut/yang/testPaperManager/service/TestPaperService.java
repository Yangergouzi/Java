package cdut.yang.testPaperManager.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cdut.yang.testPaperManager.dao.TestPaperDao;
import cdut.yang.testPaperManager.pager.PageBean;
import cdut.yang.testPaperManager.pojo.GradeSubject;
import cdut.yang.testPaperManager.pojo.Question;
import cdut.yang.testPaperManager.pojo.TestPaper;
import cdut.yang.testPaperManager.pojo.User;
import cdut.yang.testPaperManager.pojo.UserTestPaper;

public class TestPaperService {
	private TestPaperDao testPaperDao= null;

	public void setTestPaperDao(TestPaperDao testPaperDao) {
		this.testPaperDao = testPaperDao;
	}

	public User loadUser(int uid) {
		return testPaperDao.loadUser(uid);
	}
//Ìí¼ÓÊÔ¾í
	public TestPaper add(TestPaper testPaper, int gradeSubjectId, User user, int status) {
		return testPaperDao.add(testPaper,gradeSubjectId,user,status);
	}

	public PageBean<UserTestPaper> findMyTestPapers(int uid, int pc) {
		return testPaperDao.findMyTestPapers(uid, pc);
	}

	public PageBean<UserTestPaper> findByRelationship(int uid, int pc,
			int relationship) {
		return testPaperDao.findByRelationship(uid, pc, relationship);
	}

	public PageBean<UserTestPaper> findByName(int uid, int pc, String paperName) {
		return testPaperDao.findByName(uid, pc, paperName);
	}

	public PageBean<UserTestPaper> combinationFind(int uid, int pc,
			TestPaper testPaper) {
		return testPaperDao.combinationFind(uid,pc,testPaper);
	}

	public PageBean<UserTestPaper> orderByTime(int uid, int pc) {
		return testPaperDao.orderByTime(uid,pc);
	}

	public PageBean<UserTestPaper> orderByNum(int uid, int pc) {
		return testPaperDao.orderByNum(uid,pc);
	}
//¼ÓÔØÊÔ¾í
	public TestPaper loadTestPaper(int testPaperId) {
		return testPaperDao.loadTestPaper(testPaperId);
	}
//ĞŞ¸Ä
	public void update(TestPaper testPaper, int gradeSubjectId) {
		testPaperDao.update(testPaper,gradeSubjectId);
	}
	public void update(TestPaper testPaper){
		testPaperDao.update(testPaper);
	}

	public PageBean<TestPaper> findPublicTestPapers(int pc) {
		return testPaperDao.findPublicTestPapers(pc);
	}

	public TestPaper findTempPaper(int uid) {
		return testPaperDao.findTempPaper(uid);
	}

	public void delete(TestPaper testPaper) {
		testPaperDao.delete(testPaper);
	}

	public int[] countType(int tid){
		return testPaperDao.countType(tid);
	}
	
	public Map<Integer, List<Question>> sortLoadQuestion(int tid){
		return testPaperDao.sortLoadQuestion(tid);
	}

	public void collect(User loginUser, TestPaper testPaper) {
		testPaperDao.collect(loginUser,testPaper);
	}

	public void remove(int testPaperId, int uid) {
		testPaperDao.remove(testPaperId,uid);
	}
//ÒÆ³ıÁÙÊ±ÊÔ¾íµÄÎÊÌâ
	public void removeQuestion(int testPaperId, long questionId) {
		testPaperDao.removeQuestion(testPaperId,questionId);
	}
	

	
}
