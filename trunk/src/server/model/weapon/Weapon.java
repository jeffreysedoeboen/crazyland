package server.model.weapon;

import server.model.bullet.*;

public abstract class Weapon {

	protected float fireRate = 1; // The amount of bullets per second
	protected Bullet bulletType = null; 
	protected boolean unlimitedBullets = false;
	protected int maxBullets = 0;
	protected int currentBullets = 0;
	
	public Weapon() {}
	
	public Bullet shoot(int bCounter){
		
		if(isUnlimitedBullets() || currentBullets > 0){
			//Shoot a bullet
			if(!unlimitedBullets){
				currentBullets--;
			}
			return this.createBullet(bCounter);
		}
		return null;
	}
	
	public float getFireRate(){
		return this.fireRate;
	}
	
	public boolean isUnlimitedBullets() {
		return unlimitedBullets;
	}
	
	
	/**
	 * @param bCounter Het aantal bullets dat al zijn aangemaakt
	 * @return De bullet die gemaakt word;
	 */
	public Bullet createBullet(int bCounter){
		return null;
	}	
}
