package client.connection;

import java.io.PrintWriter;

public class Sender {

	private PrintWriter out;
	
	public Sender(PrintWriter out){
		this.out = out;
	}
	
	public void shoot(int i, int j){
		out.println("player_shoot");
		out.println(i + "," + j);
	}

	public void moveWeapon(int x, int y) {
		// TODO Auto-generated method stub
	}

	public void startMovingLeft() {
		out.println("player_move_left");
	}

	public void startMovingRight() {
		out.println("player_move_right");
	}

	public void jump() {
		out.println("player_jump");
	}

	public void stopMovingLeft() {
		out.println("player_stop_moving_left");
	}

	public void stopMovingRight() {
		out.println("player_stop_moving_right");
	}

	public void changeWeapon() {
		// TODO Auto-generated method stub
		
	}
	
}
