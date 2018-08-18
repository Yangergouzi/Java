package cdut.yang.testPaperManager.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import cdut.yang.testPaperManager.pojo.UserTestPaper;

@Transactional
public class TestPaperDao extends HibernateDaoSupport{
	private SessionFactory sessionFactory;

	public User loadUser(int uid) {
		return this.getHibernateTemplate().load(User.class, uid);
	}
//添加试卷
	public TestPaper add(TestPaper testPaper, int gradeSubjectId, User user, int status) {
		GradeSubject gradeSubject = this.getHibernateTemplate().load(GradeSubject.class , gradeSubjectId);
		testPaper.setGradeSubject(gradeSubject);
		testPaper.setStatus((byte) status);
		this.getHibernateTemplate().save(testPaper);
		UserTestPaper userTestPaper = new UserTestPaper();
		userTestPaper.setTestPaper(testPaper);
		userTestPaper.setUser(user);
		userTestPaper.setRelationship((byte) 1);
		this.getHibernateTemplate().save(userTestPaper);
		return testPaper;
	}
//加载用户试卷库
	public PageBean<UserTestPaper> findMyTestPapers(int uid, int pc) {
		int ps = PageConstants.PAGE_SIZE;
		//底层hibernate实现分页
		SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		String hql = "from UserTestPaper where user.uid = ? ";
		Query query = session.createQuery(hql);
		query.setParameter(0, uid);
		query.setFirstResult(ps*(pc-1));
		query.setMaxResults(ps);
		List<UserTestPaper> userTestPapers = query.list();
		//总记录数
		int tr = 0;
		List<Object> list = (List<Object>) this.getHibernateTemplate().find("select count(*) from  UserTestPaper where user.uid = ?",uid);
		if(list != null && list.size() > 0){
			Object obj = list.get(0);
			Long l = (Long) obj;
			tr = l.intValue();
		} 		
		PageBean pageBean = new PageBean<UserTestPaper>();
		pageBean.setPageCurr(pc);
		pageBean.setPageSize(ps);
		pageBean.setTotalRec(tr);
		pageBean.setBeanList(userTestPapers);
		return pageBean;
	}
	
