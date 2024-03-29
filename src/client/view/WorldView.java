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

import javax.sound.midi.Sequence;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.imageio.ImageIO;

import client.animations.Animation;
import client.animations.GunFire;
import client.animations.Poefje;
import client.connection.MasterConnection;
import client.connection.Receiver;
import client.connection.Sender;
import client.controller.KeyboardController;
import client.controller.MouseController;
import client.model.Bullet;
import client.model.GameServer;
import client.model.MidiPlayer;
import client.model.Player;
import client.model.Tile; 
import client.model.upgrades.Upgrade;


@SuppressWarnings("serial")
public class WorldView extends JPanel {

	private Receiver receiver; 
	private int offsetX = 0;
	private int offsetY = 0;
	private BufferedImage bg = null;
	private int bgWidth, bgHeight;
	private boolean showHighscore;
	private String userName;
	private MasterConnection master;
	private Sender sender;
	private GameFrame gameframe;
	private int timeRemaining;
	private boolean showLeaveTime;
	private int leaveTime;
	
	private ActionListener gameTimerPerformer = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			timeRemaining = receiver.getTimeRemaining();
			if (--timeRemaining == 0) {
				showHighscore = true;
				Player player = receiver.getPlayer();
				master.sendStats(player.getKills(), player.getDeaths());
			}
			if(timeRemaining == -5) {
				timeRemaining = receiver.getTimeRemaining();
				showHighscore = false;
			}
		}
	};

	public boolean isShowHighscore() {
		return showHighscore;
	}
	
	public void setShowLeaveTime(boolean b) {
		this.showLeaveTime = b;
	}
	
	public void setLeaveTime(int i){
		this.leaveTime = i;
	}
	
	public void setShowHighscore(boolean showHighscore) {
		this.showHighscore = showHighscore;
	}

	public WorldView(GameServer server, String userName, GameFrame gameframe, MasterConnection master) {
		this.userName = userName;
		this.gameframe = gameframe;
		this.master = master;
		connectAndPrepare(server);
		setSize(600, 300);
		new Timer(20, taskPerformer).start();
		
		timeRemaining = receiver.getTimeRemaining();
		
		Timer gameTimer = new Timer(1000, gameTimerPerformer);
		gameTimer.start();
		
		MidiPlayer mp = new MidiPlayer();
		Sequence sequence = mp.getSequence("sound/zelda.mid");
		mp.play(sequence, true);
	}
	
	public void connectAndPrepare(GameServer server) {
		try {			
			bg = ImageIO.read(new File("themes/tee/background/background.jpg"));
			bgWidth = bg.getWidth();
			bgHeight = bg.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Socket s = null;
		InputStream inStream = null;
		OutputStream outStream = null;
		Scanner in = null;
		PrintWriter out = null;

		try {
			s = new Socket(server.getIp(), 1337);
			inStream = s.getInputStream();
			outStream = s.getOutputStream();
			in = new Scanner(inStream);
			out = new PrintWriter(outStream, true /* autoFlush */);
		} catch (IOException ioe) {
			System.out.println("\nwas not able to connect with gameserver... check connection!");
		}

		receiver = new Receiver(in);
		receiver.start();
		sender = new Sender(out);
		
		sender.login(userName);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("themes/tee/weapon/cursor.png");
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
			
			int offsetX = this.getWidth() / 2 - Math.round(receiver.getPlayer().getX()) - 16;
			int mapWidth = tilesToPixels(receiver.getMap().getWidth());
			offsetX = Math.min(offsetX, 0);
			offsetX = Math.max(offsetX, this.getWidth() - mapWidth);
			
			int offsetY = this.getHeight() / 2 - Math.round(receiver.getPlayer().getY()) - 16;
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
				g.drawImage(p.getPrimaryWeapon().getImage(), Math.round(p.getPrimaryWeapon().getX() + offsetX),Math.round(p.getPrimaryWeapon().getY() + offsetY),null);
			}
			
			for(Upgrade u : receiver.getUpgrades()) {	
				g.drawImage(u.getImage(), Math.round(u.getX()+offsetX),Math.round(u.getY()+offsetY),null);
			}
			
			ArrayList<Bullet> bullets = receiver.getBullets();
			for(Bullet b : bullets) {
				if(b != null) {
					b.move();
					BufferedImage bulletImage = b.getBulletImage();
					bulletImage = rotateImage(bulletImage, (float) (b.getDirection() * (180 / Math.PI) + 180),false);
					g.drawImage(bulletImage,(int) b.getX() + offsetX,(int) b.getY() + offsetY, null);
				}
			}
			
			drawAnimations(g);
			
			if(isShowHighscore()) {
				drawHighscore(g);
			}
			if(showLeaveTime) {
				drawLeaveTime(g);
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
			if(!(seconds < 10)) {
				time = minutes + ":" + seconds;
			} else {
				time = minutes + ":0" + seconds;
			}
		} else {
			time = "0:00";
		}
		g.drawString(time, (getWidth()/2), 20);
	}
	
	private void drawLeaveTime(Graphics g) {
		Color color = new Color(1, 0, 0, 0.75f); //Red 
		g.setColor(color);

		int xPos   = (getWidth()/4);
		int yPos   = (getHeight()/4);
		g.fillRect(xPos, yPos, 350, 100);
		g.setColor(Color.CYAN);

		g.setFont(new Font("sansserif", Font.PLAIN, 18));
		int y = yPos + 30;
		g.drawString("Weet u zeker dat u wilt stoppen?     " + leaveTime, xPos + 20 , y);
		g.setFont(new Font("sansserif", Font.PLAIN, 13));
		g.drawString("Druk escape om door te gaan en enter om te stoppen", xPos + 20, y + 30);
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
		for(Player p : receiver.getPlayerStats()){
			y += 20;
			String s = String.format("%-20s Kills: %3d Deaths: %3d", p.getName(), p.getKills(), p.getDeaths());
			g.drawString(s, xPos + 20, y);
		}		
	}
	
	public int pixelsToTiles(float pixels) {
		return pixelsToTiles(Math.round(pixels));
	}
	
	public int pixelsToTiles(int pixels) {
		return (int) Math.floor((float) pixels / 16);
	}
	
	public int tilesToPixels(int numTiles) {
		return numTiles * 16;
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

		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent arg0) {
			WorldView.this.repaint();
			
			ArrayList<Animation> animations = (ArrayList<Animation>) receiver.getAnimtions();
			
			for(Bullet b : (ArrayList<Bullet>) receiver.getBullets().clone()) {
				if(b != null) {
					animations.add(new Poefje((int) b.getX(), (int) b.getY()));
				}
			}
			
			ArrayList<Animation> animationsClone = (ArrayList<Animation>) animations.clone();
			
			for(Animation a : animationsClone) {
				a.decreaseTime();
				if(a.getTimeVisible() < 1) {
					receiver.removeAnimation(a);
				}
			}
		}

	};
	
	public void logoff() {
		sender.removePlayer(); 
	}
	
	@SuppressWarnings("unchecked")
	private void drawAnimations(Graphics g) {
		
		for(Animation a : (ArrayList<Animation>) receiver.getAnimtions().clone()) {
			if(a instanceof GunFire) {
				Player p = receiver.getPlayer();
				Image image = rotateImage(a.getImage(), p.getAngle(), false);
				g.drawImage(image, (int)((p.getPrimaryWeapon().getX() + offsetX) - 12*Math.cos(p.getAngle())), (int)((p.getPrimaryWeapon().getY() + offsetY) - 12*Math.cos(p.getAngle())), null);
			} else {
				g.drawImage(a.getImage(), a.getX() + offsetX, a.getY() + offsetY, null);
			}
		}
	}
}
