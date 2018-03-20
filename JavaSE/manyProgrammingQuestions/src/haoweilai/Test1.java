package haoweilai;

import java.util.Scanner;

/*
 * 将一句话的单词进行倒置，标点不倒置。比如 I like beijing. 经过函数后变为：beijing. like I 
 * 每个测试输入包含1个测试用例： I like beijing. 输入用例长度不超过100
 */
public class Test1 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String str = scanner.nextLine();
		while(str.length() > 100) {
			str = scanner.nextLine();
		}
		String[] strings = str.split("\\s+");
		StringBuffer sBuffer = new StringBuffer();
		for(int i=strings.length-1;i>=0;i--) {
			if(i == 0) {
				sBuffer.append(strings[i]);
			}else {
				sBuffer.append(strings[i] + " ");
			}
		}
		System.out.println(sBuffer.toString());
	}
}
