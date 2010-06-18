package server.connection;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;

import server.model.Player;
import server.model.bullet.Bullet;
import server.model.upgrade.Upgrade;

public class Sender {

	private PrintWriter out;
	private Player player;
	private String lineOut = "";
	private String turnWeapon = "";
	private int playerCounter = 0;
		
	public Sender(PrintWriter out, Player p, long remainingTime){
		this.out = out;
		this.player = p;
		// stuur player met naam
		lineOut += String.format((Locale)null, "p%n%s,%d,%d,%d,%.2f,%d,%d,%d%n", player.getName(), (int)player.getX(), (int)player.getY(), player.getHitpoints(), player.getAngle(), player.getKills(), player.getDeaths(), player.getIndentifier());
		out.println("m\n" + "crazyland5\nt\n" + remainingTime);
	}
	
	public void sendTime(long remainingTime){
		lineOut += "t\n" + remainingTime + "\n";
	}
	
	public void sendPlayer(){
		lineOut += String.format((Locale)null, "p%n%d,%d,%d,%.2f,%d,%d,%d%n", (int)player.getX(), (int)player.getY(), player.getHitpoints(), player.getAngle(), player.getKills(), player.getDeaths(), player.getIndentifier());
	}
	
	public void sendPlayers(ArrayList<Player> playerList){
		String kaas = String.format("pb%n");
		for(Player player : playerList){
			if(!player.equals(this.player)) {
				if(player.getIndentifier() > playerCounter) {
					playerCounter++;
					kaas += String.format((Locale)null, "%s,%d,%d,%d,%.2f,%d,%d%n", player.getName(), (int)player.getX(), (int)player.getY(), player.getHitpoints(), player.getAngle(), player.getKills(), player.getDeaths(), player.getIndentifier());
				} else {
					kaas += String.format((Locale)null, "%d,%d,%d,%.2f,%d,%d%n", (int)player.getX(), (int)player.getY(), player.getHitpoints(), player.getAngle(), player.getKills(), player.getDeaths(), player.getIndentifier());
				}
			}
		}
		lineOut += kaas + String.format("pe%n");
	}
	
	public void sendBullet(Bullet b){
		String kaas = "b%n";
		kaas += (int)b.getX() + "," + (int)b.getY() + "," + (int)b.getIndentifier() + "," + String.format((Locale)null, "%.5f", b.getDirection()) + "%n";
		lineOut += kaas;
	}
	
	public void sendUpgrades(ArrayList<Upgrade> upgradeList) {
		String kaas = "ub%n"; // upgrades_begin
		for(Upgrade u : upgradeList) {
			if(!u.isUsed()) {
				kaas += u.getX() + "," + u.getY() + "%n";
			}
		}
		lineOut += kaas + "ue%n"; // upgrades_end
	}
	
	public void sendLineOut(){
		out.printf(lineOut + getWeaponAngle());
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
