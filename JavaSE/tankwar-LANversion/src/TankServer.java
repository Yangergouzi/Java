import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class TankServer {
	public static final int TCP_PORT = 8888;
	public static final int UDP_PORT = 6666;
	private static int ID = 100;
	
	List<Client> clients = new ArrayList<Client>();

	private void start() {
		new Thread(new UDPThread()).start();
		Socket s = null;
		try {
			ServerSocket ss = new ServerSocket(TCP_PORT);
			while(true) {
				s = ss.accept();
				DataInputStream dis = new DataInputStream(s.getInputStream());
				int udpPort = dis.readInt();
				DataOutputStream dop = new DataOutputStream(s.getOutputStream());
				dop.writeInt(ID++);
				Client c = new Client(s.getInetAddress().getHostAddress(), udpPort);
				clients.add(c);
				System.out.println("A client connected! Addr:" + s.getInetAddress() + ":" + s.getPort() + "---UDP Port:" + udpPort);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(s != null) {
					s.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static void main(String[] args) {
		new TankServer().start();
	}
	
	private class Client{
		String IP;
		int udpPort;
		public Client(String iP, int udpPort) {
			IP = iP;
			this.udpPort = udpPort;
		}
	}
	
	private class UDPThread implements Runnable{

		public void run() {
			byte[] buf = new byte[1024];
			DatagramSocket ds = null;
			try {
				ds = new DatagramSocket(UDP_PORT);
			} catch (SocketException e) {
				e.printStackTrace();
			}
System.out.println("UDP thread start at port:" + UDP_PORT);
			while(ds != null) {
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				try {
					ds.receive(dp);
System.out.println("a packet recevied!");	
					for(int i=0;i<clients.size();i++) {
						Client c = clients.get(i);
						dp.setSocketAddress(new InetSocketAddress(c.IP, c.udpPort));
						ds.send(dp);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}		
			}
		}
		
	}

}
