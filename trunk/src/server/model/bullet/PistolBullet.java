package server.model.bullet;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PistolBullet extends Bullet {
	
	private Rectangle2D shape;
	
	public PistolBullet(int inden) {	
		super(inden);
		try {
			bulletImage = ImageIO.read(new File("themes/tee/weapon/bullet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public Shape getShape(){
		shape = new Rectangle2D.Double(x,y+5,20,9);
		AffineTransform tx = new AffineTransform();
		tx.rotate(Math.PI - direction, x +5,y+5);
		Rectangle2D shape = (Rectangle2D) tx.createTransformedShape(this.shape).getBounds2D();

		return shape;
	}
	
}
