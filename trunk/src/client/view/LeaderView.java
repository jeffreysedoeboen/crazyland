package client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import db.LBPlayer;
import db.LeaderDAO;

@SuppressWarnings("serial")
public class LeaderView extends JFrame{

	private JButton backButton;
	private JTable table;
	private String [] columnNames = {"Rank", "Name","Kills","Deaths"};
	private JScrollPane scrollPane;

	public LeaderView(){

		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);
		this.setSize(400,300);
		this.setTitle("LeaderBoard");

		updateTable();
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		JLabel label = new JLabel("Top 20 Players:");
		backButton = new JButton("Back");

		this.add(label, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(backButton,BorderLayout.SOUTH);

	}

	@Override
	public void setVisible(boolean b) {
		if (b)
			updateTable();
		super.setVisible(b);
	};
	
	public void updateTable() { //TODO update de tabel nog niet
		Object[][] data = new  Object[21][21];

		for(int i = 0; i <= 19; i++ ) {
			LBPlayer player = LeaderDAO.getTop20()[i];
			if(player != null) {
				String name  = player.getUserName();
				long kills   = player.getKills();
				long deaths  = player.getDeaths();
				data[i][0] =  i + 1;
				data[i][1] =  name;
				data[i][2] =  kills;
				data[i][3] =  deaths;
			}
		}
		
		table = new JTable(data, columnNames){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		
		scrollPane = new JScrollPane(table);
		
		scrollPane.updateUI();
		add(scrollPane);
	}

	public void addListener(ActionListener listener) {
		backButton.addActionListener(listener);
	}
}
