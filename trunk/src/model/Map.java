package model;

import model.tile.Tile;

public class Map {
	private Tile[][] tiles;
	
	public Map(){
		setTiles(Mapfactory.getMap("map1"));
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}
	
	public void tekenMap() {
		for(Tile[] row : tiles) {
			for(Tile tile : row) {
				System.out.println("x: " + tile.getX() + ", y: " + tile.getY());
			}
		}
	}
}
