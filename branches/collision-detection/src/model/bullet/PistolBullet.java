package model.bullet;

import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PistolBullet extends Bullet {

	
	public PistolBullet(double direction, Point startPosition) {
		super(direction, startPosition);
		System.out.println("new pistol bullet");
		
		try {
			bulletImage = ImageIO.read(new File("../themes/tee/characters/character.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
