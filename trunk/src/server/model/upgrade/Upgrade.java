package server.model.upgrade;

import java.sql.Time;
import java.util.Date;

public class Upgrade {
	private int x,y;
	protected int actionValue;
	private boolean isUsed = false;
	private long timeStamp;
	
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
		this.timeStamp = System.currentTimeMillis() / 1000;
		this.isUsed = value;
	}
	
	public long getTimestamp() {
		return timeStamp;
	}
	
}
