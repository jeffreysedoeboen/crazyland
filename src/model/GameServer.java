package model;

import java.awt.Image;
import java.awt.Point;

import model.bullet.Bullet;

public class GameServer {
	
	World world;
	
	public GameServer(){
		world = new World();
	}
	
	public Bullet getBullet() {
		//TODO moet nog meerdere bullets worden
		return world.bullet;
	}
	
	public World getWorld(){
		return this.world;
	}

	public Point getPlayerPosition(){
		return this.world.getPlayerPosition();
	}
	
	public void jump() {
		world.playerJump();
		
	}

	public Image getPlayerImage() {

		return world.getPlayerImage();
	
	}

	public void walkLeft() {
		world.walkLeft();
	}

	public void walkRight() {
		world.walkRight();
	}

	public void moveWeapon() {
		// TODO Auto-generated method stub
		
	}

	public void shoot() {
		world.shoot();
		
	}

	public Image getBulletImage() {
		return world.getBulletImage();
	}
		
}
