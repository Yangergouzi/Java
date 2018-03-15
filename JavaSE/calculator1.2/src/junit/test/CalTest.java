package junit.test;

import java.util.Arrays;

import org.junit.Test;

import com.yang.calculator.CalFrame;

public class CalTest {
	@Test
	public void testInitFrame() {
		CalFrame cf = new CalFrame();
		cf.initFrame();
	}
	@Test
	public void testSplit() {
		String[] text = new String[8];
		text[0] = "2.3";
		text[1] = "3.68+8";
		text[2] = "3.6-8";
		text[3] = "3.6*8";
		text[4] = "3.6/8";
		text[5] = "-3.6";
		text[6] = "3.6+";
		text[7] = "-3.6+";
		for(int i = 0; i<text.length;i++) {
			String[] str = text[i].split("");
			System.out.println(Arrays.toString(str));
		}
	}
}
