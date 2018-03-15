package com.yang.admin.book.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yang.goods.Book.domain.Book;
import com.yang.goods.Book.service.BookService;
import com.yang.goods.category.domain.Category;
import com.yang.goods.category.service.CategoryService;
import com.yang.goods.pager.PageBean;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class AdminBookServlet extends BaseServlet {

private BookService service = new BookService();
private CategoryService categoryservice = new CategoryService();

/*
 * 添加图书第一步
 * 查询所有一级分类，保存，转发到/adminjsps/admin/book/add.jsp
 */
public String addPre(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	List<Category> parents = categoryservice.findAll();
	request.setAttribute("parents", parents);
	return "f:/adminjsps/admin/book/add.jsp";
}
//异步请求  查询一级分类的子类
public void ajaxFindChildren(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	String pid = request.getParameter("pid");
	List<Category> children = categoryservice.findChildren(pid);
	String json = toJson(children);
	response.getWriter().print(json);
}
private String toJson(Category category){
	StringBuilder sb = new StringBuilder("{");
		sb.append("\"cid\"").append(":").append("\"").append(category.getCid()).append("\"");
		sb.append(",");
		sb.append("\"cname\"").append(":").append("\"").append(category.getCname()).append("\"");
	sb.append("}");
	return sb.toString();
}
//json:[{"cid":"xxx","cname":"yyy"},{},{}]
private String toJson(List<Category> cList){
	StringBuilder sb = new StringBuilder("[");
	for(int i=0;i < cList.size();i++){
		sb.append(toJson(cList.get(i)));
		if(i < cList.size() - 1){
			sb.append(",");
		}
	}
	sb.append("]");
	return sb.toString();
}
/*
 * 编辑图书
 * 1.保存表单数据到book对象
 * 2.封装cid到category中，再封装category到book中
 * 3.调用bookservice#edit，修改图书
 * 4.保存成功信息，转发到mag.jsp
 */
public String edit(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
	Map map = req.getParameterMap();
	Book book = CommonUtils.toBean(map, Book.class);
	String cid = req.getParameter("cid");
	Category category = new Category();
	category.setCid(cid);
	book.setCategory(category);
	service.edit(book);
	req.setAttribute("msg", "编辑图书成功！");
	return "f:/adminjsps/msg.jsp";
}
/*
 * 删除图书
 * 1.获取bid
 * 2.调用service#delete
 * 3.保存成功信息，转发到msg.jsp
 */
public String delete(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
	String bid = req.getParameter("bid");
	//删除图片文件
	Book book = service.findByBid(bid);
	String imgbName = book.getImage_b();
	String imgwName = book.getImage_w();
	int index = imgbName.lastIndexOf("/");
	imgbName =imgbName.substring(index+1);
	index = imgwName.lastIndexOf("/");
	imgwName = imgwName.substring(index+1);
	String savepath = this.getServletContext().getRealPath("/book_img");//获取真实路径	
	File imgbFile = new File(savepath, imgbName);
	File imgwFile = new File(savepath, imgwName);
	imgbFile.delete();
	imgwFile.delete();
	//删除数据库中指定图书数据
	service.delete(bid);
	req.setAttribute("msg", "删除图书成功！");
	return "f:/adminjsps/msg.jsp";
}

/*
 * 查找所有分类，转发到/adminjsps/admin/book/left.jsp
 */
public String findAll(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
	List<Category> parents = categoryservice.findAll();
	req.setAttribute("parents", parents);
	return "f:/adminjsps/admin/book/left.jsp";
}
	//得到pc方法:设置初始为1，若req参数"pc"有值，赋值给pc
	public int getPc(HttpServletRequest req){
		int pc = 1;
		String param = req.getParameter("pc");
		if(param != null && !param.trim().isEmpty()){
			try{
				pc = Integer.parseInt(param);
			}catch(RuntimeException e){}//如果param转换失败，赋值失败，pc依旧为1
		}
		return pc;
	}
	
	//得到url，并截掉pc部分参数
	public String getUrl(HttpServletRequest req){
		String url = req.getRequestURI() + "?" + req.getQueryString();//得到如：goods/BookServlet?methed=findByCategory&cid=xxx&pc=3
		int index = url.lastIndexOf("&pc");//若url存在&pc，index为"&pc"前一个字符的下标；反之，index = -1
		if(index != -1){//如果有pc，则截取掉pc
			url = url.substring(0, index);
		}
		return url;
	}
	
	/*
	 * 按分类查询
	 * 1.得到pc，若没有，设为1
	 * 2.得到url，作为分页导航的链接
	 * 3.得到查询条件，本方法是cid
	 * 4.调用BookService#findByCategory,得到PageBean，并传入参数url
	 * 5.保存PageBean,转发到/goods/adminjsps/admin/book/list.jsp
	 * 
	 */
	public String findByCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pc = getPc(request);
		String url = getUrl(request);
		String cid = request.getParameter("cid");
		PageBean<Book> pb = service.findByCategory(cid, pc);
		pb.setUrl(url);
		request.setAttribute("pagebean", pb);
		return "f:/adminjsps/admin/book/list.jsp";
	}
	/*
	 * 按书名模糊查询
	 * 1.得到pc，若没有，设为1
	 * 2.得到url，作为分页导航的链接
	 * 3.得到查询条件，本方法是bname
	 * 4.调用BookService#findByBname,得到PageBean，并传入参数url
	 * 5.保存PageBean,转发到/goods/jsps/book/list.jsp
	 */
	public String findByBname(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pc = getPc(request);
		String url = getUrl(request);
		String bname = request.getParameter("bname");
		PageBean<Book> pb = service.findByBname(bname, pc);
		pb.setUrl(url);
		request.setAttribute("pagebean", pb);
		return "f:/adminjsps/admin/book/list.jsp";
	}
	/*
	 * 按出版社查询
	 *   1.得到pc，若没有，设为1
	 * 2.得到url，作为分页导航的链接
	 * 3.得到查询条件，本方法是press
	 * 4.调用BookService#findByPress,得到PageBean，并传入参数url
	 * 5.保存PageBean,转发到/goods/jsps/book/list.jsp
	 */
	public String findByPress(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pc = getPc(request);
		String url = getUrl(request);
		String press = request.getParameter("press");
		PageBean<Book> pb = service.findByPress(press, pc);
		pb.setUrl(url);
		request.setAttribute("pagebean", pb);
		return "f:/adminjsps/admin/book/list.jsp";
	}
	
	/*
	 * 按作者查询
	 *   1.得到pc，若没有，设为1
	 * 2.得到url，作为分页导航的链接
	 * 3.得到查询条件，本方法是author
	 * 4.调用BookService#findByAuthor,得到PageBean，并传入参数url
	 * 5.保存PageBean,转发到/goods/jsps/book/list.jsp
	 */
	public String findByAuthor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pc = getPc(request);
		String url = getUrl(request);
		String author = request.getParameter("author");
		PageBean<Book> pb = service.findByAuthor(author, pc);
		pb.setUrl(url);
		request.setAttribute("pagebean", pb);
		return "f:/adminjsps/admin/book/list.jsp";
	}
	/*
	 * 多条件查询
	 *  1.得到pc，若没有，设为1
	 * 2.得到url，作为分页导航的链接
	 * 3.得到查询条件，本方法是bname,author,press
	 * 4.调用BookService#findByCombination,得到PageBean，并传入参数url
	 * 5.保存PageBean,转发到/goods/jsps/book/list.jsp
	 */
	public String findByCombination(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pc = getPc(request);
		String url = getUrl(request);
		Book criteria = new Book();
		criteria.setBname(request.getParameter("bname"));
		criteria.setPress(request.getParameter("press"));
		criteria.setAuthor(request.getParameter("author"));
		PageBean<Book> pb = service.findByCombination(criteria, pc);
		pb.setUrl(url);
		request.setAttribute("pagebean", pb);
		return "f:/adminjsps/admin/book/list.jsp";
	}
	/*
	 * 按id查询,load()
	 *  1.得到bid
	 *  2.调用BookService#findByBid,得到book
	 *  3.保存book,转发到/jsps/book/desc.jsp
	 */
	public String load(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bid = request.getParameter("bid");
		Book book = service.findByBid(bid);
		//获取所有一级分类，保存
		List<Category> parents = categoryservice.findAll();
		request.setAttribute("parents", parents);
		//根据二级分类查找一级分类，保存在book的category的parent
		Category category = categoryservice.findCategory(book.getCategory().getCid());
		book.setCategory(category);
		
		request.setAttribute("book", book);
		return "f:/adminjsps/admin/book/desc.jsp";
	}
	
	

}
