package view;

import java.util.Observable;
import java.util.Observer;

import model.GameServer;
import model.Map;
import model.World;

public class WorldView implements Observer {

	//private GameServer server;
	private World world;
	private Map map;
	
	public WorldView(World world) {
		this.world = world;
		world.addObserver(this);
	}
	
	public void getMap() {
		map = world.getMap();
	}

	@Override
	public void update(Observable arg0, Object arg1) {

		//tikmer wat is de map, map tekenen, waar staan alle spelers, teken alle spelers.
	}
}
