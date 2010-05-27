package model;

import java.awt.Image;
import java.awt.Point;

import java.io.File;
import javax.swing.ImageIcon;

import model.bullet.Bullet;
import model.weapon.Pistol;
import model.weapon.Weapon;

public class Player {

	private String name;
	private Point position;
	private Weapon primaryWeapon = new Pistol();
	private long lastTimeShot = 0;
	private int hitpoints = 10;
	private static Image playerImage;
	
	public Player(String name, Point position) {
		this.name = name;
		this.position = position;
		
		ImageIcon playerImage2 = new ImageIcon("../themes/tee/character.png");
		playerImage = playerImage2.getImage();
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
}