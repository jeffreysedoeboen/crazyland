package client.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import client.connection.Sender;
import client.view.WorldView;


public class MouseController implements MouseListener, MouseMotionListener, MouseWheelListener{
	private Sender sender;
	private WorldView view;
	
	public MouseController(Sender sender, WorldView view) {
		this.sender = sender;
		this.view = view;
	}
	
	public void mouseClicked(MouseEvent e) {
			
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent e) {
		sender.shoot(e.getX()-view.getOffsetX(),e.getY()-view.getOffsetY());
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public void mouseDragged(MouseEvent arg0) {
	}

	public void mouseMoved(MouseEvent e) {
		sender.turnWeapon(e.getX() - view.getOffsetX(), e.getY() - view.getOffsetY());
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		sender.changeWeapon();
	}

}
