package client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.view.InlogView;
import client.view.SignupView;

import db.AccountDAO;


public class ButtonController implements ActionListener {
	
	private SignupView signupview;
	private InlogView inlogview;
	private AccountDAO accountDao;
	
	public ButtonController(SignupView signupview, InlogView inlogview, AccountDAO accountDao) {
		this.signupview = signupview;
		this.inlogview = inlogview;
		this.accountDao = accountDao;
	}
	
	public void actionPerformed(ActionEvent ae) {
		try {
			JButton button = (JButton) ae.getSource();
			if(button.getText().equals("Sign up!")){
				String filledInUsername = signupview.getUsernameinput().getText();
				String filledInPassword = signupview.getPasswordinput().getText();
				String filledInPasswordcheck = signupview.getPasswordcheckinput().getText();
				if(accountDao.addAccount(filledInUsername, filledInPassword, filledInPasswordcheck)) {
					JOptionPane.showMessageDialog(signupview,
						    "Eggs are not supposed to be green.");
					accountDao.getAccounts();
				}
			} else if(button.getText().equals("Create Account")) {
				this.signupview = new SignupView();
				signupview.setVisible(true);
				signupview.addListener(this);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
