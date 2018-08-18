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
	//�����ύ��input type = file ��name ����һ��
    private File myFile;    
    //�ļ����� (myFile+myFileFileName)�̶���ʽ
    private String myFileFileName;  
    //�ļ����� myFile+"ContentType"�̶���ʽ
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
	//�ڲ�����
	public File upload(String uploadPath){
		//�������������
		InputStream is = null;
		OutputStream os = null;
		//����Ŀ���ļ�
		String name = this.getMyFileFileName();
		String suffix = name.substring(name.lastIndexOf(".") , name.length());
		String fileName =  Utils.uuid() + suffix;
		File file = new File(uploadPath, fileName);		
		try {
			//���ļ�����InputStream
			is = new FileInputStream(myFile);
			//���������Ŀ���ļ�
			os = new FileOutputStream(file);			
			//���ļ�ͨ��outputStreamд��������ļ�Ŀ¼upload
			byte[] buffer = new byte[1024];
			int length = 0;		
			while((length = is.read(buffer)) > 0){
				os.write(buffer, 0, length);
			}
			//�ر����������
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	//
	public String ajaxUploadQuestionImg(){
		//�����ϴ��ļ�λ��
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
	//�Ծ�word�ĵ��ϴ�������
	public String ajaxUploadTestPaper(){
	
		//�����ϴ��ļ�λ��
		String uploadPath = req.getSession().getServletContext().getRealPath("/") + "upload";
	//	String uploadPath = "d:\\testPaperManager\\upload";
	//	String dirName = "/html" + System.currentTimeMillis();
		String htmlPath = uploadPath;
		String imgPath = "htmlImg" + Long.toString(System.currentTimeMillis());
		File file = upload(uploadPath);
		//����html�ļ���
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
	//�ϴ���ʱ�Ծ��html������ת��Ϊdoc�ļ�������doc�ļ����·��
	public String uploadHtmlandConvert(String html){
	//�ϴ�html
		String uploadPath = req.getSession().getServletContext().getRealPath("/") + "upload";
	//	String dirName = "/html" + System.currentTimeMillis();
		String htmlPath = uploadPath;
		//�������������
		InputStream is = null;
		OutputStream os = null;
		//����Ŀ���ļ�
		String htmlName =  Utils.uuid() + ".html";
		File file = new File(htmlPath, htmlName);		
		try {
			//���ַ�������InputStream
			is = new ByteArrayInputStream(html.getBytes());
			//���������Ŀ���ļ�
			os = new FileOutputStream(file);			
			//���ļ�ͨ��outputStreamд��������ļ�Ŀ¼upload
			byte[] buffer = new byte[1024];
			int length = 0;		
			while((length = is.read(buffer)) > 0){
				os.write(buffer, 0, length);
			}
			//�ر����������
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	System.out.println("**************************************************************");
	System.out.println("!!!!!!!!!!!!" + file.getAbsolutePath());
//htmlת��Ϊdoc
	  HtmlToWord htw = new HtmlToWord();
	//����Ŀ��doc�ļ�
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
