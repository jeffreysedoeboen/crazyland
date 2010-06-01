package model.weapon;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import view.WorldView;
import model.bullet.*;

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
	
	public Weapon(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public float getAngle() {
		return angle;
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

	public BufferedImage turnToPoint( int mouseX, int mouseY) {
		float angle = (float) (Math.toDegrees((Math.atan2(Math.toRadians(mouseY - y), Math.toRadians(mouseX - x)))));
		image = WorldView.rotateImage(baseImage,angle);
		return image;
	}
	
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
