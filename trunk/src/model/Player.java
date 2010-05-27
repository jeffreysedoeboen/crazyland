package model;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import model.weapon.Pistol;
import model.weapon.Weapon;

public class Player {

	private String name;
	private Point position;
	private Weapon weapon;
	private int hitpoints = 10;
	private static Image playerImage;
	
	public Player( String name, Point position ) {
		this.name     = name;
		this.position = position;

		ImageIcon playerImage2 = new ImageIcon("../themes/tee/character.png");
		playerImage = playerImage2.getImage();
		
		//standaart pistool
		setWeapon(new Pistol());
	}
	
	public void shoot() {
		if(weapon != null) {
			weapon.shoot();
		}
	}
	
	public Point getPosition(){
		return position;	
	}
	
	public String getName(){
		return name;
	}
	
	public int getHitpoints(){
		return hitpoints;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public Weapon getWeapon() {
		return weapon;
	}
}