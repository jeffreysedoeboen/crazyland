package server.model;

import java.io.PrintWriter;
import java.util.ArrayList;

import server.model.bullet.Bullet;

public class Sender {

	private PrintWriter out;
	private Player player;
	private String lineOut = "";
		
	public Sender(PrintWriter out, Player p){
		this.out = out;
		this.player = p;
		out.println("begin_map\n" + "crazyland2");
	}
	
	public void sendPlayer(){
		lineOut += "player%n" + (int)player.getX() + "," + (int)player.getY() + "%n";
	}
	
	public void sendPlayers(ArrayList<Player> playerList){
		String kaas = "players_begin%n";
		for(Player p : playerList){
			if(p != this.player){
				kaas += p.getName()+ "," + (int)p.getX() + "," + (int)p.getY() + "%n";
			}
		}
		lineOut += kaas + "players_end%n";
	}
	
	public void sendBullet(Bullet b){
		String kaas = "bullets_begin%n";
		kaas += (int)b.getX() + "," + (int)b.getY() + "," + (int)b.getIndentifier() + "," + b.getDirection() + "%n";
		lineOut += kaas;
	}
	
	public void sendLineOut(){
		out.printf(lineOut);
		lineOut = "";
	}
	
	public void removeBullet(Bullet b){
		String kaas = "bullets_begin_destroy%n";
		kaas += (int)b.getIndentifier();
		lineOut += kaas + "%n";
	}
	
}
