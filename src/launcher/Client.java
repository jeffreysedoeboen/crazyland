package launcher;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.Timer;

import controller.KeyboardController;
import controller.MouseController;

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
		setSize(900, 300);
		setVisible(true);
		setFocusable(true);
		
		
		KeyboardController keycontroller = new KeyboardController(server);
		this.addKeyListener(keycontroller);
		
		MouseController mouseController = new MouseController(server);
		this.addMouseListener(mouseController);
		this.addMouseMotionListener(mouseController);
		
		new Timer(33, taskPerformer).start();

	}
	
	ActionListener taskPerformer = new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {

			view.repaint();
			
		}
		
	  };


}
