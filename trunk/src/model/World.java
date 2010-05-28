package model;

import java.awt.Image;
import java.awt.Point;
import java.util.Observable;

import model.tile.Tile;

public class World{

	Map map;
	Player player;
	
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
		//TODO look if player kan walk in the direction
		return true;
	}

	public void walkRight() {
		if(checkDirection(player.getPosition(), 'r')) {
			player.setPosition(new Point(player.getPosition().x + 1, player.getPosition().y));			
		}
		
	}
}
