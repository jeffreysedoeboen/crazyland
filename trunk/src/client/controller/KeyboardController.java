package client.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import client.connection.Sender;
import client.view.WorldView;

public class KeyboardController implements KeyListener {

	private Sender sender;
	private WorldView wv;
	
	public KeyboardController(Sender sender, WorldView wv) {
		this.wv = wv;
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
			case(KeyEvent.VK_ENTER): //TODO score laten zien
				wv.setShowHighscore(false);
				break;
		}
	}

	public void keyTyped(KeyEvent e) {
		System.out.println(e.getKeyChar());
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
			case(KeyEvent.VK_ENTER): //TODO score laten zien
				wv.setShowHighscore(true);
				break;
			case(KeyEvent.VK_ESCAPE): 
				sender.removePlayer();
				System.exit(0);
				break;
		}
	}

}
