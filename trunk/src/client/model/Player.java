package client.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {

	private float x;
	private float y;
	private BufferedImage image,heartImage;
	private int hitpoints = 0;
	
	public Player(float x, float y, int hitpoints) {
		this.x = x;
		this.y = y;
		this.hitpoints = hitpoints;
		try {
			image = ImageIO.read(new File("../themes/tee/characters/character.png"));
			heartImage = ImageIO.read(new File("../themes/tee/other/heart.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public float getX(){
		return this.x;	
	}
	public float getY(){
		return this.y;
	}
	
	public Image getImage() {
		return image;
	}
	
	public Image getHeartImage(){
		return heartImage;
	}
	
	public int getHitpoints(){
		return hitpoints;
	}
	
	public int setHitpoints(int hitpoints){
		return this.hitpoints=hitpoints;
	}
	
}