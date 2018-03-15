package com.yang.ballGame;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class BallService {
	int board_startPosX = 100;
	int board_startPosY = Board.MAX_POSY - 40;
	
	List<Brick> brickList = null;//ש�鼯��
	List<Point> points = null;//ש�鼯��������ש�������
	HashSet<Brick> outBricks = new HashSet<Brick>();//��¶����һ���ש�飨��ש���ļ���
	Board board = new Board(board_startPosX, board_startPosY);
	Ball ball = new Ball(120, 100);
	
	HashSet<Integer> ranId = new HashSet<Integer>();
	List<DaoJu> daoJus = new CopyOnWriteArrayList<DaoJu>();//���߼���
	
	//����ש����״
	public void setBricks() {
		int x = 0;//ש��λ��
		int y = 0;
		int id = 0;
		int numCol = 0;//����
		int numRow = 0;//����
		int wid = Brick.BRICK_WIDTH;
		int heig = Brick.BRICK_HEIGHT;
		brickList = new CopyOnWriteArrayList<Brick>();
		Brick brick;
		for(int i = 0;;i++) {
			x = wid * (numCol + numRow);//���ש���������μ���һ��
			y = heig * numRow;
			numCol++;
			brick = new Brick(x, y,id++);
			brickList.add(brick);	
			if(x > BallFrame.GAME_WIDTH - numRow*wid - Brick.BRICK_WIDTH - wid) {//�ұ�ש���������μ���
				//���һ�н������������㣬������һ
				numCol = 0;
				numRow++;
			}
			if(x + wid + BallFrame.GAME_WIDTH - numRow*wid == BallFrame.GAME_WIDTH) {//��һ��ֻʣһ��שʱ������
				break;
			}
		}	
		points = new ArrayList<Point>();
		Point point = null;
		for(Brick b : brickList) {
			point = new Point(b.posX, b.posY);
			points.add(point);
		}
		new Thread(new ComputeOutBricksThread()).start();//����������ש�߳�
		setDaojus();//���÷ŵ��߷���
	}
	//��ש����ŵ���
	public void setDaojus(){
		int i = 0;
		DaoJu daoJu;
		randomSet(0, brickList.size(), 10, ranId);
		for(Brick brick : brickList) {
			 if(ranId.contains(brick.getId())) {
				 i++;
				 if(i <= 3) {
					 daoJu = new DaoJu(brick.posX, brick.posY, DaoJu.DAOJU_UP);
					 brick.setDaoJu(daoJu);
					 daoJus.add(daoJu);
				 }else {
					 daoJu = new DaoJu(brick.posX, brick.posY, DaoJu.DAOJU_DOWN);
					 brick.setDaoJu(daoJu);
					 daoJus.add(daoJu);
				}
			 }
		}
	}
	/** 
	 * ���ָ����Χ��N�����ظ����� 
	 * ����HashSet��������ֻ�ܴ�Ų�ͬ��ֵ 
	 * @param min ָ����Χ��Сֵ 
	 * @param max ָ����Χ���ֵ 
	 * @param n ��������� 
	 * @param HashSet<Integer> set ���������� 
	 */  
   public static void randomSet(int min, int max, int n, HashSet<Integer> set) {  
       if (n > (max - min + 1) || max < min) {  
           return;  
       }  
       for (int i = 0; i < n; i++) {  
           // ����Math.random()����  
           int num = (int) (Math.random() * (max - min)) + min;  
           set.add(num);// ����ͬ��������HashSet��  
       }  
       int setSize = set.size();  
       // ����������С��ָ�����ɵĸ���������õݹ�������ʣ�����������������ѭ����ֱ���ﵽָ����С  
       if (setSize < n) {  
        randomSet(min, max, n - setSize, set);// �ݹ�  
       }  
   }  
	//��ש��
	public void drawBricks(Graphics g) {
		for(Brick brick : brickList) {			
			brick.draw(g);
		}
		
	}
	//������
	public void drawBoard(Graphics g) {	
		board.draw(g);
	}
	//������
	public void drawBall(Graphics g) {
		ball.draw(g);
		if(isFail() || isSuccess()) {
			
		}else {
			runBall(g);//С���˶�
		}
	}
	//������
	public void drawDaoJus(Graphics g) {
if(isFail() || isSuccess()) {
			
		}else {
			for(DaoJu daoJu : daoJus) {
				if(daoJu.isFall()) {
					daoJu.draw(g);
					daoJu.posY += daoJu.speed;	
					if(isDaoJuHitBoard(daoJu)) {
						daoJus.remove(daoJu);
						if(daoJu.type == DaoJu.DAOJU_UP) {
							board.upperBoard();
						}else if(daoJu.type == DaoJu.DAOJU_DOWN){
							board.downerBoard();
						}
					}
				}
			}	
		}
	}
	
	
	//��Ϸ�ɹ���ʧ��ʱ����ʾ��ʾ��
	public void drawReminder(Graphics g) {
		Reminder reminder;
		if(isFail()) {
			reminder = new Reminder(BallFrame.GAME_WIDTH/3, BallFrame.GAME_HEIGHT/3, Reminder.SIZE_BIG);
			reminder.draw(g, "��Ϸʧ�ܣ�");
			reminder = new Reminder(BallFrame.GAME_WIDTH/3, BallFrame.GAME_HEIGHT/3 + 30, Reminder.SIZE_SMALL);
			reminder.draw(g, "��space��������ctrl���˳�");
		}
		if(isSuccess()) {
			reminder = new Reminder(BallFrame.GAME_WIDTH/4, BallFrame.GAME_HEIGHT/3, Reminder.SIZE_BIG);
			reminder.draw(g, "�������������������ש�飡��");
			reminder = new Reminder(BallFrame.GAME_WIDTH/3, BallFrame.GAME_HEIGHT/3 + 30, Reminder.SIZE_SMALL);
			reminder.draw(g, "��space��������ctrl���˳�");
		}
	}
	
	//�����ƶ�����
	public void moveBoard(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_LEFT) {
			board.posX = board.posX - board.speed;
			if(board.posX < 0) {
				board.posX = 0;
			}
		}
		if(key == KeyEvent.VK_RIGHT) {
			board.posX = board.posX + board.speed;
			if(board.posX > BallFrame.GAME_WIDTH - Board.BOARD_WIDTH) {
				board.posX = BallFrame.GAME_WIDTH - Board.BOARD_WIDTH;
			}
		}		
	}
	//С���˶�
	public void runBall(Graphics g) {
		ball.posX += (ball.dirX * ball.speedX);
		ball.posY += (ball.dirY * ball.speedY);
		//�뵲����ײʱ��x�����ٶȼ�1��y�����ٶȷ���
		if(ball.getRect().intersects(board.getRect())) {
			ball.speedX++;			
			ball.dirY = - ball.dirY;
		}
		//������ש����ש����ײʱ
		int brick_x;int brick_y;
		for(Brick brick : brickList) {
			if(ball.getRect().intersects(brick.getRect())) {
				//�ж�С�������ש����һ����ײ����ΪС���Ծ����ж��ཻ��Ϊ��׼ȷ���ò�������
				brick_x = brick.posX;
				brick_y = brick.posY;
				int dis = 2;//���ò�������Ϊ2
				if(ball.posX > brick_x - ball.BALL_WIDTH + dis && ball.posX < brick_x + Brick.BRICK_WIDTH - dis) {//���ϻ��±�Ե��ײʱ		
					brickList.remove(brick);
					if(brick.getDaoJu() != null) {//���ש�����е��ߣ����õ��ߵ���
						brick.getDaoJu().setFall(true);
					}
					points.remove(new Point(brick_x, brick_y));
					new Thread(new ComputeOutBricksThread()).start();//����������ש�߳�
					if(ball.posY >= brick_y ) {
						//��ש���±�Ե��ײʱ��x�����ٶȼ�1��y�����ٶȷ���
						if(ball.speedX > Ball.BALL_MAX_SPEEDX) {
							ball.speedX = Ball.BALL_MAX_SPEEDX;
						}else {
							ball.speedX++;
						}
						ball.dirY = - ball.dirY;
					}
					else {
						//��ש���ϱ�Ե��ײʱ��x�����ٶȼ�1��y�����ٶȷ���
						if(ball.speedX > Ball.BALL_MAX_SPEEDX) {
							ball.speedX = Ball.BALL_MAX_SPEEDX;
						}else {
							ball.speedX++;
						}			
						ball.dirY = - ball.dirY;
					}
				}
				else if(ball.posY > brick_y - Ball.BALL_HEIGHT + dis && ball.posY < brick_y + Brick.BRICK_HEIGHT - dis) {//������ұ�Ե��ײʱ
					brickList.remove(brick);
					points.remove(new Point(brick_x, brick_y));
					if(brick.getDaoJu() != null) {//���ש�����е��ߣ����õ��ߵ���
						brick.getDaoJu().setFall(true);
					}
					new Thread(new ComputeOutBricksThread()).start();//����������ש�߳�
					//x�����ٶȷ���y�����ٶȼ�1
					if(ball.speedY > Ball.BALL_MAX_SPEEDY) {
						ball.speedY = Ball.BALL_MAX_SPEEDY;
					}else {
						ball.speedY++;
					}
					ball.dirX = -ball.dirX;
				}
				
			}
		}
		//���ϱ�ǽ��ײʱ��x�����ٶȼ�1��y�����ٶȷ���
		if(ball.posY < 0) {
			ball.speedX--;			
			ball.dirY = - ball.dirY;
		}
		//������ǽ��ײʱ��x�����ٶȷ���
		if(ball.posX < 0 || ball.posX > BallFrame.GAME_WIDTH - Ball.BALL_WIDTH) {
			ball.dirX = - ball.dirX;
		}	
	}
	//�жϵ����뵲���Ƿ���ײ
	public boolean isDaoJuHitBoard(DaoJu daoJu) {
		if(daoJu.getRect().intersects(board.getRect())) {
			return true;
		}else {
			return false;
		}
	}
	
	//�ж���Ϸʧ��
	public boolean isFail() {
		if(ball.posY > BallFrame.GAME_HEIGHT) { 			
			return true;
		}else {
			return false;
		}	
	}
	//�ж���Ϸ�ɹ�
	public boolean isSuccess() {
		if(brickList.size() == 0) {
			return true;
		}else {
			return false;
		}
	}
	
	//������ש���̣߳�ÿ��С����ײש�����
	private class ComputeOutBricksThread implements Runnable{
		@Override
		public void run() {
			Point point = null;
			int id;
			int index1; int index2; int index3; int index4;
			//����ש�鼯�ϣ�ȷ����ש��ţ����뼯��
			for(Brick brick : brickList) {
				int x = brick.posX;
				int y = brick.posY;
		//		 if(x != 0 || y != 0 || x != BallFrame.GAME_WIDTH - Brick.BRICK_WIDTH) {//�ų���ǽ��
				if(x == 0) {
					index1 = 1;
					 point = new Point(x + Brick.BRICK_WIDTH, y);
					 index2 = points.indexOf(point);
					 point = new Point(x, y - Brick.BRICK_HEIGHT);
					 index3 = points.indexOf(point);
					 point = new Point(x , y + Brick.BRICK_HEIGHT);
					 index4 = points.indexOf(point);
				}
				else if(x == BallFrame.GAME_WIDTH - Brick.BRICK_WIDTH) {
					index2 = 1;
					 point = new Point(x - Brick.BRICK_WIDTH, y);
					 index1 = points.indexOf(point);
					 point = new Point(x, y - Brick.BRICK_HEIGHT);
					 index3 = points.indexOf(point);
					 point = new Point(x , y + Brick.BRICK_HEIGHT);
					 index4 = points.indexOf(point);
				}
				else if(y == 0) {
					index3 = 1;
					 point = new Point(x - Brick.BRICK_WIDTH, y);
					 index1 = points.indexOf(point);
					 point = new Point(x + Brick.BRICK_WIDTH, y);
					 index2 = points.indexOf(point);
					 point = new Point(x , y + Brick.BRICK_HEIGHT);
					 index4 = points.indexOf(point);
				}else {
					 point = new Point(x - Brick.BRICK_WIDTH, y);
					 index1 = points.indexOf(point);
					 point = new Point(x + Brick.BRICK_WIDTH, y);
					 index2 = points.indexOf(point);
					 point = new Point(x, y - Brick.BRICK_HEIGHT);
					 index3 = points.indexOf(point);
					 point = new Point(x , y + Brick.BRICK_HEIGHT);
					 index4 = points.indexOf(point);
				 }
				 if(index1 < 0 || index2 < 0 || index3 < 0 || index4 < 0) {//ֻҪ��Ӧλ��û��ש����Ϊ��ת
					 outBricks.add(brick);
				 }
			}
		}		
	}
	
}
