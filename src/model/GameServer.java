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

	public float getPlayerX(){
		return this.world.getPlayerX();
	}

	public float getPlayerY(){
		return this.world.getPlayerY();
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
	
	public void moveWeapon(Point mousePoint) {
		world.moveWeapon(mousePoint);
		
	}

	public void shoot() {
		world.shoot();
		
	}
	
	public void jump(){
		world.jump();
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
				Thread.sleep(10);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
	}
		
}
