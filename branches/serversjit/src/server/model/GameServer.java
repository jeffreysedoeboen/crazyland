package server.model;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import server.model.bullet.Bullet;


public class GameServer extends Thread{
	
	private World world;
	private ArrayList<Sender> senderList = new ArrayList<Sender>();
	
	public static void main(String args[]){
			
		GameServer server = new GameServer();
		
		new Connector(server).start();
		
		server.start();
		System.out.println("LOL");
		
	}
	
	public void addPlayer(Player p){
		this.world.addPlayer(p);
	}
	
	public GameServer(){
		this.world = new World();
	}
	
	public World getWorld(){
		return this.world;
	}
	
	public void run(){
		while(true){
			
			this.world.move();
			ArrayList<Sender> senderList = (ArrayList<Sender>) this.senderList.clone();
			for(Sender s : senderList){
				s.sendPlayer();
				s.sendPlayers(world.getPlayerList());
			}
			
			Thread.currentThread();
			try{
				Thread.sleep(10);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
	}

	public void addSender(Sender s) {
		this.senderList.add(s);		
	}
		
}
