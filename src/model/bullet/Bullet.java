package model.bullet;

import java.awt.Image;
import java.awt.Point;

public abstract class Bullet {

	protected final float BULLET_SPEED = 1.00F;
	protected Image bulletImage;
	protected double direction;
	protected Point position;
	

	
	public double getDirection() {
		return direction;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public Image getBulletImage() {
		return bulletImage;
	}
}
