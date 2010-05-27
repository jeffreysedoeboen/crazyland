package model.tile;

import java.awt.image.BufferedImage;

public class Tile {
	private int x;
	private int y;
	private BufferedImage image;
	private boolean solid;
	
	public Tile(int x, int y, BufferedImage image, boolean solid){
		this.x = x;
		this.y = y;
		this.solid = solid;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public boolean isSolid(){
		return this.solid;
	}
	
}
