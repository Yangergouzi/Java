package cdut.yang.testPaperManager.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.support.DaoSupport;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import cdut.yang.testPaperManager.pojo.Grade;
import cdut.yang.testPaperManager.pojo.GradeSubject;
import cdut.yang.testPaperManager.pojo.Subject;
import cdut.yang.testPaperManager.pojo.User;

@Transactional
public class SubjectDao extends HibernateDaoSupport{
	private SessionFactory sessionFactory;

	//查找年级父类
	public List<Grade> findGradeParents() {
		String hql = "from Grade where parentId = ?";
		List<Grade> parents = (List<Grade>) this.getHibernateTemplate().find(hql,0);
		for(Grade parent : parents){
			List<Grade> children = (List<Grade>) this.getHibernateTemplate().find(hql,parent.getGradeId());
			parent.setChildren(children);
		}
		return parents;
	}

	public List<Subject> findSubjectsByGradeId(int gradeId) {
		String hql = "from GradeSubject where grade.gradeId = ?";
		List<Subject> subjects = new ArrayList<Subject>();
		List<GradeSubject> gradeSubjects = (List<GradeSubject>) this.getHibernateTemplate().find(hql, gradeId);
		for(GradeSubject gradeSubject : gradeSubjects){			
			int id = gradeSubject.getSubject().getSubjectId();
			Subject subject = this.getHibernateTemplate().get(Subject.class, id);
			subjects.add(subject);
		}		
		return subjects;
	}

	public String addMySubject(int gradeId, int subjectId, int uid) {
		String msg = "";
		// 根据gradeId和subjectId查找gradeSubject条目
		String hql = "from GradeSubject where grade.gradeId = ? and subject.subjectId = ?";
		List<GradeSubject> gSubjects = (List<GradeSubject>) this.getHibernateTemplate().find(hql, gradeId,subjectId);
		GradeSubject gradeSubject = null;
		if(gSubjects.size() > 0){
			gradeSubject = gSubjects.get(0);
		}else{
			msg = "添加失败！";
			return msg;
		}
		//将查出的gradeSubjectId联合uid存入user_gradeSubject
		User user = this.getHibernateTemplate().get(User.class, uid);
		Set<GradeSubject> gsSet = user.getGradeSubjectSet();
		if(gsSet.contains(gradeSubject)){
			msg = "已经添加该科目，不能重复添加！";
		}
		gsSet.add(gradeSubject);
		user.setGradeSubjectSet(gsSet);

		msg = "添加科目成功！<a href = \"/testPaperManager/subject_findMySubjects.action\">查看我的科目</a>";
		return msg;
	}
//查找用户的科目
	public Set<GradeSubject> findMySubject(int uid) {
		// 根据uid查出gradeSubjectSet
		User user = this.getHibernateTemplate().get(User.class, uid);
		Set<GradeSubject> gradeSubjects = user.getGradeSubjectSet();
		/*for(GradeSubject gradeSubject : gradeSubjects){
			this.getHibernateTemplate().load(GradeSubject.class, gradeSubject.getGradeSubjectId());
		}*/
		return gradeSubjects;
	}
//删除用户科目
	public void deleteMySubject(int uid,int gradeSubjectId) {
		// 先根据uid找出user，再删除gradeSubjectSet里对应的gradeSubject
		User user = this.getHibernateTemplate().load(User.class, uid);
		Set<GradeSubject> gradeSubjects = user.getGradeSubjectSet();
		Iterator<GradeSubject> it = gradeSubjects.iterator();
		while(it.hasNext()){
			if(it.next().getGradeSubjectId() == gradeSubjectId){
				it.remove();
			}
		}
	}
}
