package model.tile;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class Tile {
	private int x;
	private int y;
	private Image image;
	private boolean solid;
	
	public Tile(int x, int y, Image image, boolean solid){
		this.x = x;
		this.y = y;
		this.image = image;
		this.solid = solid;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public boolean isSolid() {
		return solid;
	}
}