package client.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {

	private float x;
	private float y;
	private BufferedImage image;
	
	public Player(float x, float y) {
		this.x = x;
		this.y = y;
		try {
			image = ImageIO.read(new File("../themes/tee/characters/character.png"));
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
	
}