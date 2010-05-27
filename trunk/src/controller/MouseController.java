package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import model.Player;
import model.weapon.Weapon;

public class MouseController implements MouseListener, MouseMotionListener{
	private Player player;
	
	public MouseController(Player player) {
		this.player = player;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO schieten
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent e) {
		Weapon weapon = player.getWeapon();
		weapon.turnToPoint(e.getPoint());
	}

}
