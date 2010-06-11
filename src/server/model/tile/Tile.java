package server.model.tile;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import server.model.WorldObject;
import server.tools.Circle;

public class Tile implements WorldObject{
	private int x;
	private int y;
	private BufferedImage image;
	private boolean solid;
	private Shape shape;
	
	public Tile(int x, int y, BufferedImage image, boolean solid, String shape, String corner, boolean respawn){
		this.x = x;
		this.y = y;
		this.image = image;
		this.solid = solid;
		if(shape.equals("circle")){
			if(corner.equals("tl")){
				this.shape = new Circle(32,x*32,y*32);
			}else if(corner.equals("bl")){
				this.shape = new Circle(32,x*32,y*32-32);
			}else if(corner.equals("tr")){
				this.shape = new Circle(32,x*32-32,y*32);
			}else{
				this.shape = new Circle(32,x*32-32,y*32-32);
			}
		}else{
			this.shape = new Rectangle2D.Double(x*32,y*32,32,32);
		}
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

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public boolean[][] getPixelMap(){
		boolean[][] pmap = new boolean[32][32];
		for(int i = 0; i < 32; i++){
			for(int i2 = 0; i2 < 32; i2++){
				if(this.image.getRGB(i, i2) == 0){
					pmap[i2][i] = true;
				}else{
					pmap[i2][i] = false;
				}
			}
		}
		
		return pmap;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public Shape getShape(){
		return this.shape;
	}
	
}
