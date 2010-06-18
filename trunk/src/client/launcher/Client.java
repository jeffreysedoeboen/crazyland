package client.launcher;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import client.connection.MasterConnection;
import client.controller.ButtonController;
import client.view.InlogView;

public class Client {

	public static void main(String[] args) {
		assert(args.length>0);
		
		InlogView inlogview;
		
		Socket s = null;
		InputStream inStream = null;
		OutputStream outStream = null;
		Scanner in = null;
		PrintWriter out = null;

		try {
			s = new Socket("127.0.0.1", 1338);
//			s = new Socket(args[0], 1338);
			inStream = s.getInputStream();
			outStream = s.getOutputStream();
			in = new Scanner(inStream);
			out = new PrintWriter(outStream, true /* autoFlush */);
		} catch (IOException ioe) {
			System.out.println("\ncould not connect with masterserver! check network connection");
		}

		MasterConnection masterConnection = new MasterConnection(out, in);

		inlogview = new InlogView();
		inlogview.setVisible(true);
		ButtonController buttonController = new ButtonController(inlogview, masterConnection);

		inlogview.setVisible(true);
		inlogview.addListener(buttonController);
	}

}
