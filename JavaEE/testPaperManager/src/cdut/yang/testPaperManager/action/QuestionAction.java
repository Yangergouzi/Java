package cdut.yang.testPaperManager.action;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cdut.yang.testPaperManager.pager.PageBean;
import cdut.yang.testPaperManager.pojo.GradeSubject;
import cdut.yang.testPaperManager.pojo.Question;
import cdut.yang.testPaperManager.pojo.TestPaper;
import cdut.yang.testPaperManager.pojo.TestPaperQuestion;
import cdut.yang.testPaperManager.pojo.User;
import cdut.yang.testPaperManager.pojo.UserQuestion;
import cdut.yang.testPaperManager.service.QuestionService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public class QuestionAction extends ActionSupport implements ModelDriven<Question>{
	private Question question = new Question();
	private QuestionService questionService = null;
	HttpServletRequest req = ServletActionContext.getRequest();
	HttpServletResponse resp = ServletActionContext.getResponse();

	@Override
	public Question getModel() {
		return question;
	}

	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}
	
	
	//������Ŀ���
	public String toAddQuestion(){
		User loginUser = (User) req.getSession().getAttribute("user");
		User user = questionService.loadUser(loginUser.getUid());
		if(user.getGradeSubjectSet().size() == 0){//����û�û�����ÿ�Ŀ
			req.setAttribute("msg", "����û�����ÿ�Ŀ������������Ŀ�Ŀ��<a href=\"/testPaperManager/subject_toAddMySubject.action\">��ӿ�Ŀ</a>");
			return "toMsg";
		}
		req.getSession().setAttribute("user", user);
		return "toAdd";
	}
	public String add(){
		User loginUser = (User) req.getSession().getAttribute("user");
		int gradeSubjectId = Integer.parseInt(req.getParameter("gradeSubjectId"));
		questionService.add(question,gradeSubjectId,loginUser);
		req.setAttribute("msg", "��Ŀ¼��ɹ���<a href=\"/testPaperManager/question_toAddQuestion.action\">����¼��</a>");
		return "toMsg";
	}
	//�鿴�ҵ���Ŀ
	public String toMyQuestions(){
		int pc = this.getPc();
		User loginUser = (User) req.getSession().getAttribute("user");
		PageBean<UserQuestion> pagebean;	
		pagebean = questionService.findMyQuestion(loginUser.getUid(),pc);
	    pagebean.setUrl(getUrl());
	    req.getSession().setAttribute("user", questionService.loadUser(loginUser.getUid()));
		req.setAttribute("pagebean", pagebean);
		return "toMyQuestions";
	}
	//�������
	public String toPublicQuestions(){
		int pc = this.getPc();
		PageBean<Question> pagebean;	
		pagebean = questionService.findPublicQuestion(pc);
	    pagebean.setUrl(getUrl());
	    User loginUser = (User) req.getSession().getAttribute("user");
	    if(loginUser != null){
	    req.getSession().setAttribute("user", questionService.loadUser(loginUser.getUid()));
	    }
		req.setAttribute("pagebean", pagebean);
		return "toPublicQuestions";
	}
	
	
	public String findByRelationship(){
		int pc = this.getPc();
		User loginUser = (User) req.getSession().getAttribute("user");
		PageBean<UserQuestion> pagebean;
		//����н��յ��û�����Ŀ��ϵɸѡ�Ĳ����������userQuestions��ɸѡ
		String re = req.getParameter("relationship");
		int relationship = 0;
		relationship = Integer.parseInt(re);		
		pagebean = questionService.findByRelationship(loginUser.getUid(),pc,relationship);
	
		req.getSession().setAttribute("user", questionService.loadUser(loginUser.getUid()));
		req.setAttribute("pagebean", pagebean);
		req.setAttribute("re", relationship);
		return "toMyQuestions";
	}
	//ͨ������ģ������
	public String findByContent(){
		User loginUser = (User) req.getSession().getAttribute("user");
		if(question.getContent() == null || question.getContent().trim().equals("")){
			return toMyQuestions();
		}
		int pc = getPc();
		PageBean<UserQuestion> pagebean = questionService.findByContent(loginUser.getUid(),pc,question.getContent());
		req.setAttribute("pagebean", pagebean);
		req.setAttribute("re", 99);
		return "toMyQuestions";
	}
	//�߼�����
	public String advancedSearch(){
		String gsId = (String)req.getParameter("gradeSubjectId");
		if(!gsId.trim().equals("")){//�����ֵ������Ŀ��Ϊ��
			GradeSubject gradeSubject = new GradeSubject();
			gradeSubject.setGradeSubjectId(Integer.parseInt(gsId));
			question.setGradeSubject(gradeSubject);
		}
		int pc = this.getPc();
		User loginUser = (User) req.getSession().getAttribute("user");
		PageBean<UserQuestion> pagebean;	
		pagebean = questionService.combinationFind(loginUser.getUid(),pc,question);
	    pagebean.setUrl(getUrl());
	    req.getSession().setAttribute("user", questionService.loadUser(loginUser.getUid()));
		req.setAttribute("pagebean", pagebean);
		req.setAttribute("re", 99);
		return "toMyQuestions";
	}
	//���ص�������
	public String loadQuestion(){
		Question q = questionService.loadQuestion(question.getQuestionId());
		req.setAttribute("question", q);
		return "question";
	}
	//�޸�����
	public String toUpdate(){
		Question q = questionService.loadQuestion(question.getQuestionId());
		req.setAttribute("question", q);
		return "update";
	}
	public String update(){
		int gradeSubjectId = Integer.parseInt(req.getParameter("gradeSubjectId"));
		questionService.update(question,gradeSubjectId);
		req.setAttribute("msg", "��Ŀ�޸ĳɹ���");
		return "toMsg";
	}
	//ɾ��
		public String toDelete(){
			questionService.delete(question);		
			return toMyQuestions();
		}
	//�Ƴ�
		public String remove(){
			User loginUser = (User) req.getSession().getAttribute("user");
			questionService.remove(question.getQuestionId(),loginUser.getUid());
			return toMyQuestions();
		}
		
	//�ղ�
		public String collect(){
			User loginUser = (User) req.getSession().getAttribute("user");
			questionService.collect(loginUser,question);
			try {
				resp.getWriter().print(true + "");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return NONE;
		}
	
	//�õ�����·�������ص�pc��������
		public String getUrl(){
			String url = req.getRequestURI() + "?" + req.getQueryString();//����ȫ·��
			int index = url.lastIndexOf("&pc");
			if(index > 0){
				url = url.substring(0, index);
			}
			return url;
		}
		//�õ���ǰҳ��
		public int getPc(){
			int pc = 1;
			String param = req.getParameter("pc");
			if(param != null && !param.trim().equals("")){
				try{
					pc = Integer.parseInt(param);
				}catch(RuntimeException e){}
			}
			return pc;
		}
	//�˹����
		public String addToBasket(){
			int pc = getPc();
			int type = Integer.parseInt(req.getParameter("type"));
			int diff = Integer.parseInt(req.getParameter("diff"));
			int range = Integer.parseInt(req.getParameter("range"));		
			User loginUser = (User) req.getSession().getAttribute("user");
			TestPaper tempPaper = (TestPaper) req.getSession().getAttribute("tempPaper");			
			PageBean<Question> pagebean = questionService.findQuestionToBasket(tempPaper,loginUser.getUid(),pc,type,diff,range);
		//	req.getSession().setAttribute("tempPaper", questionService.loadTempPaper(tempPaper.getTestPaperId()));
			req.setAttribute("pagebean", pagebean);		
			return "addToBasket";
		}
		public String addToTempPaper(){
			TestPaper tempPaper = (TestPaper) req.getSession().getAttribute("tempPaper");	
			questionService.addToTempPaper(tempPaper, question);
			try {
				resp.getWriter().print(true + "");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return NONE;
		}



	
	

}
