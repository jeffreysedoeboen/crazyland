package model;

import java.util.Observable;

public class World extends Observable{

	Map map = new Map();
	
	public Map getMap() {
		return map;
	}
	
	public void run(){
		this.setChanged();
		this.notifyObservers();
	}
}
