package model;

import java.util.Observable;

import view.ViewWorld;

public class World extends Observable{

	public void run(){
		this.setChanged();
		this.notifyObservers();
	}
}
