package client.view;

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
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
			
			g.drawImage(receiver.getPlayer().getImage(),Math.round(playerX + offsetX),Math.round(playerY + offsetY),null);
			
			for(int i = 0; i <= receiver.getPlayer().getHitpoints(); i++){
			g.drawImage(receiver.getPlayer().getHeartImage(),i*18,0,null);
			}
			
			for(Player p : receiver.getRemotePlayers()){
				g.drawImage(p.getImage(),Math.round(p.getX() + offsetX),Math.round(p.getY() + offsetY),null);
			}
			
			ArrayList<Bullet> bullets = receiver.getBullets();
			for(Bullet b : bullets) {
				if(b != null) {
					b.move();
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
	
	public static BufferedImage verticalflip(BufferedImage img) {   
        int w = img.getWidth();   
        int h = img.getHeight();   
        BufferedImage dimg = new BufferedImage(w, h, img.getColorModel().getTransparency());   
        Graphics2D g = dimg.createGraphics();   
        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);   
        g.dispose();  
        return dimg;
    }  
	
	public static BufferedImage rotateImage(BufferedImage src, float degrees, boolean isWeapon) {
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
