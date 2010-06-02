package client.launcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JApplet;
import javax.swing.Timer;

import client.connection.Receiver;
import client.connection.Sender;
import client.controller.KeyboardController;
import client.controller.MouseController;
import client.view.WorldView;

@SuppressWarnings("serial")
public class Client extends JApplet{

	/*LobbyView view;*/
	private WorldView view;
	
	public void init() {
		
		Socket s = null;
		InputStream inStream = null;
		OutputStream outStream = null;
		Scanner in = null;
		PrintWriter out = null;
		
		// check if we can talk with the server
		System.out.println("\nEen verbinding proberen te maken met met de server...");

		try{
		    s = new Socket("127.0.0.1", 1337);
		    inStream = s.getInputStream();
		    outStream = s.getOutputStream();
		    in = new Scanner(inStream);
		    out = new PrintWriter(outStream, true /* autoFlush */);
		}catch(IOException ioe){
		    System.out.println("\nKon niet met de server een verbinding maken. Check uw internet verbinding of whatever.. =/ .");
		}
		System.out.println("has connection");
		
		Receiver receiver = new Receiver(in);
		receiver.start();
		Sender sender = new Sender(out);
		
		this.view = new WorldView(receiver);
		
		setContentPane(this.view);
		setSize(900, 300);
		setVisible(true);
		setFocusable(true);
		
		KeyboardController keycontroller = new KeyboardController(sender);
		this.addKeyListener(keycontroller);
		
		MouseController mouseController = new MouseController(sender,view);
		this.addMouseListener(mouseController);
		this.addMouseMotionListener(mouseController);
		
		new Timer(20,taskPerformer).start();
	}
	
	ActionListener taskPerformer = new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			view.repaint();
		}
		
	  };


}
