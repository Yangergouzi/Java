/*
 * ���һ���ַ�����������ͬ�ַ������Ӷ���,�ͳ�����ַ�����ż��������"xyzxyz"��"aaaaaa"��ż��,����"ababab"��"xyzxy"ȴ���ǡ�
ţţ���ڸ���һ��ֻ����Сд��ĸ��ż��s,����Դ��ַ���s��ĩβɾ��1�ͻ��߶���ַ�,��֤ɾ��֮����ַ�������һ��ż��,ţţ��֪��ɾ��֮��õ��ż�������Ƕ��١� 
Input:�������һ���ַ���s,�ַ�������length(2 �� length �� 200),��֤s��һ��ż������Сд��ĸ����
Output:���һ������,��ʾɾ��֮���ܵõ����ż�������Ƕ��١���֤���������з����
���磺input:abaababaab
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
