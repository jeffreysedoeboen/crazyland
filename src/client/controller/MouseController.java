package client.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import client.connection.Sender;


public class MouseController implements MouseListener, MouseMotionListener{
	private Sender sender;
	
	public MouseController(Sender sender) {
		this.sender = sender;
	}
	
	public void mouseClicked(MouseEvent e) {
		sender.shoot(e.getX(),e.getY());	
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public void mouseDragged(MouseEvent arg0) {
	}

	public void mouseMoved(MouseEvent e) {
		sender.moveWeapon(e.getX(),e.getY());
	}

}
