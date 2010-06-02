package client.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet {

	private int x;
	private int y;
	private BufferedImage image;
	
	public Bullet(int x, int y){
		this.x = x;
		this.y = y;
		try {
			image = ImageIO.read(new File("../themes/tee/weapon/bullet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Image getBulletImage() {
		return image;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

}
