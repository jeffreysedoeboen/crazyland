package server.model;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import server.model.bullet.Bullet;
import server.model.tile.Tile;
import server.tools.Circle;


public class World{

	private GameServer server;
	private Map map;
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private int bulletCounter = 1;

	public World(GameServer s){
		map = new Map();
		this.server = s;
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
			b.setBullet(p.getX()+15,p.getY()+15,Math.atan2(distanceFromPlayerY, distanceFromPlayerX),p);
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
					//player.setVerticalSpeed(-0.5f);
				}
				
			}
		}
		ArrayList<Bullet> bulletsclone = (ArrayList<Bullet>) bullets.clone();
		for(Bullet b : bulletsclone){
			if(!checkBulletColission(b)){
				b.move();
			}else{
				while(checkBulletColission(b)){
					b.moveOpposite();
				}
				System.out.println("HIT");
				bullets.remove(b);
				server.removeBullet(b);
			}
		}

	}
	
	public boolean checkBulletColission(Bullet bullet){
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
		
		for(WorldObject t : retList){
			
			if(bullet.getShape() instanceof Circle){
				Circle b = (Circle) bullet.getShape();
				if(t.getShape() instanceof Circle){
					Circle tile = (Circle) t.getShape();
					if(b.intersects(tile)){
						if(bullet.getOrigin() != t){
							return true;	
						}
					}
				}else{
					Rectangle2D.Double tile = (Rectangle2D.Double) t.getShape();
					if(b.intersects(tile)){
						if(bullet.getOrigin() != t){
							return true;
						}
					}
				}
			}else{
				Rectangle2D.Double b = (Rectangle2D.Double) bullet.getShape();
				if(t.getShape() instanceof Circle){
					Circle tile = (Circle) t.getShape();
					if(tile.intersects(b)){
						if(bullet.getOrigin() != t){
							return true;
						}	
					}
				}else{
					Rectangle2D.Double tile = (Rectangle2D.Double) t.getShape();
					if(b.intersects(tile)){
						if(bullet.getOrigin() != t){
							return true;
						}
					}
				}
			}
		}
		
		return false;
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
			int i = tileLeft.getX()*32;
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
		Circle shape = (Circle) player.getShape();
		if(tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY())/32)].getShape() instanceof Circle){
			Circle tile1 = (Circle) tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY())/32)].getShape();
			if(tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].getShape() instanceof Circle){
				Circle tile2 = (Circle) tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].getShape();
				if(tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].isSolid() && shape.intersects(tile2)){
					return false;
				}
			}else{
				Rectangle2D tile2 = (Rectangle2D) tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].getShape();
				if(tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].isSolid() && shape.intersects(tile2)){
					return false;
				}
			}
		}else{
			Rectangle2D tile1 = (Rectangle2D) tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY())/32)].getShape();
			if(tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].getShape() instanceof Circle){
				Circle tile2 = (Circle) tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].getShape();
				if(tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].isSolid() && shape.intersects(tile2)){
					return false;
				}
			}else{
				Rectangle2D tile2 = (Rectangle2D) tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].getShape();
				if(tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].isSolid() && shape.intersects(tile2)){
					return false;
				}
			}
		}
		System.out.println(tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY())/32)].getX() + "," + tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY())/32)].getY());
		System.out.println(tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].getX() + "," + tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].getY());
		return true;
	}
	
	private boolean canMoveRight(Player player) {
		Tile[][] tiles = map.getTiles();
		Circle shape = (Circle) player.getShape();
		if(tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY())/32)].getShape() instanceof Circle){
			Circle tile1 = (Circle) tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY())/32)].getShape(); 
			if(tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].getShape() instanceof Circle){
				Circle tile2 = (Circle) tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].getShape();
				if(tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].isSolid() && shape.intersects(tile2)){
					return false;
				}
			}else{
				Rectangle2D.Double tile2 = (Rectangle2D.Double) tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].getShape();
				if(tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].isSolid() && shape.intersects(tile2)){
					return false;
				}
			}
		}else{
			Rectangle2D tile1 = (Rectangle2D) tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY())/32)].getShape();
			if(tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].getShape() instanceof Circle){
				Circle tile2 = (Circle) tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].getShape();
				if(tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].isSolid() && shape.intersects(tile2)){
					return false;
				}
			}else{
				Rectangle2D.Double tile2 = (Rectangle2D.Double) tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].getShape();
				if(tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].isSolid() && shape.intersects(tile2)){
					return false;
				}
			}
		}
		return true;
	}

	private boolean collisionCeiling(Player player) {
		
		Tile[][] tiles = map.getTiles();
		Circle shape = (Circle) player.getShape();
		
		if(tiles[(int) (player.getX()/32)][(int) ((player.getY())/32)].getShape() instanceof Circle){
			Circle tile1 = (Circle) tiles[(int) (player.getX()/32)][(int) ((player.getY())/32)].getShape(); 
			if(tiles[(int) ((player.getX()+32)/32)][(int) ((player.getY())/32)].getShape() instanceof Circle){
				Circle tile2 = (Circle) tiles[(int) ((player.getX()+32)/32)][(int) ((player.getY())/32)].getShape();
				if(tiles[(int) (player.getX()/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+32)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile2)){
					return false;
				}
			}else{
				Rectangle2D.Double tile2 = (Rectangle2D.Double) tiles[(int) ((player.getX()+32)/32)][(int) ((player.getY())/32)].getShape();
				if(tiles[(int) (player.getX()/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+32)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile2)){
					return false;
				}
			}
		}else{
			Rectangle2D tile1 = (Rectangle2D) tiles[(int) (player.getX()/32)][(int) ((player.getY())/32)].getShape();
			if(tiles[(int) ((player.getX()+32)/32)][(int) ((player.getY())/32)].getShape() instanceof Circle){
				Circle tile2 = (Circle) tiles[(int) ((player.getX()+32)/32)][(int) ((player.getY())/32)].getShape();
				if(tiles[(int) (player.getX()/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+32)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile2)){
					return false;
				}
			}else{
				Rectangle2D.Double tile2 = (Rectangle2D.Double) tiles[(int) ((player.getX()+32)/32)][(int) ((player.getY())/32)].getShape();
				if(tiles[(int) (player.getX()/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+32)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile2)){
					return false;
				}
			}
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

	public void removePlayer(Player p) {
		playerList.remove(p);
	}
	
//	public void changeWeapon(Player player) {
//		player.changeWeapon();
//	}
}
