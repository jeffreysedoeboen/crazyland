package server.model;

import java.util.Scanner;

import server.model.Player;

public class Receiver extends Thread {

	private Scanner in;
	private boolean terminated = false;
	private Player player = null;
	private GameServer s = null;
	
	public  Receiver(Scanner in, Player p, GameServer s){
		this.in = in;
		this.player = p;
		this.s = s;
	}
	
	public synchronized void terminate(){
		this.terminated = true;
	}
	
	public void run(){	
		while(!terminated && in.hasNext()){
			String tempstr = in.nextLine();
			if(tempstr.equals("player_move_right")){
				player.setMovingRight(true);
			}else if(tempstr.equals("player_stop_moving_right")){
				player.setMovingRight(false);
			}else if(tempstr.equals("player_move_left")){
				player.setMovingLeft(true);
			}else if(tempstr.equals("player_stop_moving_left")){
				player.setMovingLeft(false);
			}else if(tempstr.equals("player_jump")){
				if(s.getWorld().onGround(player)){
					player.setVerticalSpeed(4.5f);
				}
			}else if(tempstr.equals("player_shoot")){
				String[] playerXY = in.nextLine().split(",");
				s.shoot(Integer.parseInt("" + playerXY[0]), Integer.parseInt("" + playerXY[1]), player);
			}
		}
	}
	
}
