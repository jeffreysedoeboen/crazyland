package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import model.GameServer;
import model.Player;
import model.weapon.Weapon;

public class MouseController implements MouseListener, MouseMotionListener{
	private GameServer server;
	
	public MouseController(GameServer server) {
		this.server = server;
	}
	
	public void mouseClicked(MouseEvent e) {
		server.shoot();	
		System.out.println("schiet");
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
//		Weapon weapon = player.getWeapon();
//		weapon.turnToPoint(e.getPoint());
		server.moveWeapon();
	}

}
