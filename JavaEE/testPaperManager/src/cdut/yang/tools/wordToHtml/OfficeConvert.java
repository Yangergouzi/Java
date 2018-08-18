package cdut.yang.tools.wordToHtml;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.poi.hwpf.usermodel.Picture;

public abstract class OfficeConvert {
    
        // 图片的存放地址
        private String imgPath = null;
        // 文件存放的地址
        private String parentPath = null;
        // 文件内容
        private String fileContent = null;
        private String encode = "UTF-8";
        
        
    /**
     * 将指定的doc文档进行格式转换
     * 
     * @param docPath
     *            *.doc文档地址
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    public abstract void convert(String docPath) throws FileNotFoundException,
            IOException, ParserConfigurationException, TransformerException;

    /**
     * 将文件内容写入到磁盘
     * 
     * @param filepath
     *            保存转换文件的地址
     */
    public void writeFile(String filepath) {
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        File f=new File(this.parentPath);
        
        if(!f.exists()){
            f.mkdirs();
        }
        try {
            File file = new File(filepath);
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos, encode));
            bw.write(fileContent);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fos != null)
                    fos.close();
            } catch (IOException ie) {
            }
        }
    }
    public String checkSetPath(String path){
        path=path.trim();
        if(path.lastIndexOf("/")<path.length()-1) path+="/";
        if(path.indexOf("\"")>0)path=path.replaceAll("\"", "");
        if(path.indexOf(">")>0)path=path.replaceAll(">", "&gt;");
        if(path.indexOf("<")>0)path=path.replaceAll("<", "&lt;");
        //TODO if(path.indexOf("*")>0)path=path.replaceAll("/*", "");
        return path;
    }
    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    /**
     * 获取图片存放地址
     * 
     * @return <strong>java.lang.String</strong>
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * 设置图片的存放地址文件夹路径
     * 
     * @param imgPath
     *            设置图片的存放文件夹名称
     */
    public void setImgPath(String imgPath) {
        this.imgPath = checkSetPath(imgPath);
    }

    /**
     * 获取存放文件的目录地址
     * 
     * @return <strong>java.lang.String</strong>
     */
    public String getParentPath() {
        return parentPath;
    }

    /**
     * 设置文件存放的路径
     * 
     * @param parentPath
     *            文件地址
     */
    public void setParentPath(String parentPath) {
        this.parentPath = checkSetPath(parentPath);
    }

    /**
     * 获取文件内容
     * 
     * @return <strong>java.lang.String</strong>
     */
    public String getFileContent() {
        return fileContent;
    }
    public void setFileContent(String content){
        this.fileContent=content;
    }
}