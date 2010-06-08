package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LeaderDAO {
	
	public static boolean addPlayer(String userName, long rank) {
			try {
				DBmanager dbManager = DBmanager.getInstance();
				Connection conn = dbManager.getConnection();
				
				PreparedStatement pst = conn.prepareStatement("INSERT INTO leaderboard (rank, username,kills, deaths)" +
						"VALUES(?, ?,60,20)");
				
				
				pst.setLong(1,rank);
				pst.setString(2,userName);
				pst.execute();
				
				return true;
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return false;
	}

	public static void resetLeaderTable() {
		try {
			DBmanager dbManager = DBmanager.getInstance();
			Connection conn = dbManager.getConnection();
			
			String qDelete = "DROP TABLE leaderboard";
			PreparedStatement psDelete = conn.prepareStatement(qDelete);
			psDelete.executeUpdate();

			String qCreate = "CREATE TABLE leaderboard (" +
			"rank bigint NOT NULL," +
			"username VARCHAR(255) NOT NULL," +
			"kills bigint NOT NULL DEFAULT 0," +
			"deaths bigint NOT NULL DEFAULT 0," +
			"PRIMARY KEY (username)," +
			"UNIQUE (rank)" +
			")";
			PreparedStatement psCreate = conn.prepareStatement(qCreate);
			psCreate.executeUpdate();
			
			
		} catch ( SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static LBPlayer[] getTop20() {
		LBPlayer[] top20 = new LBPlayer[20];
		try {
			DBmanager dbManager = DBmanager.getInstance();
			Connection conn = dbManager.getConnection();
			
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery("SELECT * FROM leaderboard where deaths > 0 ORDER BY (kills/deaths) desc");
			
			int aantal = 0;
			while( rs.next() && (aantal < 20) ) {
				long rank       = rs.getLong("rank");
				String userName = rs.getString("username");
				long kills      = rs.getLong("kills");
				long deaths     = rs.getLong("deaths");
				
				top20[aantal] = new LBPlayer(rank, userName, kills, deaths);
				aantal++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return top20;
	}
	
	public static LBPlayer getPlayer(String userName) {
		try {
			DBmanager dbManager = DBmanager.getInstance();
			Connection conn = dbManager.getConnection();
			
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM leaderboard WHERE username = ?");
			
			pst.setString(1, userName);
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				long rank       = rs.getLong("rank");
				long kills      = rs.getLong("kills");
				long deaths     = rs.getLong("deaths");
				
				return new LBPlayer(rank, userName, kills, deaths);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
//		LeaderDAO.resetLeaderTable();
//		LeaderDAO.addPlayer("dennis", 2);
//		LeaderDAO.addPlayer("karel",  1);
		for(LBPlayer player : LeaderDAO.getTop20()) {
			System.out.println(player);
		}
		
		System.out.println(LeaderDAO.getPlayer("dennis"));
	}
	
	public static long getLowestRank() {
		try {
			DBmanager dbManager = DBmanager.getInstance();
			Connection conn = dbManager.getConnection();
			
			PreparedStatement pst = conn.prepareStatement("SELECT rank FROM leaderboard ORDER BY rank desc");
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				return rs.getLong("rank");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}

}
