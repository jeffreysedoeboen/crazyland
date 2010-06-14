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
			case('a'):
				sender.stopMovingLeft();
				break;
			case('d'):
				sender.stopMovingRight();
				break;
			case('q'): //TODO score laten zien
				wv.setShowHighscore(false);
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
			case('q'): //TODO score laten zien
				wv.setShowHighscore(true);
				break;
			case(KeyEvent.VK_ESCAPE): 
				//sender.removePlayer();
				gameframe.dispose();
				break;
		}
	}

}
