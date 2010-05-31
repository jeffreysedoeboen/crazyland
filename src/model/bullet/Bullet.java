package model.bullet;

import java.awt.Image;
import java.awt.Point;

public abstract class Bullet {

	protected final float BULLET_SPEED = 1.00F;
	protected Image bulletImage;
	protected double direction;
	protected float x, y;
	

	
	public double getDirection() {
		return direction;
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

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public Image getBulletImage() {
		return bulletImage;
	}
}
