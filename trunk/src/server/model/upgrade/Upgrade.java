package server.model.upgrade;

import java.util.Random;

import server.model.Map;

public class Upgrade {
	private float x,y;
	private int width, height;
	protected int actionValue;
	private int respawnCountdown = 0;
	private boolean isUsed = false;
	private Map map;
	
	public Upgrade(Map map) {
		this.map = map;
		updatePosition();
	}
	
	public int getActionValue() {
		return actionValue;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public boolean isUsed() {
		return isUsed;
	}
	
	public void reset() {
		respawnCountdown = 1000;
	}
	
	public int getRespawnCountdown() {
		return respawnCountdown;
	}
	
	public void tick() {
		respawnCountdown -= 1;
	}
	
	public void updatePosition() {
		Random rnd = new Random();
		x = rnd.nextFloat() * (map.getWidth() * 32);
		y = rnd.nextFloat() * (map.getHeight() * 32);
		if(map.getTiles()[(int)(x / 32)][(int)(y /32)].isSolid()) {
			updatePosition();
		}
	}
}
