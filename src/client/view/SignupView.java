package client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignupView extends JFrame {
	
	private JButton create,back;
	private JTextField usernameinput;
	private JPasswordField passwordinput;
	private JPasswordField passwordcheckinput;
	
	public SignupView(){
		this.setTitle("Sign Up Form");
		this.setSize(400, 320);
		this.setLayout(null);
		
		JLabel username = new JLabel("Username:");
		username.setSize(150, 20);
		username.setLocation(10, 50);
		
		usernameinput = new JTextField (20);
		usernameinput.setSize(200, 20);
		usernameinput.setLocation(150, 50);
		
		JLabel password = new JLabel("Password:");
		password.setSize(150, 20);
		password.setLocation(10, 100);
		
		passwordinput = new JPasswordField (20);
		passwordinput.setSize(200, 20);
		passwordinput.setLocation(150, 100);
		
		JLabel passwordcheck = new JLabel("Confirm password:");
		passwordcheck.setSize(150, 20);
		passwordcheck.setLocation(10, 150);
		
		passwordcheckinput = new JPasswordField (20);
		passwordcheckinput.setSize(200, 20);
		passwordcheckinput.setLocation(150, 150);
		
		create = new JButton("Sign up!");
		create.setSize(150, 20);
		create.setLocation(200, 200);
		
		back = new JButton("Back");
		back.setSize(150, 20);
		back.setLocation(50, 200);
		
		add(username);
		add(usernameinput);
		add(password);
		add(passwordinput);
		add(passwordcheck);
		add(passwordcheckinput);
		add(create);
		add(back);
	}
	
	public JTextField getUsernameinput() {
		return usernameinput;
	}
	
	public JTextField getPasswordinput() {
		return passwordinput;
	}
	
	public JTextField getPasswordcheckinput() {
		return passwordcheckinput;
	}
	
	public void addListener(ActionListener listener) {
		create.addActionListener(listener);
		back.addActionListener(listener);
	}

}
