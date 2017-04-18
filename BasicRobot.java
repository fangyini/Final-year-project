package sockets;
//import lejos.utility.Delay;
import lejos.remote.ev3.RemoteRequestEV3;
import lejos.remote.ev3.RemoteRequestPilot;

public class BasicRobot {
	RemoteRequestEV3 brick;
	RemoteRequestPilot pilot;
	boolean haspilot=false;
	protected boolean disconnected=false;
	boolean connected=false;
	//EASSSensor sensor1;
	
	public void connect(String address)throws Exception{
		brick=new RemoteRequestEV3(address);
		connected=true;
		System.out.println("EV3 is connected");
	}
	
	public RemoteRequestEV3 getBrick(){
		return brick;
	}
	
	public void close(){
		disconnected=true;
			System.err.println("Disconnecting Brick");
			brick.disConnect();
			connected=false;
	}
	
	public boolean isConnected(){
		return connected;
	}
		
}
