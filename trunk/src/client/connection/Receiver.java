package client.connection;

import java.util.ArrayList;
import java.util.Scanner;

import client.animations.Animation;
import client.animations.Explosion;
import client.animations.GunFire;
import client.model.Mapfactory;

import client.model.Map;
import client.model.Bullet;
import client.model.Player;
import client.model.Tile;
import client.model.upgrades.ExtraLife;
import client.model.upgrades.Upgrade;

public class Receiver extends Thread {

	private Scanner in;
	private boolean terminated = false;
	private Map map = null;
	private Player player = null;
	private int remainingTime = 0;
	private ArrayList<Player> remotePlayers = new ArrayList<Player>();
	private ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	private ArrayList<Upgrade> upgradeList = new ArrayList<Upgrade>();
	private ArrayList<Animation> animationList = new ArrayList<Animation>();
	
	public  Receiver(Scanner in){
		this.in = in;
	}
	
	public synchronized void terminate(){
		this.terminated = true;
	}
	
	public synchronized Map getMap(){
		return this.map;
	}
	
	public void run(){	
		while(!terminated && in.hasNext()){
			String tempstr = in.nextLine();
			// TODO: chars in plaats van strings en dus switch in plaats van if else construction
			if(tempstr.equals("m")){ // map
				Tile[][] tiles = Mapfactory.getMap(in.nextLine());
				map = new Map();
				map.setTiles(tiles);
			} else if (tempstr.equals("t")) { // time
				tempstr = in.nextLine();
				remainingTime = Integer.parseInt(tempstr);
			} else if(tempstr.equals("p")){ // player
				tempstr = in.nextLine();
				String[] playerXY = tempstr.split(",");
				if (this.player == null) {
					this.player = new Player( playerXY[0], Integer.parseInt(playerXY[1]), Integer.parseInt(playerXY[2]), Integer.parseInt(playerXY[3]), Float.parseFloat(playerXY[4]), Integer.parseInt(playerXY[5]), Integer.parseInt(playerXY[6]));
				} else {
					this.player.setName(playerXY[0]);
					this.player.setX(Integer.parseInt(playerXY[1]));
					this.player.setY(Integer.parseInt(playerXY[2]));
					this.player.setHitpoints(Integer.parseInt(playerXY[3]));
					this.player.setAngle(Float.parseFloat(playerXY[4]));
					this.player.turnToPoint(Float.parseFloat(playerXY[4]));
					this.player.setKills(Integer.parseInt(playerXY[5]));
					this.player.setDeaths(Integer.parseInt(playerXY[6]));
					
				}
			}else if(tempstr.equals("pb")){ // player begin (list)
				boolean playerEnd = false;
				ArrayList<Player> tempList = new ArrayList<Player>();
				while(!playerEnd && !terminated && in.hasNext()){
					String rp = in.nextLine();
					if(rp.equals("pe")){ // player end
						playerEnd = true;
					}else{
						System.out.println(rp);
						String[] playerXY = rp.split(",");
						//TODO: het aanmaken van de speler moet verbeterd worden -> niet iedere keer een nieuwe speler aanmaken
						tempList.add(new Player(playerXY[0], Integer.parseInt(playerXY[1]),Integer.parseInt(playerXY[2]),Integer.parseInt(playerXY[3]), Float.parseFloat(playerXY[4]), Integer.parseInt(playerXY[5]), Integer.parseInt(playerXY[6])));
					}
				}
				this.remotePlayers = tempList;
			}else if(tempstr.equals("b")){ // bullet
				String[] bulletXY = in.nextLine().split(",");
				Bullet b = new Bullet(Integer.parseInt("" + bulletXY[0]),Integer.parseInt("" + bulletXY[1] +15),Integer.parseInt("" + bulletXY[2]),Float.parseFloat(bulletXY[3]));
				animationList.add(new GunFire((int)b.getX(),(int) b.getY()));
				bulletList.add(b);
			}else if(tempstr.equals("ub")) { // upgrades_begin
				boolean upgradeEnd = false;
				ArrayList<Upgrade> tempList = new ArrayList<Upgrade>();
				while(!upgradeEnd && !terminated && in.hasNext()){
					String rp = in.nextLine();
					if(rp.equals("ue")){ // upgrades_end
						upgradeEnd = true;
					}else{
						String[] upgradeXY = rp.split(",");
						tempList.add(new ExtraLife(Integer.parseInt("" + upgradeXY[0]),Integer.parseInt("" + upgradeXY[1])));
					}
				}
				this.upgradeList = tempList;
			}else if(tempstr.equals("bd")){ // destroy bullet
				Bullet b = null;
				int tmp = Integer.parseInt(in.nextLine());
				for(Bullet ba : bulletList){
					if(ba.getIndentifier() == tmp){
						b = ba;
						break;
					}
				}
				if(b != null) 
					animationList.add(new Explosion((int)b.getX(),(int) b.getY()));
				bulletList.remove(b);
			} else if(tempstr.equals("pd")){ // destroy player
				Player p = null;
				String tmp = in.nextLine();
				for(Player pl : remotePlayers){
					System.out.println(tmp);
					if(pl.getName().equals(tmp)){
						p = pl;
						break;
					}
				}
				remotePlayers.remove(p);
			} else if(tempstr.equals("tw")) { //turn weapon
				float angle = Float.parseFloat(in.nextLine());
				if(player != null) {
					player.turnToPoint(angle);
				}
			} else if(tempstr.equals("t")) {
				// TODO: Timestamp verwijdern
//				System.out.println(System.currentTimeMillis() - Long.parseLong(in.nextLine()));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized ArrayList<Bullet> getBullets() {
		return (ArrayList<Bullet>) this.bulletList.clone();
	}

	public synchronized Player getPlayer() {
		return this.player;
	}
	
	public ArrayList<Upgrade> getUpgrades() {
		return this.upgradeList;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Player> getRemotePlayers(){
		return (ArrayList<Player>) this.remotePlayers.clone();
	}
	
	public int getTimeRemaining() {
		return remainingTime;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Player> getPlayerStats() {
		ArrayList<Player> allPlayer = (ArrayList<Player>) remotePlayers.clone();
		allPlayer.add(player);
		return allPlayer;
	}
	
	public ArrayList<Animation> getAnimtions() {
		return animationList;
	}
	
	public void removeAnimation(Animation a) {
		animationList.remove(a);
	}
}
