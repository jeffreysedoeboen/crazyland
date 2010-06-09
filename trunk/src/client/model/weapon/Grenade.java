package client.model.weapon;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import client.model.weapon.Weapon;

public class Grenade extends Weapon {
	
	public Grenade(float x, float y) {
		super(x,y);
		try {
			this.baseImage = ImageIO.read(new File("../themes/tee/weapon/grenade.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
