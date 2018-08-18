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
	//上传试卷doc文件
	public String toAddTestPaper(){
		User loginUser = (User) req.getSession().getAttribute("user");
		User user = testPaperService.loadUser(loginUser.getUid());
		if(user.getGradeSubjectSet().size() == 0){//如果用户没有设置科目
			req.setAttribute("msg", "您还没有设置科目，请先添加您的科目！<a href=\"/testPaperManager/subject_toAddMySubject.action\">添加科目</a>");
			return "toMsg";
		}
		req.getSession().setAttribute("user", user);
		return "toAddTestPaper";
	}
	//添加上传的试卷
	public String add(){
		User loginUser = (User) req.getSession().getAttribute("user");
		int gradeSubjectId = Integer.parseInt(req.getParameter("gradeSubjectId"));
		testPaperService.add(testPaper,gradeSubjectId,loginUser,1);
		req.setAttribute("msg", "试卷录入成功！");
		return "toMsg";
	}
	//查看我的试卷
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
//查看公共试卷库
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
			//如果有接收到用户与题目关系筛选的参数，则遍历userQuestions来筛选
			String re = req.getParameter("relationship");
			int relationship = 0;
			relationship = Integer.parseInt(re);		
			pagebean = testPaperService.findByRelationship(loginUser.getUid(),pc,relationship);
		
			req.getSession().setAttribute("user", testPaperService.loadUser(loginUser.getUid()));
			req.setAttribute("pagebean", pagebean);
			req.setAttribute("re", relationship);
			return "toMyTestPapers";
		}
		
		//通过名称模糊查找
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
		//高级搜索
		public String advancedSearch(){
			String gsId = (String)req.getParameter("gradeSubjectId");
			if(!gsId.trim().equals("")){//如果表单值所属科目不为空
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
		//按时间排序
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
		//按收藏数排序
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
		//查看试卷
			public String load(){
				TestPaper tp = testPaperService.loadTestPaper(testPaper.getTestPaperId());
				req.setAttribute("testPaper", tp);
				return "testPaper";
			}
		//修改
			public String toUpdate(){
				TestPaper tp = testPaperService.loadTestPaper(testPaper.getTestPaperId());
				req.setAttribute("testPaper", tp);
				return "update";
			}
			public String update(){
				int gradeSubjectId = Integer.parseInt(req.getParameter("gradeSubjectId"));
				testPaperService.update(testPaper,gradeSubjectId);
				req.setAttribute("msg", "试卷修改成功！");
				return "toMsg";
			}
			//删除
			public String toDelete(){
				testPaperService.delete(testPaper);		
				return toMyTestPapers();
			}
			//移除
			public String remove(){
				User loginUser = (User) req.getSession().getAttribute("user");
				testPaperService.remove(testPaper.getTestPaperId(),loginUser.getUid());
				return toMyTestPapers();
			}
			
			//收藏
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
		
		//得到请求路径，并截掉pc参数部分
		public String getUrl(){
			String url = req.getRequestURI() + "?" + req.getQueryString();//请求全路径
			int index = url.lastIndexOf("&pc");
			if(index > 0){
				url = url.substring(0, index);
			}
			return url;
		}
		//得到当前页码
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
		//删除试题篮
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
	//组卷
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
			//上传试卷的html。并转换为doc保存，返回doc相对路径
			String docPath = uploadAction.uploadHtmlandConvert(html);
			//补全试卷信息并设置struts为1
			testPaper.setFilePath(docPath);
			testPaper.setStatus((byte) 1);
			testPaperService.update(testPaper);
			req.setAttribute("msg", "组卷成功！  <a href=\"/testPaperManager/testPaper_combine.action\">继续组卷</a>或<a href=\"/testPaperManager/testPaper_toMyTestPapers.action\">去我的试卷</a>");
			return "toMsg";
		}
	
	
	

}
