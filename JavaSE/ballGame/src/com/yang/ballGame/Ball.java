package com.yang.ballGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ball extends Stuff {
	public static final int BALL_WIDTH = 15;
	public static final int BALL_HEIGHT = 15;
	public static final int BALL_MAX_SPEEDX = 5;
	public static final int BALL_MAX_SPEEDY = 13;
	public int speedX = 2;
	public int speedY = 5;
	public int dirX = 1;
	public int dirY = 1;
	
	public Ball(int posX, int posY) {
		super(posX, posY);
	}

	public Ball(int posX, int posY, int speedX,int speedY) {
		super(posX, posY);
		this.speedX = speedX;
		this.speedY = speedY;
	}

	@Override
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.BLUE);
		g.fillOval(posX, posY, BALL_WIDTH, BALL_HEIGHT);
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle(this.posX,this.posY,this.BALL_WIDTH,this.BALL_HEIGHT);
	}
	
	

}
