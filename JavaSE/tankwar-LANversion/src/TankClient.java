/*
 * 坦克大战网络版：
 * 先运行服务端，即TankServer，再运行客户端TankClient；
 * 在客户端按C键连接到服务端
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
	
	NetClient nc = new NetClient(this);
	
	connDialog cDialog = new connDialog();
	
	Tank myTank = new Tank(50, 50,true,this);	
	
	List<Tank> tanks = new ArrayList<Tank>();
	List<Missile> missiles = new ArrayList<Missile>();
	List<Explode> explodes = new ArrayList<Explode>();
		
	public void paint(Graphics g) {
		myTank.draw(g);
		for(int i = 0;i < tanks.size();i++) {
			Tank tank = tanks.get(i);
			tank.draw(g);
		}
		g.drawString("Missile count:" + missiles.size(), 10, 50);
		g.drawString("Explode count:" + explodes.size(), 10, 65);
		g.drawString("EnemyTanks count:" + tanks.size(), 10, 80);
		for(int i=0;i<missiles.size();i++) {
			Missile m = missiles.get(i);
			if(m.hitTank(myTank)) {
				TankDeadMsg msg = new TankDeadMsg(myTank.id);
				nc.send(msg);	
				MissileDeadMsg misMsg = new MissileDeadMsg(m.id, m.tankId);
				nc.send(misMsg);
			}
		    m.draw(g);
		}
		
		for( int i = 0;i < explodes.size();i++) {
			Explode e = explodes.get(i);
			e.draw(g);
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
		
//		nc.connect("127.0.0.1",TankServer.TCP_PORT);
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
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_C) {
				cDialog.setVisible(true);
			}
			else myTank.pressed(e);
		}	   
	}
	
	private class connDialog extends Dialog{
		TextField tfIP = new TextField("127.0.0.1", 12);
		TextField tfPort = new TextField("" + TankServer.TCP_PORT, 4);
		TextField tfUdp = new TextField("2223", 4);
		Button btn = new Button("确认");
		
		public connDialog() {
			super(TankClient.this, true);
			
			this.setLayout(new FlowLayout());
			this.add(new Label("IP:"));
			this.add(tfIP);
			this.add(new Label("Port:"));
			this.add(tfPort);
			this.add(new Label("UDP port:"));
			this.add(tfUdp);
			this.add(btn);
			this.pack();
			this.setLocation(300, 300);
			
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					setVisible(false);
				}		
			});
			
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String ip = tfIP.getText().trim();
					int port = Integer.parseInt(tfPort.getText().trim());
					int udpPort = Integer.parseInt(tfUdp.getText().trim());
					setVisible(false);
					nc.setUdpPort(udpPort);
					nc.connect(ip, port);
				}
			});
		}
		
	}
	
}

