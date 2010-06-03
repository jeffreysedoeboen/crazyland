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
				this.player = new Player(Integer.parseInt("" + playerXY[0]),Integer.parseInt("" + playerXY[1]));
			}else if(tempstr.equals("players_begin")){
				boolean playerEnd = false;
				ArrayList<Player> tempList = new ArrayList<Player>();
				while(!playerEnd && !terminated && in.hasNext()){
					String rp = in.nextLine();
					if(rp.equals("players_end")){
						playerEnd = true;
					}else{
						String[] playerXY = rp.split(",");
						tempList.add(new Player(Integer.parseInt("" + playerXY[1]),Integer.parseInt("" + playerXY[2])));
					}
				}
				this.remotePlayers = tempList;
			}else if(tempstr.equals("bullets_begin")){
				String[] bulletXY = in.nextLine().split(",");
				bulletList.add(new Bullet(Integer.parseInt("" + bulletXY[0]),Integer.parseInt("" + bulletXY[1]),Integer.parseInt("" + bulletXY[2]),Float.parseFloat(bulletXY[3])));
			}
		}
	}

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
