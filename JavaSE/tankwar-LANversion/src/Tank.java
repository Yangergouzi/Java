import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

import org.omg.CORBA.TCKind;

public class Tank {
	public int id;

	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	private boolean live = true;
	
	public int x,y;
	public boolean good = true;
	
	
	static Random r = new Random();
	int step = r.nextInt(16) + 3;
	
	public Tank(int x, int y,boolean good) {
		super();
		this.x = x;
		this.y = y;
		this.good = good;
	}	
	
	public Tank(int x, int y, boolean good,TankClient tc) {
		this(x,y,good);
		this.tc = tc;
	}
	
	public Tank(int x, int y, boolean good,TankClient tc,Direction dir) {
		this(x,y,good,tc);
		this.dir = dir;
	}
	
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isGood() {
		return good;
	}

	private boolean bL = false,bU = false,bR = false,bD = false;
//	enum Direction{L,LU,U,RU,R,RD,D,LD,STOP};
	Direction dir = Direction.STOP;
	Direction  ptDir = Direction.D;
	
	TankClient tc;
	
	void move() {
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
		if(x < 0) {
			x = 0;
		}
		if(y < 25) { 
			y = 25;
		}
		if(x > TankClient.GAME_WIDTH - Tank.WIDTH) {
			x = TankClient.GAME_WIDTH - Tank.WIDTH;
		}
		if(y >TankClient.GAME_HEIGHT - Tank.HEIGHT) {
			y = TankClient.GAME_HEIGHT - Tank.HEIGHT;
		}
		
		if(dir != Direction.STOP) {
			ptDir = dir;
		}
		
//		if(!good) {
//			Direction[] dirs = Direction.values();
//			if(step == 0) {
//				int rn = r.nextInt(dirs.length);
//				dir = dirs[rn];
//				step = r.nextInt(16) + 3;
//			}
//			step--;
//			
//			if(r.nextInt(40) - 38 > 0) this.fire();
//		}
	}
	
	void draw(Graphics g) {
		if(!live) {
			if(!good) {
				tc.tanks.remove(this);
			}
			return;
		}
		Color c = g.getColor();
		if(good)	g.setColor(Color.RED);
		else 	g.setColor(Color.BLUE);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.drawString("ID:" + this.id,x, y - 10);
		g.setColor(c);
		
		move();
		
		switch (ptDir) {
		case L: g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y + Tank.HEIGHT/2); break;
		case LU: g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y); break;
		case U: g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2, y); break;
		case RU: g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y); break;
		case R: g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y + Tank.HEIGHT/2); break;
		case RD: g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y + Tank.HEIGHT); break;
		case D: g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2, y + Tank.HEIGHT); break;
		case LD: g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y + Tank.HEIGHT); break;
		default:
			break;
		}
	}
	
	
	void pressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
 		default:
			break;
		}
		locateDirection();
	}
	void released(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		case KeyEvent.VK_CONTROL:
			fire();			
			break;
		default:
			break;
		}
		locateDirection();
	}
	
	private void locateDirection() {
		
		Direction oldDir = this.dir;
		
		if(bL&&!bU&&!bR&&!bD) {
			dir = Direction.L;
		}
		else if(bL&&bU&&!bR&&!bD) {
			dir = Direction.LU;
		}
		else if(bU&&!bL&&!bR&&!bD) {
			dir = Direction.U;
		}
		else if(bR&&bU&&!bL&&!bD) {
			dir = Direction.RU;
		}
		else if(bR&&!bU&&!bL&&!bD) {
			dir = Direction.R;
		}
		else if(bR&&bD&&!bU&&!bL) {
			dir = Direction.RD;
		}
		else if(bD&&!bL&&!bU&&!bR) {
			dir = Direction.D;
		}
		else if(bL&&bD&&!bU&&!bR) {
			dir = Direction.LD;
		}
		else {
			dir = Direction.STOP;
		}
		
		if(dir != oldDir) {
			TankMoveMsg msg = new TankMoveMsg(this);
			tc.nc.send(msg);
		}
	}
	
	public void fire() {
		if(!live) return;
		
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		Missile m;
		if(good) {
			m = new Missile(id,x, y, ptDir,true,tc);
		}
		else{
			m = new Missile(id, x, y, ptDir,false,tc);
		}
		tc.missiles.add(m);
		
		MissileNewMsg msg = new MissileNewMsg(m);
		tc.nc.send(msg);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
}
