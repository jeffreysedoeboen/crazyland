package server.model.weapon;

import server.model.bullet.Bullet;
import server.model.bullet.PistolBullet;


public class Pistol extends Weapon {

	protected float fireRate = 3F;
	protected boolean unlimitedBullets = true;
	
	public Pistol() {	
		super();
	}

	public boolean isUnlimitedBullets() {
		return unlimitedBullets;
	}
	
	public Bullet createBullet(int bCounter){
		return new PistolBullet(bCounter);
	}
	
	public float getFireRate(){
		return this.fireRate;
	}
	
}
