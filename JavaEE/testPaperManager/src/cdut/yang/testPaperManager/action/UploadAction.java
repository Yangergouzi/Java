package cdut.yang.testPaperManager.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.struts2.ServletActionContext;

import cdut.yang.tools.Utils;
import cdut.yang.tools.wordToHtml.DocToHtml;
import cdut.yang.tools.wordToHtml.HtmlToWord;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.jmx.snmp.Timestamp;
 
public class UploadAction extends ActionSupport{
	//跟表单提交的input type = file 的name 保持一致
    private File myFile;    
    //文件名称 (myFile+myFileFileName)固定格式
    private String myFileFileName;  
    //文件类型 myFile+"ContentType"固定格式
    private String myFileContentType;
    HttpServletRequest req = ServletActionContext.getRequest();
	HttpServletResponse resp = ServletActionContext.getResponse();
    
	public File getMyFile() {
		return myFile;
	}
	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}
    
	public String getMyFileFileName() {
		return myFileFileName;
	}
	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}
	
	public String getMyFileContentType() {
		return myFileContentType;
	}
	public void setMyFileContentType(String myFileContentType) {
		this.myFileContentType = myFileContentType;
	}
	//内部方法
	public File upload(String uploadPath){
		//创建输入输出流
		InputStream is = null;
		OutputStream os = null;
		//创建目标文件
		String name = this.getMyFileFileName();
		String suffix = name.substring(name.lastIndexOf(".") , name.length());
		String fileName =  Utils.uuid() + suffix;
		File file = new File(uploadPath, fileName);		
		try {
			//将文件放入InputStream
			is = new FileInputStream(myFile);
			//输出流连接目标文件
			os = new FileOutputStream(file);			
			//将文件通过outputStream写入服务器文件目录upload
			byte[] buffer = new byte[1024];
			int length = 0;		
			while((length = is.read(buffer)) > 0){
				os.write(buffer, 0, length);
			}
			//关闭输入输出流
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	//
	public String ajaxUploadQuestionImg(){
		//设置上传文件位置
		String uploadPath = req.getSession().getServletContext().getRealPath("/") + "upload";
	//	String uploadPath = "d:\\testPaperManager\\upload";
		File file = upload(uploadPath);
		String url = "/upload/" + file.getName();
	//	String url = file.getAbsolutePath();
		String data = "{\"url\" : {\"value\" : \"" + url + "\"}}";
		
		try {
			resp.getWriter().print(data);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return NONE;		
	}
	//试卷word文档上传及处理
	public String ajaxUploadTestPaper(){
	
		//设置上传文件位置
		String uploadPath = req.getSession().getServletContext().getRealPath("/") + "upload";
	//	String uploadPath = "d:\\testPaperManager\\upload";
	//	String dirName = "/html" + System.currentTimeMillis();
		String htmlPath = uploadPath;
		String imgPath = "htmlImg" + Long.toString(System.currentTimeMillis());
		File file = upload(uploadPath);
		//设置html文件名
		String fileName = file.getName();
		String htmlName = fileName.substring(0,fileName.lastIndexOf("."));
	      
		DocToHtml dth = new DocToHtml(htmlPath,imgPath, "GB2312");
		 try {
	            dth.convert(file.getAbsolutePath());
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ParserConfigurationException e) {
	            e.printStackTrace();
	        } catch (TransformerException e) {
	            e.printStackTrace();
	        }
	    dth.writeWithName(htmlName);
	    String url =  "/upload/" + file.getName();
	   // String url = file.getAbsolutePath();
	    System.out.println(file.getAbsolutePath());	
		String data = "{\"url\" : {\"value\" : \"" + url + "\"}}";
	//	System.out.println(data);
	
		try {
			resp.getWriter().print(data);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return NONE;
	}
	//上传临时试卷的html，并且转换为doc文件，返回doc文件相对路径
	public String uploadHtmlandConvert(String html){
	//上传html
		String uploadPath = req.getSession().getServletContext().getRealPath("/") + "upload";
	//	String dirName = "/html" + System.currentTimeMillis();
		String htmlPath = uploadPath;
		//创建输入输出流
		InputStream is = null;
		OutputStream os = null;
		//创建目标文件
		String htmlName =  Utils.uuid() + ".html";
		File file = new File(htmlPath, htmlName);		
		try {
			//将字符串放入InputStream
			is = new ByteArrayInputStream(html.getBytes());
			//输出流连接目标文件
			os = new FileOutputStream(file);			
			//将文件通过outputStream写入服务器文件目录upload
			byte[] buffer = new byte[1024];
			int length = 0;		
			while((length = is.read(buffer)) > 0){
				os.write(buffer, 0, length);
			}
			//关闭输入输出流
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	System.out.println("**************************************************************");
	System.out.println("!!!!!!!!!!!!" + file.getAbsolutePath());
//html转换为doc
	  HtmlToWord htw = new HtmlToWord();
	//创建目标doc文件
	  String docName =  htmlName.substring(0,htmlName.lastIndexOf(".")) + ".doc";
	  File docFile = htw.createFile(uploadPath, docName);	
      try {
		htw.convert(htmlPath + "/" + htmlName, uploadPath + "/" + docName);
	} catch (Exception e) {
		e.printStackTrace();
	}
      String url = "/upload/" + docFile.getName();
		return url;
	}
}
