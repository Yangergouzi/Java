import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankMoveMsg implements Msg {
	int msgType = TANK_MOVE_MSG;
	
	Tank tank;
	TankClient tc;
	
	public TankMoveMsg(TankClient tc) {
		this.tc = tc;
	}
	public TankMoveMsg(Tank tank) {
		this.tank = tank;
	}
	
	public void send(DatagramSocket ds,String IP,int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(msgType);
			dos.writeInt(tank.id);
			dos.writeInt(tank.x);
			dos.writeInt(tank.y);
			dos.writeInt(tank.dir.ordinal());

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
			if(id == tc.myTank.id) {
				return;
			}
			int x = dis.readInt();
			int y = dis.readInt();
			Direction dir = Direction.values()[dis.readInt()];
//System.out.println("ID:" + id +" -x:" + x + " -y:" + y + " -dir:" + dir + " -good:" + good);
		
			boolean exist = false;
		
			for(int i=0;i<tc.tanks.size();i++) {
				Tank t = tc.tanks.get(i);
				if(t.id == id) {
					t.x = x;
					t.y = y;
					t.dir = dir;
					exist = true;
					return;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
}
