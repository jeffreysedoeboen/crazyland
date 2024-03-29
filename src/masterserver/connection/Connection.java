package masterserver.connection;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import masterserver.MasterServer;
import masterserver.db.AccountDAO;
import masterserver.db.GameServerDAO;
import masterserver.db.LeaderDAO;
import masterserver.model.*;

public class Connection extends Thread {
	private Scanner in;
	private PrintWriter out;
	private MasterServer server;
	private boolean guest = false;
	private String username = null;
	private boolean terminated = false;

	public  Connection(Scanner in, PrintWriter out, MasterServer s){
		this.in = in;
		this.out = out;
		this.server = s;

	}

	public synchronized void terminate(){
		this.terminated = true;
	}

	public void run(){	
		try {
			while(!terminated){
				//if (in.hasNext()){
				String input = in.nextLine();

				// login
				if(input.equals("login")){
					String[] credentials = in.nextLine().split(",");
					boolean loginOK = AccountDAO.login(credentials[0], credentials[1]);
					if ( loginOK ) {
						System.out.println("user " + credentials[0] + " logged in successfully");
						out.println("login_ok");
						username = credentials[0];
					} else {
						System.out.println("false credentials with username " + credentials[0]);
						out.println("login_false");
					}
				} else 

					// get_gameservers
					if(input.equals("get_gameservers")){
						String output = "";
						if (username != null || guest) {
							output = "gameservers_begin\n";
							ArrayList<GameServer> serverlist = GameServerDAO.getGameServers();
							for (GameServer gs : serverlist) {
								output += gs.getIp() + "," + gs.getName() + "\n";
							}
							output += "gameservers_end";
						} else {
							output = "access_denied";
						}
						out.println(output);
					} else 

						// get_leadebord
						if (input.equals("get_leaderboard")) {
							String output = "";
							if (username != null || guest) {
								output = "leaderboard_begin\n";
								LBPlayer[] leaderbord = LeaderDAO.getTop20();
								for (LBPlayer p : leaderbord) {
									if (p != null)
										output += p.getRank() + "," + p.getUserName() + "," + p.getKills() + "," + p.getDeaths() + "\n";
								}
								output += "leaderboard_end";
							} else {
								output = "access_denied";
							}
							out.println(output);
						}  else 

							// get_guest_access
							if(input.equals("get_guest_access")){
								String output = "";
								output = "guest_access_granted\n";
								guest = true;
								int number = server.getNewGuestNumber();
								output += number;
								out.println(output);
								System.out.println("guest access granted with number " + number);
							} else 

								// sign_up
								if(input.equals("sign_up")){
									String output = "";
									String[] credentials = null;
									if (in.hasNext())
										credentials = in.nextLine().split(",");
									if (AccountDAO.addAccount(credentials[0], credentials[1])) {
										output = "sign_up_successful";
										System.out.println("user signed up with username " + credentials[0]);
									} else {
										output = "sign_up_failed";
									}
									out.println(output);
								} else 

									// update stats
									if(input.equals("update_stats")){
										if(username != null) {
											String[] stats = null;
											if (in.hasNext())
												stats = in.nextLine().split(",");
											LeaderDAO.updateLeaderBoard(username,Integer.parseInt(stats[0]), Integer.parseInt(stats[1]));
										}
									}
				//}
			}
		} catch (SQLException ex) {
			System.err.println("ERROR with Database: " + ex.getMessage());
		} catch (NoSuchElementException ex) {
			//do nothing
		}
	}

}
