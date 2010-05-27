package model;

import java.awt.Point;
import java.util.ArrayList;

import model.weapon.Pistol;
import model.weapon.Weapon;

public class Player {

	private String name;
	private Point position;
	private Weapon weapon;
	private int hitpoints;
	
	public Player( String name, Point position ) {
		this.name     = name;
		this.position = position;
		
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