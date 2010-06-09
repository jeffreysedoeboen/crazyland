package client.model.weapon;
import java.awt.image.BufferedImage;

public abstract class Weapon {

	protected BufferedImage image = null, baseImage;

	private int weaponDirection  =0 ;
	protected float x;
	protected float y;
	
	public Weapon(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public int getWeaponDirection () {
		return weaponDirection;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public BufferedImage getBaseImage() {
		return baseImage;
	}

	public void setWeaponDirection(int value) {
		this.weaponDirection = value;
	}

	public void setImage(BufferedImage image) {
		this.image = image;	
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}

	public void setBaseImage(BufferedImage baseImage) {
		this.baseImage = baseImage;
	}
}
