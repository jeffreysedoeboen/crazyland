package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
		
		Tile[][] rows = server.getWorld().getMap().getTiles();
		
		for(int y = 0; y < 10; y++){
			for(int x = 0; x < 50; x++){
				g.drawImage(rows[x][y].getImage(),x*32, y*32, null);
			}
		}
		
		Point poistiePlayer1 = server.getPlayerPosition();

		g.drawImage(server.getPlayerImage(),((int)poistiePlayer1.getX()),((int)poistiePlayer1.getY()),null);
		
		//TODO meerdere bullets
		Bullet bullet = server.getBullet();
		if(bullet != null) {
			g.drawImage(server.getBulletImage(),(int) bullet.getPosition().getX(),(int) bullet.getPosition().getY(), null);
		}
		
		//TODO meerder spelers
		Player player1 = server.getPlayer();
		if(player1.getWeapon() != null) {
			int midPlayerX = (int) (player1.getPosition().getX() + ( player1.getImage().getWidth(null) / 2));
			int midPlayerY = (int) (player1.getPosition().getY() + ( player1.getImage().getHeight(null) / 2));
			
			g.drawImage(player1.getWeapon().getImage(), midPlayerX, midPlayerY, null);
		}
		
	}
	
}
