package model.bullet;

import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PistolBullet extends Bullet {

	
	public PistolBullet(Point destination) {		
		this.destination = destination;
		try {
			bulletImage = ImageIO.read(new File("../themes/tee/weapon/bullet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
