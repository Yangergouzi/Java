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
    
        // ͼƬ�Ĵ�ŵ�ַ
        private String imgPath = null;
        // �ļ���ŵĵ�ַ
        private String parentPath = null;
        // �ļ�����
        private String fileContent = null;
        private String encode = "UTF-8";
        
        
    /**
     * ��ָ����doc�ĵ����и�ʽת��
     * 
     * @param docPath
     *            *.doc�ĵ���ַ
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    public abstract void convert(String docPath) throws FileNotFoundException,
            IOException, ParserConfigurationException, TransformerException;

    /**
     * ���ļ�����д�뵽����
     * 
     * @param filepath
     *            ����ת���ļ��ĵ�ַ
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
     * ��ȡͼƬ��ŵ�ַ
     * 
     * @return <strong>java.lang.String</strong>
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * ����ͼƬ�Ĵ�ŵ�ַ�ļ���·��
     * 
     * @param imgPath
     *            ����ͼƬ�Ĵ���ļ�������
     */
    public void setImgPath(String imgPath) {
        this.imgPath = checkSetPath(imgPath);
    }

    /**
     * ��ȡ����ļ���Ŀ¼��ַ
     * 
     * @return <strong>java.lang.String</strong>
     */
    public String getParentPath() {
        return parentPath;
    }

    /**
     * �����ļ���ŵ�·��
     * 
     * @param parentPath
     *            �ļ���ַ
     */
    public void setParentPath(String parentPath) {
        this.parentPath = checkSetPath(parentPath);
    }

    /**
     * ��ȡ�ļ�����
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