package masterserver;

import java.util.ArrayList;

import masterserver.connection.*;

public class MasterServer extends Thread{
	private int guestCount = 0;
	
	public static void main(String args[]){
			
		MasterServer server = new MasterServer();
		
		new Connector(server).start();

	}
	
	public int getNewGuestNumber() {
		return ++guestCount;
	}
}
