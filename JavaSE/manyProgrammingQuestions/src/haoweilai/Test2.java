/*
 * ���������ַ������ӵ�һ�ַ�����ɾ���ڶ����ַ��������е��ַ������磬���롱They are students.���͡�aeiou������ɾ��֮��ĵ�һ���ַ�����ɡ�Thy r stdnts.�� 
 * input:They are students. aeiou
 * output:Thy r stdnts.
 */
package haoweilai;

import java.util.Scanner;

public class Test2 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String str1 = scanner.nextLine();
		String str2 = scanner.nextLine();
		String[] s1 = str1.split("");
		String[] s2 = str2.split("");
		
		for(int i=0;i<s1.length;i++) {
			for(String t : s2) {
				if(s1[i].equals(t)) {
					s1[i] = "";
				}
			}
		}
		StringBuffer sBuffer = new StringBuffer();
		for(int i=0;i<s1.length;i++) {
			sBuffer.append(s1[i]);
		}
		System.out.println(sBuffer.toString());
	}
	
}
