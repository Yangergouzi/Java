/*
 * ţţ��һЩ��ĸ��Ƭ,ÿ�ſ�Ƭ�϶���һ��Сд��ĸ,���п�Ƭ���һ���ַ���s��ţţһֱ��Ϊ������������ʮ������,����ţţϣ������Щ��Ƭƴ�ճ�һЩ���Ĵ�,����������Ҫ��:
1��ÿ�ſ�Ƭֻ��ʹ��һ��
2��Ҫ�󹹳ɵĻ��Ĵ�����������
ţţ��֪������Щ��ĸ��Ƭ,������ƴ�ճ����ٸ����Ĵ���
����: s = "abbaa",���1,��Ϊ���ٿ���ƴ�ճ�"ababa"��һ�����Ĵ�
s = "abc", ���3,��Ϊ����ֻ��ƴ�ճ�"a","b","c"���������Ĵ� 
Input:�������һ��,һ���ַ���s,�ַ���s����length(1 �� length �� 1000).
s��ÿ���ַ�����Сд��ĸ
Output:���һ������,�����ٵĻ��Ĵ�������
���磺input:abc	output:3
 */
package niuke2017UniformQuestions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Question4 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		String regex = "[a-z]{1,1000}";
		while(!str.matches(regex)) {
			str = sc.nextLine();
		}
		String[] ss = str.split("");
		//�ж����ٻ�����
		Map<String,Integer> map = new HashMap<String,Integer>();
		int val;
		int count = 0;
		for(String s : ss) {
			if(map.containsKey(s)) {
				val = map.get(s);
				map.put(s, ++val);
			}else {
				map.put(s, 1);
			}
		}
		Collection<Integer> values = map.values();
		for(int value : values) {
			if(value % 2 == 1) {//�������������������һ
				count++;
			}
		}
		if(count == 0) {//�������ȫΪż����count=1
			count = 1;
		}
		System.out.println(count);
	}
	
	
}
