package niuke2017UniformQuestions;
/*
 * 牛牛喜欢彩色的东西,尤其是彩色的瓷砖。牛牛的房间内铺有L块正方形瓷砖。每块砖的颜色有四种可能:红、绿、蓝、黄。给定一个字符串S, 如果S的第i个字符是'R', 'G', 'B'或'Y',那么第i块瓷砖的颜色就分别是红、绿、蓝或者黄。
牛牛决定换掉一些瓷砖的颜色,使得相邻两块瓷砖的颜色均不相同。请帮牛牛计算他最少需要换掉的瓷砖数量。 
input:输入包括一行,一个字符串S,字符串长度length(1 ≤ length ≤ 10),字符串中每个字符串都是'R', 'G', 'B'或者'Y'。
output:输出一个整数,表示牛牛最少需要换掉的瓷砖数量
例如：input:RRRRR    output:2
 */
import java.util.Scanner;  
import java.util.regex.*;
public class Question1{
    public static void main (String[] args){
        System.out.println("please input:");//
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String regex = "[RGBY]{1,10}";
        while(!str.matches(regex)) {
        	System.out.println("please input right:");//
        	str = sc.nextLine();
        }
        char c0;
        char c1;
        char c2;
        int count = 0;
        boolean isLastBeChanged = false;
        boolean isBeChanged = false;
        for(int i = 0;i < str.length(); i+=2) {
        	c1 = str.charAt(i);
        	if(i > 0) {
        		c0 = str.charAt(i-1);
        		if(c0 == c1) {
            		if(!isLastBeChanged) {//如果上一个瓷砖颜色没被改变
            			count++;
            			isBeChanged = true;
            		}else {
            			isBeChanged = false;
            		}
            	}
        	}
        	if(!isBeChanged) {
	        	if(i < str.length() - 1) {
	        		c2 = str.charAt(i+1);
	        		if(c1 == c2) {
	            		count++;
	            		isLastBeChanged = true;
	            	}else {
	            		isLastBeChanged = false;
	            	}
	        	} 
        	}
        }
        System.out.println(count);
    }
}
