package server.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import server.model.bullet.Bullet;
import server.model.tile.Tile;


public class World{

	private Map map;
	private GameServer server;
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private int bulletCounter = 1;

	public World( GameServer server ){
		map = new Map();
		this.server = server;
	}
	
	public void addPlayer(Player p){
		playerList.add(p);
	}

	public Map getMap() {
		return map;
	}
	
	public int getBulletCounter(){
		return this.bulletCounter;
	}

	public Bullet shoot(float x,float y, Player p) {
		float distanceFromPlayerX = p.getX()-x;
		float distanceFromPlayerY = p.getY()-y;
		Bullet b = p.shoot(this.bulletCounter);
		if(b != null){
			b.setBullet(x,y,p.getX(),p.getY()+6,Math.atan2(distanceFromPlayerY, distanceFromPlayerX));
			bullets.add(b);
			bulletCounter++;
			return b;
		}
		return null;
	}
	
//	public void moveWeapon(int mouseX, int mouseY) {
//		player.getWeapon().turnToPoint(mouseX, mouseY);
//		//player.getWeapon().setX(player.getMidPlayerX() + 5);
//		player.updateWeaponPosition();
//	}

	@SuppressWarnings("unchecked")
	public void move() {
		for(Player player : this.getPlayerList()){
			if(player != null){
				if(player.isMovingLeft() && canMoveLeft(player)){
					player.moveLeft(onGround(player));
				}else if(player.isMovingRight() && canMoveRight(player)){
					player.moveRight(onGround(player));
				}
				player.moveVertical();
				player.calcVerticalSpeed(onGround(player));
				if(collisionCeiling(player)){
					player.setVerticalSpeed(-0.5f);
				}
				
			}
		}
		ArrayList<Bullet> bulletsclone = (ArrayList<Bullet>) bullets.clone();
		for(Bullet b : bulletsclone){
			b.move();
			if (b.hasCollision( getNextItems(b) ) != null) {
				server.removeBullet(b);
				bullets.remove(b);
			}
		}

	}
	
	public ArrayList<WorldObject> getNextItems(Bullet bullet) {
		
		ArrayList<WorldObject> retList = new ArrayList<WorldObject>();

		// doorloop alle spelers
		for( Player player : playerList ) {
			if( player.getX()+32 > bullet.getX() && player.getX()-32 < bullet.getX() &&
					player.getY()+32 > bullet.getY() && player.getY()-32 < bullet.getY() ) {
				retList.add(player);
			}
		}
		
		// doorloop alle tiles
		for( int i = 0; i < map.getTiles().length; i++ ) {
			if( i*32+32 > bullet.getX() && i*32-32 < bullet.getX() ) {
				for( Tile tile : map.getTiles()[i] ) {
					if( tile.isSolid() && tile.getY()*32-32 < bullet.getY() && tile.getY()*32+32 > bullet.getY() ) {
						retList.add(tile);
					}
				}
			}
		}
		
		return retList;
	}

	public boolean[][] getPixelMap( BufferedImage image ){
		boolean[][] pmap = new boolean[32][32];
		for(int i = 0; i < 32; i++){
			for(int i2 = 0; i2 < 32; i2++){
				if(image.getRGB(i, i2) == 0){
					pmap[i2][i] = true;
				}else{
					pmap[i2][i] = false;
				}
			}
		}
		
		return pmap;
	}
	
	private boolean isCollision(Tile tileLeft, Tile tileRight, boolean ground, Player player) {
		
		if( tileLeft.isSolid() || tileRight.isSolid()) {
			
			int playerX  = (int) player.getX();
			int playerY  = (int) player.getY();
			int tilePosY = (int) Math.abs(playerY - tileLeft.getY()*32);
			
			if( ground ) {
				tilePosY = 32-tilePosY;
			}
			
			// create tile pixelmaps
			boolean[] pixelMapLeft  = getPixelMap(tileLeft.getImage())[tilePosY];
			boolean[] pixelMapRight = getPixelMap(tileRight.getImage())[tilePosY];
			
			// create de player pixelmap
//			try {
//				boolean[][] pixelMapPlayer = getPixelMap(ImageIO.read(new File("../themes/tee/pixelmaps/character.png")));
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
			// left map is voor de rechte kant
			float i = tileLeft.getX()*32;
			for( boolean pixel : pixelMapLeft ) {
				if( i >= playerX && i <= tileLeft.getX()*32+32 && pixel == false ) {
					return true;
				}
				i++;
			}
			
			// check de links kant (links lopenend)
			i = tileRight.getX()*32;
			playerX += 32;
			for( boolean pixel : pixelMapRight ) {
				if( i <= playerX && i <= tileRight.getX()*32+32 && pixel == false ) {
					return true;
				}
				i++;
			}
		}
		
		return false;
	}
	
	public boolean onGround(Player player) {
		
		Tile[][] tiles = map.getTiles();
		
		if( isCollision( tiles[(int) (player.getX()/32)] [(int) ((player.getY()+32)/32)],
				tiles[(int) ((player.getX()+32)/32)][(int) ((player.getY()+32)/32)], true,player)) {
			return true;
		}
		
		return false;
	}
	
	private boolean canMoveLeft(Player player) {
		
		Tile[][] tiles = map.getTiles();
		
		if(tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY())/32)].isSolid() || tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].isSolid()){
			return false;
		}
		return true;
	}
	
	private boolean canMoveRight(Player player) {
		Tile[][] tiles = map.getTiles();
		if(tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY())/32)].isSolid() || tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].isSolid()){
			return false;
		}
		return true;
	}

	private boolean collisionCeiling(Player player) {
		
		Tile[][] tiles = map.getTiles();
		
		if( isCollision(tiles[(int) (player.getX()/32)][(int) ((player.getY())/32)],
				tiles[(int) ((player.getX()+32)/32)][(int) ((player.getY())/32)], false ,player) ) {
			return true;
		}
		
		return false;
	}

	public void startMovingRight(Player p) {
		p.setMovingRight(true);
	}

	public void stopMovingRight(Player p) {
		p.setMovingRight(false);		
	}

	public void startMovingLeft(Player p) {
		p.setMovingLeft(true);		
	}

	public void stopMovingLeft(Player p) {
		p.setMovingLeft(false);		
	}

	public void jump(Player p) {
		if(onGround(p)){
			p.setVerticalSpeed(4.5f);
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Bullet> getBullets() {
		return (ArrayList<Bullet>) this.bullets.clone();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Player> getPlayerList() {
		return (ArrayList<Player>) this.playerList.clone();
	}
	
//	public void changeWeapon(Player player) {
//		player.changeWeapon();
//	}
}
