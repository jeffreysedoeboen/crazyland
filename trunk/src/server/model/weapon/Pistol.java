package server.model.weapon;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import server.model.bullet.Bullet;
import server.model.bullet.PistolBullet;


public class Pistol extends Weapon {

	protected final Bullet bulletType = new PistolBullet();
	protected float fireRate = 3F;
	protected boolean unlimitedBullets = true;
	
	public Pistol(int x, int y) {	
		super(x,y);
		try {
			this.baseImage = ImageIO.read(new File("../themes/tee/weapon/gun.png"));
		} catch (IOException e) {
			System.err.println("Error while reading image for weapon");
			e.printStackTrace();
		}
	}

	public boolean isUnlimitedBullets() {
		return unlimitedBullets;
	}
	
	public Bullet createBullet(){
		return new PistolBullet();
	}
	
	public float getFireRate(){
		return this.fireRate;
	}
	
}
