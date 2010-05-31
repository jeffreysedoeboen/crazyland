package model;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.bullet.Bullet;
import model.weapon.Pistol;
import model.weapon.Weapon;

public class Player {

	private String name;
	private float x;
	private float y;
	private Weapon primaryWeapon;
	private long lastTimeShot = 0;
	private int hitpoints = 10;
	private Image playerImage;
	private boolean movingRight = false;
	private boolean movingLeft = false;
	
	public Player(String name, float x, float y) {
		this.name = name;
		this.x = x;
		this.y = y;
		
		primaryWeapon = new Pistol();
		
		try {
			playerImage = ImageIO.read(new File("../themes/tee/characters/character.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Bullet shoot() {

		float fireRate = primaryWeapon.getFireRate();
		float timePerShot = 1/fireRate;
		
		if(System.currentTimeMillis()-lastTimeShot >= timePerShot){
			
			lastTimeShot = System.currentTimeMillis();
			
			return primaryWeapon.shoot();
		}		
		
		return null;		
	}
	
	public float getX(){
		return this.x;	
	}
	public float getY(){
		return this.y;
	}
	
	public String getName(){
		return name;
	}
	
	public int getHitpoints(){
		return hitpoints;
	}

	public Weapon getWeapon() {
		return primaryWeapon;
	}

	public Image getImage() {
		return playerImage;
	}

	public void setMovingRight(boolean b) {
		movingRight = b;
	}

	public void setMovingLeft(boolean b) {
		movingLeft = b;
	}
	
	public boolean isMovingLeft(){
		return movingLeft;
	}
	
	public boolean isMovingRight(){
		return movingRight;
	}
	
	public boolean onGround(){
		return true;
	}

	public void moveRight() {
		this.x += 2.5;
	}
	
	public void moveLeft() {
		this.x -= 2.5;
	}
	
}