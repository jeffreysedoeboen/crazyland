package client.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet {

	private float verticalSpeed = 0;
	private float direction = 0;
	private float x;
	private float y;
	private BufferedImage image;
	private int indentifier;
	
	public Bullet(int x, int y, int indentifier, float dir){
		System.out.println(y);
		this.x = x;
		this.y = y - 8;
		this.direction = dir;
		this.indentifier = indentifier;
		try {
			image = ImageIO.read(new File("themes/tee/weapon/bullet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getIndentifier(){
		return this.indentifier;
	}
	
	public void move(){
		this.x -= 12*Math.cos(this.direction);
		this.y -= 12*Math.sin(this.direction);
		this.y = this.y - verticalSpeed;
		verticalSpeed += -0.005;
	}
	
	public BufferedImage getBulletImage() {
		return image;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public double getDirection() {
		return this.direction;
	}

}
