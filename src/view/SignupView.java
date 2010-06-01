package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.GameServer;

public class SignupView extends JPanel{
	
	GameServer server;
	
	public SignupView(GameServer server){
		this.server = server;
		
		this.setLayout(new GridLayout(5,2));
		
		JLabel header = new JLabel("Sign up form");
		header.setFont(new Font("sansserif", Font.BOLD, 32));
		
		JLabel username = new JLabel("Username:");
		JTextField usernameinput = new JTextField (20);
		usernameinput.setBackground(Color.RED);
		
		JLabel password = new JLabel("Password:");
		JPasswordField passwordinput = new JPasswordField (20);
		
		JLabel passwordcheck = new JLabel("Confirm password:");
		JPasswordField passwordcheckinput = new JPasswordField (20);
		passwordcheckinput.setBackground(Color.BLUE);
		
		JButton create = new JButton("Sign up!");
		
		this.add(header);
		this.add(new JLabel(""));
		this.add(username);
		this.add(usernameinput);
		this.add(password);
		this.add(passwordinput);
		this.add(passwordcheck);
		this.add(passwordcheckinput);
		this.add(new JLabel(""));
		this.add(create);
		
	}

}
