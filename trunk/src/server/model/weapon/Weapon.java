package server.model.weapon;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import server.model.bullet.*;

public abstract class Weapon {

	protected float fireRate = 1; // The amount of bullets per second
	protected Bullet bulletType = null; 
	protected boolean unlimitedBullets = false;
	protected int maxBullets = 0;
	protected int currentBullets = 0;
	protected BufferedImage image = null, baseImage;
	protected int x;
	protected int y;
	protected float angle;
	
	private int weaponDirection  =0 ;
	private boolean posChanged = false;
	
	public Weapon(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getWeaponDirection () {
		return weaponDirection;
	}
	
	public boolean getPosChanged() {
		return posChanged;
	}
	
	public Bullet shoot(){
		
		if(isUnlimitedBullets() || currentBullets > 0){
			//Shoot a bullet
			if(!unlimitedBullets){
				currentBullets--;
			}
			return this.createBullet();
		}
		return null;
	}

//	public BufferedImage turnToPoint( int mouseX, int mouseY ) {
//		float angle = (float) (Math.toDegrees((Math.atan2(Math.toRadians(mouseY - y), Math.toRadians(mouseX - x)))));
//		if(Math.abs(angle) > 90.00000f) {
//			setWeaponDir(1);
//			image = WorldView.rotateImage(baseImage,angle,true);
//		} else {
//			setWeaponDir(0);
//			image = WorldView.rotateImage(baseImage,angle,true);
//		}
//		
//		return image;
//	}
//	
//	public void setWeaponDir(int value) {
//		if(weaponDirection != value) {
//			baseImage = WorldView.verticalflip(baseImage);
//			weaponDirection = value;
//		}
//	}
	
	public float getFireRate(){
		return this.fireRate;
	}
	
	public Image getImage() {
		return image;
	}
	
	public boolean isUnlimitedBullets() {
		return unlimitedBullets;
	}
	
	public Bullet createBullet(){
		return this.bulletType;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
