package view;

import java.util.Observable;
import java.util.Observer;

import model.World;

public class ViewWorld implements Observer {

	private World worldObject;
	
	public ViewWorld(World world){
		world.addObserver(this);
		worldObject = world;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
