package niuke2017UniformQuestions;
/*
 * ţţϲ����ɫ�Ķ���,�����ǲ�ɫ�Ĵ�ש��ţţ�ķ���������L�������δ�ש��ÿ��ש����ɫ�����ֿ���:�졢�̡������ơ�����һ���ַ���S, ���S�ĵ�i���ַ���'R', 'G', 'B'��'Y',��ô��i���ש����ɫ�ͷֱ��Ǻ졢�̡������߻ơ�
ţţ��������һЩ��ש����ɫ,ʹ�����������ש����ɫ������ͬ�����ţţ������������Ҫ�����Ĵ�ש������ 
input:�������һ��,һ���ַ���S,�ַ�������length(1 �� length �� 10),�ַ�����ÿ���ַ�������'R', 'G', 'B'����'Y'��
output:���һ������,��ʾţţ������Ҫ�����Ĵ�ש����
���磺input:RRRRR    output:2
 */
import java.util.Scanner;  
import java.util.regex.*;
public class Question1{
    public static void main (String[] args){
        System.out.println("please input:");//
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String regex = "[RGBY]{1,10}";
        while(!str.matches(regex)) {
        	System.out.println("please input right:");//
        	str = sc.nextLine();
        }
        char c0;
        char c1;
        char c2;
        int count = 0;
        boolean isLastBeChanged = false;
        boolean isBeChanged = false;
        for(int i = 0;i < str.length(); i+=2) {
        	c1 = str.charAt(i);
        	if(i > 0) {
        		c0 = str.charAt(i-1);
        		if(c0 == c1) {
            		if(!isLastBeChanged) {//�����һ����ש��ɫû���ı�
            			count++;
            			isBeChanged = true;
            		}else {
            			isBeChanged = false;
            		}
            	}
        	}
        	if(!isBeChanged) {
	        	if(i < str.length() - 1) {
	        		c2 = str.charAt(i+1);
	        		if(c1 == c2) {
	            		count++;
	            		isLastBeChanged = true;
	            	}else {
	            		isLastBeChanged = false;
	            	}
	        	} 
        	}
        }
        System.out.println(count);
    }
}
