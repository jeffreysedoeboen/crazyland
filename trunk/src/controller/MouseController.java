package controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import view.WorldView;

import model.GameServer;

public class MouseController implements MouseListener, MouseMotionListener{
	private GameServer server;
	private WorldView worldView;
	
	public MouseController(GameServer server, WorldView worldView) {
		this.server = server;
		this.worldView = worldView;
	}
	
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent arg0) {
    }
	

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent e) {
		server.shoot(e.getX() - worldView.getOffsetX(), e.getY() - worldView.getOffsetY());
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public void mouseDragged(MouseEvent arg0) {
	}

	public void mouseMoved(MouseEvent e) {
		server.moveWeapon(e.getX() - worldView.getOffsetX(), e.getY() -worldView.getOffsetY());
	}

}
