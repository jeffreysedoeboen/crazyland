package model;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import model.bullet.Bullet;
import model.tile.Tile;

public class World{

	Map map;
	Player player;
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
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
	
	public void shoot(Point mouseDot) {
		float playerX = getPlayerX() + 16;
        float playerY = getPlayerY() + 16;
        float dx = (float) (playerX - mouseDot.getX());
        float dy = (float) (playerY - mouseDot.getY());
        double angle = 0.0d;
        
        if (dx == 0.0) {
                if(dy == 0.0) {
                        angle = 0.0;
                } else if(dy > 0.0) {
                        angle = Math.PI / 2.0;
                } else {
                        angle = (Math.PI * 3.0) / 2.0;
                }
        } else if(dy == 0.0) {
                if(dx > 0.0) {
                        angle = 0.0;
                } else {
                        angle = Math.PI;
                }
        } else {
                if(dx < 0.0) {
                        angle = Math.atan(dy/dx) + Math.PI;
                } else if(dy < 0.0) {
                        angle = Math.atan(dy/dx) + (2*Math.PI);
                } else {
                        angle = Math.atan(dy/dx);
                }
        }
        float direction = (float) ((angle * 180) / Math.PI);

        bullets.add( player.shoot() );
        if(bullets.size() > 0) {
        	System.out.println(bullets.size());
        }
	}

	public Image getBulletImage() {
		return bullets.get(0).getBulletImage();
	}
	
	public Player getPlayer() {
		return player;
	}

	public void moveWeapon(Point mousePoint) {
		player.getWeapon().turnToPoint(mousePoint);
	}

	public void move() {
		if(player.isMovingLeft() && canMoveLeft()){
			player.moveLeft(onGround());
		}else if(player.isMovingRight() && canMoveRight()){
			player.moveRight(onGround());
		}
		player.moveVertical();
		player.calcVerticalSpeed(onGround());
		if(collisionCeiling()){
			player.setVerticalSpeed(-0.5f);
		}
		
//		Tile[][] tiles = map.getTiles();
//		for(boolean[] pmap2 : tiles[0][1].getPixelMap()){
//			for(boolean pma : pmap2){
//				System.out.print(pma + " ");
//			}
//			System.out.println();
//		}
		
	}
	
	private boolean canMoveLeft() {
		Tile[][] tiles = map.getTiles();
		if(tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY())/32)].isSolid() || tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].isSolid()){
			return false;
		}
		return true;
	}
	
	private boolean canMoveRight() {
		Tile[][] tiles = map.getTiles();
		if(tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY())/32)].isSolid() || tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].isSolid()){
			return false;
		}
		return true;
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
	
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
}
