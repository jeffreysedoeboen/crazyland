package client.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.print.attribute.standard.OutputDeviceAssigned;

import client.model.GameServer;
import client.model.LBPlayer;

public class MasterConnection {

	private PrintWriter out;
	private Scanner in;
	
	public MasterConnection(PrintWriter out, Scanner in){
		this.out = out;
		this.in = in;
	}
	
	public ArrayList<GameServer> getGameServers() {
		out.println("get_gameservers");
		ArrayList<GameServer> serverlist = new ArrayList<GameServer>();
		String line = "";
		if(in.hasNext()) {
			line = in.nextLine();
		}
		if (line.equals("access_denied")) {
			System.out.println("access denied for getting game servers");
		} else if (line.equals("gameservers_begin")) {
			boolean gameserversEnd = false;
			while (!gameserversEnd && in.hasNext()) {
				String tmp = in.nextLine();
				if (tmp.equals("gameservers_end")) {
					gameserversEnd = true;
				} else {
					String[] server = tmp.split(",");
					serverlist.add(new GameServer(server[0], server[1]));
				}
			}
		} else {
			System.out.println("unexpected response from server:" + line);
		}
		return serverlist;
	}
	
	public LBPlayer[] getLeaderboard() {
		out.println("get_leaderboard");
		LBPlayer[] leaderboard = new LBPlayer[20];
		String line = "";
		if(in.hasNext()) {
			line = in.nextLine();
		}
		if (line.equals("access_denied")) {
			System.out.println("access denied for getting leaderboard");
		} else if (line.equals("leaderboard_begin")) {
			boolean leaderboardEnd = false;
			int i = 0;
			while (!leaderboardEnd && in.hasNext()) {
				String tmp = in.nextLine();
				if (tmp.equals("leaderboard_end")) {
					leaderboardEnd = true;
				} else {
					String[] lbplayer = tmp.split(",");
					leaderboard[i++] = new LBPlayer(Long.parseLong(lbplayer[0]), lbplayer[1], Long.parseLong(lbplayer[2]), Long.parseLong(lbplayer[3]));
				}
			}
		} else {
			System.out.println("unexpected response from server:" + line);
		}
		return leaderboard;
	}
	
	public boolean login(String name, String password) {
		MessageDigest m;
		String passwordHash = "";
		try {
			//creating md5 hash of password
			m = MessageDigest.getInstance("MD5");
			m.update(password.getBytes(),0,password.length());
			passwordHash = new BigInteger(1,m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("ERROR while creating password hash! " + e.getMessage());
		}
		
		String output = "login\n";
		output += name + "," + passwordHash;
		out.println(output);
		
		String line = "";
		if(in.hasNext()) {
			line = in.nextLine();
		}
		if (line.equals("login_ok")) {
			return true;
		} else if (line.equals("login_false")) {
			return false;
		} else {
			System.out.println("unexpected response from server");
		}
		return false;
	}
	
	public int getGuestAccess() {
		int guestNumber = 0;
		String output = "get_guest_access";
		out.println(output);
		
		String line = "";
		if(in.hasNext()) {
			line = in.nextLine();
		}
		if (line.equals("guest_access_granted")) {
			if(in.hasNext()) {
				guestNumber = Integer.parseInt(in.nextLine());
			}
		} else {
			System.out.println("unexpected response from server");
		}
		return guestNumber;
	}
	
	public static void main (String[] a){
		Socket s = null;
		InputStream inStream = null;
		OutputStream outStream = null;
		Scanner in = null;
		PrintWriter out = null;

		// check if we can talk with the server
		System.out
				.println("\nEen verbinding proberen te maken met met de server...");

		try {
			s = new Socket("127.0.0.1", 1338);
			inStream = s.getInputStream();
			outStream = s.getOutputStream();
			in = new Scanner(inStream);
			out = new PrintWriter(outStream, true /* autoFlush */);
		} catch (IOException ioe) {
			System.out
					.println("\nKon niet met de server een verbinding maken. Check uw internet verbinding of whatever.. =/ .");
		}
		System.out.println("has connection");

		MasterConnection c = new MasterConnection(out, in);
		
		if (c.login("jef", "lol"))
			System.out.println("true");
		else
			System.out.println("false");
		
		for(GameServer g : c.getGameServers()) {
			System.out.println(g.getName() + " " +g.getIp());
		}
	}

	public boolean addAccount(String userName, String password) {
		MessageDigest m;
		String passwordHash = "";
		try {
			//creating md5 hash of password
			m = MessageDigest.getInstance("MD5");
			m.update(password.getBytes(),0,password.length());
			passwordHash = new BigInteger(1,m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("ERROR while creating password hash! " + e.getMessage());
		}
		
		String output = "sign_up\n";
		output += userName + "," + passwordHash;
		out.println(output);
		
		String line = "";
		if(in.hasNext()) {
			line = in.nextLine();
		}
		if (line.equals("sign_up_successful")) {
			return true;
		} else if (line.equals("sign_up_failed")) {
			return false;
		} else {
			System.out.println("unexpected response from server");
		}
		return false;
	}
	
}
