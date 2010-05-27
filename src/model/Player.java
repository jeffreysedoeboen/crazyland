package model;

import java.awt.Point;
import java.util.ArrayList;

import model.weapon.Pistol;
import model.weapon.Weapon;

public class Player {

	protected String name;
	protected Point position;
	protected ArrayList<Weapon> weapons;
	
	public Player( String name, Point position ) {
		this.weapons  = new ArrayList<Weapon>();
		this.name     = name;
		this.position = position;
		
		//standaart pistool
		weapons.add(new Pistol());
	}
	
	public void shoot() {
		if(weapons.size() > 0) {
			weapons.get(0).shoot();
		}
	}
	
}