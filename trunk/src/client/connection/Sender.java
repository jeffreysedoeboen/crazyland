package client.connection;

import java.io.PrintWriter;

public class Sender {

	private PrintWriter out;
	
	public Sender(PrintWriter out){
		this.out = out;
	}
	
	public void shoot(int i, int j){
		out.println("s\n" + i + "," + j); // player_shoot
	}

	public void turnWeapon(int x, int y) {
		out.printf("tw%n" + x + "," + y + "%n"); // player_turn_weapon
	}

	public void startMovingLeft() {
		out.println("l"); // player_move_left
	}

	public void startMovingRight() {
		out.println("r"); // player_move_right
	}

	public void jump() {
		out.println("j"); // player_jump
	}

	public void stopMovingLeft() {
		out.println("sl"); // player_stop_moving_left
	}

	public void stopMovingRight() {
		out.println("sr"); // player_stop_moving_right
	}

	public void changeWeapon() {
		// TODO Auto-generated method stub
		
	}
	
	public void removePlayer () {
		out.println("rm"); // player_end
	}
	
	public void login(String userName) {
		out.println("n\n" + userName ); // player_name
	}
	
}
