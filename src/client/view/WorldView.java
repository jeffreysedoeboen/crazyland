package client.view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import client.connection.Receiver;
import client.model.Bullet;
import client.model.Player;
import client.model.Tile;

@SuppressWarnings("serial")
public class WorldView extends JPanel {

	private Receiver receiver;
	private int offsetX = 0;
	private int offsetY = 0;
	
	private BufferedImage bg = null;
	private int bgWidth, bgHeight;
	private boolean showHighscore;
	
	public boolean isShowHighscore() {
		return showHighscore;
	}

	public WorldView(Receiver receiver) {        
		this.receiver = receiver;
		try {
			bg = ImageIO.read(new File("../themes/tee/background/background.jpg"));
			bgWidth = bg.getWidth();
			bgHeight = bg.getHeight();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        setSize(900, 300);
	}
	
	public void paintComponent(Graphics g){

		super.paintComponent(g);
		
		if(receiver.getMap() != null && receiver.getPlayer() != null){
			Tile[][] tiles = receiver.getMap().getTiles();
			
			//nieuwe shit
			int offsetX = this.getWidth() / 2 - Math.round(receiver.getPlayer().getX()) - 32;
			int mapWidth = tilesToPixels(receiver.getMap().getWidth());
			offsetX = Math.min(offsetX, 0);
			offsetX = Math.max(offsetX, this.getWidth() - mapWidth);
			
			int offsetY = this.getHeight() / 2 - Math.round(receiver.getPlayer().getY()) - 32;
			int mapHeight = tilesToPixels(receiver.getMap().getHeight());
			offsetY = Math.min(offsetY, 0);
			offsetY = Math.max(offsetY, this.getHeight() - mapHeight);
			
			this.offsetX = offsetX;
			this.offsetY = offsetY;
			
			Rectangle2D.Double playerRect = new Rectangle2D.Double(
					receiver.getPlayer().getX(), 
					receiver.getPlayer().getY(), 
					receiver.getPlayer().getImage().getWidth(null), 
					receiver.getPlayer().getImage().getHeight(null)
			);
			Area player = new Area(playerRect);
			

			for (int i = offsetX/3; i<=this.getWidth()-offsetX; i += bgWidth) {
				for (int j = offsetY/3; j<=this.getHeight()-offsetY; j += bgHeight) {	
					g.drawImage(bg, i, j, null);
				}
			}
			
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
			
			float weaponX = receiver.getPlayer().getPrimaryWeapon().getX();
			float weaponY = receiver.getPlayer().getPrimaryWeapon().getY();
			
		
			g.drawImage(receiver.getPlayer().getImage(),Math.round(playerX + offsetX),Math.round(playerY + offsetY),null);
			g.drawImage(receiver.getPlayer().getPrimaryWeapon().getImage(), Math.round(weaponX + offsetX),Math.round(weaponY + offsetY),null);
			g.drawString(receiver.getPlayer().getName(), Math.round(playerX + offsetX + 20), Math.round(playerY + offsetY + 5));
			
			
			for(int i = 0; i <= receiver.getPlayer().getHitpoints(); i++){
				g.drawImage(receiver.getPlayer().getHeartImage(),i*18,0,null);
			}
			
			for(Player p : receiver.getRemotePlayers()){
				g.drawImage(p.getImage(),Math.round(p.getX() + offsetX),Math.round(p.getY() + offsetY),null);
				g.drawImage(p.getPrimaryWeapon().getImage(), Math.round(p.getPrimaryWeapon().getX() + offsetX),Math.round(p.getPrimaryWeapon().getY() + offsetY),null);
				g.drawString(p.getName(), Math.round(p.getX() + offsetX + 20), Math.round(p.getY() + offsetY + 5));
			}			
			
			ArrayList<Bullet> bullets = receiver.getBullets();
			for(Bullet b : bullets) {
				if(b != null) {
					b.move();
					g.drawImage(b.getBulletImage(),(int) b.getX() + offsetX,(int) b.getY() + offsetY, null);
				}
				
			}
			
			if(isShowHighscore()) {
				Color color = new Color(1, 0, 0, 0.75f); //Red 
				g.setColor(color);
				
				int xPos   = (getWidth()/4);
				int yPos   = (getHeight()/4);
				g.fillRect(xPos, yPos, 350, (17 * 20) + 30);
				g.setColor(Color.CYAN);
			
				g.setFont(new Font("sansserif", Font.BOLD, 32));
				int y = yPos + 30;
				g.drawString("Score:", xPos + 20 , y);
				g.setFont(new Font("sansserif", Font.PLAIN, 16));
				for(Player p : receiver.getRemotePlayers()){
					y += 20;
					String s = String.format("%-20s Kills: %3d Deaths: %3d", p.getName(), 100, 50);//TODO moet nog de kills van de player worden
					g.drawString(s, xPos + 20, y);
				}
				
				y += 20;
				String s = String.format("%-20s Kills: %3d Deaths: %3d", receiver.getPlayer().getName(), 100, 50);//TODO moet nog de kills van de player worden
				g.drawString(s, xPos + 20, y);
				
				
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
	
	 public void setShowHighscore(boolean showHighscore) {
		this.showHighscore = showHighscore;
	 }
}
