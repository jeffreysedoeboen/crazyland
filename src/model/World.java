package model;

import java.awt.Image;
import java.awt.Point;
import model.bullet.Bullet;

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
			player.moveLeft();
		}else if(player.isMovingRight()){
			player.moveRight();
		}
		if(!onGround()){
			//player.fall();
		}
		
	}
	
	public boolean onGround(){
		return true;
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
}
