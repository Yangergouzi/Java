package cdut.yang.testPaperManager.action;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cdut.yang.testPaperManager.pojo.Grade;
import cdut.yang.testPaperManager.pojo.GradeSubject;
import cdut.yang.testPaperManager.pojo.Subject;
import cdut.yang.testPaperManager.pojo.User;
import cdut.yang.testPaperManager.service.SubjectService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class SubjectAction extends ActionSupport implements ModelDriven<Subject>{
	private Subject subject = new Subject();
	private SubjectService subjectService = null;
	HttpServletRequest req = ServletActionContext.getRequest();
	HttpServletResponse resp = ServletActionContext.getResponse();

	@Override
	public Subject getModel() {
		return subject;
	}
	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}
	
	//
	public String toAddMySubject(){
		List<Grade> gradeParents = subjectService.findGradeParents();
		//根据年级寻找对应的科目
		req.setAttribute("gradeParents", gradeParents);
		String method = req.getParameter("method");
		if(method != null && method.endsWith("select")){
			req.setAttribute("method", method);
		}
		return "toAMS";
	}
	//添加到我的科目
	public String addMySubject(){
		int gradeId = Integer.parseInt(req.getParameter("gradeId"));
		//当前登录用户
		User loginUser = (User) req.getSession().getAttribute("user");
		//调用service方法，得到信息（成功或失败）
		String msg = subjectService.addMySubject(gradeId,subject.getSubjectId(),loginUser.getUid());
		req.setAttribute("msg", msg);		
		return "toMsg";
	}
	//查看我的科目
	public String findMySubjects(){
		//当前登录用户
		User loginUser = (User) req.getSession().getAttribute("user");
		Set<GradeSubject> gradeSubjects = subjectService.findMySubject(loginUser.getUid());
		req.setAttribute("mySubjects", gradeSubjects);
		return "toMySubjects";
	}
	//删除我的科目
	public String deleteMySubject(){
		//当前登录用户
		User loginUser = (User) req.getSession().getAttribute("user");
		int gradeSubjectId = Integer.parseInt(req.getParameter("gradeSubjectId"));
		subjectService.deleteMySubject(loginUser.getUid(),gradeSubjectId);
		return findMySubjects();
	}
	//ajax 根据年级寻找对应的科目
	public String findSubjectsByGradeId(){
	//  设置response的ContentType解决中文乱码  
		 resp.setContentType("text/html;charset=UTF-8"); 
		   
		int gradeId = Integer.parseInt(req.getParameter("gradeId"));
		List<Subject> subjects = subjectService.findSubjectsByGradeId(gradeId);
		//{"subjects":[{"subjectId" : "1" , "subjectName" : "数学"},{"id" : "2" , "subjectName" : "语文"}]}
		String json = "{\"subjects\":[";
		String sub = "";
		for(Subject subject : subjects){
			sub += "{\"subjectId\":\"" + subject.getSubjectId() + "\",\"subjectName\":\"" + subject.getSubjectName() + "\"},";
		}	
		json += sub.substring(0,sub.length() - 1) + "]}";
//		System.out.println(json);
	
		try {
			resp.getWriter().print(json);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return NONE;
	}

}