	public PageBean<UserTestPaper> findByRelationship(int uid,int pc, int relationship) {
		int ps = PageConstants.PAGE_SIZE;
		//底层hibernate实现分页
		SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		String hql = "from UserTestPaper where user.uid = ? ";
		if(relationship == 1 || relationship == 2){
			hql += "and relationship = " + relationship;
		}
		Query query = session.createQuery(hql);
		query.setParameter(0, uid);
		query.setFirstResult(ps*(pc-1));
		query.setMaxResults(ps);
		List<UserTestPaper> userTestPapers = query.list();
		//总记录数
		int tr = 0;
		List<Object> list = (List<Object>) this.getHibernateTemplate().find("select count(*) from  UserTestPaper where user.uid = ?",uid);
		if(list != null && list.size() > 0){
			Object obj = list.get(0);
			Long l = (Long) obj;
			tr = l.intValue();
		} 		
		PageBean pageBean = new PageBean<UserTestPaper>();
		pageBean.setPageCurr(pc);
		pageBean.setPageSize(ps);
		pageBean.setTotalRec(tr);
		pageBean.setBeanList(userTestPapers);
		return pageBean;
	}
	//通过名称查找
	public PageBean<UserTestPaper> findByName(int uid, int pc, String paperName) {
		int ps = PageConstants.PAGE_SIZE;
		//底层hibernate实现分页
		SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		String hql = "from UserTestPaper where user.uid = ? and testPaper.paperName like '%"+paperName+"%'";
		Query query = session.createQuery(hql);
		query.setParameter(0, uid);
		query.setFirstResult(ps*(pc-1));
		query.setMaxResults(ps);
		List<UserTestPaper> userTestPapers = query.list();
		//总记录数
		int tr = 0;
		hql = "select count(*) " + hql;
		List<Object> list = (List<Object>) this.getHibernateTemplate().find(hql,uid);
		if(list != null && list.size() > 0){
			Object obj = list.get(0);
			Long l = (Long) obj;
			tr = l.intValue();
		} 		
		PageBean pageBean = new PageBean<UserTestPaper>();
		pageBean.setPageCurr(pc);
		pageBean.setPageSize(ps);
		pageBean.setTotalRec(tr);
		pageBean.setBeanList(userTestPapers);
		return pageBean;
	}
	//多条件组合查询
	public PageBean<UserTestPaper> combinationFind(int uid, int pc,
			TestPaper testPaper) {
		int ps = PageConstants.PAGE_SIZE;
		//底层hibernate实现分页
		SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		String hql = "from UserTestPaper where user.uid = ?";
		String s = null;
		int i = 0;
		if((s = testPaper.getPaperName()) != null && !s.equals("")){
			hql = hql + " and testPaper.paperName like '%"+ s +"%' ";
		}
		if(testPaper.getGradeSubject() != null){
			hql = hql + " and testPaper.gradeSubject.gradeSubjectId = " + testPaper.getGradeSubject().getGradeSubjectId() ;
		}
		if((i = testPaper.getDifficulty()) != 0){
			hql = hql + " and testPaper.difficulty = "+ i;
		}
		
		Query query = session.createQuery(hql);
		query.setParameter(0, uid);
		query.setFirstResult(ps*(pc-1));
		query.setMaxResults(ps);
		List<UserTestPaper> userQuestions = query.list();
		//总记录数
		int tr = 0;
		hql = "select count(*) " + hql;
		List<Object> list = (List<Object>) this.getHibernateTemplate().find(hql,uid);
		if(list != null && list.size() > 0){
			Object obj = list.get(0);
			Long l = (Long) obj;
			tr = l.intValue();
		} 		
		PageBean pageBean = new PageBean<UserTestPaper>();
		pageBean.setPageCurr(pc);
		pageBean.setPageSize(ps);
		pageBean.setTotalRec(tr);
		pageBean.setBeanList(userQuestions);
		return pageBean;
	}
	//根据时间排序
	public PageBean<UserTestPaper> orderByTime(int uid, int pc) {
		int ps = PageConstants.PAGE_SIZE;
		//底层hibernate实现分页
		SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		String hql = "from UserTestPaper where user.uid = ? order by testPaper.createTime DESC";
		Query query = session.createQuery(hql);
		query.setParameter(0, uid);
		query.setFirstResult(ps*(pc-1));
		query.setMaxResults(ps);
		List<UserTestPaper> userTestPapers = query.list();
		//总记录数
		int tr = 0;
		List<Object> list = (List<Object>) this.getHibernateTemplate().find("select count(*) from  UserTestPaper where user.uid = ?",uid);
		if(list != null && list.size() > 0){
			Object obj = list.get(0);
			Long l = (Long) obj;
			tr = l.intValue();
		} 		
		PageBean pageBean = new PageBean<UserTestPaper>();
		pageBean.setPageCurr(pc);
		pageBean.setPageSize(ps);
		pageBean.setTotalRec(tr);
		pageBean.setBeanList(userTestPapers);
		return pageBean;
	}
	//按收藏数排序
	public PageBean<UserTestPaper> orderByNum(int uid, int pc) {
		int ps = PageConstants.PAGE_SIZE;
		//底层hibernate实现分页
		SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		String hql = "from UserTestPaper where user.uid = ? order by testPaper.collectionNum DESC";
		Query query = session.createQuery(hql);
		query.setParameter(0, uid);
		query.setFirstResult(ps*(pc-1));
		query.setMaxResults(ps);
		List<UserTestPaper> userTestPapers = query.list();
		//总记录数
		int tr = 0;
		List<Object> list = (List<Object>) this.getHibernateTemplate().find("select count(*) from  UserTestPaper where user.uid = ?",uid);
		if(list != null && list.size() > 0){
			Object obj = list.get(0);
			Long l = (Long) obj;
			tr = l.intValue();
		} 		
		PageBean pageBean = new PageBean<UserTestPaper>();
		pageBean.setPageCurr(pc);
		pageBean.setPageSize(ps);
		pageBean.setTotalRec(tr);
		pageBean.setBeanList(userTestPapers);
		return pageBean;
	}
	//加载试卷
	public TestPaper loadTestPaper(int testPaperId) {
		return this.getHibernateTemplate().load(TestPaper.class, testPaperId);
	}
	//修改
	public void update(TestPaper testPaper, int gradeSubjectId) {
		GradeSubject gradeSubject = this.getHibernateTemplate().load(GradeSubject.class , gradeSubjectId);
		testPaper.setGradeSubject(gradeSubject);
		this.getHibernateTemplate().update(testPaper);			
	}
	//
	public PageBean<TestPaper> findPublicTestPapers(int pc) {
		int ps = PageConstants.PAGE_SIZE;
		//底层hibernate实现分页
		SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		String hql = "from TestPaper where isPublic = 1 ";
		Query query = session.createQuery(hql);
		query.setFirstResult(ps*(pc-1));
		query.setMaxResults(ps);
		List<TestPaper> testPapers = query.list();
		
		//总记录数
		int tr = 0;
		List<Object> list = (List<Object>) this.getHibernateTemplate().find("select count(*) from  TestPaper where isPublic = 1");
		if(list != null && list.size() > 0){
			Object obj = list.get(0);
			Long l = (Long) obj;
			tr = l.intValue();
		} 		
		PageBean pageBean = new PageBean<UserTestPaper>();
		pageBean.setPageCurr(pc);
		pageBean.setPageSize(ps);
		pageBean.setTotalRec(tr);
		pageBean.setBeanList(testPapers);
		return pageBean;
	}
	//查找用户是否已有试题篮
	public TestPaper findTempPaper(int uid) {
		TestPaper testPaper = null;
		User user = this.getHibernateTemplate().load(User.class, uid);
		Set<UserTestPaper> set = user.getUserTestPaperSet();
		for(UserTestPaper ut : set){
			if(ut.getTestPaper().getStatus() == (byte)0){
				testPaper = ut.getTestPaper();
			}
		}
		return testPaper;
	}
	//删除
	public void delete(TestPaper testPaper) {
		List<UserTestPaper> utps = (List<UserTestPaper>) getHibernateTemplate().find("from UserTestPaper where testPaper.testPaperId = ?", testPaper.getTestPaperId());
		this.getHibernateTemplate().deleteAll(utps);
		this.getHibernateTemplate().delete(testPaper);
	}
	//数目统计
	public int[] countType(int tid){
		
		String hql = "select count(*) from TestPaperQuestion where testPaper.testPaperId = ? and question.type = ?";	
		int[] count = new int[6];
		for(int i = 1; i<=5;i++){
			List<Object> list = (List<Object>) this.getHibernateTemplate().find(hql,tid,i+"");
			if(list != null && list.size() > 0){
				Object obj = list.get(0);
				Long l = (Long) obj;
				count[i] = l.intValue();
			} 	
		}
	
		for(int i=1;i<=5;i++){
			count[0] += count[i];
		}
		
		return count;
	}
	
