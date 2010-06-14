package server.model;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;

import server.model.bullet.Bullet;
import server.model.upgrade.Upgrade;

public class Sender {

	private PrintWriter out;
	private Player player;
	private String lineOut = "";
	private String turnWeapon = "";
		
	public Sender(PrintWriter out, Player p){
		this.out = out;
		this.player = p;
		out.println("m\n" + "crazyland2\n" + p.getName() + "\n" + (int)p.getX() + "," + (int)p.getY() + "\n" + p.getHitpoints());
	}
	
	public void sendPlayer(){
		lineOut += "p%n" + player.getName() + "," + (int)player.getX() + "," + (int)player.getY() + "," + player.getHitpoints() + "," + String.format((Locale)null, "%.2f", player.getAngle()) + "%n";
	}
	
	public void sendPlayers(ArrayList<Player> playerList){
		String kaas = "pb%n";
		for(Player p : playerList){
			if(p != this.player){
				kaas += p.getName()+ "," + (int)p.getX() + "," + (int)p.getY() + "," + player.getHitpoints() + ","  + String.format((Locale)null, "%.2f", p.getAngle()) + "%n";
			}
		}
		lineOut += kaas + "pe%n";
	}
	
	public void sendBullet(Bullet b){
		String kaas = "b%n";
		kaas += (int)b.getX() + "," + (int)b.getY() + "," + (int)b.getIndentifier() + "," + String.format((Locale)null, "%.2f", b.getDirection()) + "%n";
		lineOut += kaas;
	}
	
	public void sendUpgrades(ArrayList<Upgrade> upgradeList) {
//		String kaas = "ub%n"; // upgrades_begin
//		for(Upgrade u : upgradeList) {
//			kaas += u.getX() + "," + u.getY() + "%n";
//		}
//		lineOut += kaas + "ue%n"; // upgrades_end
	}
	
	public void sendLineOut(){
		out.printf(lineOut + getWeaponAngle() /*+ "t%n" + System.currentTimeMillis() + "%n" */); //TODO: Timestamp verwijdern
		//System.out.println(lineOut.length());
		//System.out.println(getWeaponAngle() + lineOut);
		lineOut = "";
		turnWeapon = "";
	}
	
	public void removeBullet(Bullet b){
		String kaas = "bd%n";
		kaas += b.getIndentifier();
		lineOut += kaas + "%n";
	}
	
	public void removePlayer(Player p) {
		String kaas = "pd%n";
		kaas += p.getName();
		lineOut += kaas + "%n";
	}
	
	public boolean isPlayer(Player p) {
		return p.equals(player);
	}

	public void sendWeaponAngle(float angle) {
		turnWeapon = String.format((Locale)null, "%.2f", angle);
	}
	
	public String getWeaponAngle() {
		if (turnWeapon.length() > 0) {
			return "tw%n" + turnWeapon + "%n";
		}
		return "";
	}
}
