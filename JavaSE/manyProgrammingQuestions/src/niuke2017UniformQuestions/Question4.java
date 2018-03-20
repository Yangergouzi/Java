/*
 * 牛牛有一些字母卡片,每张卡片上都有一个小写字母,所有卡片组成一个字符串s。牛牛一直认为回文这种性质十分优雅,于是牛牛希望用这些卡片拼凑出一些回文串,但是有以下要求:
1、每张卡片只能使用一次
2、要求构成的回文串的数量最少
牛牛想知道用这些字母卡片,最少能拼凑出多少个回文串。
例如: s = "abbaa",输出1,因为最少可以拼凑出"ababa"这一个回文串
s = "abc", 输出3,因为最少只能拼凑出"a","b","c"这三个回文串 
Input:输入包括一行,一个字符串s,字符串s长度length(1 ≤ length ≤ 1000).
s中每个字符都是小写字母
Output:输出一个整数,即最少的回文串个数。
例如：input:abc	output:3
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
		//判断最少回文数
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
			if(value % 2 == 1) {//如果是奇数，回文数加一
				count++;
			}
		}
		if(count == 0) {//如果个数全为偶数，count=1
			count = 1;
		}
		System.out.println(count);
	}
	
	
}
