package launcher;


import java.applet.AudioClip;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JApplet;
import javax.swing.Timer;

import controller.ButtonController;
import controller.KeyboardController;
import controller.MouseController;
import db.AccountDAO;

import model.GameServer;
import model.MidiPlayer;
import model.World;

import view.SignupView;
import view.InlogView;
import view.LobbyView;
import view.WorldView;

public class Client extends JApplet{

	LobbyView lobbyview;
	InlogView inlogview;
	SignupView signupview;
	WorldView worldview;
	GameServer server;
	
	public void init() {
		
		server = new GameServer();
		server.start();
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
    	Image image = toolkit.getImage("../themes/tee/weapon/cursor.png");
    	Cursor c = toolkit.createCustomCursor(image , new Point(0,0), "cursor");
    	setCursor (c);
		
		this.worldview = new WorldView(server);
		/*this.view = new SignupView(server);*/
		setContentPane(this.worldview);
		setSize(900, 300);
		setVisible(true);
		setFocusable(true);
		
//		MidiPlayer midiplayer = new MidiPlayer();
//		Sequence sequence = midiplayer.getSequence("../sound/background.mid");
//		midiplayer.play(sequence, true);
		
		KeyboardController keycontroller = new KeyboardController(server);
		this.addKeyListener(keycontroller);
		
		MouseController mouseController = new MouseController(server, worldview);
		this.addMouseListener(mouseController);
		this.addMouseMotionListener(mouseController);
		
		AccountDAO accountDao = new AccountDAO();
		ButtonController buttonController = new ButtonController(signupview, inlogview, accountDao);
		
		this.inlogview = new InlogView();
		inlogview.setVisible(true);
		inlogview.addListener(buttonController);
		
		new Timer(1,taskPerformer).start();
	}
	
	ActionListener taskPerformer = new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			worldview.repaint();
		}
		
	  };


}
