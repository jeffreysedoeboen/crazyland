package client.view;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	private WorldView view;
	
	public GameFrame(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void setWorldView(WorldView view) {
		this.view = view;
		add(view);
	}
	
	@Override
	public void dispose() {
		view.logoff();
		super.dispose();
	}

}
