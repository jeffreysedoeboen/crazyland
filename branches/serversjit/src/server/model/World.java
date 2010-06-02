package server.model;

import java.util.ArrayList;

import server.model.bullet.Bullet;
import server.model.tile.Tile;


public class World{

	private Map map;
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	public World(){
		map = new Map();
	}
	
	public void addPlayer(Player p){
		playerList.add(p);
	}

	public Map getMap() {
		return map;
	}

	public void shoot(float x,float y, Player p) {
		float distanceFromPlayerX = p.getX()-x;
		float distanceFromPlayerY = p.getY()-x;
		Bullet b = p.shoot();
		if(b != null){
			b.setBullet(p.getX(),p.getY(),Math.atan2(distanceFromPlayerY, distanceFromPlayerX));
			bullets.add(b);
		}
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
				ArrayList<Bullet> bulletsclone = (ArrayList<Bullet>) bullets.clone();
				for(Bullet b : bulletsclone){
					b.move();
				}
			}
		}

	}

	private boolean canMoveLeft(Player player) {
		Tile[][] tiles = map.getTiles();
		if(tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY())/32)].isSolid() || tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].isSolid()){
			return false;
		}
		return true;
	}

	private boolean canMoveRight(Player player) {
		Tile[][] tiles = map.getTiles();
		if(tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY())/32)].isSolid() || tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].isSolid()){
			return false;
		}
		return true;
	}

	private boolean collisionCeiling(Player player) {
		Tile[][] tiles = map.getTiles();
		if(tiles[(int) (player.getX()/32)][(int) ((player.getY())/32)].isSolid() || tiles[(int) ((player.getX()+32)/32)][(int) ((player.getY())/32)].isSolid()){
			return true;
		}
		return false;
	}

	public boolean onGround(Player player){
		Tile[][] tiles = map.getTiles();
		if(tiles[(int) (player.getX()/32)][(int) ((player.getY()+32)/32)].isSolid() || tiles[(int) ((player.getX()+32)/32)][(int) ((player.getY()+32)/32)].isSolid()){
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
}
