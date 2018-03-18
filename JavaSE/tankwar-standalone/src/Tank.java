import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.List;

public class Tank {

	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	public static final int FILL_LIFE = 100;
	private boolean live = true;
	private int life = FILL_LIFE;
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	private int x,y;
	private boolean good = true;
	private int oldX,oldY;
	
	static Random r = new Random();
	int step = r.nextInt(16) + 3;
	
	BloodBar bb = new BloodBar();
	
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
	enum Direction{L,LU,U,RU,R,RD,D,LD,STOP};
	Direction dir = Direction.STOP;
	Direction  ptDir = Direction.D;
	
	TankClient tc;
	
	void stay() {
		x = oldX;
		y = oldY;
	}
	
	void move() {
		
		oldX = x;
		oldY = y;
		
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
		
		if(!good) {
			Direction[] dirs = Direction.values();
			if(step == 0) {
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
				step = r.nextInt(16) + 3;
			}
			step--;
			
			if(r.nextInt(40) - 38 > 0) this.fire();
		}
	}
	
	void draw(Graphics g) {
		if(!live) {
			if(!good) {
				tc.enemyTanks.remove(this);
			}
			return;
		}
		
		if(good) {
			bb.draw(g);
		}
		
		Color c = g.getColor();
		if(good)	g.setColor(Color.RED);
		else 	g.setColor(Color.BLUE);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		move();
		this.collidesWithWall(tc.w1);
		this.collidesWithWall(tc.w2);
		
		this.collidesWithTanks(tc.enemyTanks);
		
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
		case KeyEvent.VK_F2:
			if(!this.live) {
				this.live = true;
				this.life = FILL_LIFE;
			}
		
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
		case KeyEvent.VK_A:
			superFire();
			break;
		default:
			break;
		}
		locateDirection();
	}
	
	private void locateDirection() {
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
	}
	
	private void fire() {
		if(!live) return;
		
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		Missile m;
		if(good) {
			m = new Missile(x, y, ptDir,true,tc);
		}
		else{
			m = new Missile(x, y, ptDir,false,tc);
		}
		tc.missiles.add(m);
	}
	
	private void fire(Direction dir) {
		if(!live) return;
		
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		Missile m;
		if(good) {
			m = new Missile(x, y, dir,true,tc);
		}
		else{
			m = new Missile(x, y, dir,false,tc);
		}
		tc.missiles.add(m);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public boolean collidesWithWall(Wall wall) {
		if(this.live && this.getRect().intersects(wall.getRect())) {
			stay();
			return true;
		}
		return false;
	}
	
	public boolean collidesWithTanks(List<Tank> tanks) {		
		for(int i = 0;i<tanks.size();i++) {	
			Tank t = tanks.get(i);
			if(this != t && this.live && t.isLive() && this.getRect().intersects(t.getRect())) {
				stay();
				return true;
			}
		}
		return false;
	}
	
	private void superFire() {
		Direction[] dirs = Direction.values();
		for(int i = 0;i<8;i++) {
			fire(dirs[i]);
		}
	}
	
	public boolean eat(Blood b) {
		if(good && this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
			this.life = FILL_LIFE;
			b.setLive(false);
			return true;
		}
		return true;
	}
	
	private class BloodBar{
		void draw(Graphics g) {
			Color c = g.getColor();
			g.drawRect(x, y - 15, WIDTH, 10);
			g.setColor(Color.RED);
			int width = WIDTH * life/FILL_LIFE;
			g.fillRect(x, y - 15, width, 10);
			g.setColor(c);
			}
	}
	
	
}
