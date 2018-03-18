/*坦克大战单机版：
 * 方向键控制八个方向移动；
 * ctrl键发射炮弹,A键同时发射八个方向炮弹；
 * 图形化血条；
 * 添加“血块”，我军坦克吃了满血复活;
 * 敌方坦克死完了，重新出现,我方坦克死了，按F2复活;
 */


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame{
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	Image offScreenImage = null;
	
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.lanchFrame();
	}
	
	Tank myTank = new Tank(50, 50,true,this);	
	
	List<Tank> enemyTanks = new ArrayList<Tank>();
	List<Missile> missiles = new ArrayList<Missile>();
	List<Explode> explodes = new ArrayList<Explode>();
	
	Wall w1 = new Wall(200, 200, 20, 250, this);
	Wall w2 = new Wall(300, 200, 400, 20, this);
	
	Blood blood = new Blood(this);
		
	public void paint(Graphics g) {
		w1.draw(g);
		w2.draw(g);
		myTank.draw(g);
		for(int i = 0;i < enemyTanks.size();i++) {
			Tank enemyTank = enemyTanks.get(i);
			enemyTank.draw(g);
		}
		blood.draw(g);
		
		g.drawString("Missile count:" + missiles.size(), 10, 50);
		g.drawString("Explode count:" + explodes.size(), 10, 65);
		g.drawString("EnemyTanks count:" + enemyTanks.size(), 10, 80);
		g.drawString("myTank life:" + myTank.getLife(), 10, 95);
		for(int i=0;i<missiles.size();i++) {
			Missile m = missiles.get(i);
//			if(!myMissile.isLive()) {
//				missiles.remove(i);
//			}
			
//			m.hitTanks(enemyTanks);
//			m.hitTank(myTank);
//			m.hitWall(w1);
//			m.hitWall(w2);
			
			
		    m.draw(g);
		}
		
		for( int i = 0;i < explodes.size();i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}
		
		myTank.eat(blood);
		
		if(enemyTanks.size() <= 0) {
			for(int i = 0;i<5;i++) {
				Tank eTank = new Tank(250 + 40 * (i + 1), 250, false, this);
				enemyTanks.add(eTank);
			}
		}
	}
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage( GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		
	}


	public void lanchFrame() {
		
		for(int i = 0;i<10;i++) {
			Tank eTank = new Tank(250 + 40 * (i + 1), 250, false, this);
			enemyTanks.add(eTank);
		}
		
		this.setTitle("Tank War");
	//	this.setLocation(400, 300);
		this.setSize( GAME_WIDTH, GAME_HEIGHT);
		this.setBackground(Color.GREEN);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}	
		});
		this.addKeyListener(new KeyMonitor());
		setVisible(true);
		
		new Thread(new PaintThread()).start();
	}
	
	private class PaintThread implements Runnable {

		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	private class KeyMonitor extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			myTank.released(e);
			
		}

		public void keyPressed(KeyEvent e) {
			myTank.pressed(e);
		}	   
	}
	
}

