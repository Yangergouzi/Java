package com.yang.calculator;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class CalFrame extends JFrame{
	private static final int WIDTH = 400;
	private static final int HEIGHT = 300;
	
	
	
	CalService service = new CalService();
	public void initFrame() {
		JFrame jf = new JFrame("我的计算器");		
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocation(300, 200);
		jf.setSize(WIDTH, HEIGHT);
		jf.setResizable(false);
		
		JPanel jpText = new JPanel();
		JPanel jpWest = new JPanel();
		JPanel jpCen = new JPanel();
		
		JTextArea textDown = new JTextArea();
		textDown.setEditable(false);//禁止输入文本框
		JTextArea textUp = new JTextArea();
		textUp.setEditable(false);
		JPanel resultkeys = new JPanel();
		JPanel operationkeys = new JPanel();
		
		jpText.setLayout(new GridLayout(2, 1, 0, 5));
		jpWest.setLayout(new GridLayout(5, 1, 1, 8));
		jpCen.setLayout(new BorderLayout(1, 5));
		resultkeys.setLayout(new GridLayout(1, 3, 1 ,3));
		operationkeys.setLayout(new GridLayout(4, 5,1 ,3));
		
		JButton[] mButtons = getMButtons();
		for(JButton mButton : mButtons) {
			jpWest.add(mButton);
		}
		JButton[] rButtons = getRButtons();
		for(JButton rButton : rButtons) {
			resultkeys.add(rButton);
		}
		JButton[] oButtons = getOButtons();
		for(JButton oButton : oButtons) {
			operationkeys.add(oButton);
		}
		
		jpText.add(textUp);
		jpText.add(textDown);
		jpCen.add(resultkeys,BorderLayout.NORTH);
		jpCen.add(operationkeys,BorderLayout.CENTER);
		
		jf.getContentPane().add(BorderLayout.NORTH, jpText);
		jf.getContentPane().add(BorderLayout.WEST, jpWest);
		jf.getContentPane().add(BorderLayout.CENTER,jpCen);
		jf.setVisible(true);
		//监听事件	
		ActionListener actionListener = new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				String cmd = e.getActionCommand();
				service.getMethod(cmd, textDown.getText(),jpText);
				Pattern pattern = Pattern.compile("[0-9]|\\.");
				Matcher matcher = pattern.matcher(cmd);
				if(matcher.matches()) {
					textDown.append(cmd);
				}
			}
		};
		for(JButton oBut : oButtons) {
			oBut.addActionListener(actionListener);
		}
		for(JButton rBut : rButtons) {
			rBut.addActionListener(actionListener);
		}
		for(JButton mBut : mButtons) {
			mBut.addActionListener(actionListener);
		}
		
	}
	
	//得到存储操作键
	private JButton[] getMButtons() {
		JButton[] mButtons = new JButton[5];
		JButton mnull = new JButton();
		JButton mc = new JButton("MC");
		JButton mr = new JButton("MR");
		JButton ms = new JButton("MS");
		JButton madd = new JButton("M+");
		mButtons[0] = mnull;
		mButtons[1] = mc;
		mButtons[2] = mr;
		mButtons[3] = ms;
		mButtons[4] = madd;
		return mButtons;
	}
//得到结果操作键
	private JButton[] getRButtons() {
		JButton[] rButtons = new JButton[3];
		JButton back = new JButton("Back");
		JButton ce = new JButton("CE");
		JButton c = new JButton("C");
		rButtons[0] = back;
		rButtons[1] = ce;
		rButtons[2] = c;
		return rButtons;
	}
//得到运算操作键
	private JButton[] getOButtons() {
		JButton[] oButtons = new JButton[20];
		JButton k0 = new JButton("0");
		JButton k1 = new JButton("1");
		JButton k2 = new JButton("2");
		JButton k3 = new JButton("3");
		JButton k4 = new JButton("4");
		JButton k5 = new JButton("5");
		JButton k6 = new JButton("6");
		JButton k7 = new JButton("7");
		JButton k8 = new JButton("8");
		JButton k9 = new JButton("9");
		JButton jia = new JButton("+");
		JButton jian = new JButton("-");
		JButton cheng = new JButton("*");
		JButton chu = new JButton("/");
		JButton sqrt = new JButton("sqrt");
		JButton percent = new JButton("%");
		JButton inverse = new JButton("1/x");
		JButton eq = new JButton("=");
		JButton pOm = new JButton("+/-");
		JButton point = new JButton(".");
		oButtons[0] = k7;
		oButtons[1] = k8;
		oButtons[2] = k9;
		oButtons[3] = chu;
		oButtons[4] = sqrt;
		oButtons[5] = k4;
		oButtons[6] = k5;
		oButtons[7] = k6;
		oButtons[8] = cheng;
		oButtons[9] = percent;
		oButtons[10] = k1;
		oButtons[11] = k2;
		oButtons[12] = k3;
		oButtons[13] = jian;
		oButtons[14] = inverse;
		oButtons[15] = k0;
		oButtons[16] = pOm;
		oButtons[17] = point;
		oButtons[18] = jia;
		oButtons[19] = eq;
		return oButtons;
	}
}
