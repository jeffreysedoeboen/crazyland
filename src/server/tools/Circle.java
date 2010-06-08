package server.tools;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Circle extends Ellipse2D.Float {

	private int radius;
	
	public Circle(int radius, int x, int y){
		super(x,y,radius*2,radius*2);
		this.radius = radius;
	}
	
	public int getRadius(){
		return this.radius;
	}
	
	//Checks if the Circle has an intersection with the other Circle
	public boolean intersects(Circle c){
		
		if(Point2D.distance(this.getCenterX(),this.getCenterY(), c.getCenterX(), c.getCenterY()) <= (this.radius+c.getRadius())){
			return true;
		}
		
		return false;
	}
}
