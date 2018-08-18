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
		//拼一个标准的HTML格式文档
	//	String content = "<html><head><style>" + css + "</style></head><body>" + body + "</body></html>";
		InputStream is = new ByteArrayInputStream(content.getBytes("GB2312"));
		OutputStream os = new FileOutputStream(docPath);
		this.inputStreamToWord(is, os);
	}
	      
	/**
	* 把is写入到对应的word输出流os中
	* 不考虑异常的捕获，直接抛出
	* @param is
	* @param os
	* @throws IOException
	*/
	private void inputStreamToWord(InputStream is, OutputStream os) throws IOException {
		POIFSFileSystem fs = new POIFSFileSystem();
		//对应于org.apache.poi.hdf.extractor.WordDocument
		fs.createDocument(is, "WordDocument");
		fs.writeFilesystem(os);
		os.close();
		is.close();
	}
	      
	/**
	* 把输入流里面的内容以UTF-8编码当文本取出。
	* 不考虑异常，直接抛出
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
	 * 在指定路径创建文件
	 */
	 public File createFile(String path,String fileName){
	        
	        File f = new File(path);
	        
	        if(!f.exists()){
	            
	            f.mkdirs();//创建目录
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
