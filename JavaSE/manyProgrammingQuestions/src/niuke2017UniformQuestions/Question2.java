/*
 * 牛牛从生物科研工作者那里获得一段字符串数据s,牛牛需要帮助科研工作者从中找出最长的DNA序列。DNA序列指的是序列中只包括'A','T','C','G'。牛牛觉得这个问题太简单了,就把
 * 问题交给你来解决。
例如: s = "ABCBOATER"中包含最长的DNA片段是"AT",所以最长的长度是2。 
Input:输入包括一个字符串s,字符串长度length(1 ≤ length ≤ 50),字符串中只包括大写字母('A'~'Z')。
Output:输出一个整数,表示最长的DNA片段
例如：input:ABCBOATER
	output:2
 */
package niuke2017UniformQuestions;

import java.util.Scanner;
public class Question2 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String str = scanner.nextLine();
		String regex = "[A-Z]{1,50}";
		while(!str.matches(regex)) {
			System.out.println("again");
			str = scanner.nextLine();
		}
		
		String regex2 = "[^ATCG]";
		int maxCount = 0;
		String[] strArray = str.split(regex2);
		for(int i = 0;i < strArray.length;i++) {
			if(strArray[i].length() > maxCount) {
				maxCount = strArray[i].length();
			}
		}
		System.out.println(maxCount);
	}
}
