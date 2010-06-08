package server.model.tile;

import java.awt.image.BufferedImage;

public class Tile {
	private int x;
	private int y;
	private BufferedImage image;
	private boolean solid;
	
	public Tile(int x, int y, BufferedImage image, boolean solid){
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
}
