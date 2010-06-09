package server.model.weapon;
import server.model.bullet.GrenadeBullet;
import server.model.bullet.Bullet;
import server.model.weapon.Weapon;

public class Grenade extends Weapon {

	protected float fireRate = 1F;
	protected boolean unlimitedBullets = false;
	
	public Grenade() {
		super();
		maxBullets = 4;
	}
	
	public boolean isUnlimitedBullets() {
		return unlimitedBullets;
	}
	
	public Bullet createBullet(int bCounter){
		return new GrenadeBullet(bCounter);
	}
	
	public float getFireRate(){
		return this.fireRate;
	}
}
