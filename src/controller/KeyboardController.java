package controller;

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
			
		}
	}
	
	private void walkRight(){
		if(server.checkDirection(player.getPosition(), 'r')) {
			
		}
	}
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case(KeyEvent.VK_SPACE): //TODO springen
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
