package model;

import java.awt.Image;
import java.awt.Point;
import java.util.Observable;

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
}
