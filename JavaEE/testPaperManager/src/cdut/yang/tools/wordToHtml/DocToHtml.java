package cdut.yang.tools.wordToHtml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;

/**
 * ��*.doc�ĵ�ת��Ϊ*.html�ļ���ʽ
 * 
 * @author Jdk.feiruo.
 * @since JDK 1.7 POI 3.8
 * @version 1.0
 */
public class DocToHtml extends OfficeConvert implements IOfficeConvert {
    private List<Picture> pics = null;
    

    /**
     * @param parentPath
     *            html�ļ���ŵ�ַ
     * @param imageppth
     *            htmlͼƬ��ŵ�ַ
     * @param encoding
     *            ����html�ı����ʽ
     */
    public DocToHtml(String parentPath, String imageppth, String encoding) {
        setParentPath(checkSetPath(parentPath));
        setImgPath(checkSetPath(imageppth));
        this.setEncode(encoding);
    }

    public DocToHtml() {

    }

    /**
     * ��*doc�ĵ�תΪ*html�ļ�
     * 
     * @param docPath
     *            *doc�ĵ������ڵ�ַ
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    public void convert(String docPath) throws FileNotFoundException,
            IOException, ParserConfigurationException, TransformerException {
        HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(
                docPath));
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .newDocument());
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            public String savePicture(byte[] content, PictureType pictureType,
                    String suggestedName, float widthInches, float heightInches) {
                return suggestedName;
            }
        });
        wordToHtmlConverter.processDocument(wordDocument);
        pics = wordDocument.getPicturesTable().getAllPictures();

        Document htmlDocument = wordToHtmlConverter.getDocument();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(out);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();

        serializer.setOutputProperty(OutputKeys.ENCODING, this.getEncode());
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);

        out.close();
        
        String htmlContent = new String(out.toByteArray());
        if(htmlContent.indexOf("<img src=\"") > 0){
            htmlContent=htmlContent.replaceAll("<img src=\"", "<img src=\"" + getImgPath());
        }
        setFileContent(htmlContent);
    }

    @Override
    public void writeWithName(String fileName) {
        // �ȱ����ĵ��е�ͼƬ
        if (pics != null) {
            File imgfile = new File(this.getParentPath() + this.getImgPath());
            // �����ǰ�ļ��в����ڣ��򴴽����ļ���
             if (!imgfile.exists())
                imgfile.mkdirs();
            for (int i = 0; i < pics.size(); i++) {
                Picture pic = (Picture) pics.get(i);
                try {
                    pic.writeImageContent(new FileOutputStream(imgfile + "//"
                            + pic.suggestFullFileName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // ����htmlԴ���ļ�
        this.writeFile(getParentPath()+fileName+".html");
    }
}