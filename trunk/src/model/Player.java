package model;

import java.awt.Image;
import java.awt.Point;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

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
	
	public Player(String name, Point position) {
		this.name = name;
		this.position = position;
		
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
}