package client.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import client.connection.Sender;

public class KeyboardController implements KeyListener {

	private Sender sender;
	
	public KeyboardController(Sender sender) {
		this.sender = sender;
	}

	
	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			case(KeyEvent.VK_A):
				sender.stopMovingLeft();
				break;
			case(KeyEvent.VK_D):
				sender.stopMovingRight();
				break;
			case(KeyEvent.VK_TAB): //TODO score laten zien
				break;
		}
	}

	public void keyTyped(KeyEvent e) {
		
		switch(e.getKeyChar()) {
			case(KeyEvent.VK_SPACE):
				sender.jump();
				break;
			case('a'):
				sender.startMovingLeft();
				break;
			case('d'):
				sender.startMovingRight();
				break;
			case(KeyEvent.VK_TAB): //TODO score laten zien
				break;
			case(KeyEvent.VK_ESCAPE): 
				sender.removePlayer();
				System.exit(0);
				break;
		}
	}

}
