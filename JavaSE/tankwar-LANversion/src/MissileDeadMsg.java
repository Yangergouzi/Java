import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class MissileDeadMsg implements Msg{
	int msgType = Msg.MISSILE_DEAD_MSG;
	TankClient tc;
	int tankId;
	int id;
	
	public MissileDeadMsg(int id,int tankId) {
		this.id = id;
		this.tankId = tankId;
	}
	
	public MissileDeadMsg(TankClient tc) {
		this.tc = tc;
	}
	
	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(tankId);
			dos.writeInt(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] buf = baos.toByteArray();
		
		DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(IP,udpPort));
		try {
			ds.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void prase(DataInputStream dis) {
		try {
			int tankId = dis.readInt();
			int id = dis.readInt();

			for(int i =0;i<tc.missiles.size();i++) {
				Missile m = tc.missiles.get(i);
				if(m.tankId == tankId && m.id == id) {
					m.setLive(false);
					Explode e = new Explode(m.x, m.y, tc);
					tc.explodes.add(e);
					break;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
