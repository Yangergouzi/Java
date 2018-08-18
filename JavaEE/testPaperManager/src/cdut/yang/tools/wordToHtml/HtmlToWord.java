package cdut.yang.tools.wordToHtml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class HtmlToWord {
	InputStream html;
	
	public void convert(String htmlPath,String docPath) throws Exception {
		html = new FileInputStream(htmlPath);	
		String content = this.getContent(html);
		//ƴһ����׼��HTML��ʽ�ĵ�
	//	String content = "<html><head><style>" + css + "</style></head><body>" + body + "</body></html>";
		InputStream is = new ByteArrayInputStream(content.getBytes("GB2312"));
		OutputStream os = new FileOutputStream(docPath);
		this.inputStreamToWord(is, os);
	}
	      
	/**
	* ��isд�뵽��Ӧ��word�����os��
	* �������쳣�Ĳ���ֱ���׳�
	* @param is
	* @param os
	* @throws IOException
	*/
	private void inputStreamToWord(InputStream is, OutputStream os) throws IOException {
		POIFSFileSystem fs = new POIFSFileSystem();
		//��Ӧ��org.apache.poi.hdf.extractor.WordDocument
		fs.createDocument(is, "WordDocument");
		fs.writeFilesystem(os);
		os.close();
		is.close();
	}
	      
	/**
	* �������������������UTF-8���뵱�ı�ȡ����
	* �������쳣��ֱ���׳�
	* @param ises
	* @return
	* @throws IOException
	*/
	private String getContent(InputStream... ises) throws IOException {
		if (ises != null) {
			StringBuilder result = new StringBuilder();
			BufferedReader br;
			String line;
			for (InputStream is : ises) {
				br = new BufferedReader(new InputStreamReader(is, "GB2312"));
				while ((line=br.readLine()) != null) {
					result.append(line);
				}
			}
			return result.toString();
		}
		return null;
	}
	/**
	 * ��ָ��·�������ļ�
	 */
	 public File createFile(String path,String fileName){
	        
	        File f = new File(path);
	        
	        if(!f.exists()){
	            
	            f.mkdirs();//����Ŀ¼
	        }
	        
	  
	        File file = new File(path, fileName);
	        
	        if(!file.exists()){
	            
	            try {
	                file.createNewFile();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            
	        }
	        return file;
	    }
}
