/*
 * ţţ��������й�����������һ���ַ�������s,ţţ��Ҫ�������й����ߴ����ҳ����DNA���С�DNA����ָ����������ֻ����'A','T','C','G'��ţţ�����������̫����,�Ͱ�
 * ���⽻�����������
����: s = "ABCBOATER"�а������DNAƬ����"AT",������ĳ�����2�� 
Input:�������һ���ַ���s,�ַ�������length(1 �� length �� 50),�ַ�����ֻ������д��ĸ('A'~'Z')��
Output:���һ������,��ʾ���DNAƬ��
���磺input:ABCBOATER
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
