package model.weapon;

import model.bullet.*;

public abstract class Weapon {

	protected final float FIRE_RATE = 1; // The amount of bullets per second
	protected final Bullet BULLETTYPE = null; 
	protected final boolean UNLIMITED_BULLETS = false;
	protected final int MAX_BULLETS = 0;
	protected int currentBullets = 0;
	
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
	
}
