package client.animations;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.imageio.ImageIO;

public class Poefje implements Animation {
	private int x, y;
	private BufferedImage image;
	private int timeVisible = 15;

	public Poefje(int x, int y) {
		this.x = x;
		this.y = y;
		try {
			image = ImageIO.read(new File("themes/tee/other/poefje.png"));
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
