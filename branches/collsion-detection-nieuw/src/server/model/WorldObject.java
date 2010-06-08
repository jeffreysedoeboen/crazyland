package server.model;

import java.awt.Shape;

public interface WorldObject {

	public float getX();
	public float getY();
//	public int[] getShapeX();
//	public int[] getShapeY();
	public Shape getShape();
}
