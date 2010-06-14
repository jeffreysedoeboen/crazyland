package client.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.imageio.ImageIO;
import client.connection.Receiver;
import client.connection.Sender;
import client.controller.KeyboardController;
import client.controller.MouseController;
import client.model.Bullet;
import client.model.GameServer;
import client.model.Player;
import client.model.Tile; 


@SuppressWarnings("serial")
public class WorldView extends JPanel {

	private Receiver receiver; 
	private int offsetX = 0;
	private int offsetY = 0;
	private BufferedImage bg = null;
	private int bgWidth, bgHeight, timeRemaining;
	private boolean showHighscore;
	private String userName;
	private Sender sender;
	private GameFrame gameframe;
	private ActionListener gameTimerPerformer = new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			if (--timeRemaining == 0) {
				showHighscore = true;

				
				
			}
			if(timeRemaining == -5) {
//				timeRemaining = receiver.getTimeRemaining();//TODO
				timeRemaining = 570;
				showHighscore = false;
			}
		}

	};

	public boolean isShowHighscore() {
		return showHighscore;
	}

	public void setShowHighscore(boolean showHighscore) {
		this.showHighscore = showHighscore;
	}

	public WorldView(GameServer server, String userName, GameFrame gameframe) {
		this.userName = userName;
		this.gameframe = gameframe;
		connectAndPrepare(server);
		setSize(900, 300);
		new Timer(20, taskPerformer).start();
		
//		timeRemaining = receiver.getTimeRemaining();//TODO
		timeRemaining = 600;
		
		Timer gameTimer = new Timer(1000, gameTimerPerformer);
		gameTimer.start();
	}

	public void connectAndPrepare(GameServer server) {
		try {			
			bg = ImageIO.read(new File("../themes/tee/background/background.jpg"));
			bgWidth = bg.getWidth();
			bgHeight = bg.getHeight();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Socket s = null;
		InputStream inStream = null;
		OutputStream outStream = null;
		Scanner in = null;
		PrintWriter out = null;

		// check if we can talk with the server
		System.out
		.println("\ntry to connect with gameserver...");

		try {
			s = new Socket(server.getIp(), 1337);
			inStream = s.getInputStream();
			outStream = s.getOutputStream();
			in = new Scanner(inStream);
			out = new PrintWriter(outStream, true /* autoFlush */);
		} catch (IOException ioe) {
			System.out
			.println("\nwas not able to connect with gameserver... check connection!");
		}
		System.out.println("has connection with gameserver");

		receiver = new Receiver(in);
		receiver.start();
		sender = new Sender(out);

		sender.login(userName);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("../themes/tee/weapon/cursor.png");
		Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "cursor");

		this.setCursor(c);

		setSize(400, 320);
		setVisible(true);

		KeyboardController keycontroller = new KeyboardController(sender, this, gameframe);
		this.addKeyListener(keycontroller);
		this.setFocusable(true);

		MouseController mouseController = new MouseController(sender, this);
		this.addMouseListener(mouseController);
		this.addMouseMotionListener(mouseController);

		this.addMouseWheelListener(mouseController);

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
			
			if(receiver.getPlayer().getHitpoints() > 0) {
				g.drawImage(receiver.getPlayer().getImage(),Math.round(playerX + offsetX),Math.round(playerY + offsetY),null);
				g.drawString(receiver.getPlayer().getName(), Math.round(playerX + offsetX + 20), Math.round(playerY + offsetY + 5));
				g.drawImage(receiver.getPlayer().getPrimaryWeapon().getImage(), Math.round(weaponX + offsetX),Math.round(weaponY + offsetY),null);
			}

			for(int i = 0; i <= receiver.getPlayer().getHitpoints() -1; i++){
				g.drawImage(receiver.getPlayer().getHeartImage(),i*18,0,null);
			}

			for(Player p : receiver.getRemotePlayers()){
				g.drawImage(p.getImage(),Math.round(p.getX() + offsetX),Math.round(p.getY() + offsetY),null);
				g.drawString(p.getName(), Math.round(p.getX() + offsetX + 20), Math.round(p.getY() + offsetY + 5));
			}

			ArrayList<Bullet> bullets = receiver.getBullets();
			for(Bullet b : bullets) {
				if(b != null) {
					b.move();
					BufferedImage bulletImage = (BufferedImage) b.getBulletImage();
					bulletImage = rotateImage(bulletImage, (float) (b.getDirection() * (180 / Math.PI) + 180),false);
					g.drawImage(bulletImage,(int) b.getX() + offsetX,(int) b.getY() + offsetY, null);
				}
			}
			
			if(isShowHighscore()) {
				drawHighscore(g);
			}
			
			drawTime(g);
		}	
	}

	private void drawTime(Graphics g) {
		String time = null;
		g.setColor(Color.PINK);
		g.setFont(new Font("Arial", Font.BOLD, 19));
		if(timeRemaining > 0 ){
			int minutes = (int) (timeRemaining/60);
			int seconds = (int) (timeRemaining%60);
			time = minutes + ":" + seconds;
		} else {
			time = "0:0";
		}
		g.drawString(time, (getWidth()/2), 20);
	}

	private void drawHighscore(Graphics g) {
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
//		for(Player p : receiver.getPlayerStats()){//TODO
//			y += 20;
//			String s = String.format("%-20s Kills: %3d Deaths: %3d", p.getName(), p.getKills(), p.getDeaths());
//			g.drawString(s, xPos + 20, y);
//		}		
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

	ActionListener taskPerformer = new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			WorldView.this.repaint();
		}

	};

	public void logoff() {
				sender.removePlayer(); 
	};
}
