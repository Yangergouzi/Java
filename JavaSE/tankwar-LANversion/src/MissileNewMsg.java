import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class MissileNewMsg implements Msg {
int msgType = MISSILE_NEW_MSG;
	Missile m;
	TankClient tc;
	
	public MissileNewMsg(TankClient tc) {
		this.tc = tc;
	}
	public MissileNewMsg(Missile m) {
		this.m = m;
	}
	
	public void send(DatagramSocket ds,String IP,int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(m.id);
			dos.writeInt(m.tankId);
			dos.writeInt(m.x);
			dos.writeInt(m.y);
			dos.writeInt(m.dir.ordinal());
			dos.writeBoolean(m.good);
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
			int id = dis.readInt();
			int tankId = dis.readInt();
			if(tankId == tc.myTank.id) {
				return;
			}
			int x = dis.readInt();
			int y = dis.readInt();
			Direction dir = Direction.values()[dis.readInt()];
			boolean good = dis.readBoolean();
//System.out.println("ID:" + id +" -x:" + x + " -y:" + y + " -dir:" + dir + " -good:" + good);
			
			Missile m = new Missile(tankId, x, y, dir, good, tc);
			m.id = id;
			tc.missiles.add(m);	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
