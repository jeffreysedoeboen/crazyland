package model.weapon;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pistol extends Weapon {

	private final float FIRE_RATE = 1.50F;
	private final boolean UNLIMITED_BULLETS = true;
	
	
	public Pistol() {
		try {
			this.image = ImageIO.read(new File("../themes/tee/weapon/pistol.jpg")); //TODO file nog aangeven
		} catch (IOException e) {
			System.err.println("Error while reading image for weapon");
			e.printStackTrace();
		}
	}
	
}
