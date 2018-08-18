package cdut.yang.testPaperManager.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import cdut.yang.testPaperManager.pojo.UserTestPaper;
import cdut.yang.testPaperManager.service.TestPaperService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public class TestPaperAction extends ActionSupport implements ModelDriven<TestPaper>{
	private TestPaper testPaper= new TestPaper();
	private TestPaperService testPaperService = null;
	HttpServletRequest req = ServletActionContext.getRequest();
	HttpServletResponse resp = ServletActionContext.getResponse();
	
	@Override
	public TestPaper getModel() {
		return testPaper;
	}

	public void setTestPaperService(TestPaperService testPaperService) {
		this.testPaperService = testPaperService;
	}
	//�ϴ��Ծ�doc�ļ�
	public String toAddTestPaper(){
		User loginUser = (User) req.getSession().getAttribute("user");
		User user = testPaperService.loadUser(loginUser.getUid());
		if(user.getGradeSubjectSet().size() == 0){//����û�û�����ÿ�Ŀ
			req.setAttribute("msg", "����û�����ÿ�Ŀ������������Ŀ�Ŀ��<a href=\"/testPaperManager/subject_toAddMySubject.action\">��ӿ�Ŀ</a>");
			return "toMsg";
		}
		req.getSession().setAttribute("user", user);
		return "toAddTestPaper";
	}
	//����ϴ����Ծ�
	public String add(){
		User loginUser = (User) req.getSession().getAttribute("user");
		int gradeSubjectId = Integer.parseInt(req.getParameter("gradeSubjectId"));
		testPaperService.add(testPaper,gradeSubjectId,loginUser,1);
		req.setAttribute("msg", "�Ծ�¼��ɹ���");
		return "toMsg";
	}
	//�鿴�ҵ��Ծ�
		public String toMyTestPapers(){
			int pc = this.getPc();
			User loginUser = (User) req.getSession().getAttribute("user");
			PageBean<UserTestPaper> pagebean;	
			pagebean = testPaperService.findMyTestPapers(loginUser.getUid(),pc);
		    pagebean.setUrl(getUrl());
		    req.getSession().setAttribute("user", testPaperService.loadUser(loginUser.getUid()));
			req.setAttribute("pagebean", pagebean);
			return "toMyTestPapers";
		}
//�鿴�����Ծ��
		public String toPublicTestPapers(){
			int pc = this.getPc();	
			PageBean<TestPaper> pagebean;	
			pagebean = testPaperService.findPublicTestPapers(pc);
		    pagebean.setUrl(getUrl());
		    User loginUser = (User) req.getSession().getAttribute("user");
		    if(loginUser != null){
		    req.getSession().setAttribute("user", testPaperService.loadUser(loginUser.getUid()));
		    }
			req.setAttribute("pagebean", pagebean);			
			return "toPublicTestPapers";
		}
		
		public String findByRelationship(){
			int pc = this.getPc();
			User loginUser = (User) req.getSession().getAttribute("user");
			PageBean<UserTestPaper> pagebean;
			//����н��յ��û�����Ŀ��ϵɸѡ�Ĳ����������userQuestions��ɸѡ
			String re = req.getParameter("relationship");
			int relationship = 0;
			relationship = Integer.parseInt(re);		
			pagebean = testPaperService.findByRelationship(loginUser.getUid(),pc,relationship);
		
			req.getSession().setAttribute("user", testPaperService.loadUser(loginUser.getUid()));
			req.setAttribute("pagebean", pagebean);
			req.setAttribute("re", relationship);
			return "toMyTestPapers";
		}
		
		//ͨ������ģ������
		public String findByName(){
			User loginUser = (User) req.getSession().getAttribute("user");
			if(testPaper.getPaperName() == null || testPaper.getPaperName().trim().equals("")){
				return toMyTestPapers();
			}
			int pc = getPc();
			PageBean<UserTestPaper> pagebean = testPaperService .findByName(loginUser.getUid(),pc,testPaper.getPaperName());
			req.setAttribute("pagebean", pagebean);
			req.setAttribute("re", 99);
			return "toMyTestPapers";
		}
		//�߼�����
		public String advancedSearch(){
			String gsId = (String)req.getParameter("gradeSubjectId");
			if(!gsId.trim().equals("")){//�����ֵ������Ŀ��Ϊ��
				GradeSubject gradeSubject = new GradeSubject();
				gradeSubject.setGradeSubjectId(Integer.parseInt(gsId));
				testPaper.setGradeSubject(gradeSubject);
			}
			int pc = this.getPc();
			User loginUser = (User) req.getSession().getAttribute("user");
			PageBean<UserTestPaper> pagebean;	
			pagebean = testPaperService.combinationFind(loginUser.getUid(),pc,testPaper);
		    pagebean.setUrl(getUrl());
		    req.getSession().setAttribute("user", testPaperService.loadUser(loginUser.getUid()));
			req.setAttribute("pagebean", pagebean);
			req.setAttribute("re", 99);
			return "toMyTestPapers";
		}
		//��ʱ������
		public String orderByTime(){
			int pc = this.getPc();
			User loginUser = (User) req.getSession().getAttribute("user");
			PageBean<UserTestPaper> pagebean;	
			pagebean = testPaperService.orderByTime(loginUser.getUid(),pc);
		    pagebean.setUrl(getUrl());
		    req.getSession().setAttribute("user", testPaperService.loadUser(loginUser.getUid()));
			req.setAttribute("pagebean", pagebean);
			return "toMyTestPapers";
		}
		//���ղ�������
			public String orderByNum(){
				int pc = this.getPc();
				User loginUser = (User) req.getSession().getAttribute("user");
				PageBean<UserTestPaper> pagebean;	
				pagebean = testPaperService.orderByNum(loginUser.getUid(),pc);
			    pagebean.setUrl(getUrl());
			    req.getSession().setAttribute("user", testPaperService.loadUser(loginUser.getUid()));
				req.setAttribute("pagebean", pagebean);
				return "toMyTestPapers";
			}
		//�鿴�Ծ�
			public String load(){
				TestPaper tp = testPaperService.loadTestPaper(testPaper.getTestPaperId());
				req.setAttribute("testPaper", tp);
				return "testPaper";
			}
		//�޸�
			public String toUpdate(){
				TestPaper tp = testPaperService.loadTestPaper(testPaper.getTestPaperId());
				req.setAttribute("testPaper", tp);
				return "update";
			}
			public String update(){
				int gradeSubjectId = Integer.parseInt(req.getParameter("gradeSubjectId"));
				testPaperService.update(testPaper,gradeSubjectId);
				req.setAttribute("msg", "�Ծ��޸ĳɹ���");
				return "toMsg";
			}
			//ɾ��
			public String toDelete(){
				testPaperService.delete(testPaper);		
				return toMyTestPapers();
			}
			//�Ƴ�
			public String remove(){
				User loginUser = (User) req.getSession().getAttribute("user");
				testPaperService.remove(testPaper.getTestPaperId(),loginUser.getUid());
				return toMyTestPapers();
			}
			
			//�ղ�
			public String collect(){
				User loginUser = (User) req.getSession().getAttribute("user");
				testPaperService.collect(loginUser,testPaper);
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
		//ɾ��������
		public String delete(){	
			
			testPaper = (TestPaper) req.getSession().getAttribute("tempPaper");
			testPaperService.delete(testPaper);
			try {
				resp.getWriter().print(true + "");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return NONE;
		}
	//���
		public String combine(){
			 User loginUser = (User) req.getSession().getAttribute("user");			 
			 TestPaper tempPaper = testPaperService.findTempPaper(loginUser.getUid());
			 if(tempPaper != null){
				 int[] typeNums = testPaperService.countType(tempPaper.getTestPaperId());
				 req.setAttribute("typeNums", typeNums);
				 req.getSession().setAttribute("tempPaper", tempPaper);
			 }else{
				 req.getSession().removeAttribute("tempPaper");
				 req.getSession().setAttribute("user", testPaperService.loadUser(loginUser.getUid()));
			 }
			return "combine";
		}
		public String create(){
			User loginUser = (User) req.getSession().getAttribute("user");		
			 TestPaper tempPaper = new TestPaper();
			 int gradeSubjectId = Integer.parseInt(req.getParameter("gradeSubjectId"));	 
			 tempPaper = testPaperService.add(testPaper,gradeSubjectId,loginUser,0);	
			 req.getSession().setAttribute("tempPaper",testPaperService.loadTestPaper(tempPaper.getTestPaperId()));	
			 int[] typeNums = testPaperService.countType(tempPaper.getTestPaperId());
			 req.setAttribute("typeNums", typeNums);
			return "combine";
		}
		public String generate(){
			Map<Integer, List<Question>> map = testPaperService.sortLoadQuestion(testPaper.getTestPaperId());
			req.setAttribute("map", map);
			return "tempPaper";
		}
		public String removeQuestion(){
			long questionId = Long.parseLong(req.getParameter("questionId"));
			testPaper = (TestPaper) req.getSession().getAttribute("tempPaper");
			testPaperService.removeQuestion(testPaper.getTestPaperId(),questionId);
			return generate();
		}
		public String finishCombine(){
			String html = req.getParameter("html");
			UploadAction uploadAction = new UploadAction();
			//�ϴ��Ծ��html����ת��Ϊdoc���棬����doc���·��
			String docPath = uploadAction.uploadHtmlandConvert(html);
			//��ȫ�Ծ���Ϣ������strutsΪ1
			testPaper.setFilePath(docPath);
			testPaper.setStatus((byte) 1);
			testPaperService.update(testPaper);
			req.setAttribute("msg", "���ɹ���  <a href=\"/testPaperManager/testPaper_combine.action\">�������</a>��<a href=\"/testPaperManager/testPaper_toMyTestPapers.action\">ȥ�ҵ��Ծ�</a>");
			return "toMsg";
		}
	
	
	

}
