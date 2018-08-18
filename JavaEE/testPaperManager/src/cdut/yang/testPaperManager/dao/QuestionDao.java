package cdut.yang.testPaperManager.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import cdut.yang.testPaperManager.pager.PageBean;
import cdut.yang.testPaperManager.pager.PageConstants;
import cdut.yang.testPaperManager.pojo.GradeSubject;
import cdut.yang.testPaperManager.pojo.Question;
import cdut.yang.testPaperManager.pojo.TestPaper;
import cdut.yang.testPaperManager.pojo.TestPaperQuestion;
import cdut.yang.testPaperManager.pojo.User;
import cdut.yang.testPaperManager.pojo.UserQuestion;

@Transactional
public class QuestionDao extends HibernateDaoSupport{
	private SessionFactory sessionFactory;

	//单题入库
	public void add(Question question, int gradeSubjectId , User user) {
		GradeSubject gradeSubject = this.getHibernateTemplate().load(GradeSubject.class , gradeSubjectId);
		question.setGradeSubject(gradeSubject);
		this.getHibernateTemplate().save(question);
		UserQuestion userQuestion = new UserQuestion();
		userQuestion.setQuestion(question);
		userQuestion.setUser(user);
		userQuestion.setRelationship((byte) 1);
		this.getHibernateTemplate().save(userQuestion);
	}

