import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Blood {
	int x,y,w = 10,h = 10;
	public Blood(TankClient tc) {
		this.tc = tc;
	}

	TankClient tc;
	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	int[][] pos = {
			{350,350},{360,300},{360,340},{380,340},{370,360},{360,350}
	};
	
	int step = 0;
	
	void draw(Graphics g) {
		if(!live)	return;
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x,y,w,h);
		
		move();		
	}
	
	void move() {
		step++;
		if(step == pos.length) {
			step = 0;
		}
		
		x = pos[step][0];
		y = pos[step][1];
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
}
