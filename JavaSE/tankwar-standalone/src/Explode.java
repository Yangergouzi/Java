import java.awt.Color;
import java.awt.Graphics;

public class Explode {
	int x,y;
	TankClient tc;
	public Explode(int x, int y,TankClient tc) {
		super();
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	private boolean live = true;
	
	int[] diameter = {4,7,12,18,26,32,49,30,14,6};
	int step = 0;
	
	public void draw(Graphics g) {
		if(step == diameter.length) {
			step = 0;
			live = false;
		}
		if(!live)	{
			tc.explodes.remove(this);
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		g.fillOval(x - diameter[step]/2, y - diameter[step]/2, diameter[step], diameter[step]);
		g.setColor(c);
		step++;
		
 	}
	
}
