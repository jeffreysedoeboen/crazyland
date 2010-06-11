package server.model.bullet;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.Shape;

import server.model.Player;

public abstract class Bullet {

	protected final float BULLET_SPEED = 1.00F;
	protected BufferedImage bulletImage;
	protected float direction;
	protected float x, y;
	protected float verticalSpeed = 0;
	protected Point destination;
	protected int indentifier;
	protected Shape shape;
	protected Player player;

	public Bullet(int inden){
		this.indentifier = inden;
	}
	
	public int getIndentifier(){
		return this.indentifier;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setDirection(float direction) {
		this.direction = direction;
	}
	
	public float getDirection () {
		return direction;
	}

	public BufferedImage getBulletImage() {
		return bulletImage;
	}
	
	public void move(){
		this.x -= 6*Math.cos(this.direction);
		this.y -= 6*Math.sin(this.direction);
		this.y = this.y - verticalSpeed;
		verticalSpeed += -0.005;
	}
	
	public void moveOpposite(){
		this.x += 1*Math.cos(this.direction + Math.PI);
		this.y += 1*Math.sin(this.direction + Math.PI);
	}
	
	public void setBullet(float x, float y, double dir, Player p) {
		this.x = x;
		this.y = y - 8;
		this.player = p;
		//this.bulletImage = WorldView.rotateImage(bulletImage, (float) (Math.toDegrees((Math.atan2(Math.toRadians(clickedY - y), Math.toRadians(clickedX - x))))),false);
		this.direction = (float) dir;	
		
	}
	
	public Player getOrigin(){
		return this.player;
	}
	
	public Shape getShape(){
		
		return null;
	}
	
}
