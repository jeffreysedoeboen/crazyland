package model.bullet;

import java.awt.Image;
import java.awt.Point;

public abstract class Bullet {

	protected final float BULLET_SPEED = 1.00F;
	protected Image bulletImage;
	protected double direction;
	protected float x, y;
	protected float verticalSpeed = 0;
	protected float horizontalSpeed = 0;
	protected Point destination;
	
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
	
	public float getBulletSpeed() {
		return BULLET_SPEED;
	}
	
	public float getVerticalSpeed() {
		return verticalSpeed;
	}
	
	public float getHorizontalSpeed() {
		return horizontalSpeed;
	}
	
	private void moveVertical() {
		this.y += this.verticalSpeed;
	}
	
	private void moveHorizontal() {
		this.x += this.horizontalSpeed;
	}
	
	private float calcVerticalSpeed() {
		return 0;
	}
	
	private float calcHorizontalSpeed() {
		if(direction <= 90) {
			return (float) (Math.cos(this.direction) * this.BULLET_SPEED);
		} else if (direction > 90 && direction <= 180) {
			return (float) (Math.cos(180 - this.direction) * this.BULLET_SPEED);
		} else if (direction > 180 && direction <= 270) {
			return (float) (Math.cos(180 - this.direction) * this.BULLET_SPEED);
		} else {
			
		}
		return 0;
	}
	
	public void step() {
		verticalSpeed = calcVerticalSpeed();
		horizontalSpeed = calcHorizontalSpeed();
		moveVertical();
		moveHorizontal();
	}
}
