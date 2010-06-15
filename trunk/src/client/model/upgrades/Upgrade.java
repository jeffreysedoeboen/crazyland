package client.model.upgrades;

import java.awt.image.BufferedImage;

public class Upgrade {
	protected int value;
	protected BufferedImage image = null;
	protected int x, y; 
	
	public Upgrade(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public int getValue() {
		return value;
	}
	
	public BufferedImage getImage() {
		return image;
	}
}
