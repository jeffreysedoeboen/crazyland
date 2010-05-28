package model;

import java.awt.Image;
import java.awt.Point;
import java.util.Observable;

import model.bullet.Bullet;
import model.tile.Tile;

public class World{

	Map map;
	Player player;
	Bullet bullet;
	
	public World(){
		map = new Map();
		player = new Player("Henk", new Point(2,2));
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
			player.setPosition(new Point(player.getPosition().x - 1, player.getPosition().y));
		}
		
	}

	private boolean checkDirection(Point position, char direction) {
		//TODO look if player can walk in the direction
		switch (direction) {
		case 'l':
			return map.getTiles()[position.x-1][position.y].isSolid() ? false :true;
		case 'r':
			return map.getTiles()[position.x+1][position.y].isSolid() ? false :true; 
		}
		return true;
	}

	public void walkRight() {
		if(checkDirection(player.getPosition(), 'r')) {
			player.setPosition(new Point(player.getPosition().x + 1, player.getPosition().y));			
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
}