	//加载临时卷的题目并按题型分类
	public Map<Integer, List<Question>> sortLoadQuestion(int tid){
		
		Map<Integer, List<Question>> lists = new HashMap<Integer, List<Question>>();
		List<TestPaperQuestion> tpqs;
		List<Question> qs = null;
		String hql = "from TestPaperQuestion where testPaper.testPaperId = ? and question.type = ?";
		for(int i = 1;i < 6;i++){		
			qs = new ArrayList<Question>();
			tpqs = (List<TestPaperQuestion>) this.getHibernateTemplate().find(hql, tid,i+"");
			if(tpqs.size() > 0){//如果存在该题型的题目
				for(TestPaperQuestion tpq : tpqs){
					qs.add(tpq.getQuestion());
				}
				lists.put(i, qs);
			}
		}
		return lists;
	}
	
	public void update(TestPaper testPaper) {
		this.getHibernateTemplate().update(testPaper);
	}
	//收藏
	public void collect(User loginUser, TestPaper testPaper) {
		UserTestPaper ut = new UserTestPaper();
		List<UserTestPaper> uts = (List<UserTestPaper>) this.getHibernateTemplate().find("from UserTestPaper where user.uid = ? and testPaper.testPaperId = ?", loginUser.getUid(),testPaper.getTestPaperId());
		if(uts.size() == 0){
			testPaper = this.getHibernateTemplate().load(TestPaper.class,testPaper.getTestPaperId());
			int cNum = testPaper.getCollectionNum();
			testPaper.setCollectionNum(++cNum);
			ut.setTestPaper(testPaper);
			ut.setUser(loginUser);
			ut.setRelationship((byte) 2);
			this.getHibernateTemplate().save(ut);
		}
	}
	//移除
	public void remove(int testPaperId, int uid) {
		List<UserTestPaper> ut = (List<UserTestPaper>) getHibernateTemplate().find("from UserTestPaper where user.uid = ? and testPaper.testPaperId = ?", uid,testPaperId);
		if(ut.size() > 0){
			getHibernateTemplate().delete(ut.get(0));
		}
	}
	
	public void removeQuestion(int testPaperId, long questionId) {
		List<TestPaperQuestion> tqs = (List<TestPaperQuestion>) getHibernateTemplate().find("from TestPaperQuestion where testPaper.testPaperId = ? and question.questionId=?", testPaperId,questionId);
		if(tqs.size()> 0){
		getHibernateTemplate().delete(tqs.get(0));
		}
	}

	
	
	
}
