package model.bullet;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GrenadeBullet extends Bullet {
	public GrenadeBullet() {
		try {
			bulletImage = ImageIO.read(new File("../themes/tee/weapon/grenade.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
