package com.yang.ballGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Brick extends Stuff{
	public static final int BRICK_WIDTH = 35;
	public static final int BRICK_HEIGHT = 15;
	
	
	private int id;//砖块id，用来标记初始布置所有砖块
	private DaoJu daoJu = null;
	private boolean isLive = true;//是否存在，即是否被消灭
	
	//set和get
	public boolean isLive() {
		return isLive;
	}
	public DaoJu getDaoJu() {
		return daoJu;
	}
	public void setDaoJu(DaoJu daoJu) {
		this.daoJu = daoJu;
	}
	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	//构造方法
	public Brick(int posX, int posY) {
		super(posX, posY);
	}
	public Brick(int posX, int posY,int id) {
		super(posX, posY);
		this.id = id;
	}

	public Brick(int posX, int posY,int id, DaoJu daoJu) {
		super(posX,posY);
		this.id = id;
		this.daoJu = daoJu;
	}
	@Override
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillRect(posX-1, posY-1, BRICK_WIDTH-1, BRICK_HEIGHT-1);
		g.setColor(c);
	}
	@Override
	public Rectangle getRect() {
		return new Rectangle(this.posX,this.posY,this.BRICK_WIDTH,this.BRICK_HEIGHT);
	}
	
}
