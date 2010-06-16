package server.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import server.connection.Connector;
import server.connection.Receiver;
import server.connection.Sender;
import server.model.bullet.Bullet;
import server.model.tile.Tile;

public class GameServer extends Thread{

	private final int GAME_TIME = 600;
	private World world;
	private ArrayList<Sender> senderList = new ArrayList<Sender>();
	private ArrayList<Receiver> receiverList = new ArrayList<Receiver>();
	private int count = 0;
	private long seconds = GAME_TIME;
	private Timer timer;

	public static void main(String args[]){

		GameServer server = new GameServer();
		new Connector(server).start();
	}
	
	private ActionListener gameTimerPerformer = new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			lowerTimeWaitingPlayers();
			if (--seconds == -5) {
				restartGame();
			}
		}
	};

	private void restartGame() {
		//Players worden gereset
		int i = 0;
		world.getPlayerList().addAll(world.getPlayerWaitList());
		world.getPlayerWaitList().clear();
		for(Player player : world.getPlayerList()) {
			player.resetKills();
			player.resetHitpoints();
			player.resetKills();
			player.resetTimeToWait();

			Tile respawn = (Tile)world.getRespawns().get(i++);
			player.setPosition(respawn.getX() * 16, respawn.getY() * 16);
		}

		//Bullets weghalen
		world.getBullets().clear();

		seconds = GAME_TIME;
	}

	public void addPlayer(Player p){
		this.world.addPlayer(p);
	}

	public GameServer(){
		this.world = new World(this);
		timer = new Timer(10, taskPerformer);
		timer.start();
		Timer gameTimer = new Timer(1000, gameTimerPerformer);
		gameTimer.start();

	}

	@SuppressWarnings("unchecked")
	private void lowerTimeWaitingPlayers() {
		for(Player p : (ArrayList<Player>)world.getPlayerWaitList().clone()) {
			p.decreaseTimeToWait();
		}
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
				s.sendUpgrades(world.getUpgradeList());
				s.sendLineOut();
			}
		}

		ArrayList<Player> waitingPlayers = (ArrayList<Player>)world.getPlayerWaitList().clone();
		ArrayList<WorldObject> respawns = world.getRespawns();
		for(Player p : waitingPlayers) {
			if(p.getTimeToWait() < 1) {
				Tile respawn = (Tile) respawns.get((int) ((respawns.size() * Math.random())));
				System.out.println("Player word gersespawn op: " + respawn.getX() + ", " + respawn.getY());
				p.setPosition(respawn.getX() * 16, respawn.getY() * 16);
				p.resetHitpoints();
				p.resetTimeToWait();
				world.RemovePlayerWaitList(p);

				if(count == 100) {
					count = 0;
					if (--seconds == 0) {
						timer.stop();
					}
				}
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
	
	public long getRemainingTimeInSeconds() {
		return seconds;
	}
}
