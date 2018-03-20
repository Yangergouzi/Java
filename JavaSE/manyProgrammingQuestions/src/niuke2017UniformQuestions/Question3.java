/*
 * 如果一个字符串由两个相同字符串连接而成,就称这个字符串是偶串。例如"xyzxyz"和"aaaaaa"是偶串,但是"ababab"和"xyzxy"却不是。
牛牛现在给你一个只包含小写字母的偶串s,你可以从字符串s的末尾删除1和或者多个字符,保证删除之后的字符串还是一个偶串,牛牛想知道删除之后得到最长偶串长度是多少。 
Input:输入包括一个字符串s,字符串长度length(2 ≤ length ≤ 200),保证s是一个偶串且由小写字母构成
Output:输出一个整数,表示删除之后能得到的最长偶串长度是多少。保证测试数据有非零解
例如：input:abaababaab
	output:6
 */
package niuke2017UniformQuestions;

import java.util.Scanner;

public class Question3 {
	public static void main(String[] args) {
		Question3 main = new Question3();
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		while(!main.isEvenStr(str)) {
			str = sc.nextLine();
		}
		
		while(str.length() > 0){
			str = str.substring(0, str.length() - 2);
			if(main.isEvenStr(str)) {
				System.out.println(str.length());
				break;
			}
		}
		
	}

	boolean isEvenStr(String str) {
		int len = str.length();
		if(len % 2 == 0) {
			String s1 = str.substring(0, len / 2);
			String s2 = str.substring(len / 2);
			if(s1.equals(s2)) {
				return true;
			}
		}
		return false;
	}
}
