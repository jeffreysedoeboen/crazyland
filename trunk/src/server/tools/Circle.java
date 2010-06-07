package server.tools;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Circle extends Ellipse2D.Float {

	private int radius;
	
	public Circle(int radius, int x, int y, int w, int h){
		super(x,y,w,h);
		if(w != h){
			System.err.println("NIET GOED Height WORD Width IN CIRCLE");
		}
		this.height = this.width;
		this.radius = radius;
	}
	
	public int getRadius(){
		return this.radius;
	}
	
	public boolean intersects(Circle c){
		
		if(Point2D.distance(this.getCenterX(),this.getCenterY(), c.getCenterX(), c.getCenterY()) <= (this.radius+c.getRadius())){
			return true;
		}
		
		return false;
	}
	
	public void increaseX(){
		this.x++;
	}
}
