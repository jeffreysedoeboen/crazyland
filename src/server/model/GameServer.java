package server.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import server.model.bullet.Bullet;


public class GameServer extends Thread{
	
	private World world;
	private ArrayList<Sender> senderList = new ArrayList<Sender>();
	private ArrayList<Receiver> receiverList = new ArrayList<Receiver>(); 
	private int count = 0;
	private long seconds = 600;
	private Timer timer;
	
	public static void main(String args[]){
			
		GameServer server = new GameServer();
		
		new Connector(server).start();

	}
	
	public void addPlayer(Player p){
		this.world.addPlayer(p);
	}
	
	public GameServer(){
		this.world = new World(this);
		timer = new Timer(10,taskPerformer);
		timer.start();
	}
	
	public World getWorld(){
		return this.world;
	}
	
	ActionListener taskPerformer = new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			run();
		}
		
	  };
	
	@SuppressWarnings("unchecked")
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

		if(count % 20 == 0){
			System.out.println("Players in-game: " + senderList.size());
		}
		if(count == 100) {
			count = 0;
			if (--seconds == 0) {
				timer.stop();
				//TODO Timer	
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void removeBullet(Bullet b){
		for(Sender s : (ArrayList<Sender>) senderList.clone()){
			s.removeBullet(b);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void removePlayer(Player p) {
		for(Sender s : (ArrayList<Sender>) senderList.clone()){
			if (s.isPlayer(p)) {
				senderList.remove(s);
				world.removePlayer(p);
				s.removePlayer(p);
			}
		}
		for(Receiver r : (ArrayList<Receiver>) receiverList.clone()){
			if (r.isPlayer(p)) {
				receiverList.remove(r);
				r.terminate();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
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

	public void addReceiver(Receiver r) {
		this.receiverList.add(r);
		
	}

	public void turnWeapon(int mouseX, int mouseY, Player player) {
		float x = player.getX(), y = player.getY();
		for(Sender s : senderList) {
			if(s.isPlayer(player)) {
				float angle = (float) (Math.toDegrees((Math.atan2(Math.toRadians(mouseY - y), Math.toRadians(mouseX - x)))));
				player.setAngle(angle);
				s.sendWeaponAngle(angle);
			}
		}
		
	}
		
}
