package client.view;

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

import client.connection.Receiver;
import client.model.Bullet;
import client.model.Player;
import client.model.Tile;

@SuppressWarnings("serial")
public class WorldView extends JPanel {

	private Receiver receiver;
	private int offsetX = 0;
	private int offsetY = 0;
	
	public WorldView(Receiver receiver) {
		this.receiver = receiver;
	}
	
	public void paintComponent(Graphics g){

		super.paintComponent(g);
		
		if(receiver.getMap() != null && receiver.getPlayer() != null){
			Tile[][] tiles = receiver.getMap().getTiles();
			
			//nieuwe shit
			offsetX = this.getWidth() / 2 - Math.round(receiver.getPlayer().getX()) - 32;
			int mapWidth = tilesToPixels(receiver.getMap().getWidth());
			offsetX = Math.min(offsetX, 0);
			offsetX = Math.max(offsetX, this.getWidth() - mapWidth);
			
			offsetY = this.getHeight() / 2 - Math.round(receiver.getPlayer().getY()) - 32;
			int mapHeight = tilesToPixels(receiver.getMap().getHeight());
			offsetY = Math.min(offsetY, 0);
			offsetY = Math.max(offsetY, this.getHeight() - mapHeight);
			
			int firstTileX = pixelsToTiles(-offsetX);
			int lastTileX = firstTileX + pixelsToTiles(this.getWidth()) + 1;
			int firstTileY = pixelsToTiles(-offsetY);
			int lastTileY = firstTileY + pixelsToTiles(this.getHeight()) + 1;
			
			for (int y=firstTileY; y <= lastTileY; y++) {
				if (y < pixelsToTiles(mapHeight)){
				    for (int x=firstTileX; x <= lastTileX; x++) {
				    	if(x < pixelsToTiles(mapWidth)){
				        	g.drawImage(tiles[x][y].getImage(),tilesToPixels(x) + offsetX, tilesToPixels(y) + offsetY, null);
				        }
				    }
				}
			}
	
			
			float playerX = receiver.getPlayer().getX();
			float playerY = receiver.getPlayer().getY();
			
			g.drawImage(receiver.getPlayer().getImage(),Math.round(playerX + offsetX),Math.round(playerY + offsetY),null);
			
			
			for(Player p : receiver.getRemotePlayers()){
				g.drawImage(p.getImage(),Math.round(p.getX() + offsetX),Math.round(p.getY() + offsetY),null);
			}
			
			ArrayList<Bullet> bullets = receiver.getBullets();
			for(Bullet b : bullets) {
				if(b != null) {
					g.drawImage(b.getBulletImage(),(int) b.getX() + offsetX,(int) b.getY() + offsetY, null);
				}
				
			}
			
			//TODO meerder spelers
//			Player player1 = receiver.getPlayer();
//			if(player1.getWeapon() != null) {
//				int midPlayerX = (int) (player1.getX() + ( player1.getImage().getWidth(null) / 2));
//				int midPlayerY = (int) (player1.getY() + ( player1.getImage().getHeight(null) / 2));
//				
//				g.drawImage(player1.getWeapon().getImage(), midPlayerX + offsetX, midPlayerY + offsetY, null);
//			}
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

	public int getOffsetX() {
		return this.offsetX;
	}
	
	public int getOffsetY() {
		return this.offsetY;
	}
}
