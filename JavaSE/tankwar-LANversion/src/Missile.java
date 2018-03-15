import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;


public class Missile {

	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	private static int ID = 1;
	
	int x,y;
	TankClient tc;
	
	int tankId;
	int id;
	
	boolean live = true;

	boolean good;
	
	public boolean isGood() {
		return good;
	}
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}

	Direction dir;

	public Missile(int tankId,int x, int y,Direction dir, boolean good) {
		super();
		this.tankId = tankId;
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.good = good;
		
		this.id = ID++;
	}
	public Missile(int tankId,int x, int y, Direction dir,boolean good,TankClient tc) {
		this(tankId,x, y, dir,good);
		this.tc = tc;
	}
	

	void draw(Graphics g) {
		if(!live) {
			tc.missiles.remove(this);
			return;
		}
		Color c = g.getColor();
		if(good) g.setColor(Color.BLACK);	
		else g.setColor(Color.BLUE);
		g.fillOval(x,y,WIDTH,HEIGHT);
		g.setColor(c);
		
		move();
	}
	
	private void move() {
		switch (dir) {
		case L: x -= XSPEED; break;
		case LU: x -= XSPEED;y -= YSPEED; break;
		case U: y -= YSPEED; break;
		case RU: x += XSPEED;y -= YSPEED; break;
		case R: x += XSPEED; break;
		case RD: x += XSPEED;y += YSPEED; break;
		case D: y += YSPEED; break;
		case LD: x -= XSPEED;y += YSPEED; break;
		default:
			break;
		}
		
		if(x < 0 || y < 0 ||x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT) {
			live = false;
		}
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y,WIDTH,HEIGHT);
	}
	
	public boolean hitTank(Tank t) {
		if(this.live && this.getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood()) {
			live = false;
			t.setLive(false);
			Explode e = new Explode(x, y, tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
	}
	
	public void hitTanks(List<Tank> tanks) {
		for(int i = 0;i < tanks.size();i++) {
			this.hitTank(tanks.get(i));
		}
	}


}
