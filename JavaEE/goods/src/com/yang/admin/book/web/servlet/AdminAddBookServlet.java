package com.yang.admin.book.web.servlet;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.commons.CommonUtils;

import com.yang.goods.Book.domain.Book;
import com.yang.goods.Book.service.BookService;
import com.yang.goods.category.domain.Category;

public class AdminAddBookServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	/*
	 * 添加图书第二步
	 * 1.使用commons-fileupload组件完成上传三步，解析表单得到List<FileItem>
	 * 2.将List<FileItem>的普通表单存入Map,保存到Book对象和Category对象，再关联两对象
	 * 3.判断图片后缀名，若不为.jpg，保存错误信息转发到add.jsp
	 * 4.保存图片到本地book_img
	 * 5.判断图片尺寸，若超过350*350，删除图片，保存错误信息并转发
	 * 6.将图片路径保存到Book
	 * 7.调用service#add(book)
	 * 8.保存成功信息，转发到msg.jsp
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		/*
		 * 1.解析表单
		 */
		//创建工具
		FileItemFactory factory = new DiskFileItemFactory();
		//创建解析器对象
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setFileSizeMax(80 * 1024);//文件大小不超过80k
		//解析表单
		List<FileItem> fileItemList = null;
		try {
			fileItemList= sfu.parseRequest(request);
		} catch (FileUploadException e) {
			error("上传文件超过80kB",request,response);
			return;
		}
		/*
		 * 2.普通表单数据映射到Book
		 */
		Map<String,Object> map = new HashMap<String,Object>();
		for(FileItem fileitem : fileItemList){
			map.put(fileitem.getFieldName(), fileitem.getString("utf-8"));
		}
		Book book = new Book();
		book = CommonUtils.toBean(map, Book.class);
		Category category = CommonUtils.toBean(map, Category.class);
		book.setCategory(category); 
		/*
		 * 3.图片文件
		 */
		//截取图片名称
		FileItem fileitem = fileItemList.get(1);//获取大图
		String filename = fileitem.getName();//获取路径，可能为绝对路径
		int index = filename.lastIndexOf("\\");
		if(index != -1){
			filename = filename.substring(index);
		}
		//创建唯一前缀，避免重名
		filename = CommonUtils.uuid() + "_" + filename;
		//判断后缀名
		if(!filename.toLowerCase().endsWith(".jpg") && !filename.toLowerCase().endsWith(".png")){
			error("文件只能是jpg或png格式",request,response);
			return;
		}
		//保存图片
		String savepath = this.getServletContext().getRealPath("/book_img");//获取真实路径
		File destFile = new File(savepath, filename);//创建目标文件
		try {
			fileitem.write(destFile);//会把临时文件重定向到目标文件，并删除临时文件
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		//校验尺寸
		ImageIcon imageicon = new ImageIcon(destFile.getAbsolutePath());
		Image image = imageicon.getImage();
		if(image.getWidth(null) > 350 || image.getHeight(null) >350){
			error("您上传的图片尺寸超过350*350",request,response);
			destFile.delete();//删除不合格图片
			return;
		}
		//把图片路径设置给book
		book.setImage_w("book_img/" + filename);
		
		//截取图片名称
				fileitem = fileItemList.get(2);//获取小图
				filename = fileitem.getName();//获取路径，可能为绝对路径
				index = filename.lastIndexOf("\\");
				if(index != -1){
					filename = filename.substring(index);
				}
				//创建唯一前缀，避免重名
				filename = CommonUtils.uuid() + "_" + filename;
				//判断后缀名
				if(!filename.toLowerCase().endsWith(".jpg") && !filename.toLowerCase().endsWith(".png")){
					error("文件只能是jpg或png格式",request,response);
					return;
				}
				//保存图片
				savepath = this.getServletContext().getRealPath("/book_img");//获取真实路径
				destFile = new File(savepath, filename);//创建目标文件
				try {
					fileitem.write(destFile);//会把临时文件重定向到目标文件，并删除临时文件
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				//校验尺寸
				imageicon = new ImageIcon(destFile.getAbsolutePath());
				image = imageicon.getImage();
				if(image.getWidth(null) > 350 || image.getHeight(null) >350){
					error("您上传的图片尺寸超过350*350",request,response);
					destFile.delete();//删除不合格图片
					return;
				}
				//把图片路径设置给book
				book.setImage_b("book_img/" + filename);
		/*
		 * 4.调用service,完成添加
		 */
				book.setBid(CommonUtils.uuid());
				BookService bookService = new BookService();
				bookService.add(book);
				//保存成功信息，转发到msg.jsp
				request.setAttribute("msg", "新书上架成功！");
				request.getRequestDispatcher("/adminjsps/msg.jsp").forward(request, response);
	}

	//保存错误信息，转发到/adminjsps/admin/book/add.jsp
	public void error(String msg, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
	}
}
