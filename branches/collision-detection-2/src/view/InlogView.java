package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.GameServer;

public class InlogView extends JPanel {

	private GameServer server;

	public InlogView(GameServer server){
		this.server = server;

		this.setLayout(new BorderLayout());
		this.setBackground(Color.RED);

		JLabel label = new JLabel("Login");
		this.add(label, BorderLayout.NORTH);

		JPanel inlog = new JPanel();
		inlog.setLayout(new GridLayout(2,2));

		JLabel username = new JLabel("Username:");
		JTextField usernameinput = new JTextField(20);

		JLabel password = new JLabel("Password:");
		JPasswordField passwordinput = new JPasswordField(20);

		inlog.add(username);
		inlog.add(usernameinput);
		inlog.add(password);
		inlog.add(passwordinput);

		this.add(inlog, BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1,3));

		JButton guest = new JButton("Guest");
		JButton create = new JButton("Create");
		JButton login = new JButton("Login");

		buttons.add(guest);
		buttons.add(create);
		buttons.add(login);

		this.add(buttons, BorderLayout.SOUTH);
	}
}



