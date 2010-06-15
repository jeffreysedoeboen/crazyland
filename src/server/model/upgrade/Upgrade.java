package server.model.upgrade;

import java.security.Timestamp;
import java.util.Random;

import server.model.Map;

public class Upgrade {
	private int x,y;
	protected int actionValue;
	private boolean isUsed = false;
	private Timestamp timeStamp;
	
	public Upgrade(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getActionValue() {
		return actionValue;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isUsed() {
		return isUsed;
	}
	
	public void setIsUsed(boolean value) {
		this.isUsed = value;
	}
	
	public Timestamp getTimestamp() {
		return timeStamp;
	}
	
	public void setTimestamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
}
