import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class NetClient {
	TankClient tc;
	DatagramSocket ds = null;
	
	public NetClient(TankClient tc) {
		this.tc = tc;
	}
	
	String IP;
	private int udpPort;
	
	public int getUdpPort() {
		return udpPort;
	}

	public void setUdpPort(int udpPort) {
		this.udpPort = udpPort;
	}
	
	public void connect(String IP,int port) {	
		
		this.IP = IP;
		
		try {
			ds = new DatagramSocket(udpPort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		Socket s = null;
		try {
			s = new Socket(IP, port);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeInt(udpPort);
			DataInputStream dis = new DataInputStream(s.getInputStream());
			tc.myTank.id = dis.readInt();
			System.out.println("Connect to server!");
	//		s.close();			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(s != null) {
				try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		TankNewMsg msg = new TankNewMsg(tc.myTank);
		send(msg);
		
		new Thread(new UDPRecThread()).start();
	}
	
	public void send(Msg msg) {
		msg.send(ds,IP,TankServer.UDP_PORT);
	}
	
	private class UDPRecThread implements Runnable{
		
		byte[] buf = new byte[1024];
		
		public void run() {
			while(ds != null) {
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				try {
					ds.receive(dp);
System.out.println("a packet recevied from server!");	
					prase(dp);
				} catch (IOException e) {
					e.printStackTrace();
				}		
			}
		}
		
		private void prase(DatagramPacket dp) {
			ByteArrayInputStream bais = new ByteArrayInputStream(buf, 0, dp.getLength());
			DataInputStream dis = new DataInputStream(bais);
			int msgType = 0;
			try {
				msgType = dis.readInt();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Msg msg = null;
			
			switch (msgType) {
			case Msg.TANK_NEW_MSG:
				msg = new TankNewMsg(tc);
				msg.prase(dis);
				break;
			case Msg.TANK_MOVE_MSG:
				msg = new TankMoveMsg(tc);
				msg.prase(dis);
			case Msg.MISSILE_NEW_MSG:
				msg = new MissileNewMsg(tc);
				msg.prase(dis);
			case Msg.TANK_DEAD_MSG:
				msg = new TankDeadMsg(tc);
				msg.prase(dis);
				break;
			case Msg.MISSILE_DEAD_MSG:
				msg = new MissileDeadMsg(tc);
				msg.prase(dis);
				break;
			default:
				break;
			}
		}
		
	}
	

}
