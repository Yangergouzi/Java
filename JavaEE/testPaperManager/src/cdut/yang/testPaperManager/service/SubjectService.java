package cdut.yang.testPaperManager.service;

import java.util.List;
import java.util.Set;

import cdut.yang.testPaperManager.dao.SubjectDao;
import cdut.yang.testPaperManager.pojo.Grade;
import cdut.yang.testPaperManager.pojo.GradeSubject;
import cdut.yang.testPaperManager.pojo.Subject;
import cdut.yang.testPaperManager.pojo.User;

public class SubjectService {
	private SubjectDao subjectDao = null;

	public void setSubjectDao(SubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}

	public List<Grade> findGradeParents() {
		return subjectDao.findGradeParents();
	}

	public List<Subject> findSubjectsByGradeId(int gradeId) {
		return subjectDao.findSubjectsByGradeId(gradeId);
	}

	public String addMySubject(int gradeId, int subjectId, int uid) {
		return subjectDao.addMySubject(gradeId,subjectId,uid);
	}

	public Set<GradeSubject> findMySubject(int uid) {
		return subjectDao.findMySubject(uid);
	}

	public void deleteMySubject(int uid , int gradeSubjectId) {
		subjectDao.deleteMySubject(uid,gradeSubjectId);
	}
	
	
}
