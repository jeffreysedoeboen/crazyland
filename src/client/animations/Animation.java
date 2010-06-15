package client.animations;

import java.awt.image.BufferedImage;

public interface Animation {

	public BufferedImage getImage();
	
	public int getX();
	
	public int getY();
	
	public void decreaseTime();
	
	public int getTimeVisible();
}
