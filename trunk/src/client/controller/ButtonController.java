package client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.view.InlogView;
import client.view.LobbyView;
import client.view.SignupView;

import db.AccountDAO;


public class ButtonController implements ActionListener {
	
	private SignupView signupview;
	private InlogView inlogview;
	private LobbyView lobbyview;
	private AccountDAO accountDao;
	
	public ButtonController(InlogView inlogview, AccountDAO accountDao) {
		this.inlogview = inlogview;
		this.accountDao = accountDao;
		this.signupview = new SignupView();
		this.lobbyview = new LobbyView();
	}
	
	public void actionPerformed(ActionEvent ae) {
		try {
			JButton button = (JButton) ae.getSource();
			if(button.getText().equals("Sign up!")){
				String filledInUsername = signupview.getUsernameinput().getText();
				String filledInPassword = signupview.getPasswordinput().getText();
				String filledInPasswordcheck = signupview.getPasswordcheckinput().getText();
				if(accountDao.addAccount(filledInUsername, filledInPassword, filledInPasswordcheck) == 5) {
					signupview.setVisible(false);
					JOptionPane.showMessageDialog(inlogview, "You have been registered!", "registration succesful", JOptionPane.PLAIN_MESSAGE);
				} else if(accountDao.addAccount(filledInUsername, filledInPassword, filledInPasswordcheck) == 1) {
					JOptionPane.showMessageDialog(signupview, "You didn't fill in your username", "username not filled in", JOptionPane.ERROR_MESSAGE);
				} else if(accountDao.addAccount(filledInUsername, filledInPassword, filledInPasswordcheck) == 2) {
					JOptionPane.showMessageDialog(signupview, "You didn't fill in your password", "password not filled in", JOptionPane.ERROR_MESSAGE);
				} else if(accountDao.addAccount(filledInUsername, filledInPassword, filledInPasswordcheck) == 3) {
					JOptionPane.showMessageDialog(signupview, "You didn't fill in the same password twice", "password not the same as repeated password", JOptionPane.ERROR_MESSAGE);
				} else if(accountDao.addAccount(filledInUsername, filledInPassword, filledInPasswordcheck) == 4) {
					JOptionPane.showMessageDialog(signupview, "This username already exists", "username already exists", JOptionPane.ERROR_MESSAGE);
				}
			} else if(button.getText().equals("Create Account")) {
				signupview.setVisible(true);
				signupview.addListener(this);
			} else if(button.getText().equals("Login")) {
				String filledInUsername = inlogview.getUsernameinput().getText();
				String filledInPassword = inlogview.getPasswordinput().getText();
				if(accountDao.login(filledInUsername, filledInPassword)) {
					inlogview.setVisible(false);
					lobbyview.setVisible(true);
					JOptionPane.showMessageDialog(lobbyview, "You are logged in!", "log in succesful", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(inlogview, "You didn't fill in the right username and password", "wrong username or password", JOptionPane.ERROR_MESSAGE);
				}
			} else if(button.getText().equals("Guest Account")) {
				inlogview.setVisible(false);
				lobbyview.setVisible(true);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
