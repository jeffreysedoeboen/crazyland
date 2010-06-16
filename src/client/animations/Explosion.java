package client.animations;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Explosion implements Animation{
	private int x, y;
	private BufferedImage image;
	private int timeVisible = 50;

	public Explosion(int x, int y) {
		this.x = x - 32;
		this.y = y - 32;
		try {
			image = ImageIO.read(new File("themes/tee/other/explosion.png"));
		} catch (IOException e) {
			System.err.println("Fout in Explosie inladen plaatje");
			e.printStackTrace();
		}
	}

	public BufferedImage getImage() {
		return image;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}
	
	public void decreaseTime() {
		timeVisible--;
	}

	public int getTimeVisible() {
		return timeVisible;
	}

}
