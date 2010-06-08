package server.model.bullet;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import server.model.Player;
import server.model.WorldObject;
import server.model.tile.Tile;

public class PistolBullet extends Bullet {
	
	public PistolBullet(int inden) {	
		super(inden);
//		try {
//			bulletImage = ImageIO.read(new File("themes/tee/weapon/bullet.png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	// check if er is een collision met distance methode
	public WorldObject hasCollision( ArrayList<WorldObject> objects ) {
		for(WorldObject object : objects) {
			
			// get middelpunt
//			double objectX = 0, objectY = 0;
			
//			if( object instanceof Tile ) {
//				objectX = (double) (object.getX()*32+16);
//				objectY = (double) (object.getY()*32+16);
//			} else if( object instanceof Player ) {
//				objectX = (double) (object.getX()+16);
//				objectY = (double) (object.getY()+15);
//			}
//			
//			double bulletX = (double) (this.getX()+10);
//			double bulletY = (double) (this.getY()+10);
			
			//double distance = 10; //TODO: bereken distance precies afhankelijk van de richting
			
//			double[] intersections = Geometry.findLinePolygonIntersections(getShapeX(), getShapeY(), bulletX, bulletY, objectX, objectY);
//			boolean intersect = Geometry.isPointInsidePolygon(object.getShapeX(), object.getShapeY(), intersections[0], intersections[1]);
			
//			boolean intersect = Geometry.isPolygonIntersectingRectangle( object.getShapeX(), object.getShapeY(), (int)getX(), (int)getY(), (int)getX()+20, (int)getY()+20);
			
			boolean intersect = false;
			
			if( object instanceof Tile ) {
				intersect = object.getShape().intersects( (Rectangle2D.Double) this.getShape() );
			} else if ( object instanceof Player ) {
				intersect = object.getShape().intersects( (Rectangle2D.Double) this.getShape() );
			}
			
			if( intersect ) {
				System.out.println("Collision");
				return object;
			}
		}
		return null;
	}
	
//	public double[] getShapeX() {
//		
//		double[] shape = Geometry.createRectangle(x, y, 20.0, 20.0);
//		double[] retVal = new double[shape.length];
//		
//		int j = 0;
//		for( int i = 0; i < shape.length; i++ ) {
//			if( i%2 == 0 ) {
//				retVal[j] = shape[i];
//				j++;
//			}
//		}
//		return retVal;
//	}
//	
//	public double[] getShapeY() {
//		
//		double[] shape = Geometry.createRectangle(x, y, 20.0, 20.0);
//		double[] retVal = new double[shape.length];
//		
//		int j = 0;
//		for( int i = 0; i < shape.length; i++ ) {
//			if( i%2 != 0 ) {
//				retVal[j] = shape[i];
//				j++;
//			}
//		}
//		return retVal;
//	}
	
	public Shape getShape() {
		//TODO: met richting van kogel rekening houden
		return new Rectangle2D.Double(x, y, 20, 20);
	}
}
