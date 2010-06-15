package client.view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class InlogView extends JFrame {

	private JButton guest, create, login;
	private JTextField usernameinput;
	private JPasswordField passwordinput;

	public InlogView(){
		this.setTitle("Log in");
		this.setSize(400, 320);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel username = new JLabel("Username:");
		username.setSize(150, 20);
		username.setLocation(10, 50);
		
		usernameinput = new JTextField(20);
		usernameinput.setSize(200, 20);
		usernameinput.setLocation(150, 50);

		JLabel password = new JLabel("Password:");
		password.setSize(150, 20);
		password.setLocation(10, 100);
		
		passwordinput = new JPasswordField(20);
		passwordinput.setSize(200, 20);
		passwordinput.setLocation(150, 100);
		
		login = new JButton("Login");
		login.setSize(200, 20);
		login.setLocation(150, 150);
		
		guest = new JButton("Guest Account");
		guest.setSize(140, 20);
		guest.setLocation(10, 220);
		
		create = new JButton("Create Account");
		create.setSize(140, 20);
		create.setLocation(240, 220);

		add(username);
		add(usernameinput);
		add(password);
		add(passwordinput);
		add(guest);
		add(create);
		add(login);
	}
	
	public JTextField getUsernameinput() {
		return usernameinput;
	}
	
	public JTextField getPasswordinput() {
		return passwordinput;
	}
	
	public void addListener(ActionListener listener) {
		guest.addActionListener(listener);
		create.addActionListener(listener);
		login.addActionListener(listener);
	}
	
}



