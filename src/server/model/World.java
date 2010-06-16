package server.model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import server.model.bullet.Bullet;
import server.model.map.Map;
import server.model.tile.Tile;
import server.model.upgrade.Upgrade;
import server.tools.Circle;

public class World{

	private GameServer server;
	private Map map;
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private ArrayList<Player> playerWaitList = new ArrayList<Player>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Upgrade> upgrades = new ArrayList<Upgrade>();
	private int bulletCounter = 1;

	public World(GameServer s){
		map = new Map();
		this.server = s;
		//initialiseren van upgrades (actueel 14)
		for(WorldObject wo : getUpgrades()) {
			Tile tile = ((Tile) wo);
			if(tile.getUpgrade().equals("life")) {
				upgrades.add(new Upgrade(tile.getX(), tile.getY()));
			}
		}
	}

	public void addPlayerWaitList(Player p) {
		playerList.remove(p);
		playerWaitList.add(p);
	}

	public void RemovePlayerWaitList(Player p) {
		playerWaitList.remove(p);
		playerList.add(p);
	}

	public void addPlayer(Player p){
		playerList.add(p);
	}
	
	public Map getMap() {
		return map;
	}
	
	public int getBulletCounter(){
		return this.bulletCounter;
	}

	public Bullet shoot(float x,float y, Player p) {
		float distanceFromPlayerX = p.getX()-x;
		float distanceFromPlayerY = p.getY()-y;
		Bullet b = p.shoot(this.bulletCounter);
		if(b != null){
			b.setBullet(p.getX()+15,p.getY()+15,Math.atan2(distanceFromPlayerY, distanceFromPlayerX),p);
			bullets.add(b);
			bulletCounter++;
			return b;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public void move() {
		for(Player player : this.getPlayerList()){
			if(player != null){
				if(player.isMovingLeft() && canMoveLeft(player)){
					player.moveLeft(onGround(player));
				}else if(player.isMovingRight() && canMoveRight(player)){
					player.moveRight(onGround(player));
				}
				player.moveVertical();
				player.calcVerticalSpeed(onGround(player));
				if(collisionCeiling(player)){
					player.setVerticalSpeed(-0.5f);
				}
				upgrades = checkIfUpgradesAvailable(upgrades);
				upgrades = colideWithUpgrade(player, upgrades);
			}
		}
		ArrayList<Bullet> bulletsclone = (ArrayList<Bullet>) bullets.clone();
		for(Bullet b : bulletsclone){
			if(!checkBulletColission(b)){
				b.move();
			}else{
				ArrayList<Player> players = (ArrayList<Player>)getPlayerList().clone();
				for(Player p : players) {
					if(p.getHitpoints() < 1) {
						addPlayerWaitList(p);
						p.increaseDeaths();
						b.getOrigin().increaseKills();
						System.out.println("dooie: kills" + p.getKills() + " deaths " + p.getDeaths());
						System.out.println("owner: kills" + b.getOrigin().getKills() + " deaths " + b.getOrigin().getDeaths());
					}
				}
				bullets.remove(b);
				server.removeBullet(b);
			}
		}
	}
	
	private ArrayList<Upgrade> checkIfUpgradesAvailable(ArrayList<Upgrade> upgrades) {
		for(Upgrade u : upgrades) {
			if(u.isUsed()) {
				if((System.currentTimeMillis() / 1000) - u.getTimestamp() > 15) {
					u.setIsUsed(false);
				}
			}
		}
		return upgrades;
	}
	
	public ArrayList<Upgrade> colideWithUpgrade(Player p, ArrayList<Upgrade> upgrades) {
		Rectangle speler = new Rectangle(new Point((int)p.getX(),(int)p.getY()), new Dimension(20,20));
		for(Upgrade u : upgrades) {
			Rectangle heartje = new Rectangle(new Point(u.getX() * 16, u.getY() * 16), new Dimension(5,5));
			
			if (speler.intersects(heartje)) {
				if(!u.isUsed()) {
					p.setHitpoints(1);
					u.setIsUsed(true);
				}
			}
		}
		return upgrades;
	}
	
	public ArrayList<WorldObject> getRespawns() {
		ArrayList<WorldObject> respawns = new ArrayList<WorldObject>();
		for( int i = 0; i < map.getTiles().length; i++ ) {
			for( Tile tile : map.getTiles()[i] ) {
				if(tile.isRespawn()) {
					respawns.add(tile);
				}
			}
		}
		return respawns;
	}
	
	public ArrayList<WorldObject> getUpgrades() {
		ArrayList<WorldObject> upgradeList = new ArrayList<WorldObject>();
		for( int i = 0; i < map.getTiles().length; i++ ) {
			for( Tile tile : map.getTiles()[i] ) {
				if(tile.getUpgrade().equals("life")) {
					upgradeList.add(tile);
				}
			}
		}
		return upgradeList;
	}

	public boolean checkBulletColission(Bullet bullet){
		ArrayList<WorldObject> retList = new ArrayList<WorldObject>();

		// doorloop alle spelers
		for( Player player : playerList ) {
			if( player.getX()+32 > bullet.getX() && player.getX()-32 < bullet.getX() &&
					player.getY()+32 > bullet.getY() && player.getY()-32 < bullet.getY() ) {
				retList.add(player);
			}
		}

		// doorloop alle tiles
		for( int i = 0; i < map.getTiles().length; i++ ) {
			if( i*16+16 > bullet.getX() && i*16-16 < bullet.getX() ) {
				for( Tile tile : map.getTiles()[i] ) {
					if( tile.isSolid() && tile.getY()*16-32 < bullet.getY() && tile.getY()*16+32 > bullet.getY() && tile.getX()*16-32 < bullet.getX() && tile.getX()*16+32 > bullet.getX() ) {
						retList.add(tile);
					}
				}
			}
		}
		
		for(WorldObject t : retList){
			Rectangle2D.Double b = (Rectangle2D.Double) bullet.getShape();
			if(t.getShape() instanceof Circle){
				Circle tile = (Circle) t.getShape();
				if(tile.intersects(b)){
					if(bullet.getOrigin() != t){
						if(checkCloseBulletColission(bullet, tile)){
							if(t instanceof Player) {
								Player p = (Player) t;
								p.decreaseHitpoints(bullet.getDamage());
							}
							return true;	 
						}
					}	
				}
			}else{
				Rectangle2D.Double tile = (Rectangle2D.Double) t.getShape();
				if(t instanceof Tile){
					
					if(b.intersects(tile)){
						return true;
					}
				}else{
					if(b.intersects(tile)){
						if(bullet.getOrigin() != t){
							if(checkCloseBulletColission(bullet, tile)){
								if(t instanceof Player) {
									Player p = (Player) t;
									p.decreaseHitpoints(bullet.getDamage());
								}
								return true;	
							}
						}
					}
				}
			}
		}

		return false;
	}
	
public boolean checkCloseBulletColission(Bullet b, Shape tile){	
		
		float dir = b.getDirection();
		Rectangle2D shape = b.getShape().getBounds();

		if(tile instanceof Circle){
			Circle e1 = (Circle) tile;
			Circle c1,c2,c3;
			
			c1 = new Circle(4,(int)shape.getCenterX()-5,(int)shape.getCenterY()-5);
			
			int posX = (int) (5*Math.cos(dir));
			int posY = (int) (5*Math.sin(dir));
			c2 = new Circle(4,(int)shape.getCenterX()+posX-5, (int)shape.getCenterY()+posY-5);
			
			posX = (int) (5*Math.cos(dir+Math.PI));
			posY = (int) (5*Math.sin(dir+Math.PI));
			c3 = new Circle(4,(int)shape.getCenterX()+posX-5, (int)shape.getCenterY()+posY-5);
			
			if(c1.intersects(e1)){
				return true;
			}else if(c2.intersects(e1)){
				return true;
			}else if(c3.intersects(e1)){
				return true;
			}
		}else{
			Rectangle2D e1 = (Rectangle2D) tile;
			Circle c1,c2,c3;
			
			c1 = new Circle(4,(int)shape.getCenterX()-5,(int)shape.getCenterY()-5);
			
			int posX = (int) (5*Math.cos(dir));
			int posY = (int) (5*Math.sin(dir));
			c2 = new Circle(4,(int)shape.getCenterX()+posX-5, (int)shape.getCenterY()+posY-5);
			
			posX = (int) (5*Math.cos(dir+Math.PI));
			posY = (int) (5*Math.sin(dir+Math.PI));
			c3 = new Circle(4,(int)shape.getCenterX()+posX-5, (int)shape.getCenterY()+posY-5);
			
			if(c1.intersects(e1)){
				return true;
			}else if(c2.intersects(e1)){
				return true;
			}else if(c3.intersects(e1)){
				return true;
			}
		}
		return false;
	}
	
	public boolean onGround(Player player) {
		Tile[][] tiles = map.getTiles();
		Tile botLeft = tiles[(int) ((player.getX())/16)][(int) ((player.getY()+35)/16)];
		Tile mid = tiles[(int) ((player.getX()+16)/16)][(int) ((player.getY()+35)/16)];
		Tile botRight = tiles[(int) ((player.getX()+32)/16)][(int) ((player.getY()+35)/16)];
		if(botLeft.isSolid() || mid.isSolid() || botRight.isSolid()){
			return true;
		}
		return false;
	}
	
	private boolean canMoveLeft(Player player) {			
		Tile[][] tiles = map.getTiles();
		Tile topLeft = tiles[(int) ((player.getX()-3)/16)][(int) ((player.getY())/16)];
		Tile mid = tiles[(int) ((player.getX()-3)/16)][(int) ((player.getY()+16)/16)];
		Tile botLeft = tiles[(int) ((player.getX()-3)/16)][(int) ((player.getY()+32)/16)];
		if(topLeft.isSolid() || mid.isSolid() || botLeft.isSolid()){
			return false;
		}
		return true;
	}
	
	private boolean canMoveRight(Player player) {
		Tile[][] tiles = map.getTiles();
		Tile topRight = tiles[(int) ((player.getX()+35)/16)][(int) ((player.getY())/16)];
		Tile mid = tiles[(int) ((player.getX()+35)/16)][(int) ((player.getY()+16)/16)];
		Tile botRight = tiles[(int) ((player.getX()+35)/16)][(int) ((player.getY()+32)/16)];
		if(topRight.isSolid() || mid.isSolid() || botRight.isSolid()){
			return false;
		}
		return true;
	}

	private boolean collisionCeiling(Player player) {
		Tile[][] tiles = map.getTiles();
		Tile topLeft = tiles[(int) ((player.getX())/16)][(int) ((player.getY()-3)/16)];
		Tile mid = tiles[(int) ((player.getX()+16)/16)][(int) ((player.getY()-3)/16)];
		Tile topRight = tiles[(int) ((player.getX()+32)/16)][(int) ((player.getY()-3)/16)];
		if(topLeft.isSolid() || mid.isSolid() || topRight.isSolid()){
			return true;
		}
		return false;
	}
	
	public void startMovingRight(Player p) {
		p.setMovingRight(true);
	}

	public void stopMovingRight(Player p) {
		p.setMovingRight(false);		
	}

	public void startMovingLeft(Player p) {
		p.setMovingLeft(true);		
	}

	public void stopMovingLeft(Player p) {
		p.setMovingLeft(false);		
	}

	public void jump(Player p) {
		if(onGround(p)){
			p.setVerticalSpeed(4.5f);
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Bullet> getBullets() {
		return (ArrayList<Bullet>) this.bullets.clone();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Player> getPlayerList() {
		return (ArrayList<Player>) this.playerList.clone();
	}
	
	public void removePlayer(Player p) {
		playerList.remove(p);
	}

	public ArrayList<Player> getPlayerWaitList() {
		return playerWaitList;
	}

	public ArrayList<Upgrade> getUpgradeList() {
		return upgrades;
	}
}
