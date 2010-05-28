package model;

import java.awt.Image;
import java.awt.Point;

public class GameServer {
	
	World world;
	
	public GameServer(){
		
		world = new World();
		
	}
	
	public World getWorld(){
		return this.world;
	}

	public Point getPlayerPosition(){
		return this.world.getPlayerPosition();
	}
	
	public void jump() {
		// TODO Creating jump
		world.playerJump();
		
	}

	public Image getPlayerImage() {

		return world.getPlayerImage();
	
	}
		
}
