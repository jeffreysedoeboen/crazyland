package client.model.upgrades;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ExtraLife extends Upgrade {
	
	public ExtraLife(int x, int y) {
		super(x * 16, y * 16);
		try {
			image = ImageIO.read(new File("../themes/tee/other/heart.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
