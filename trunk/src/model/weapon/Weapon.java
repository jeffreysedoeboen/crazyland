package model.weapon;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import model.bullet.*;

import javax.swing.ImageIcon;
public abstract class Weapon {

	protected final float FIRE_RATE = 1; // The amount of bullets per second
	protected final Bullet BULLETTYPE = null; 
	protected final boolean UNLIMITED_BULLETS = false;
	protected final int MAX_BULLETS = 0;
	protected int currentBullets = 0;
	protected ImageIcon image = null;
	
	// TODO Implement Shoot
	public Bullet shoot(){
		if(UNLIMITED_BULLETS || currentBullets > 0){
			//Shoot a bullet
			if(!UNLIMITED_BULLETS){
				currentBullets--;
			}
			return BULLETTYPE;
		}
		return null;
	}
	
	public void turnToPoint( Point point ) {
		Graphics2D g = (Graphics2D) image.getImage().getGraphics();
		g.setTransform(AffineTransform.getRotateInstance(point.getX(), point.getY())); //TODO controleren als view er is
		g.dispose();
	}
	
	public float getFireRate(){
		return this.FIRE_RATE;
	}
	
}
