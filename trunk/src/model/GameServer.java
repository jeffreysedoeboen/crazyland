package model;

import java.awt.Image;
import java.awt.Point;

import model.bullet.Bullet;

public class GameServer extends Thread{
	
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
	
	public void startMovingRight(){
		world.startMovingRight();
	}
	
	public void stopMovingRight(){
		world.stopMovingRight();
	}

	public void startMovingLeft(){
		world.startMovingLeft();
	}
	
	public void stopMovingLeft(){
		world.stopMovingLeft();
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
	
	public Player getPlayer() {
		return world.getPlayer();
	}
	
	public void run(){
		while(true){
			
			world.move();
			
			Thread.currentThread();
			try{
				Thread.sleep(50);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
	}
		
}
