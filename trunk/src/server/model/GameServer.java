package server.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import server.model.bullet.Bullet;


public class GameServer extends Thread{
	
	private World world;
	private ArrayList<Sender> senderList = new ArrayList<Sender>();
	private int count = 0;
	
	public static void main(String args[]){
			
		GameServer server = new GameServer();
		
		new Connector(server).start();

	}
	
	public void addPlayer(Player p){
		this.world.addPlayer(p);
	}
	
	public GameServer(){
		this.world = new World();
		new Timer(10,taskPerformer).start();
	}
	
	public World getWorld(){
		return this.world;
	}
	
//	public void moveWeapon(int mouseX, int mouseY) {
//		world.moveWeapon(mouseX, mouseY);
//		
//	}
	
	ActionListener taskPerformer = new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			run();
		}
		
	  };
	
	public void run(){
		count++;
		this.world.move();
		ArrayList<Sender> senderList = (ArrayList<Sender>) this.senderList.clone();
		if(count % 2 == 0){
			for(Sender s : senderList){
				s.sendPlayer();
				s.sendPlayers(world.getPlayerList());
				s.sendLineOut();
			}
		}

		if(count == 20){
			count = 0;
			System.out.println("Players in-game: " + senderList.size());
		}
		
	}
	
	public void removeBullet(Bullet b){
		for(Sender s : (ArrayList<Sender>) senderList.clone()){
			s.removeBullet(b);
		}
	}
	
	public void shoot(float x,float y, Player p){
		Bullet b = world.shoot(x, y, p);
		if(b != null){
		System.out.println("test");
			for(Sender s : (ArrayList<Sender>) senderList.clone()){
				s.sendBullet(b);
			}
		}
	}

	public void addSender(Sender s) {
		this.senderList.add(s);		
	}
	
//	public void changeWeapon() {
//		world.changeWeapon();
//	}
		
}
