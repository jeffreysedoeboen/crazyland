package client.model.weapon;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Pistol extends Weapon {

	public Pistol(float x, float y) {	
		super(x,y);
		try {
			this.baseImage = ImageIO.read(new File("../themes/tee/weapon/gun.png"));
		} catch (IOException e) {
			System.err.println("Error while reading image for weapon");
			e.printStackTrace();
		}
	}
}
