/*
 * ���浯��С��Ϸ��
 * ���Ҽ������·����������ƶ���סС��
 * С��ײ�������Ϸ�ש�飻
 * �м������������ߣ���ɫ����ʹ�������ƣ���ɫ����ʹ�������ƣ�
 * С���ٶȻ���ײ�������𽥼ӿ�
 */

package com.yang.ballGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BallFrame extends JFrame{
	public static final int GAME_WIDTH=Brick.BRICK_WIDTH * 9;
	public static final int GAME_HEIGHT=450;
	private BallPanel ballPanel = null;
	public BallService ballService = new BallService();	
	public static void main(String[] args) {
		BallFrame ballFrame = new BallFrame();
		ballFrame.init();	
	}
	
	public void init() {
		this.setTitle("����");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(300, 100);
		this.setSize(this.GAME_WIDTH,this.GAME_HEIGHT);
		this.setResizable(false);
		this.setVisible(true);
		this.setBackground(Color.BLACK);
		ballPanel = getBallPanel();
		this.add(ballPanel);
		//��Ӽ�����
		this.addKeyListener(new KeyMonitor());
		//�����ػ��߳�
		new Thread(new PaintThread()).start();	
		ballService.setBricks();//����ש����״
	}
	public BallPanel getBallPanel() {
		if(ballPanel == null) {
			ballPanel = new BallPanel();
		}
		return ballPanel;
	}
	
	//����һ��JPanel�ڲ�����ʵ�ֻ�ͼ����
	private class BallPanel extends JPanel{	
		Image offScreenImage = null;
		@Override
		public void paint(Graphics g) {
			ballService.drawBricks(g);
			ballService.drawBoard(g);
			ballService.drawBall(g);
			ballService.drawDaoJus(g);
			ballService.drawReminder(g);
		}
		@Override
		public void update(Graphics g) {
			if(offScreenImage == null) {
				offScreenImage = this.createImage( GAME_WIDTH, GAME_HEIGHT);
			}
			Graphics gOffScreen = offScreenImage.getGraphics();
			paint(gOffScreen);
			g.drawImage(offScreenImage, 0, 0, null);
			Color c = gOffScreen.getColor();
			gOffScreen.setColor(Color.BLACK);
			gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
			gOffScreen.setColor(c);
		}
		
	}
	//�ػ��̣߳�ÿ��50�����ػ�
	private class PaintThread implements Runnable{

		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	//���̼�����
	public class KeyMonitor extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			if(ballService.isFail() || ballService.isSuccess()) {
				restartOrOver(e); 
			}else {			
				ballService.moveBoard(e);
			}
		}
	}
	//�����ж����¿�ʼ�����˳�
	public void restartOrOver(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_SPACE) {
			BallFrame ballFrame = new BallFrame();
			ballFrame.init();	
		}
		if(key == KeyEvent.VK_CONTROL) {
			System.exit(0);
		}
	}
}
