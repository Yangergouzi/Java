package com.yang.goods.Book.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yang.goods.Book.domain.Book;
import com.yang.goods.Book.service.BookService;
import com.yang.goods.pager.PageBean;

import cn.itcast.servlet.BaseServlet;

public class BookServlet extends BaseServlet {
	private BookService service = new BookService();
	
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
	 * 5.保存PageBean,转发到/goods/jsps/book/list.jsp
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
		return "f:/jsps/book/list.jsp";
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
		return "f:/jsps/book/list.jsp";
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
		return "f:/jsps/book/list.jsp";
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
		return "f:/jsps/book/list.jsp";
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
		return "f:/jsps/book/list.jsp";
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
		request.setAttribute("book", book);
		return "f:/jsps/book/desc.jsp";
	}
}
