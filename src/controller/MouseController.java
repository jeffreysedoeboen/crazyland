package controller;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
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
//		Weapon weapon = player.getWeapon();
//		weapon.turnToPoint(e.getPoint());
		Point mouseDot = e.getPoint();
		server.moveWeapon(mouseDot);
	}

}
