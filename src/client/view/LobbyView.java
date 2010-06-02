package client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import server.model.GameServer;


public class LobbyView extends JPanel {
	private GameServer server;
	
	public LobbyView(GameServer server) {
		this.server = server;
		
		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);
		
		String[] columnNames = {"Naam van Server",
                "Players",
                "Gamemode",
                "Map",
                "Ping"};
		
		Object[][] data = {
			    {"servertest", "1/16",
			     "Team DeathMatch", "tdm_1", new Integer(15)},
			    {"servertest2", "1/8",
			     "Team DeathMatch", "tdm_1", new Integer(30)},
			    {"servertest3", "1/4",
			     "Team DeathMatch", "tdm_1", new Integer(45)},
			    {"servertest4", "1/8",
			     "Team DeathMatch", "tdm_1", new Integer(100)},
			    {"servertest5", "1/8",
			     "Team DeathMatch", "tdm_1", new Integer(200)}
			};


		JTable table = new JTable(data, columnNames) {
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
        table.setFillsViewportHeight(true);
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        
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
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));
        JButton buttonConnect = new JButton("Connect");
        buttonConnect.setSize(100, 15);
        JButton buttonSettings = new JButton("Settings");
        buttonSettings.setSize(300, 15);
        JButton buttonQuit = new JButton("Quit");
        buttonQuit.setSize(100, 15);
        panel.add(buttonConnect);
        panel.add(buttonSettings);
        panel.add(buttonQuit);
        
        table.setBackground(Color.BLACK);
        table.setForeground(Color.WHITE);
        
        this.add(panel, BorderLayout.NORTH);
        this.add(table, BorderLayout.CENTER);
	}
}
