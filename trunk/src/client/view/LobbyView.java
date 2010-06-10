package client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import client.connection.MasterConnection;
import client.model.GameServer;


@SuppressWarnings("serial")
public class LobbyView extends JFrame {
	
	private JButton buttonConnect, buttonLeader, buttonSettings, buttonQuit;
	private String userName = "";
	private MasterConnection master;
	private ArrayList<GameServer> gameservers;
	private JTable table;
	
	public LobbyView(MasterConnection master) {
		
		this.master = master;
		
		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);
		this.setSize(640, 500);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4));
        buttonConnect = new JButton("Connect");
        buttonLeader = new JButton("Leaderboard");
        buttonSettings = new JButton("Settings");
        buttonQuit = new JButton("Quit");
        panel.add(buttonConnect);
        panel.add(buttonLeader);
        panel.add(buttonSettings);
        panel.add(buttonQuit);
        
        this.add(panel, BorderLayout.NORTH);
        
	}
	
	@Override
	public void setVisible(boolean b) {
		if (b) {
			updateTable();
		}
		super.setVisible(b);
	};
	public void updateTable() {
		String[] columnNames = {"Naam van Server",
                "Players",
                "Gamemode",
                "Map",
                "Ping"};
		
		gameservers = master.getGameServers();
		
		Object[][] data = new Object[gameservers.size()][5];
		for(int i = 0; i < gameservers.size(); i++) {
			//ToDo andere gegevens opvragen
			data[i][0] = gameservers.get(i).getName(); 
			data[i][1] = "xx";
			data[i][2] = "mode";
			data[i][3] = "map";
			data[i][4] = 0;
		}

		table = new JTable(data, columnNames) {
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
        table.setFillsViewportHeight(true);
        
        table.getTableHeader().setReorderingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        TableColumn column = null;
        for (int i = 0; i < 5; i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(300);
            } else if(i == 2){
                column.setPreferredWidth(150);
            } else {
            	column.setPreferredWidth(80);
            }
        }
        
        table.setBackground(Color.BLACK);
        table.setForeground(Color.WHITE);
        this.add(scrollPane, BorderLayout.CENTER);
	}
	
	public void addListener(ActionListener listener) {
		buttonConnect.addActionListener(listener);
		buttonLeader.addActionListener(listener);
		buttonQuit.addActionListener(listener);
	}
	
	public void setUserName(String name) {
		this.userName = name;
	}
	public String getUserName() {
		return userName;
	}

	public GameServer getSelectedGameserver() {
		if (table.getSelectedRow() >= 0)
			return gameservers.get(table.getSelectedRow());
		return null;
	}
}
