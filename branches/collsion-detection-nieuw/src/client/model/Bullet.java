package client.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet {

	private float verticalSpeed = 0;
	private float direction = 0;
	private float x, y;
	private BufferedImage image;
	private int indentifier;
	
	public Bullet(int x, int y, int indentifier, float dir){
		this.x = x;
		this.y = y;
		this.direction = dir;
		this.indentifier = indentifier;
		try {
			image = ImageIO.read(new File("../themes/tee/weapon/bullet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getIndentifier(){
		return this.indentifier;
	}
	
	public void move(){
		this.x -= 6*Math.cos(this.direction);
		this.y -= 6*Math.sin(this.direction);
		this.y = (int) (this.y - verticalSpeed);
		verticalSpeed += -0.005;
	}
	
	public Image getBulletImage() {
		return image;
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

}
