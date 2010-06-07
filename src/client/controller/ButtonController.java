package client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import client.view.InlogView;
import client.view.LobbyView;
import client.view.SignupView;
import client.view.LeaderView;

import db.AccountDAO;


public class ButtonController implements ActionListener {

	private SignupView signupview;
	private InlogView inlogview;
	private LobbyView lobbyview;
	private LeaderView leaderview;

	public ButtonController(InlogView inlogview) {
		this.inlogview = inlogview;
		this.signupview = new SignupView();
		signupview.addListener(this);
		this.lobbyview = new LobbyView();
		lobbyview.addListener(this);
		this.leaderview = new LeaderView();
		leaderview.addListener(this);
	}

	public void actionPerformed(ActionEvent ae) {
		try {
			JButton button = (JButton) ae.getSource();
			if(button.getText().equals("Sign up!")){
				String filledInUsername = signupview.getUsernameinput().getText();
				String filledInPassword = signupview.getPasswordinput().getText();
				String filledInPasswordcheck = signupview.getPasswordcheckinput().getText();
				if(AccountDAO.addAccount(filledInUsername, filledInPassword, filledInPasswordcheck) == 5) {
					signupview.setVisible(false);
					JOptionPane.showMessageDialog(inlogview, "You have been registered!", "registration succesful", JOptionPane.PLAIN_MESSAGE);
					inlogview.setVisible(true);
				} else if(AccountDAO.addAccount(filledInUsername, filledInPassword, filledInPasswordcheck) == 1) {
					JOptionPane.showMessageDialog(signupview, "You didn't fill in your username", "username not filled in", JOptionPane.ERROR_MESSAGE);
				} else if(AccountDAO.addAccount(filledInUsername, filledInPassword, filledInPasswordcheck) == 2) {
					JOptionPane.showMessageDialog(signupview, "You didn't fill in your password", "password not filled in", JOptionPane.ERROR_MESSAGE);
				} else if(AccountDAO.addAccount(filledInUsername, filledInPassword, filledInPasswordcheck) == 3) {
					JOptionPane.showMessageDialog(signupview, "You didn't fill in the same password twice", "password not the same as repeated password", JOptionPane.ERROR_MESSAGE);
				} else if(AccountDAO.addAccount(filledInUsername, filledInPassword, filledInPasswordcheck) == 4) {
					JOptionPane.showMessageDialog(signupview, "This username already exists", "username already exists", JOptionPane.ERROR_MESSAGE);
				}
			} else if(button.getText().equals("Create Account")) {
				inlogview.setVisible(false);
				signupview.setVisible(true);
			} else if(button.getText().equals("Login")) {
				String filledInUsername = inlogview.getUsernameinput().getText();
				String filledInPassword = inlogview.getPasswordinput().getText();
				if(AccountDAO.login(filledInUsername, filledInPassword)) {
					inlogview.setVisible(false);
					signupview.setVisible(false);
					lobbyview.setVisible(true);
					JOptionPane.showMessageDialog(lobbyview, "You are logged in!", "log in succesful", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(inlogview, "You didn't fill in the right username and password", "wrong username or password", JOptionPane.ERROR_MESSAGE);
				}
			} else if(button.getText().equals("Guest Account")) {
				inlogview.setVisible(false);
				signupview.setVisible(false);
				lobbyview.setVisible(true);
			}else if(button.getText().equals("Leaderboard")) {
				lobbyview.setVisible(false);
				leaderview.setVisible(true);
			}else if(button.getText().equals("Back") && leaderview.isVisible()) {
				leaderview.setVisible(false);
				lobbyview.setVisible(true);
			}else if(button.getText().equals("Back") && signupview.isVisible()) {
				signupview.setVisible(false);
				inlogview.setVisible(true);
			}else if(button.getText().equals("Quit")) {
				System.exit(0);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
