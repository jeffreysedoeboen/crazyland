package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.GameServer;
import model.Map;
import model.World;
import model.tile.Tile;

public class WorldView extends JPanel {

	private GameServer server;
	
	public WorldView(GameServer server) {
		this.server = server;
	}
	
	public void paintComponent(Graphics g){
		//TODO tiles tekenen kan nog verbeterd worden
		
		super.paintComponent(g);
		for(Tile[] rows : server.getWorld().getMap().getTiles()) {
			for(Tile tile : rows) {
				g.drawImage(tile.getImage(), tile.getX()*32, tile.getY()*32, null);
			}
		}
		
		Point s = server.getPlayerPosition();

		g.drawImage(server.getPlayerImage(),((int)s.getX())*32,((int)s.getY())*32,null);
		
		System.out.println("VERF!");
	}
	
}
