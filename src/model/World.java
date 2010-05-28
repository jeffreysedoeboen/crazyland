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
		player = new Player("Henk", new Point(200,100));
	}
	
	public Map getMap() {
		return map;
	}
	
	public Point getPlayerPosition(){
		return player.getPosition();
	}

	public void playerJump() {
		// TODO jump
		
		
	}

	public Image getPlayerImage() {
		
		return player.getImage();
		
	}

	public void walkLeft() {
		if(checkDirection(player.getPosition(), 'l')) {
			player.setPosition(new Point(player.getPosition().x - 2, player.getPosition().y));
		}
		
	}

	private boolean checkDirection(Point position, char direction) {
		//TODO look if player can walk in the direction
		switch (direction) {
		case 'l':
			return map.getTiles()[position.x/32][position.y/32].isSolid() ? false :true;
		case 'r':
			return map.getTiles()[position.x/32+1][position.y/32].isSolid() ? false :true;
		case 'd':
			return map.getTiles()[position.x/32][position.y/32+1].isSolid() ? false :true;
		}
		return true;
	}

	public void walkRight() {
		if(checkDirection(player.getPosition(), 'r')) {
			player.setPosition(new Point(player.getPosition().x + 2, player.getPosition().y));			
		}
		
	}

	public void fall() {
		if(checkDirection(player.getPosition(), 'd')) {
			player.setPosition(new Point(player.getPosition().x, player.getPosition().y+2));			
		}
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
