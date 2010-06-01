package model;

import model.tile.Tile;

public class Map {
	private Tile[][] tiles;
	private int width;
	private int height;
	
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
	
	public void tekenMap() {
		for(Tile[] row : tiles) {
			for(Tile tile : row) {
				System.out.println("x: " + tile.getX() + ", y: " + tile.getY());
			}
		}
	}
}