	public User loadUser(int uid) {
		return this.getHibernateTemplate().load(User.class, uid);
	}
//加载用户试题库
	public PageBean<UserQuestion> findMyQuestion(int uid,int pc) {
		int ps = PageConstants.PAGE_SIZE;
		//底层hibernate实现分页
		SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		String hql = "from UserQuestion where user.uid = ? ";
		Query query = session.createQuery(hql);
		query.setParameter(0, uid);
		query.setFirstResult(ps*(pc-1));
		query.setMaxResults(ps);
		List<UserQuestion> userQuestions = query.list();
		//总记录数
		int tr = 0;
		List<Object> list = (List<Object>) this.getHibernateTemplate().find("select count(*) from  UserQuestion where user.uid = ?",uid);
		if(list != null && list.size() > 0){
			Object obj = list.get(0);
			Long l = (Long) obj;
			tr = l.intValue();
		} 		
		PageBean pageBean = new PageBean<UserQuestion>();
		pageBean.setPageCurr(pc);
		pageBean.setPageSize(ps);
		pageBean.setTotalRec(tr);
		pageBean.setBeanList(userQuestions);
		return pageBean;
	}
	public PageBean<UserQuestion> findByRelationship(int uid,int pc, int relationship) {
		int ps = PageConstants.PAGE_SIZE;
		//底层hibernate实现分页
		SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		String hql = "from UserQuestion where user.uid = ? ";
		if(relationship == 1 || relationship == 2){
			hql += "and relationship = " + relationship;
		}
		Query query = session.createQuery(hql);
		query.setParameter(0, uid);
		query.setFirstResult(ps*(pc-1));
		query.setMaxResults(ps);
		List<UserQuestion> userQuestions = query.list();
		//总记录数
		int tr = 0;
		List<Object> list = (List<Object>) this.getHibernateTemplate().find("select count(*) from  UserQuestion where user.uid = ?",uid);
		if(list != null && list.size() > 0){
			Object obj = list.get(0);
			Long l = (Long) obj;
			tr = l.intValue();
		} 		
		PageBean pageBean = new PageBean<UserQuestion>();
		pageBean.setPageCurr(pc);
		pageBean.setPageSize(ps);
		pageBean.setTotalRec(tr);
		pageBean.setBeanList(userQuestions);
		return pageBean;
	}
//根据内容模糊查找
	public PageBean<UserQuestion> findByContent(int uid, int pc, String content) {
		int ps = PageConstants.PAGE_SIZE;
		//底层hibernate实现分页
		SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		String hql = "from UserQuestion where user.uid = ? and question.content like '%"+content+"%'";
		Query query = session.createQuery(hql);
		query.setParameter(0, uid);
		query.setFirstResult(ps*(pc-1));
		query.setMaxResults(ps);
		List<UserQuestion> userQuestions = query.list();
		//总记录数
		int tr = 0;
		hql = "select count(*) " + hql;
		List<Object> list = (List<Object>) this.getHibernateTemplate().find(hql,uid);
		if(list != null && list.size() > 0){
			Object obj = list.get(0);
			Long l = (Long) obj;
			tr = l.intValue();
		} 		
		PageBean pageBean = new PageBean<UserQuestion>();
		pageBean.setPageCurr(pc);
		pageBean.setPageSize(ps);
		pageBean.setTotalRec(tr);
		pageBean.setBeanList(userQuestions);
		return pageBean;
	}
//组合查询
	public PageBean<UserQuestion> combinationFind(int uid, int pc,
			Question question) {
		int ps = PageConstants.PAGE_SIZE;
		//底层hibernate实现分页
		SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		String hql = "from UserQuestion where user.uid = ?";
		String s = null;
		int i = 0;
		if((s = question.getContent()) != null && !s.equals("")){
			hql = hql + " and question.content like '%"+ s +"%' ";
		}
		if((s = question.getType()) != null && !s.equals("")){
			hql = hql + " and question.type like '%"+ s +"%' ";
		}
		if(question.getGradeSubject() != null){
			hql = hql + " and question.gradeSubject.gradeSubjectId = " + question.getGradeSubject().getGradeSubjectId() ;
		}
		if((i = question.getDifficulty()) != 0){
			hql = hql + " and question.difficulty = "+ i;
		}
		if((s = question.getMainPoint()) != null && !s.equals("")){
			hql = hql + " and question.mainPoint like '%"+ s +"%' ";
		}
		Query query = session.createQuery(hql);
		query.setParameter(0, uid);
		query.setFirstResult(ps*(pc-1));
		query.setMaxResults(ps);
		List<UserQuestion> userQuestions = query.list();
		//总记录数
		int tr = 0;
		hql = "select count(*) " + hql;
		List<Object> list = (List<Object>) this.getHibernateTemplate().find(hql,uid);
		if(list != null && list.size() > 0){
			Object obj = list.get(0);
			Long l = (Long) obj;
			tr = l.intValue();
		} 		
		PageBean pageBean = new PageBean<UserQuestion>();
		pageBean.setPageCurr(pc);
		pageBean.setPageSize(ps);
		pageBean.setTotalRec(tr);
		pageBean.setBeanList(userQuestions);
		return pageBean;
	}
//根据id加载试题
	public Question loadQuestion(long questionId) {
		return this.getHibernateTemplate().load(Question.class, questionId);
	}
		
//修改试题
	public void update(Question question, int gradeSubjectId) {
		GradeSubject gradeSubject = this.getHibernateTemplate().load(GradeSubject.class , gradeSubjectId);
		question.setGradeSubject(gradeSubject);
		this.getHibernateTemplate().update(question);		
	}
//删除
	public void delete(Question question) {
		List<UserQuestion> uqs = (List<UserQuestion>) this.getHibernateTemplate().find("from UserQuestion where question.questionId = ?", question.getQuestionId());
		this.getHibernateTemplate().deleteAll(uqs);
		this.getHibernateTemplate().delete(question);
	}
//查询公共题目
	public PageBean<Question> findPublicQuestion(int pc) {
		int ps = PageConstants.PAGE_SIZE;
		//底层hibernate实现分页
		SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Question where isPublic = 1 ";
		Query query = session.createQuery(hql);
		query.setFirstResult(ps*(pc-1));
		query.setMaxResults(ps);
		List<Question> questions = query.list();
		//总记录数
		int tr = 0;
		List<Object> list = (List<Object>) this.getHibernateTemplate().find("select count(*) from  Question where isPublic = 1");
		if(list != null && list.size() > 0){
			Object obj = list.get(0);
			Long l = (Long) obj;
			tr = l.intValue();
		} 		
		PageBean pageBean = new PageBean<UserQuestion>();
		pageBean.setPageCurr(pc);
		pageBean.setPageSize(ps);
		pageBean.setTotalRec(tr);
		pageBean.setBeanList(questions);
		return pageBean;
	}
//收藏
	public void collect(User user, Question question) {
		
		UserQuestion uq = new UserQuestion();
		question = this.getHibernateTemplate().load(Question.class, question.getQuestionId());
		uq.setQuestion(question);
		uq.setUser(user);
		
		List<UserQuestion> uqs = (List<UserQuestion>) this.getHibernateTemplate().find("from UserQuestion where user.uid = ? and question.questionId = ?", user.getUid(),question.getQuestionId());
		if(uqs.size() == 0){
			uq.setRelationship((byte) 2);
			this.getHibernateTemplate().save(uq);
		}
		
	}
//添加到临时试卷
	public void addToTempPaper(TestPaper testPaper,Question question){
		TestPaperQuestion tpq = new TestPaperQuestion();
		question = this.getHibernateTemplate().load(Question.class, question.getQuestionId());
		testPaper = this.getHibernateTemplate().load(TestPaper.class, testPaper.getTestPaperId());
		tpq.setQuestion(question);
		tpq.setTestPaper(testPaper);
		this.getHibernateTemplate().saveOrUpdate(tpq);
				
	}
	
