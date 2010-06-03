package server.model;

import server.model.tile.Tile;

public class Map {
	private Tile[][] tiles;
	
	public Map(){
		setTiles(Mapfactory.getMap("crazyland2"));
	}
	
	public int getWidth() {
		return tiles.length;
	}
	
	public int getHeight() {
		return tiles[0].length;
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}
}
