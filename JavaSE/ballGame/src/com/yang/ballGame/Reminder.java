package com.yang.ballGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Reminder {
	public static final int SIZE_SMALL = 15;
	public static final int SIZE_MEDIUM = 20;
	public static final int SIZE_BIG = 30;
	int posX;
	int posY;
	int size;
	Color color = Color.RED;
	
	public Reminder(int posX, int posY, int size, Color color) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.size = size;
		this.color = color;
	}

	public Reminder(int posX, int posY, int size) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.size = size;
	}

	public void draw(Graphics g,String str) {
		Color color = g.getColor();
		g.setColor(color);	
		g.setFont(new Font("ËÎÌו", Font.BOLD, size));
		g.drawString(str, posX, posY);
		g.setColor(color);
	}
}
