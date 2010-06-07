package client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class LeaderView extends JFrame{
	
	public LeaderView(){
	
	this.setLayout(new BorderLayout());
	this.setBackground(Color.BLACK);
	this.setSize(400,300);
	this.setTitle("LeaderBoard");
		
	String [] columnNames = {"Rank", "Name"};

	Object [][]data = {
			{new Integer(1),"Dennis", },
			{new Integer(2),"Jeffrey"},
			{new Integer(3),"Marco"},
			{new Integer(4),"Jan"},
			{new Integer(5),"Marijn"},
			{new Integer(6),"Marco"},
			{new Integer(7),"Johannes"},
			{new Integer(8),"Henri"},
			{new Integer(9),"Bram"},
			{new Integer(10),"Gerben"},
			{new Integer(11),"Joep"},
			{new Integer(12),"Jochem"},
			{new Integer(13),"Nick"},
			{new Integer(14),"Freek"},
			{new Integer(15),"Douwe"},
			{new Integer(16),"Arthur"},
			{new Integer(17),"Etto"},
			{new Integer(18),"Ruud"},
			{new Integer(19),"Ernst"},
			{new Integer(20),"Jelle"}
	};

    final JTable table = new JTable(data, columnNames){
    	public boolean isCellEditable(int row, int column)
		{
			return false;
		}
	};
	
    table.setPreferredScrollableViewportSize(new Dimension(500, 70));
    table.setFillsViewportHeight(true);
    
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane);
    
    JLabel label = new JLabel("Top 20 Players:");
    JButton button = new JButton("Back");
  
    this.add(label, BorderLayout.NORTH);
    this.add(scrollPane, BorderLayout.CENTER);
    this.add(button,BorderLayout.SOUTH);
    
	}
}
