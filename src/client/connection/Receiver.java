package client.connection;

import java.util.ArrayList;
import java.util.Scanner;

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
	private ArrayList<Player> remotePlayers = new ArrayList<Player>();
	private ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	private ArrayList<Upgrade> upgradeList = new ArrayList<Upgrade>();
	
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
			if(tempstr.equals("m")){ // map
				Tile[][] tiles = Mapfactory.getMap(in.nextLine());
				map = new Map();
				map.setTiles(tiles);
			}else if(tempstr.equals("p")){ // player
				tempstr = in.nextLine();
				String[] playerXY = tempstr.split(",");
				if (this.player == null) {
					this.player = new Player( playerXY[0], Integer.parseInt("" + playerXY[1]), Integer.parseInt("" + playerXY[2]), Integer.parseInt("" + playerXY[3]), Float.parseFloat(playerXY[4]));
				} else {
					this.player.setName(playerXY[0]);
					this.player.setX(Integer.parseInt("" + playerXY[1]));
					this.player.setY(Integer.parseInt("" + playerXY[2]));
					this.player.setHitpoints(Integer.parseInt("" + playerXY[3]));
					this.player.turnToPoint(Float.parseFloat(playerXY[4]));
				}
			}else if(tempstr.equals("pb")){ // player begin (list)
				boolean playerEnd = false;
				ArrayList<Player> tempList = new ArrayList<Player>();
				while(!playerEnd && !terminated && in.hasNext()){
					String rp = in.nextLine();
					if(rp.equals("pe")){ // player end
						playerEnd = true;
					}else{
						String[] playerXY = rp.split(",");
						//TODO: het aanmaken van de speler moet verbeterd worden -> niet iedere keer een nieuwe speler aanmaken
						tempList.add(new Player(playerXY[0], Integer.parseInt("" + playerXY[1]),Integer.parseInt("" + playerXY[2]),Integer.parseInt("" + playerXY[3]), Float.parseFloat(playerXY[4])));
					}
				}
				this.remotePlayers = tempList;
			}else if(tempstr.equals("b")){ // bullet
				String[] bulletXY = in.nextLine().split(",");
				bulletList.add(new Bullet(Integer.parseInt("" + bulletXY[0]),Integer.parseInt("" + bulletXY[1]),Integer.parseInt("" + bulletXY[2]),Float.parseFloat(bulletXY[3])));
			}else if(tempstr.equals("ub")) { // upgrades_begin
				boolean upgradeEnd = false;
				ArrayList<Upgrade> tempList = new ArrayList<Upgrade>();
				
				while(!upgradeEnd && !terminated && in.hasNext()){
					String rp = in.nextLine();
					if(rp.equals("ue")){ // upgrades_end
						upgradeEnd = true;
					}else{
						String[] upgradeXY = rp.split(",");
						tempList.add(new ExtraLife(Float.parseFloat("" + upgradeXY[0]),Float.parseFloat("" + upgradeXY[1])));
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
				player.turnToPoint(angle);
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
	
}
