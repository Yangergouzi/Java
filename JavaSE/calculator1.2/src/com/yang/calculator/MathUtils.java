package com.yang.calculator;

import java.math.BigDecimal;

public class MathUtils {
	//加
	public static String add(String firNum,String secNum) {		
		BigDecimal fir = new BigDecimal(firNum);	
		BigDecimal sec = new BigDecimal(secNum);		
		return fir.add(sec).toString();
	}
	//减
	public static String subtract(String firNum,String secNum) {		
		BigDecimal fir = new BigDecimal(firNum);	
		BigDecimal sec = new BigDecimal(secNum);		
		return fir.subtract(sec).toString();
	}
	//乘
	public static String multiply(String firNum,String secNum) {		
		BigDecimal fir = new BigDecimal(firNum);	
		BigDecimal sec = new BigDecimal(secNum);		
		return fir.multiply(sec).toString();
	}
	//除
	public static String divide(String firNum,String secNum) {		
		BigDecimal fir = new BigDecimal(firNum);	
		BigDecimal sec = new BigDecimal(secNum);		
		return fir.divide(sec, 10,BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros().toString();
	}
	//百分化
	public static String percent(String text) {		
		BigDecimal fir = new BigDecimal(text);		
		BigDecimal sec = new BigDecimal("100");	
		return fir.divide(sec, 4,BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros().toString();
	}
	//开平方
	public static String sqrt(String text) {		
		Double num = Double.valueOf(text);
		Double res = Math.sqrt(num);
		return Double.toString(res);
	}
	//求倒数
	public static String daosShu(String text) {		
		BigDecimal fir = new BigDecimal("1");		
		BigDecimal sec = new BigDecimal(text);	
		if(text.equals("0")) {
			return text;
		}
		return fir.divide(sec, 10,BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros().toString();
	}
	//负数
	public static String fuShu(String text) {		
		text = "-" + text;
		return text;
	}
	
}
