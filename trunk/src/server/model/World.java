package server.model;

import java.awt.Shape;
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
	private ArrayList<Player> playerWaitList = new ArrayList<Player>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private int bulletCounter = 1;

	public World(GameServer s){
		map = new Map();
		this.server = s;
	}

	public void addPlayerWaitList(Player p) {
		playerList.remove(p);
		playerWaitList.add(p);
	}

	public void RemovePlayerWaitList(Player p) {
		playerWaitList.remove(p);
		playerList.add(p);
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
					player.setVerticalSpeed(-0.5f);
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
				ArrayList<Player> players = (ArrayList<Player>)getPlayerList().clone();
				for(Player p : players) {
					if(p.getHitpoints() < 1) {
						addPlayerWaitList(p);
						p.increaseDeaths();
						b.getOrigin().increaseKills();
						System.out.println("dooie: kills" + p.getKills() + " deaths " + p.getDeaths());
						System.out.println("owner: kills" + b.getOrigin().getKills() + " deaths " + b.getOrigin().getDeaths());
					}
				}
				bullets.remove(b);
				server.removeBullet(b);
				
			}
		}

	}

	public ArrayList<WorldObject> getRespawns() {
		ArrayList<WorldObject> respawns = new ArrayList<WorldObject>();
		for( int i = 0; i < map.getTiles().length; i++ ) {
			for( Tile tile : map.getTiles()[i] ) {
				if(tile.isRespawn()) {
					respawns.add(tile);
				}
			}
		}
		return respawns;
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
							if(checkCloseBulletColission(bullet, tile)){
								if(t instanceof Player) {
									Player p = (Player) t;
									p.decreaseHitpoints(bullet.getDamage());
								}
								return true;	
							}
						}
					}
				}else{
					Rectangle2D.Double tile = (Rectangle2D.Double) t.getShape();
					if(b.intersects(tile)){
						if(bullet.getOrigin() != t){
							if(checkCloseBulletColission(bullet, tile)){
								if(t instanceof Player) {
									Player p = (Player) t;
									p.decreaseHitpoints(bullet.getDamage());
								}
								return true;	
							}
						}
					}
				}
			}else{
				Rectangle2D.Double b = (Rectangle2D.Double) bullet.getShape();
				if(t.getShape() instanceof Circle){
					Circle tile = (Circle) t.getShape();
					if(tile.intersects(b)){
						if(bullet.getOrigin() != t){
							if(checkCloseBulletColission(bullet, tile)){
								if(t instanceof Player) {
									Player p = (Player) t;
									p.decreaseHitpoints(bullet.getDamage());
								}
								return true;	
							}
						}	
					}
				}else{
					Rectangle2D.Double tile = (Rectangle2D.Double) t.getShape();
					if(b.intersects(tile)){
						if(bullet.getOrigin() != t){
							if(checkCloseBulletColission(bullet, tile)){
								if(t instanceof Player) {
									Player p = (Player) t;
									p.decreaseHitpoints(bullet.getDamage());
								}
								return true;	
							}
						}
					}
				}
			}
		}

		return false;
	}
	
