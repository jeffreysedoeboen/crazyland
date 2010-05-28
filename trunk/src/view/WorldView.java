package view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Map;
import model.World;
import model.tile.Tile;

public class WorldView extends JPanel {

	//private GameServer server;
	private World world; 
	private Map map;
	
	public WorldView(World world) {
		this.world = world;
		map = world.getMap();
	}
	
	public void paintComponent(Graphics g){
		//TODO tiles tekenen kan nog verbeterd worden
		
		super.paintComponent(g);
		for(Tile[] rows : map.getTiles()) {
			for(Tile tile : rows) {
				g.drawImage(tile.getImage(), tile.getX()*64, tile.getY()*64, null);
			}
		}
	}
}
