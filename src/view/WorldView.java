package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

import javax.swing.JPanel;

import model.GameServer;
import model.Player;
import model.bullet.Bullet;
import model.tile.Tile;

public class WorldView extends JPanel {

	private GameServer server;
	
	static int offsetX, offsetY;
	
	public WorldView(GameServer server) {
		this.server = server;
	}
	
	public static int getOffsetX() {
		return offsetX;
	}

	public static int getOffsetY() {
		return offsetY;
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
		int offsetX = this.getWidth() / 2 - Math.round(server.getPlayerX()) - 32;
		int mapWidth = tilesToPixels(server.getWorld().getMap().getWidth());
		offsetX = Math.min(offsetX, 0);
		offsetX = Math.max(offsetX, this.getWidth() - mapWidth);
		
		int offsetY = this.getHeight() / 2 - Math.round(server.getPlayerY()) - 32;
		int mapHeight = tilesToPixels(server.getWorld().getMap().getHeight());
		offsetY = Math.min(offsetY, 0);
		offsetY = Math.max(offsetY, this.getHeight() - mapHeight);
		
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		
		int firstTileX = pixelsToTiles(-offsetX);
		int lastTileX = firstTileX + pixelsToTiles(this.getWidth()) + 1;
		int firstTileY = pixelsToTiles(-offsetY);
		int lastTileY = firstTileY + pixelsToTiles(this.getHeight()) + 1;
		
		for (int y=firstTileY; y <= lastTileY; y++) {
		    if( y < pixelsToTiles(mapHeight)) {
				for (int x=firstTileX; x <= lastTileX; x++) {
			    	if(x < pixelsToTiles(mapWidth)){
			        	g.drawImage(tiles[x][y].getImage(),tilesToPixels(x) + offsetX, tilesToPixels(y) + offsetY, null);
			        }
			    }
		    }
		}

		
		float playerX = server.getPlayerX();
		float playerY = server.getPlayerY();

		g.drawImage(server.getPlayerImage(),Math.round(playerX + offsetX),Math.round(playerY + offsetY),null);
		
		ArrayList<Bullet> bullets = server.getBullets();
		for(Bullet b : bullets) {
			if(b != null) {
				g.drawImage(b.getBulletImage(),(int) b.getX() + offsetX,(int) b.getY() + offsetY, null);
			}
			
		}
		
		//TODO meerder spelers
		Player player1 = server.getPlayer();
		if(player1.getWeapon() != null) {
			//System.out.println("x: " + player1.getWeapon().getX() + ", y: " + player1.getWeapon().getY());
			g.drawImage(player1.getWeapon().getImage(), player1.getWeapon().getX() + offsetX, player1.getWeapon().getY() + offsetY, null);
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
	
	public static BufferedImage rotateImage(BufferedImage src, float degrees) {
        AffineTransform affineTransform = AffineTransform.getRotateInstance(
                Math.toRadians(degrees),
                src.getWidth() / 2,
                src.getHeight() / 2);
        BufferedImage rotatedImage = new BufferedImage(src.getWidth(), src
                .getHeight(), src.getType());
        Graphics2D g = (Graphics2D) rotatedImage.getGraphics();
        g.setTransform(affineTransform);
        g.drawImage(src, 0, 0, null);
        return rotatedImage;
    }
	
	
}
