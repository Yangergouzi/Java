package com.yang.ballGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Board extends Stuff{
	public static final int BOARD_WIDTH = 60;
	public static final int BOARD_HEIGHT = 10;
	public static final int BOARD_SPEED = 15;
	public static final int MAX_POSY = BallFrame.GAME_HEIGHT - Board.BOARD_HEIGHT - 30;
	
	public int speed = BOARD_SPEED;//设置默认速度
	private int width = BOARD_WIDTH;
	public Board(int posX, int posY, int speed) {
		super(posX, posY);
		this.speed = speed;
	}
	
	public Board(int posX, int posY) {
		super(posX, posY);
	}

	@Override
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillRect(posX, posY, width, BOARD_HEIGHT);
		g.setColor(c);
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle(this.posX,this.posY,this.BOARD_WIDTH,this.BOARD_HEIGHT);
	}
	//上升挡板
	public void upperBoard() {
		this.posY -= 8;
	}
	//下降挡板
	public void downerBoard() {
		this.posY += 8;
		if(this.posY >= MAX_POSY) {
			this.posY = MAX_POSY;
		}
	}
}
