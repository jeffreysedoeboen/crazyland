package launcher;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.Timer;

import controller.KeyboardController;

import model.GameServer;
import model.World;

import view.WorldView;

public class Client extends JApplet{

	WorldView view;
	GameServer server;
	
	public void init() {
		
		server = new GameServer();
		
		this.view = new WorldView(server);
		setContentPane(this.view);
		setSize(320, 320);
		setVisible(true);
		
		KeyboardController keycontroller = new KeyboardController(server);
		this.addKeyListener(keycontroller);
		
		new Timer(100, taskPerformer).start();

	}
	
	ActionListener taskPerformer = new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			
			
			
			view.repaint();
			
		}
		
	  };


}
