package server.model;

import java.awt.Image;
import java.awt.Shape;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import server.model.bullet.Bullet;
import server.model.weapon.Pistol;
import server.model.weapon.Weapon;
import server.tools.Circle;

public class Player implements WorldObject{
	private final int respawnTime = 5;
	private float angle;
	private String name;
	private float x;
	private float y;
	private Weapon primaryWeapon;
	private long lastTimeShot = 0;
	private int hitpoints = 10;
	private Image playerImage, shootImage;
	private boolean movingRight = false;
	private boolean movingLeft = false;
	private boolean shooting = false;
	private int shootCounter, kills, deaths, timeToWait;
	private float verticalSpeed = 0;
	private ArrayList<Weapon> weaponlist;
	protected int indentifier;

	public Player(String name, float x, float y, int indentifier) {
		weaponlist = new ArrayList<Weapon>();
		this.name = name;
		this.x = x;
		this.y = y;
		this.shootCounter = 0;
		this.timeToWait = respawnTime;
		this.indentifier = indentifier;

		try {
			playerImage = ImageIO.read(new File("themes/tee/characters/character.png"));
			shootImage = ImageIO.read(new File("themes/tee/characters/charactershoot.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		primaryWeapon = new Pistol();
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void decreaseTimeToWait() {
		timeToWait--;
	}

	public void decreaseHitpoints(int damage) {
		hitpoints -= damage;
	}
	
	public void resetHitpoints() {
		hitpoints = 10;
	}

	public int getKills() {
		return kills;
	}

	public void increaseKills() {
		kills++;
	}

	public void resetKills() {
		kills = 0;
	}

	public int getDeaths() {
		return deaths;
	}

	public void increaseDeaths() {
		deaths++;
	}

	public void resetDeaths() {
		deaths = 0;
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setVerticalSpeed(float x){
		this.verticalSpeed = x;
	}

	public float getVerticalSpeed(){
		return this.verticalSpeed;
	}

	public void addToHitpoints(int value) {
		hitpoints += value;
	}
	
	/**
	 * Hier word de Bullet afgeschoten.
	 * @param bCounter Dit is de Identifier van de Bullet die aangemaakt word
	 * @return Bullet Dit is de Bullet die afgeschoten word
	 */
	public Bullet shoot(int bCounter) {
		if(hitpoints > 0) {
			float fireRate = primaryWeapon.getFireRate();
			float timePerShot = 1/fireRate;
			timePerShot = timePerShot*1000;

			if(System.currentTimeMillis()-lastTimeShot >= timePerShot){

				lastTimeShot = System.currentTimeMillis();

				shooting = true;
				shootCounter = 100;
				return primaryWeapon.shoot(bCounter);
			}		
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
		if(shootCounter > 1) {
			shootCounter--;
		} else {
			shooting = false;
		}
		return isShooting() ? shootImage: playerImage;
	}

	private boolean isShooting() {
		return shooting;
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

	public void moveRight(boolean onGround) {
		if(onGround){
			this.x += 2.5; //4
		}else{
			this.x += 2; //3
		}
	}

	public void moveLeft(boolean onGround) {
		if(onGround){
			this.x -= 2.5; //4
		}else{
			this.x -= 2; //3
		}
	}

	public void moveVertical() {
		this.y -= this.verticalSpeed;
	}

	public void calcVerticalSpeed(boolean onGround) {

		if(!onGround){
			if(this.verticalSpeed > -2.5){ //4
				this.verticalSpeed += -0.1;
			}
		}else{
			this.verticalSpeed = 0;
		}
	}

	public void changeWeapon() {
		if(weaponlist.size()>1) {
			if(primaryWeapon.equals(weaponlist.get(weaponlist.size()-1))) {
				primaryWeapon = weaponlist.get(0);
			} else {
				for(int i = 0; i < weaponlist.size()-1; i++) {
					if(weaponlist.get(i).equals(primaryWeapon)) {
						primaryWeapon = weaponlist.get(i+1);
					}
				}
			}
		}
	}

	public Shape getShape(){
		return new Circle(15,(int)x,(int)y);
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public void setTimeToWait(int timeToWait) {
		this.timeToWait = timeToWait;
	}

	public int getTimeToWait() {
		return timeToWait;
	}
	
	public void resetTimeToWait() {
		timeToWait = respawnTime;
	}

	public void setHitpoints(int value) {
		hitpoints += value;
	}
	
	public int getIndentifier() {
		return indentifier;
	}
}