public boolean checkCloseBulletColission(Bullet b, Shape tile){
		
		System.out.println("CHECKING");
		
		float dir = b.getDirection();
		Rectangle2D shape = b.getShape().getBounds();
		
		if(tile instanceof Circle){
			Circle e1 = (Circle) tile;
			Circle c1,c2,c3;
			
			c1 = new Circle(4,(int)shape.getCenterX()-5,(int)shape.getCenterY()-5);
			
			int posX = (int) (5*Math.cos(dir));
			int posY = (int) (5*Math.sin(dir));
			c2 = new Circle(4,(int)shape.getCenterX()+posX-5, (int)shape.getCenterY()+posY-5);
			
			posX = (int) (5*Math.cos(dir+Math.PI));
			posY = (int) (5*Math.sin(dir+Math.PI));
			c3 = new Circle(4,(int)shape.getCenterX()+posX-5, (int)shape.getCenterY()+posY-5);
			
			if(c1.intersects(e1)){
				return true;
			}else if(c2.intersects(e1)){
				return true;
			}else if(c3.intersects(e1)){
				return true;
			}
		}else{
			Rectangle2D e1 = (Rectangle2D) tile;
			Circle c1,c2,c3;
			
			c1 = new Circle(3,(int)shape.getCenterX()-5,(int)shape.getCenterY()-5);
			
			int posX = (int) (3*Math.cos(dir));
			int posY = (int) (3*Math.sin(dir));
			c2 = new Circle(3,(int)shape.getCenterX()+posX-5, (int)shape.getCenterY()+posY-5);
			
			posX = (int) (3*Math.cos(dir+Math.PI));
			posY = (int) (3*Math.sin(dir+Math.PI));
			c3 = new Circle(3,(int)shape.getCenterX()+posX-5, (int)shape.getCenterY()+posY-5);
			
			if(c1.intersects(e1)){
				return true;
			}else if(c2.intersects(e1)){
				return true;
			}else if(c3.intersects(e1)){
				return true;
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
	
	/*public boolean onGround(Player player) {
		
		Tile[][] tiles = map.getTiles();

		if( isCollision( tiles[(int) (player.getX()/32)] [(int) ((player.getY()+32)/32)],
				tiles[(int) ((player.getX()+32)/32)][(int) ((player.getY()+32)/32)], true,player)) {
			return true;
		}

		return false;
	}*/
	
	public boolean onGround(Player player) {
		player.setPosition(player.getX(), player.getY() + 2.5F);
		
		Tile[][] tiles = map.getTiles();
		Circle shape = (Circle) player.getShape();
		if(tiles[(int) ((player.getX())/32)][(int) ((player.getY()+35)/32)].getShape() instanceof Circle){
			Circle tile1 = (Circle) tiles[(int) ((player.getX())/32)][(int) ((player.getY()+35)/32)].getShape();
			if(tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()+35)/32)].getShape() instanceof Circle){
				Circle tile2 = (Circle) tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()+35)/32)].getShape();
				if(tiles[(int) ((player.getX())/32)][(int) ((player.getY()+35)/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()+35)/32)].isSolid() && shape.intersects(tile2)){
					player.setPosition(player.getX(), player.getY() - 2.5F);
					return true;
				}
			}else{
				Rectangle2D tile2 = (Rectangle2D) tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()+35)/32)].getShape();
				if(tiles[(int) ((player.getX())/32)][(int) ((player.getY()+35)/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()+35)/32)].isSolid() && shape.intersects(tile2)){
					player.setPosition(player.getX(), player.getY() - 2.5F);
					return true;
				}
			}
		}else{
			Rectangle2D tile1 = (Rectangle2D) tiles[(int) ((player.getX())/32)][(int) ((player.getY()+35)/32)].getShape();
			if(tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()+35)/32)].getShape() instanceof Circle){
				Circle tile2 = (Circle) tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()+35)/32)].getShape();
				if(tiles[(int) ((player.getX())/32)][(int) ((player.getY()+35)/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()+35)/32)].isSolid() && shape.intersects(tile2)){
					player.setPosition(player.getX(), player.getY() - 2.5F);
					return true;
				}
			}else{
				Rectangle2D tile2 = (Rectangle2D) tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()+35)/32)].getShape();
				if(tiles[(int) ((player.getX())/32)][(int) ((player.getY()+35)/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()+35)/32)].isSolid() && shape.intersects(tile2)){
					player.setPosition(player.getX(), player.getY() - 2.5F);
					return true;
				}
			}
		}
		player.setPosition(player.getX(), player.getY() - 2.5F);
		return false;
	}
	
	private boolean canMoveLeft(Player player) {	
		player.setPosition(player.getX()-2, player.getY());
		
		Tile[][] tiles = map.getTiles();

		Circle shape = (Circle) player.getShape();
		if(tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY())/32)].getShape() instanceof Circle){
			Circle tile1 = (Circle) tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY())/32)].getShape();
			if(tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].getShape() instanceof Circle){
				Circle tile2 = (Circle) tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].getShape();
				if(tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].isSolid() && shape.intersects(tile2)){
					player.setPosition(player.getX()+2, player.getY()-3);
					while(onGround(player)) {
						player.setPosition(player.getX(), player.getY()-1);
					}
					return true;
				}
			}else{
				Rectangle2D tile2 = (Rectangle2D) tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].getShape();
				if(tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].isSolid() && shape.intersects(tile2)){
					player.setPosition(player.getX()+2, player.getY());
					while(onGround(player)) {
						player.setPosition(player.getX(), player.getY()-1);
					}
					return false;
				}
			}
		}else{
			Rectangle2D tile1 = (Rectangle2D) tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY())/32)].getShape();
			if(tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].getShape() instanceof Circle){
				Circle tile2 = (Circle) tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].getShape();
				if(tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].isSolid() && shape.intersects(tile2)){
					player.setPosition(player.getX()+2, player.getY()-3);
					while(onGround(player)) {
						player.setPosition(player.getX(), player.getY()-1);
					}
					return true;
				}
			}else{
				Rectangle2D tile2 = (Rectangle2D) tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].getShape();
				if(tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()-3)/32)][(int) ((player.getY()+29)/32)].isSolid() && shape.intersects(tile2)){
					player.setPosition(player.getX()+2, player.getY());
					while(onGround(player)) {
						player.setPosition(player.getX(), player.getY()-1);
					}
					return false;
				}
			}
		}
		player.setPosition(player.getX()+2, player.getY());
		return true;
	}

	private boolean canMoveRight(Player player) {
		player.setPosition(player.getX()+2, player.getY());
		
		Tile[][] tiles = map.getTiles();
		Circle shape = (Circle) player.getShape();
		if(tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY())/32)].getShape() instanceof Circle){
			Circle tile1 = (Circle) tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY())/32)].getShape(); 
			if(tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].getShape() instanceof Circle){
				Circle tile2 = (Circle) tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].getShape();
				if(tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].isSolid() && shape.intersects(tile2)){
					player.setPosition(player.getX()-2, player.getY()-3);
					while(onGround(player)) {
						player.setPosition(player.getX(), player.getY()-1);
					}
					return true;
				}
			}else{
				Rectangle2D.Double tile2 = (Rectangle2D.Double) tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].getShape();
				if(tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].isSolid() && shape.intersects(tile2)){
					player.setPosition(player.getX()-2, player.getY());
					while(onGround(player)) {
						player.setPosition(player.getX(), player.getY()-1);
					}
					return false;
				}
			}
		}else{
			Rectangle2D tile1 = (Rectangle2D) tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY())/32)].getShape();
			if(tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].getShape() instanceof Circle){
				Circle tile2 = (Circle) tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].getShape();
				if(tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].isSolid() && shape.intersects(tile2)){
					player.setPosition(player.getX()-2, player.getY()-3);
					while(onGround(player)) {
						player.setPosition(player.getX(), player.getY()-1);
					}
					return true;
				}
			}else{
				Rectangle2D.Double tile2 = (Rectangle2D.Double) tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].getShape();
				if(tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY())/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+35)/32)][(int) ((player.getY()+29)/32)].isSolid() && shape.intersects(tile2)){
					player.setPosition(player.getX()-2, player.getY());
					while(onGround(player)) {
						player.setPosition(player.getX(), player.getY()-1);
					}
					return false;
				}
			}
		}
		player.setPosition(player.getX()-2, player.getY());
		return true;
	}

	private boolean collisionCeiling(Player player) {
		player.setPosition(player.getX(), player.getY() - 2.5F);
		
		Tile[][] tiles = map.getTiles();
		Circle shape = (Circle) player.getShape();
		if(tiles[(int) ((player.getX())/32)][(int) ((player.getY()-3)/32)].getShape() instanceof Circle){
			Circle tile1 = (Circle) tiles[(int) ((player.getX())/32)][(int) ((player.getY()-3)/32)].getShape();
			if(tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()-3)/32)].getShape() instanceof Circle){
				Circle tile2 = (Circle) tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()-3)/32)].getShape();
				if(tiles[(int) ((player.getX())/32)][(int) ((player.getY()-3)/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()-3)/32)].isSolid() && shape.intersects(tile2)){
					player.setPosition(player.getX(), player.getY() + 2.5F);
					return true;
				}
			}else{
				Rectangle2D tile2 = (Rectangle2D) tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()-3)/32)].getShape();
				if(tiles[(int) ((player.getX())/32)][(int) ((player.getY()-3)/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()-3)/32)].isSolid() && shape.intersects(tile2)){
					player.setPosition(player.getX(), player.getY() + 2.5F);
					return true;
				}
			}
		}else{
			Rectangle2D tile1 = (Rectangle2D) tiles[(int) ((player.getX())/32)][(int) ((player.getY()-3)/32)].getShape();
			if(tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()-3)/32)].getShape() instanceof Circle){
				Circle tile2 = (Circle) tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()-3)/32)].getShape();
				if(tiles[(int) ((player.getX())/32)][(int) ((player.getY()-3)/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()-3)/32)].isSolid() && shape.intersects(tile2)){
					player.setPosition(player.getX(), player.getY() + 2.5F);
					return true;
				}
			}else{
				Rectangle2D tile2 = (Rectangle2D) tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()-3)/32)].getShape();
				if(tiles[(int) ((player.getX())/32)][(int) ((player.getY()-3)/32)].isSolid() && shape.intersects(tile1) || tiles[(int) ((player.getX()+29)/32)][(int) ((player.getY()-3)/32)].isSolid() && shape.intersects(tile2)){
					player.setPosition(player.getX(), player.getY() + 2.5F);
					return true;
				}
			}
		}
		player.setPosition(player.getX(), player.getY() + 2.5F);
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

	public ArrayList<Player> getPlayerWaitList() {
		return playerWaitList;
	}

	//	public void changeWeapon(Player player) {
	//		player.changeWeapon();
	//	}
}
