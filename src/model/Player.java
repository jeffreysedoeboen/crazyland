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
	private Point position;
	private Weapon primaryWeapon;
	private long lastTimeShot = 0;
	private int hitpoints = 10;
	private Image playerImage;
	private boolean movingRight = false;
	private boolean movingLeft = false;
	
	public Player(String name, Point position) {
		this.name = name;
		this.position = position;
		
		primaryWeapon = new Pistol();
		
		try {
			playerImage = ImageIO.read(new File("../themes/tee/characters/character.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setPosition(Point p) {
		this.position = p;
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
	
	public Point getPosition(){
		return position;	
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
		position.setLocation(position.getX()+2,position.getY());
	}
	
	public void moveLeft() {
		position.setLocation(position.getX()-2,position.getY());
	}
	
}