package haoweilai;

import java.util.Scanner;

/*
 * ��һ�仰�ĵ��ʽ��е��ã���㲻���á����� I like beijing. �����������Ϊ��beijing. like I 
 * ÿ�������������1������������ I like beijing. �����������Ȳ�����100
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
