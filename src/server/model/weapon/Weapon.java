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
	protected BufferedImage image = null;
	
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

	public void turnToPoint( Point point ) {
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setTransform(AffineTransform.getRotateInstance(point.getX(), point.getY())); //TODO controleren als view er is
		g.dispose();
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
	
}
