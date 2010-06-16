package client.model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import client.model.weapon.Pistol;
import client.model.weapon.Weapon;

public class Player {

	private float x;
	private float y;
	private float angle;
	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	private BufferedImage heartImage, leftImage, rightImage;
	private String name;
	private int hitpoints = 0;
	private Weapon primaryWeapon;
	private int kills;
	private int deaths;
	private Timer timer;
	private ActionListener timerPerformer = new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			//image = normalImage;
			timer.stop();
		}

	}; 
	
	
	public Player(String name, float x, float y,int hitpoints, float angle, int kills, int deaths) {
		this.angle = angle;
		this.x = x;
		this.y = y;
		this.name = name;
		this.hitpoints = hitpoints;
		this.kills = kills;
		this.deaths = deaths;
		try {
			leftImage 		= ImageIO.read(new File("themes/tee/characters/character_left.png"));
			rightImage 		= ImageIO.read(new File("themes/tee/characters/character_right.png"));
			heartImage 	= ImageIO.read(new File("themes/tee/other/heart.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//this.image = normalImage;
		timer = new Timer(2000, timerPerformer);
		this.primaryWeapon = new Pistol(getMidPlayerX(), getMidPlayerY());
		turnToPoint(angle);
		if(this.getPrimaryWeapon().getWeaponDirection() == 0) {
			this.getPrimaryWeapon().setX(this.getMidPlayerX() + 10);
		} else {
			this.getPrimaryWeapon().setX(this.getMidPlayerX() - 10);
		}
	}
	
//	public void setShootImage() {
//		this.image = shootImage;
//		timer.start();
//		
//	}
	
	public int getKills() {
		return kills;
	}
	
	public void setKills(int k) {
		kills = k;
	}
	
	public int getDeaths() {
		return deaths;
	}
	
	public void setDeaths(int d) {
		deaths = d;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	private int getMidPlayerX() {
		return (int) (x + (this.getImage().getWidth(null) / 2) -18);
	}
	
	private int getMidPlayerY() {
		return (int) (y + ( this.getImage().getHeight(null) / 2) -15);
	}
	
	public String getName() {
		return name;
	}
	
	public float getX(){
		return this.x;	
	}
	public float getY(){
		return this.y;
	}
	public void setX(float x) {
		this.x = x;
		this.getPrimaryWeapon().setX(this.getMidPlayerX());
		
	}
	public void setY(float y) {
		this.y = y;
		this.getPrimaryWeapon().setY(this.getMidPlayerY());
	}

	public Image getImage() {
		if (Math.abs(angle) < 90.0f)
			return rightImage;
		return leftImage;
	}
	
	public Image getHeartImage(){
		return heartImage;
	}
	
	public int getHitpoints(){
		return hitpoints;
	}
	
	public int setHitpoints(int hitpoints){
		return this.hitpoints=hitpoints;
	}
	
	public Weapon getPrimaryWeapon() {
		return primaryWeapon;
	}
	
	public void turnToPoint( float angle ) {
		if(this.getPrimaryWeapon().getImage() == null)
			this.getPrimaryWeapon().setImage(this.getPrimaryWeapon().getBaseImage());
		if(Math.abs(angle) > 90.00000f) {
			setWeaponDir(1);
			this.getPrimaryWeapon().setImage(rotateImage(this.getPrimaryWeapon().getBaseImage(),angle));
		} else {
			setWeaponDir(0);
			this.getPrimaryWeapon().setImage(rotateImage(this.getPrimaryWeapon().getBaseImage(),angle));
		}
	}
	
	public void setWeaponDir(int value) {
		if(this.getPrimaryWeapon().getWeaponDirection() != value) {
			this.getPrimaryWeapon().setBaseImage(verticalflip(this.getPrimaryWeapon().getBaseImage()));
			this.getPrimaryWeapon().setWeaponDirection(value);
		}
	}
	
	private static BufferedImage verticalflip(BufferedImage img) {   
		int w = img.getWidth();   
		int h = img.getHeight();   
		BufferedImage dimg = new BufferedImage(w, h, img.getColorModel().getTransparency());   
		Graphics2D g = dimg.createGraphics();   
		g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);   
		g.dispose();  
		return dimg;
	}  

	public static BufferedImage rotateImage(BufferedImage src, float degrees) {
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