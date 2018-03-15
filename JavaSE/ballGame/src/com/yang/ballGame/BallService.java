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
	
	List<Brick> brickList = null;//砖块集合
	List<Point> points = null;//砖块集合里所有砖块的坐标
	HashSet<Brick> outBricks = new HashSet<Brick>();//有露在外一面的砖块（外砖）的集合
	Board board = new Board(board_startPosX, board_startPosY);
	Ball ball = new Ball(120, 100);
	
	HashSet<Integer> ranId = new HashSet<Integer>();
	List<DaoJu> daoJus = new CopyOnWriteArrayList<DaoJu>();//道具集合
	
	//布置砖块形状
	public void setBricks() {
		int x = 0;//砖块位置
		int y = 0;
		int id = 0;
		int numCol = 0;//列数
		int numRow = 0;//行数
		int wid = Brick.BRICK_WIDTH;
		int heig = Brick.BRICK_HEIGHT;
		brickList = new CopyOnWriteArrayList<Brick>();
		Brick brick;
		for(int i = 0;;i++) {
			x = wid * (numCol + numRow);//左边砖块向下依次减少一个
			y = heig * numRow;
			numCol++;
			brick = new Brick(x, y,id++);
			brickList.add(brick);	
			if(x > BallFrame.GAME_WIDTH - numRow*wid - Brick.BRICK_WIDTH - wid) {//右边砖块向下依次减少
				//如果一行结束，列数清零，行数加一
				numCol = 0;
				numRow++;
			}
			if(x + wid + BallFrame.GAME_WIDTH - numRow*wid == BallFrame.GAME_WIDTH) {//当一行只剩一块砖时，结束
				break;
			}
		}	
		points = new ArrayList<Point>();
		Point point = null;
		for(Brick b : brickList) {
			point = new Point(b.posX, b.posY);
			points.add(point);
		}
		new Thread(new ComputeOutBricksThread()).start();//开启计算外砖线程
		setDaojus();//调用放道具方法
	}
	//在砖块里放道具
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
	 * 随机指定范围内N个不重复的数 
	 * 利用HashSet的特征，只能存放不同的值 
	 * @param min 指定范围最小值 
	 * @param max 指定范围最大值 
	 * @param n 随机数个数 
	 * @param HashSet<Integer> set 随机数结果集 
	 */  
   public static void randomSet(int min, int max, int n, HashSet<Integer> set) {  
       if (n > (max - min + 1) || max < min) {  
           return;  
       }  
       for (int i = 0; i < n; i++) {  
           // 调用Math.random()方法  
           int num = (int) (Math.random() * (max - min)) + min;  
           set.add(num);// 将不同的数存入HashSet中  
       }  
       int setSize = set.size();  
       // 如果存入的数小于指定生成的个数，则调用递归再生成剩余个数的随机数，如此循环，直到达到指定大小  
       if (setSize < n) {  
        randomSet(min, max, n - setSize, set);// 递归  
       }  
   }  
	//画砖块
	public void drawBricks(Graphics g) {
		for(Brick brick : brickList) {			
			brick.draw(g);
		}
		
	}
	//画挡板
	public void drawBoard(Graphics g) {	
		board.draw(g);
	}
	//画弹球
	public void drawBall(Graphics g) {
		ball.draw(g);
		if(isFail() || isSuccess()) {
			
		}else {
			runBall(g);//小球运动
		}
	}
	//画道具
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
	
	
	//游戏成功或失败时，显示提示符
	public void drawReminder(Graphics g) {
		Reminder reminder;
		if(isFail()) {
			reminder = new Reminder(BallFrame.GAME_WIDTH/3, BallFrame.GAME_HEIGHT/3, Reminder.SIZE_BIG);
			reminder.draw(g, "游戏失败！");
			reminder = new Reminder(BallFrame.GAME_WIDTH/3, BallFrame.GAME_HEIGHT/3 + 30, Reminder.SIZE_SMALL);
			reminder.draw(g, "按space键继续，ctrl键退出");
		}
		if(isSuccess()) {
			reminder = new Reminder(BallFrame.GAME_WIDTH/4, BallFrame.GAME_HEIGHT/3, Reminder.SIZE_BIG);
			reminder.draw(g, "好厉害！你打完了所有砖块！！");
			reminder = new Reminder(BallFrame.GAME_WIDTH/3, BallFrame.GAME_HEIGHT/3 + 30, Reminder.SIZE_SMALL);
			reminder.draw(g, "按space键继续，ctrl键退出");
		}
	}
	
	//按键移动挡板
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
	//小球运动
	public void runBall(Graphics g) {
		ball.posX += (ball.dirX * ball.speedX);
		ball.posY += (ball.dirY * ball.speedY);
		//与挡板相撞时，x方向速度加1，y方向速度反向
		if(ball.getRect().intersects(board.getRect())) {
			ball.speedX++;			
			ball.dirY = - ball.dirY;
		}
		//遍历外砖，与砖块相撞时
		int brick_x;int brick_y;
		for(Brick brick : brickList) {
			if(ball.getRect().intersects(brick.getRect())) {
				//判断小球具体与砖块哪一面碰撞，因为小球以矩形判断相交，为更准确设置补偿距离
				brick_x = brick.posX;
				brick_y = brick.posY;
				int dis = 2;//设置补偿距离为2
				if(ball.posX > brick_x - ball.BALL_WIDTH + dis && ball.posX < brick_x + Brick.BRICK_WIDTH - dis) {//与上或下边缘相撞时		
					brickList.remove(brick);
					if(brick.getDaoJu() != null) {//如果砖块里有道具，设置道具掉落
						brick.getDaoJu().setFall(true);
					}
					points.remove(new Point(brick_x, brick_y));
					new Thread(new ComputeOutBricksThread()).start();//开启计算外砖线程
					if(ball.posY >= brick_y ) {
						//与砖块下边缘相撞时，x方向速度加1，y方向速度反向
						if(ball.speedX > Ball.BALL_MAX_SPEEDX) {
							ball.speedX = Ball.BALL_MAX_SPEEDX;
						}else {
							ball.speedX++;
						}
						ball.dirY = - ball.dirY;
					}
					else {
						//与砖块上边缘相撞时，x方向速度加1，y方向速度反向
						if(ball.speedX > Ball.BALL_MAX_SPEEDX) {
							ball.speedX = Ball.BALL_MAX_SPEEDX;
						}else {
							ball.speedX++;
						}			
						ball.dirY = - ball.dirY;
					}
				}
				else if(ball.posY > brick_y - Ball.BALL_HEIGHT + dis && ball.posY < brick_y + Brick.BRICK_HEIGHT - dis) {//与左或右边缘相撞时
					brickList.remove(brick);
					points.remove(new Point(brick_x, brick_y));
					if(brick.getDaoJu() != null) {//如果砖块里有道具，设置道具掉落
						brick.getDaoJu().setFall(true);
					}
					new Thread(new ComputeOutBricksThread()).start();//开启计算外砖线程
					//x方向速度反向，y方向速度加1
					if(ball.speedY > Ball.BALL_MAX_SPEEDY) {
						ball.speedY = Ball.BALL_MAX_SPEEDY;
					}else {
						ball.speedY++;
					}
					ball.dirX = -ball.dirX;
				}
				
			}
		}
		//与上边墙相撞时，x方向速度减1，y方向速度反向
		if(ball.posY < 0) {
			ball.speedX--;			
			ball.dirY = - ball.dirY;
		}
		//与两边墙相撞时，x方向速度反向
		if(ball.posX < 0 || ball.posX > BallFrame.GAME_WIDTH - Ball.BALL_WIDTH) {
			ball.dirX = - ball.dirX;
		}	
	}
	//判断道具与挡板是否相撞
	public boolean isDaoJuHitBoard(DaoJu daoJu) {
		if(daoJu.getRect().intersects(board.getRect())) {
			return true;
		}else {
			return false;
		}
	}
	
	//判断游戏失败
	public boolean isFail() {
		if(ball.posY > BallFrame.GAME_HEIGHT) { 			
			return true;
		}else {
			return false;
		}	
	}
	//判断游戏成功
	public boolean isSuccess() {
		if(brickList.size() == 0) {
			return true;
		}else {
			return false;
		}
	}
	
	//计算外砖的线程，每次小球碰撞砖块后开启
	private class ComputeOutBricksThread implements Runnable{
		@Override
		public void run() {
			Point point = null;
			int id;
			int index1; int index2; int index3; int index4;
			//遍历砖块集合，确认外砖序号，存入集合
			for(Brick brick : brickList) {
				int x = brick.posX;
				int y = brick.posY;
		//		 if(x != 0 || y != 0 || x != BallFrame.GAME_WIDTH - Brick.BRICK_WIDTH) {//排除靠墙的
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
				 if(index1 < 0 || index2 < 0 || index3 < 0 || index4 < 0) {//只要相应位置没有砖，则为外转
					 outBricks.add(brick);
				 }
			}
		}		
	}
	
}
