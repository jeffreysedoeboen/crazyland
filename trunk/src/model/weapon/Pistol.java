package model.weapon;

import javax.swing.ImageIcon;

import model.bullet.Bullet;
import model.bullet.PistolBullet;

public class Pistol extends Weapon {

	private final float FIRE_RATE = 1.50F;
	private final Bullet BULLETTYPE = new PistolBullet();
	private final boolean UNLIMITED_BULLETS = true;
	
	
	public Pistol() {
		this.image = new ImageIcon();//TODO file nog aangeven
	}
	
}
