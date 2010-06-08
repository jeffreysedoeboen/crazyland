package server.model.bullet;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GrenadeBullet extends Bullet {
	public GrenadeBullet(int inden) {
		super(inden);
		try {
			bulletImage = ImageIO.read(new File("../themes/tee/weapon/grenade.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
