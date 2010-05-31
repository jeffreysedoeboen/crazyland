package model.weapon;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import model.bullet.*;

public abstract class Weapon {

	protected final float FIRE_RATE = 1; // The amount of bullets per second
	protected Bullet BULLETTYPE = null; 
	protected final boolean UNLIMITED_BULLETS = false;
	protected final int MAX_BULLETS = 0;
	protected int currentBullets = 0;
	protected BufferedImage image = null;
	
	public Bullet shoot(){
		if(isUNLIMITED_BULLETS() || currentBullets > 0){
			//Shoot a bullet
			if(!UNLIMITED_BULLETS){
				currentBullets--;
			}
			return BULLETTYPE;
		}
		return null;
	}
	
	public void turnToPoint( Point point ) {
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setTransform(AffineTransform.getRotateInstance(point.getX(), point.getY())); //TODO controleren als view er is
		g.dispose();
	}
	
	public float getFireRate(){
		return this.FIRE_RATE;
	}
	
	public Image getImage() {
		return image;
	}
	
	public boolean isUNLIMITED_BULLETS() {
		return UNLIMITED_BULLETS;
	}
	
}
