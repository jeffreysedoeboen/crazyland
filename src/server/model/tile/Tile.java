package server.model.tile;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import server.model.WorldObject;

public class Tile implements WorldObject {
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

	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getY() {
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
	
//	public int[] getShapeX() {
//		
//		double[] shape = Geometry.createRectangle(x*32, y*32, 32.0, 32.0);
//		int[] retVal = new int[shape.length];
//		
//		int j = 0;
//		for( int i = 0; i < shape.length; i++ ) {
//			if( i%2 == 0 ) {
//				retVal[j] = (int) shape[i];
//				j++;
//			}
//		}
//		return retVal;
//	}
//	
//	public int[] getShapeY() {
//		
//		double[] shape = Geometry.createRectangle(x*32, y*32, 32.0, 32.0);
//		int[] retVal = new int[shape.length];
//		
//		int j = 0;
//		for( int i = 0; i < shape.length; i++ ) {
//			if( i%2 != 0 ) {
//				retVal[j] = (int) shape[i];
//				j++;
//			}
//		}
//		return retVal;
//	}
	
	public Shape getShape() {
		return new Rectangle2D.Double(x*32, y*32, 32, 32);
	}
}