	public TestPaper loadTempPaper(int tid) {
		return this.getHibernateTemplate().load(TestPaper.class, tid);
	}
	//根据各种条件查找能够被加入临时试卷的题目
		public PageBean<Question> findQuestionToBasket(TestPaper tp,
				int uid,int pc,int type,int diff,int range) {
			TestPaper tempPaper = this.loadTempPaper(tp.getTestPaperId());
			int ps = PageConstants.PAGE_SIZE;
			
			Set<TestPaperQuestion> tpqs = tempPaper.getTestPaperQuestionSet();
			List<Question> tempQuestions = new ArrayList<Question>();
			List<Question> questions = new ArrayList<Question>();
			for(TestPaperQuestion tpq : tpqs){
				tempQuestions.add(tpq.getQuestion());
			}
			//底层hibernate实现分页
			SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
			Session session = sessionFactory.getCurrentSession();
			int gsId = tp.getGradeSubject().getGradeSubjectId();
			String hql;
			if(range == 0){
				hql = "from Question where  gradeSubject.gradeSubjectId = ?";
				if(type != 0){
					hql = hql + " and type = "+ type +" ";
				}
				if(diff != 0){
					hql = hql + " and difficulty = "+ diff +" ";
				}
				Query query = session.createQuery(hql);
				query.setParameter(0, gsId);
				query.setFirstResult(ps*(pc-1));
				query.setMaxResults(ps);
				List<Question> qs = query.list();
			
				for(Question q : qs){
					if(!tempQuestions.contains(q)){
						questions.add(q);
					}
				}
			}else{//如果范围是个人题库
				hql = "from UserQuestion where user.uid = ? and question.gradeSubject.gradeSubjectId = ?";
				if(type != 0){
					hql = hql + " and question.type = "+ type +" ";
				}
				if(diff != 0){
					hql = hql + " and question.difficulty = "+ diff +" ";
				}
				Query query = session.createQuery(hql);
				query.setParameter(0, uid);
				query.setParameter(1, gsId);
				query.setFirstResult(ps*(pc-1));
				query.setMaxResults(ps);
				List<UserQuestion> userQuestions = query.list();
			
				for(UserQuestion uq : userQuestions){
					if(!tempQuestions.contains(uq.getQuestion())){
						questions.add(uq.getQuestion());
					}
				}
			}
			//总记录数
			int tr = 0;
			hql = "select count(*) " + hql;
			List<Object> list = null;
			if(range == 0){
				list = (List<Object>) this.getHibernateTemplate().find(hql,gsId);
			}else{
				list = (List<Object>) this.getHibernateTemplate().find(hql,uid,gsId);
			}
			if(list != null && list.size() > 0){
				Object obj = list.get(0);
				Long l = (Long) obj;
				tr = l.intValue();
			} 	
		
			PageBean pageBean = new PageBean<Question>();
			pageBean.setPageCurr(pc);
			pageBean.setPageSize(ps);
			pageBean.setTotalRec(tr);
			pageBean.setBeanList(questions);
			return pageBean;
		}
//移除，删除用户与题目关系
		public void remover(long questionId, int uid) {
			List<UserQuestion> uq = (List<UserQuestion>) getHibernateTemplate().find("from UserQuestion where user.uid = ? and question.questionId = ?", uid,questionId);
			if(uq.size() > 0){
				getHibernateTemplate().delete(uq.get(0));
			}
		}
	
	
}
