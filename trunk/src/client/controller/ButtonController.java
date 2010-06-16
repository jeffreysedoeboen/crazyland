package client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;


import client.connection.MasterConnection;
import client.model.GameServer;
import client.view.GameFrame;
import client.view.InlogView;
import client.view.LobbyView;
import client.view.SignupView;
import client.view.LeaderView;
import client.view.WorldView;

public class ButtonController implements ActionListener {

	private SignupView signupview;
	private InlogView inlogview;
	private LobbyView lobbyview;
	private LeaderView leaderview;
	private MasterConnection master;

	public ButtonController(InlogView inlogview, MasterConnection masterConnection) {
		this.inlogview = inlogview;
		this.signupview = new SignupView();
		this.master = masterConnection;
		signupview.addListener(this);
		this.lobbyview = new LobbyView(master);
		lobbyview.addListener(this);
		this.leaderview = new LeaderView(master);
		leaderview.addListener(this);
	}

	public void actionPerformed(ActionEvent ae) {
		JButton button = (JButton) ae.getSource();
		if(button.getText().equals("Sign up!")){
			String filledInUsername = signupview.getUsernameinput().getText();
			String filledInPassword = signupview.getPasswordinput().getText();
			String filledInPasswordcheck = signupview.getPasswordcheckinput().getText();
			if (!filledInPassword.equals(filledInPasswordcheck)) {
				JOptionPane.showMessageDialog(signupview, "You didn't fill in the same password twice", "password not the same as repeated password", JOptionPane.ERROR_MESSAGE);
			} else if(master.addAccount(filledInUsername, filledInPassword)) {
				signupview.setVisible(false);
				JOptionPane.showMessageDialog(inlogview, "You have been registered!", "registration succesful", JOptionPane.PLAIN_MESSAGE);
				inlogview.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(inlogview, "Registration failed!", "registration failed", JOptionPane.PLAIN_MESSAGE);
			}
		} else if(button.getText().equals("Create Account")) {
			inlogview.setVisible(false);
			signupview.setVisible(true);
		} else if(button.getText().equals("Login")) {
			String filledInUsername = inlogview.getUsernameinput().getText();
			String filledInPassword = inlogview.getPasswordinput().getText();
			if(master.login(filledInUsername, filledInPassword)) {
				inlogview.setVisible(false);
				signupview.setVisible(false);
				lobbyview.setVisible(true);
				JOptionPane.showMessageDialog(lobbyview, "You are logged in!", "log in succesful", JOptionPane.PLAIN_MESSAGE);
				lobbyview.setUserName(filledInUsername);
			} else {
				JOptionPane.showMessageDialog(inlogview, "You didn't fill in the right username and password", "wrong username or password", JOptionPane.ERROR_MESSAGE);
			}
		} else if(button.getText().equals("Guest Account")) {
			int guestNumber = master.getGuestAccess();
			System.out.println("has guest access");
			lobbyview.setUserName("Guest" + guestNumber);
			inlogview.setVisible(false);
			signupview.setVisible(false);
			lobbyview.setVisible(true);
		}else if(button.getText().equals("Leaderboard")) {
			lobbyview.setVisible(false);
			leaderview.updateTable();
			leaderview.setVisible(true);
		}else if(button.getText().equals("Connect")) {
			GameServer server = lobbyview.getSelectedGameserver();
			if (server == null) {
				JOptionPane.showMessageDialog(lobbyview, "Could not connect. No server selected?", "Could not connect", JOptionPane.ERROR_MESSAGE);
			} else {
				GameFrame f = new GameFrame();
				f.setWorldView(new WorldView(server, lobbyview.getUserName(), f));
				f.setSize(600, 300);
				f.setVisible(true);
			}
		}else if(button.getText().equals("Back") && leaderview.isVisible()) {
			leaderview.setVisible(false);
			lobbyview.setVisible(true);
		}else if(button.getText().equals("Back") && signupview.isVisible()) {
			signupview.setVisible(false);
			inlogview.setVisible(true);
		}else if(button.getText().equals("Quit")) {
			System.exit(0);
		}
	}
}
