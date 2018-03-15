package com.yang.imageViewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

public class ViewerFrame extends JFrame {
	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;

	JLabel label = new JLabel();
	ViewerService service  = new ViewerService(this);
	
	public JLabel getLabel() {
		return label;
	}
	/*
	 * ��ʼ��
	 */
	public void init() {
		this.setTitle("ͼƬ�����");
		this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createMenuBar();
		JPanel toolBar = creteToolBar();
		this.add(toolBar, BorderLayout.NORTH);
		this.add(new JScrollPane(label), BorderLayout.CENTER);
		this.setVisible(true);
		this.pack();
	}
	
	//����������
	ActionListener listener = new ActionListener() {		
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if(cmd.equals("��")) {
				service.open();
			}else if(cmd.equals("�Ŵ�")) {
				service.zoom(true);
			}else if(cmd.equals("��С")) {
				service.zoom(false);
			}else if(cmd.equals("��һ��")) {
				service.last();
			}else if(cmd.equals("��һ��")) {
				service.next();
			}else if(cmd.equals("�˳�")) {
				System.exit(0);
			}
		}
	};
	/*
	 * �����˵���
	 */
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		String[] menuArr = {"�ļ�","����","����"};
		String[][] menuItemArr = {
				{"��","-","�˳�"},
				{"�Ŵ�","��С","-","��һ��","��һ��"},
				{"��������","����"}
		};
		for(int i = 0;i < menuArr.length;i++) {
			JMenu menu = new JMenu(menuArr[i]);
			for(int j = 0; j < menuItemArr[i].length;j++) {
				if(menuItemArr[i][j].equals("-")) {
					menu.addSeparator();
				}else {
					JMenuItem menuItem = new JMenuItem(menuItemArr[i][j]);
					menuItem.addActionListener(listener);
					menu.add(menuItem);
				}
			}
			menuBar.add(menu);
		}
		this.setJMenuBar(menuBar );
	}
	/*
	 * ����������
	 */
	private JPanel creteToolBar() {
		JPanel panel = new JPanel();
		JToolBar toolBar = new JToolBar("����");
		toolBar.setFloatable(false);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		//��������
		String[] toolname = {"��","��һ��","��һ��","�Ŵ�","��С"};
		for(String name : toolname) {
			ImageIcon icon = new ImageIcon("imgs/" + name + ".png");    
		//	ViewerAction action = new ViewerAction(new ImageIcon("imgs/" + name + ".png"), name, this);
		//	JButton button = new JButton(action);
			JButton button = new JButton(name, icon);
			button.addActionListener(listener);
			toolBar.add(button);
		}
		panel.add(toolBar);
		return panel;				
	}
	/*
	 * �ж�ִ�в˵�����
	 */
}
