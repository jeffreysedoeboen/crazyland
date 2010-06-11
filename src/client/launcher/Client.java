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

@SuppressWarnings("serial")
public class Client extends JApplet {

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
			
		setSize(400, 320);
		setVisible(true);

		this.inlogview = new InlogView();
		setContentPane(this.inlogview);
		ButtonController buttonController = new ButtonController(inlogview, sender, masterConnection);

		inlogview.setVisible(true);
		inlogview.addListener(buttonController);
	}

}
