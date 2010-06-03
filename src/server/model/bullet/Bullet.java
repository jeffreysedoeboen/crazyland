package server.model.bullet;

import java.awt.image.BufferedImage;
import java.awt.Point;

public abstract class Bullet {

	protected final float BULLET_SPEED = 1.00F;
	protected BufferedImage bulletImage;
	protected float direction;
	protected float x, y;
	protected float verticalSpeed = 0;
	protected float horizontalSpeed = 0;
	protected Point destination;

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
		this.x -= 3*Math.cos(this.direction);
		this.y -= 3*Math.sin(this.direction);
	}
	
	public void setBullet(float clickedX, float clickedY, float x, float y, double dir) {
		this.x = x;
		this.y = y;
		//this.bulletImage = WorldView.rotateImage(bulletImage, (float) (Math.toDegrees((Math.atan2(Math.toRadians(clickedY - y), Math.toRadians(clickedX - x))))),false);
		this.direction = (float) dir;
	}
	
}
