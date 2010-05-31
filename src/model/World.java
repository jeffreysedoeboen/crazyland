package model;

import java.awt.Image;
import java.awt.Point;
import model.bullet.Bullet;
import model.tile.Tile;

public class World{

	Map map;
	Player player;
	Bullet bullet;
	
	public World(){
		map = new Map();
		player = new Player("Henk", 200f,100f);
	}
	
	public Map getMap() {
		return map;
	}
	
	public float getPlayerX(){
		return player.getX();
	}

	public float getPlayerY(){
		return player.getY();
	}
	
	public Image getPlayerImage() {
		
		return player.getImage();
		
	}
	
	public void shoot() {
		//TODO moet nog een lijst worden
		bullet = player.shoot();
	}

	public Image getBulletImage() {
		return bullet.getBulletImage();
	}
	
	public Player getPlayer() {
		return player;
	}

	public void moveWeapon(Point mousePoint) {
		player.getWeapon().turnToPoint(mousePoint);
	}

	public void move() {
		if(player.isMovingLeft()){
			player.moveLeft(onGround());
		}else if(player.isMovingRight()){
			player.moveRight(onGround());
		}
		player.moveVertical();
		player.calcVerticalSpeed(onGround());
		if(collisionCeiling()){
			player.setVerticalSpeed(-0.1f);
		}
		System.out.println("Onground: " + onGround());
		System.out.println("OnCeiling: " + collisionCeiling());
		
	}
	
	private boolean collisionCeiling() {
		Tile[][] tiles = map.getTiles();
		if(tiles[(int) (player.getX()/32)][(int) ((player.getY())/32)].isSolid() || tiles[(int) ((player.getX()+32)/32)][(int) ((player.getY())/32)].isSolid()){
			return true;
		}
		return false;
	}

	public boolean onGround(){
		Tile[][] tiles = map.getTiles();
		if(tiles[(int) (player.getX()/32)][(int) ((player.getY()+32)/32)].isSolid() || tiles[(int) ((player.getX()+32)/32)][(int) ((player.getY()+32)/32)].isSolid()){
			return true;
		}
		return false;
	}

	public void startMovingRight() {
		player.setMovingRight(true);
	}
	
	public void stopMovingRight() {
		player.setMovingRight(false);		
	}
	
	public void startMovingLeft() {
		player.setMovingLeft(true);		
	}
	
	public void stopMovingLeft() {
		player.setMovingLeft(false);		
	}

	public void jump() {
		if(onGround()){
			player.setVerticalSpeed(4.5f);
		}
	}
}
