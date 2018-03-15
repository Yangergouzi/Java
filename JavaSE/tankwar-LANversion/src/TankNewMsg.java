import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankNewMsg implements Msg{
	int msgType = TANK_NEW_MSG;
	
	Tank tank;
	TankClient tc;
	
	public TankNewMsg(TankClient tc) {
		this.tc = tc;
	}
	public TankNewMsg(Tank tank) {
		this.tank = tank;
	}
	
	public void send(DatagramSocket ds,String IP,int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		
		if(tank.id%2 == 0) {
			tank.good = false;
		}else {
			tank.good = true;
		}
		
		try {
			dos.writeInt(msgType);
			dos.writeInt(tank.id);
			dos.writeInt(tank.x);
			dos.writeInt(tank.y);
			dos.writeInt(tank.dir.ordinal());
			dos.writeBoolean(tank.good);
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
			boolean good = dis.readBoolean();
//System.out.println("ID:" + id +" -x:" + x + " -y:" + y + " -dir:" + dir + " -good:" + good);
			boolean exists = false;
			for(int i =0;i<tc.tanks.size();i++) {
				Tank t = tc.tanks.get(i);
				if(t.id == id) {
					exists = true;
					break;
				}
			}
			
			if(!exists) {
				Tank t = new Tank(x, y, good, tc, dir);
				t.id = id;
				tc.tanks.add(t);	
				
				TankNewMsg msg = new TankNewMsg(tc.myTank);
				tc.nc.send(msg);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
