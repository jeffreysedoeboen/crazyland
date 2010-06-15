package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.GameServer;

public class KeyboardController implements KeyListener {

	private GameServer server;
	
	public KeyboardController(GameServer server) {
		this.server = server;
		server.getWorld();
	}

	
	public void keyPressed(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			case(KeyEvent.VK_SPACE):
				//server.jump();
				break;
			case(KeyEvent.VK_A):
				server.stopMovingLeft();
				break;
			case(KeyEvent.VK_D):
				server.stopMovingRight();
				break;
			case(KeyEvent.VK_TAB): //TODO score laten zien
				break;
			case(KeyEvent.VK_ESCAPE): System.exit(0);
				break;
		}
	}

	public void keyTyped(KeyEvent e) {
		
		switch(e.getKeyChar()) {
			case(KeyEvent.VK_SPACE):
				server.jump();
				break;
			case('a'):
				server.startMovingLeft();
				break;
			case('d'):
				server.startMovingRight();
				break;
			case(KeyEvent.VK_TAB): //TODO score laten zien
				break;
			case(KeyEvent.VK_ESCAPE): System.exit(0);
				break;
		}
	}

}