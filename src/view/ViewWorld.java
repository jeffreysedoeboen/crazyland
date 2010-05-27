package view;

import java.util.Observable;
import java.util.Observer;

import model.GameServer;
import model.Map;
import model.World;

public class ViewWorld implements Observer {

	private GameServer server;
	
	public ViewWorld(World world){
		world.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
		
		
		
		
		//tikmer wat is de map, map tekenen, waar staan alle spelers, teken alle spelers.
	}
}
