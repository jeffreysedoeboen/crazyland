package client.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet {

	private float direction = 0;
	private int x;
	private int y;
	private BufferedImage image;
	
	public Bullet(int x, int y){
		this.x = x;
		this.y = y;
		//this.direction = dir;
		try {
			image = ImageIO.read(new File("../themes/tee/weapon/bullet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void move(){
		this.x -= 3*Math.cos(this.direction);
		this.y -= 3*Math.sin(this.direction);
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
