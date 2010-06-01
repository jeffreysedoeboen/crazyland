package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonController implements ActionListener {
	public void actionPerformed(ActionEvent ae) {
		JButton button = (JButton) ae.getSource();
		if( button.getText().equals("Sign up!")){
			
		}
	}
}
