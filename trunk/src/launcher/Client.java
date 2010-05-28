package launcher;


import javax.swing.JApplet;

import model.World;

import view.WorldView;

public class Client extends JApplet{

	public void init() {
		// TODO Auto-generated method stub
		setContentPane(new WorldView(new World()));
		setSize(320, 320);
		setVisible(true);

	}

}
