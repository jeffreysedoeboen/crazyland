package client.animations;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GunFire implements Animation{
	private int x, y;
	private BufferedImage image;
	private int timeVisible = 5;
	
	public GunFire(int x, int y) {
		this.x = x;
		this.y = y;
		try {
			//TODO hoeft eigelijk niet elke keer opnieuw ingelezen te worden
			image = ImageIO.read(new File("../themes/tee/other/gunfire.png"));
		} catch (IOException e) {
			System.err.println("Fout in gunFire inladen plaatje");
			e.printStackTrace();
		}
	}

	@Override
	public BufferedImage getImage() {
		return image;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void decreaseTime() {
		timeVisible--;
	}

	@Override
	public int getTimeVisible() {
		return timeVisible;
	}

}
