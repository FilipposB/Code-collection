package udp.server;

import java.net.InetAddress;

public class ClientProfile {
	private static int id=1;
	private int profileId;
	
	public String name;
	
	public InetAddress ip;
	public int port;
	
	public int timeLimit;
	public int quitTimer;
	
	public ClientProfile(String nam,InetAddress ipAdr,int por,int limit){
		profileId=id;
		id++;
		
		ip=ipAdr;
		port=por;
		timeLimit=limit;
		
		name=nam;
	}
	
	public int getID(){
		return profileId;
	}
	
	public boolean toRemove(){
		return timeLimit<quitTimer;
	}
}
