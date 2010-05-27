package controller;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.GameServer;
import model.Player;
import view.WorldView;

public class KeyboardController implements KeyListener {

	private GameServer server;
	private Player player;
	
	public KeyboardController(Player player, GameServer server) {
		this.server = server;
		this.player = player;
	}
	
	private void walkLeft(){
		if(server.checkDirection(player.getPosition(), 'l')) {
			player.setPosition(new Point(player.getPosition().x - 1, player.getPosition().y));
		}
	}
	
	private void walkRight(){
		if(server.checkDirection(player.getPosition(), 'r')) {
			player.setPosition(new Point(player.getPosition().x + 1, player.getPosition().y));			
		}
	}
	private void jump() {
		if(server.checkDirection(player.getPosition(), 'u')) {
			
		}
	}
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case(KeyEvent.VK_SPACE): //TODO springen
			jump();
			break;
		case(KeyEvent.VK_A): //TODO naar links lopen
			walkLeft();
			break;
		case(KeyEvent.VK_D): //TODO naar rechts lopen
			walkRight();
			break;
		case(KeyEvent.VK_TAB): //TODO score laten zien
			break;
		case(KeyEvent.VK_ESCAPE): System.exit(0);
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
