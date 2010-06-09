package client.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import client.connection.Sender;
import client.view.ScoreView;
import client.view.WorldView;

public class KeyboardController implements KeyListener {

	private Sender sender;
	private ScoreView scoreview;

	public KeyboardController(Sender sender, ScoreView scoreview) {
		this.sender = sender;
		this.scoreview = scoreview;

	}

	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case(KeyEvent.VK_ENTER): //TODO score laten zien
			scoreview.setBounds(100,100,100,100);
			scoreview.setVisible(true);	
			break;
		}
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
			scoreview.setVisible(false);
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
		case(KeyEvent.VK_ESCAPE): 
			sender.removePlayer();
		System.exit(0);
		break;
		}
	}

}
