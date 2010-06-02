package model.weapon;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.bullet.Bullet;
import model.bullet.GrenadeBullet;
import model.bullet.PistolBullet;

public class Grenade extends Weapon {

	protected final Bullet bulletType = new GrenadeBullet();
	protected float fireRate = 1F;
	protected boolean unlimitedBullets = false;
	
	public Grenade(int x, int y) {
		super(x, y);
		maxBullets = 4;
		try {
			this.baseImage = ImageIO.read(new File("../themes/tee/weapon/grenade.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}
	
	public boolean isUnlimitedBullets() {
		return unlimitedBullets;
	}
	
	public Bullet createBullet(){
		return new GrenadeBullet();
	}
	
	public float getFireRate(){
		return this.fireRate;
	}
}
