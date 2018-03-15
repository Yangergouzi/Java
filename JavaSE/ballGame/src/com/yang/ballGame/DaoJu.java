package com.yang.ballGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class DaoJu extends Stuff {
	public static final int DAOJU_WIDTH = 15;
	public static final int DAOJU_HEIGHT = 15;
	public static final int DAOJU_UP = 1;
	public static final int DAOJU_DOWN = 2;
	public static final int DAOJU_NONE = 0;
	
	public int type;
	public int speed = 3;
	private boolean isFall = false;
	
	public void setFall(boolean isFall) {
		this.isFall = isFall;
	}

	public boolean isFall() {
		return isFall;
	}

	public DaoJu(int posX,int posY, int type) {
		super(posX,posY);
		this.type = type;
	}

	public DaoJu(int posX,int posY, int type, int speed) {
		super(posX,posY);
		this.type = type;
		this.speed = speed;
	}

	@Override
	public void draw(Graphics g) {
		Color color = g.getColor();
		if(type == DAOJU_UP) {
			g.setColor(Color.RED);
			g.fillRect(posX,posY,DAOJU_WIDTH, DAOJU_HEIGHT);
		}
		if(type == DAOJU_DOWN) {
			g.setColor(Color.GRAY);
			g.fillRect(posX,posY,DAOJU_WIDTH, DAOJU_HEIGHT);
		}
		g.setColor(color);
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle(this.posX,this.posY,DAOJU_WIDTH,DAOJU_HEIGHT);
	}
	

}
