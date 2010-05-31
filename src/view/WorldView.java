package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;

import javax.swing.JPanel;

import model.GameServer;
import model.Player;
import model.bullet.Bullet;
import model.tile.Tile;

public class WorldView extends JPanel {

	private GameServer server;
	
	public WorldView(GameServer server) {
		this.server = server;
	}
	
	public void paintComponent(Graphics g){
		//TODO tiles tekenen kan nog verbeterd worden

		super.paintComponent(g);
//		for(Tile[] rows : server.getWorld().getMap().getTiles()) {
//			for(Tile tile : rows) {
//				g.drawImage(tile.getImage(), tile.getX()*32, tile.getY()*32, null);
//			}
//		}
		
		Tile[][] tiles = server.getWorld().getMap().getTiles();
		
		//nieuwe shit
		int offsetX = this.getWidth() / 2 - Math.round(server.getPlayerX());
		int mapWidth = tilesToPixels(server.getWorld().getMap().getWidth());
		offsetX = Math.min(offsetX, 0);
		offsetX = Math.max(offsetX, this.getWidth() - mapWidth);
		
		int offsetY = this.getHeight() - tilesToPixels(server.getWorld().getMap().getHeight());
		int mapHeight = tilesToPixels(server.getWorld().getMap().getHeight());
		offsetY = Math.min(offsetY, 0);
		offsetY = Math.max(offsetY, this.getHeight() - mapHeight);
		
		int firstTileX = pixelsToTiles(-offsetX);
		int lastTileX = firstTileX + pixelsToTiles(this.getWidth()) + 1;
		int firstTileY = pixelsToTiles(-offsetY);
		int lastTileY = firstTileY + pixelsToTiles(this.getHeight()) + 1;
		
		for (int y=firstTileY; y < lastTileY; y++) {
		    for (int x=firstTileX; x < lastTileX; x++) {
		        g.drawImage(tiles[x][y].getImage(),tilesToPixels(x) + offsetX, tilesToPixels(y) + offsetY, null);
		    }
		}

		
		float playerX = server.getPlayerX();
		float playerY = server.getPlayerY();

		g.drawImage(server.getPlayerImage(),Math.round(playerX) + offsetX,Math.round(playerY) + offsetY,null);
		
		ArrayList<Bullet> bullets = server.getBullets();
		for(Bullet b : bullets) {
			if(b != null) {
				g.drawImage(server.getBulletImage(),(int) b.getPosition().getX(),(int) b.getPosition().getY(), null);
			}
		}
		
		//TODO meerder spelers
		Player player1 = server.getPlayer();
		if(player1.getWeapon() != null) {
			int midPlayerX = (int) (player1.getX() + ( player1.getImage().getWidth(null) / 2));
			int midPlayerY = (int) (player1.getY() + ( player1.getImage().getHeight(null) / 2));
			
			g.drawImage(player1.getWeapon().getImage(), midPlayerX + offsetX, midPlayerY + offsetY, null);
		}
		
	}
	
	public int pixelsToTiles(float pixels) {
		return pixelsToTiles(Math.round(pixels));
	}
	
	public int pixelsToTiles(int pixels) {
		return (int) Math.floor((float) pixels / 32);
	}
	
	public int tilesToPixels(int numTiles) {
		return numTiles * 32;
	}
}
