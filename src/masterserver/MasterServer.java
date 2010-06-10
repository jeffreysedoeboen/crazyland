package masterserver;

import java.util.ArrayList;

import masterserver.connection.*;

public class MasterServer extends Thread{
	
	private int guestCount = 0;
	private ArrayList<Connection> connectionList = new ArrayList<Connection>();
	
	public static void main(String args[]){
			
		MasterServer server = new MasterServer();
		
		new Connector(server).start();

	}

	public void addConnection(Connection c) {
		this.connectionList.add(c);		
	}
	
	public int getNewGuestNumber() {
		return ++guestCount;
	}
}
