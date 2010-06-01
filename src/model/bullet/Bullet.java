package model.bullet;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

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
	
	public BufferedImage getBulletImage() {
		return bulletImage;
	}
	
	public void move(){
		this.x -= 3*Math.cos(this.direction);
		this.y -= 3*Math.sin(this.direction);
	}
	
	public void setBullet(int clickedX, int clickedY, float x, float y, double dir) {
		this.x = x;
		this.y = y;
		this.bulletImage = rotateImage(bulletImage, (float) (Math.toDegrees((Math.atan2(Math.toRadians(clickedY - y), Math.toRadians(clickedX - x))))));
		this.direction = (float) dir;
		//bulletImage = rotateImage(baseBulletImage, (float) Math.toDegrees((Math.atan2(Math.toRadians(mouseDot.y - y), Math.toRadians(mouseDot.x - x)))));
		//(float) Math.toDegrees((Math.atan2(Math.toRadians(mouseDot.y - y), Math.toRadians(mouseDot.x - x)))));
		
	}
	
	public BufferedImage rotateImage(BufferedImage src, float degrees) {
        AffineTransform affineTransform = AffineTransform.getRotateInstance(
                Math.toRadians(degrees),
                src.getWidth() / 2,
                src.getHeight() / 2);
        BufferedImage rotatedImage = new BufferedImage(src.getWidth(), src
                .getHeight(), src.getType());
        Graphics2D g = (Graphics2D) rotatedImage.getGraphics();
        g.setTransform(affineTransform);
        g.drawImage(src, 0, 0, null);
        return rotatedImage;
    }
}
