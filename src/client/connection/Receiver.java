package client.connection;

import java.util.ArrayList;
import java.util.Scanner;

import client.model.Mapfactory;

import client.model.Map;
import client.model.Bullet;
import client.model.Player;
import client.model.Tile;

public class Receiver extends Thread {

	private Scanner in;
	private boolean terminated = false;
	private Map map = null;
	private Player player = null;
	private ArrayList<Player> remotePlayers = new ArrayList<Player>();
	private ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	
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
			if(tempstr.equals("begin_map")){
				Tile[][] tiles = Mapfactory.getMap(in.nextLine());
				map = new Map();
				map.setTiles(tiles);
			}else if(tempstr.equals("player")){
				tempstr = in.nextLine();
				String[] playerXY = tempstr.split(",");
				this.player = new Player( playerXY[0],Integer.parseInt("" + playerXY[1]),Integer.parseInt("" + playerXY[2]),Integer.parseInt("" + playerXY[3]), Float.parseFloat(playerXY[4]));
			}else if(tempstr.equals("players_begin")){
				boolean playerEnd = false;
				ArrayList<Player> tempList = new ArrayList<Player>();
				while(!playerEnd && !terminated && in.hasNext()){
					String rp = in.nextLine();
					if(rp.equals("players_end")){
						playerEnd = true;
					}else{
						String[] playerXY = rp.split(",");
						tempList.add(new Player(playerXY[0], Integer.parseInt("" + playerXY[1]),Integer.parseInt("" + playerXY[2]),Integer.parseInt("" + playerXY[3]), Float.parseFloat(playerXY[4])));
					}
				}
				this.remotePlayers = tempList;
			}else if(tempstr.equals("bullets_begin")){
				String[] bulletXY = in.nextLine().split(",");
				bulletList.add(new Bullet(Integer.parseInt("" + bulletXY[0]),Integer.parseInt("" + bulletXY[1]),Integer.parseInt("" + bulletXY[2]),Float.parseFloat(bulletXY[3])));
			}else if(tempstr.equals("bullets_begin_destroy")){
				Bullet b = null;
				int tmp = Integer.parseInt(in.nextLine());
				for(Bullet ba : bulletList){
					if(ba.getIndentifier() == tmp){
						b = ba;
						break;
					}
				}
				bulletList.remove(b);
			} else if(tempstr.equals("player_begin_destroy")){
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
			} else if(tempstr.equals("player_turn_weapon")) {
				float angle = Float.parseFloat(in.nextLine());
				player.turnToPoint(angle);
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
	
	@SuppressWarnings("unchecked")
	public ArrayList<Player> getRemotePlayers(){
		return (ArrayList<Player>) this.remotePlayers.clone();
	}
	
}
