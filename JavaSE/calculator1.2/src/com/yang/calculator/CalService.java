package com.yang.calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class CalService {

	String firNum = null;
	String secNum = null;
	String sym = null;
	String save = null;
	
	public void getMethod(String cmd, String text, JPanel jpText) {
		JTextArea textUp = (JTextArea) jpText.getComponent(0);
		JTextArea textDown = (JTextArea) jpText.getComponent(1);
		if(textUp.getText().contains("=")) {
			textUp.setText("");
		}
		if(cmd.equals("+") || cmd.equals("-") || cmd.equals("*") || cmd.equals("/")) {
			judgeNum(text);
			textUp.append(text + cmd);
			textDown.setText("");
			doOperation();
			sym = cmd;
		}
		else if(cmd.equals("=")) {
			judgeNum(text);
			doOperation();
			textUp.append(text + cmd);
			textDown.setText(firNum);	
		}
		else if(cmd.equals("%")) {
			text = MathUtils.percent(text);
			textDown.setText(text);
		}
		else if(cmd.equals("sqrt")) {
			text = MathUtils.sqrt(text);
			textDown.setText(text);
		}
		else if(cmd.equals("1/x")) {
			text = MathUtils.daosShu(text);
			textDown.setText(text);
		}
		else if(cmd.equals("+/-")) {
			text = MathUtils.fuShu(text);
			judgeNum(text);
			textDown.setText(text);
		}
		else if(cmd.equals("Back")) {
			text = text.substring(0, text.length()-1);
			textDown.setText(text);
		}
		else if(cmd.equals("C")) {
			firNum = null;
			secNum = null;
			sym = null;
			textUp.setText("");
			textDown.setText("");
		}
		else if(cmd.equals("CE")) {
			textDown.setText("");
		}
		else if(cmd.equals("MS")) {
			//�ڴ�洢������text����save
			if(!text.equals("")) {
				save = text;
			}
		}
		else if(cmd.equals("M+")) {
			//�ڴ����
			if(!text.equals("")) {
				save = MathUtils.add(save, text);
			}
		}
		else if(cmd.equals("MR")) {
			//�ڴ����
			if(save != null) {
				textDown.setText(save);
			}
		}
		else if(cmd.equals("MC")) {
			//�ڴ����
			if(save != null) {
				save = null;
			}
		}
	}
	/*��������
	 *�������firNum��
	 */
	private void doOperation() {
		if(sym != null &&secNum != null) {
			if(sym.equals("+")) {
				firNum = MathUtils.add(firNum, secNum);
			}
			if(sym.equals("-")) {
				firNum = MathUtils.subtract(firNum, secNum);
			}
			if(sym.equals("*")) {
				firNum = MathUtils.multiply(firNum, secNum);
			}
			if(sym.equals("/")) {
				firNum = MathUtils.divide(firNum, secNum);
			}
			sym = null;
			secNum = null;
		}
	}
	//�жϵ�һ�������ڶ������������
	private void judgeNum(String text) {
		if(text.equals("")) {
			text = "0";
		}
		if(firNum == null) {
			firNum = text;
		}else {
			secNum = text;
		}
	}
	
}
