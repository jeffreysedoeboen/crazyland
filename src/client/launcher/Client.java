package client.launcher;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JApplet;

import client.connection.MasterConnection;
import client.connection.Sender;
import client.controller.ButtonController;
import client.view.InlogView;
import client.view.WorldView;

@SuppressWarnings("serial")
public class Client extends JApplet {

	private WorldView view;
	private InlogView inlogview;
	private Sender sender;

	public void init() {

		Socket s = null;
		InputStream inStream = null;
		OutputStream outStream = null;
		Scanner in = null;
		PrintWriter out = null;

		// check if we can talk with the masterserver
		System.out
				.println("\ntry to connect with masterserver...");

		try {
			s = new Socket("127.0.0.1", 1338);
			inStream = s.getInputStream();
			outStream = s.getOutputStream();
			in = new Scanner(inStream);
			out = new PrintWriter(outStream, true /* autoFlush */);
		} catch (IOException ioe) {
			System.out
					.println("\ncould not connect with masterserver! check network connection");
		}
		System.out.println("has connection");

		MasterConnection masterConnection = new MasterConnection(out, in);
		
//		Socket s = null;
//		InputStream inStream = null;
//		OutputStream outStream = null;
//		Scanner in = null;
//		PrintWriter out = null;
//
//		// check if we can talk with the server
//		System.out
//				.println("\nEen verbinding proberen te maken met met de server...");
//
//		try {
//			s = new Socket("127.0.0.1", 1337);
//			inStream = s.getInputStream();
//			outStream = s.getOutputStream();
//			in = new Scanner(inStream);
//			out = new PrintWriter(outStream, true /* autoFlush */);
//		} catch (IOException ioe) {
//			System.out
//					.println("\nKon niet met de server een verbinding maken. Check uw internet verbinding of whatever.. =/ .");
//		}
//		System.out.println("has connection");
//
//		Receiver receiver = new Receiver(in);
//		receiver.start();
//		sender = new Sender(out);
//
//		Toolkit toolkit = Toolkit.getDefaultToolkit();
//		Image image = toolkit.getImage("../themes/tee/weapon/cursor.png");
//		Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "cursor");
//		
//
//		this.view = new WorldView(receiver);
//		view.setCursor(c);
//		
//		setSize(400, 320);
//		setVisible(true);
//
//		KeyboardController keycontroller = new KeyboardController(sender, view);
//		view.addKeyListener(keycontroller);
//		view.setFocusable(true);
//
//		MouseController mouseController = new MouseController(sender, view);
//		view.addMouseListener(mouseController);
//		view.addMouseMotionListener(mouseController);
//
//		view.addMouseWheelListener(mouseController);
//
		this.inlogview = new InlogView();
		setContentPane(this.inlogview);
		ButtonController buttonController = new ButtonController(inlogview, sender, masterConnection);

		inlogview.setVisible(true);
		inlogview.addListener(buttonController);
//		
//		JFrame f = new JFrame();
//		f.add(view);
//		f.setVisible(true);
//		f.setSize(900, 500);
//		
//		new Timer(20, taskPerformer).start();
	}

}
