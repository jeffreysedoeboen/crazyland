package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.Player;
import view.ViewWorld;

public class KeyboardController implements KeyListener {

	private ViewWorld world;
	private Player player;
	
	
	public void walkLeft(){
		
	}
	
	public void walkRight(){
		
	}
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case(KeyEvent.VK_SPACE): //TODO springen
			break;
		case(KeyEvent.VK_A): //TODO naar links lopen
			break;
		case(KeyEvent.VK_D): //TODO naar rechts lopen
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
