package client.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import client.view.GameFrame;

import client.connection.Sender;
import client.view.WorldView;

public class KeyboardController implements KeyListener {

	private Sender sender;
	private WorldView wv;
	private GameFrame gameframe;
	
	public KeyboardController(Sender sender, WorldView wv, GameFrame gameframe) {
		this.wv = wv;
		this.sender = sender;
		this.gameframe = gameframe;
	}

	
	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {
		switch(e.getKeyChar()) {
			case(KeyEvent.VK_LEFT):
				sender.stopMovingLeft();
				break;
			case(KeyEvent.VK_RIGHT):
				sender.stopMovingRight();
				break;
			case(KeyEvent.VK_CONTROL):
				wv.setShowHighscore(false);
				break;
		}
	}

	public void keyTyped(KeyEvent e) {
		
		switch(e.getKeyChar()) {
			case(KeyEvent.VK_SPACE):
				sender.jump();
				break;
			case(KeyEvent.VK_LEFT):
				sender.startMovingLeft();
				break;
			case(KeyEvent.VK_RIGHT):
				sender.startMovingRight();
				break;
			case(KeyEvent.VK_CONTROL):
				wv.setShowHighscore(true);
				break;
			case(KeyEvent.VK_ESCAPE): 
				//sender.removePlayer();
				gameframe.dispose();
				break;
		}
	}

}
