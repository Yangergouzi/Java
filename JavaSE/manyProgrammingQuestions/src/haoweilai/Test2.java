/*
 * 输入两个字符串，从第一字符串中删除第二个字符串中所有的字符。例如，输入”They are students.”和”aeiou”，则删除之后的第一个字符串变成”Thy r stdnts.” 
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
