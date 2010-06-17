package server.model.bullet;

import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.Shape;

import server.model.Player;

public abstract class Bullet {

	protected final float BULLET_SPEED = 1.00F;
	protected final int DAMAGE = 1;
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
	
	public int getDamage() {
		return DAMAGE;
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
	
	
	/**
	 * De bullet een stap verder zetten
	 */
	public void move(){
		this.x -= 6*Math.cos(this.direction);
		this.y -= 6*Math.sin(this.direction);
		this.y = this.y - verticalSpeed;
		verticalSpeed += -0.005;
	}
	
	
	/**
	 * De kogel achteruit laten gaan
	 */
	public void moveOpposite(){
		this.x += 1*Math.cos(this.direction + Math.PI);
		this.y += 1*Math.sin(this.direction + Math.PI);
	}
	
	/**
	 * @param x-coordinaat van de kogel
	 * @param y-coordinaat van de kogel
	 * @param dir De richting waar de kogel heen gaat 
	 * @param p De player de de kogel afschiet
	 */
	public void setBullet(float x, float y, double dir, Player p) {
		this.x = x;
		this.y = y - 8;
		this.player = p;
		this.direction = (float) dir;	
		
	}
	
	
	/**
	 * @return De player die de kogel afschoot
	 */
	public Player getOrigin(){
		return this.player;
	}
	
	
	public Shape getShape(){	
		return null;
	}
	
}
