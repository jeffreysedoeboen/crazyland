package server.model.tile;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import server.model.WorldObject;
import server.tools.Circle;

public class Tile implements WorldObject{
	private int x;
	private int y;
	private BufferedImage image;
	private boolean solid, respawn;
	
	public Tile(int x, int y, BufferedImage image, boolean solid, boolean respawn){
		this.x = x;
		this.y = y;
		this.image = image;
		this.solid = solid;
		this.setRespawn(respawn);
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
	
	public boolean isSolid() {
		return solid;
	}

	public void setRespawn(boolean respawn) {
		this.respawn = respawn;
	}

	public boolean isRespawn() {
		return respawn;
	}

	@Override
	public Shape getShape() {
		return new Rectangle2D.Double(x*16,y*16,16,16);
	}
	
}
