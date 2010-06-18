package client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	private WorldView view;
	private boolean leaving = false;
	private boolean wantsToLeave = false;
	private Timer leaveTimer;
	private int leaveTime = 0;
	
	public GameFrame(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void setWorldView(WorldView view) {
		this.view = view;
		add(view);
	}
	
	public void setLeaving(){
		if(wantsToLeave){
			leaving = true;
			dispose();
		}
	}
	
	@Override
	public void dispose() {
		if(leaving){
			view.setShowLeaveTime(false);
			view.logoff();
			super.dispose();
		}
		wantsToLeave = !wantsToLeave;
		if(wantsToLeave){
			leaveTime = 10;
			view.setLeaveTime(leaveTime);
			view.setShowLeaveTime(true);
			if(leaveTime <= 0){
				setLeaving();
				view.setShowLeaveTime(false);
				dispose();
			}
			leaveTimer = new Timer(1000,timeLeave);
			leaveTimer.start();
		}else if(leaveTimer != null){
			view.setShowLeaveTime(false);
			leaveTimer.stop();
			leaveTimer = null;
		}
	}

	private ActionListener timeLeave = new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			leaveTime--;
			view.setLeaveTime(leaveTime);
			view.setShowLeaveTime(true);
			if(leaveTime <= 0){
				setLeaving();
				view.setShowLeaveTime(false);
				dispose();
			}
		}
	};
}
