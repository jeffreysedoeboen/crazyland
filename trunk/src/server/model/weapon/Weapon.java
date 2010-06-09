package server.model.weapon;

import server.model.bullet.*;

public abstract class Weapon {

	protected float fireRate = 1; // The amount of bullets per second
	protected Bullet bulletType = null; 
	protected boolean unlimitedBullets = false;
	protected int maxBullets = 0;
	protected int currentBullets = 0;
	
	public Weapon() {}
	
	public Bullet shoot(int bCounter){
		
		if(isUnlimitedBullets() || currentBullets > 0){
			//Shoot a bullet
			if(!unlimitedBullets){
				currentBullets--;
			}
			return this.createBullet(bCounter);
		}
		return null;
	}

//	public BufferedImage turnToPoint( int mouseX, int mouseY ) {
//		float angle = (float) (Math.toDegrees((Math.atan2(Math.toRadians(mouseY - y), Math.toRadians(mouseX - x)))));
//		if(Math.abs(angle) > 90.00000f) {
//			setWeaponDir(1);
//			image = WorldView.rotateImage(baseImage,angle,true);
//		} else {
//			setWeaponDir(0);
//			image = WorldView.rotateImage(baseImage,angle,true);
//		}
//		
//		return image;
//	}
//	
//	public void setWeaponDir(int value) {
//		if(weaponDirection != value) {
//			baseImage = WorldView.verticalflip(baseImage);
//			weaponDirection = value;
//		}
//	}
	
	public float getFireRate(){
		return this.fireRate;
	}
	
	public boolean isUnlimitedBullets() {
		return unlimitedBullets;
	}
	
	public Bullet createBullet(int bCounter){
		return null;
	}	
}
