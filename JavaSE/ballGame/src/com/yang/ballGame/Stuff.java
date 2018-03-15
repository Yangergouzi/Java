package com.yang.ballGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class Stuff {
	public int posX;
	public int posY;
//	public int speed;
	
	public Stuff() {}
	
	public Stuff(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public abstract void draw(Graphics g);
	public abstract Rectangle getRect();
	
}
