package cdut.yang.tools.wordToHtml;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class Test{
    public static void main(String[] args) {
    	   DocToHtml dth=new DocToHtml("D://test", "f", "gb2312");
           try {
             dth.convert("D://test//test.doc");
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         } catch (ParserConfigurationException e) {
             e.printStackTrace();
         } catch (TransformerException e) {
             e.printStackTrace();
         }
           dth.writeWithName("feiruo");
       }

    }
    

