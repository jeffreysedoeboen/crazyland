package model.bullet;

import java.awt.Image;
import java.awt.Point;

public abstract class Bullet {

	protected final float BULLET_SPEED = 1.00F;
	protected Image bulletImage;
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

	public Image getBulletImage() {
		return bulletImage;
	}
	
	public void move(){
		this.x -= 3*Math.cos(this.direction);
		this.y -= 3*Math.sin(this.direction);
	}
	
	public void setBullet(float x, float y, double dir) {
		this.x = x;
		this.y = y;
		this.direction = (float) dir;
	}
	
}
