package masterserver.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {
	
	/**
	 * @param passwordHash de hashcode van het wachtwoord
	 * @return :
	 * 1: gebruikersnaam niet ingevuld
	 * 2: wachtwoord niet ingevuld
	 * 3: wachtwoord niet gelijk aan herhaling wachtwoord
	 * 4: gebruikersnaam staat al in database
	 * 5: account aanmaken is gelukt!
	 */
	public static boolean addAccount(String username, String passwordHash) {
		DBmanager dbManager = DBmanager.getInstance();
		Connection conn = dbManager.getConnection();
		try {
			String qSelect = "SELECT * FROM Account WHERE username = ?";
			PreparedStatement psSelect = conn.prepareStatement(qSelect);
			psSelect.setString(1, username);
			ResultSet result;
			result = psSelect.executeQuery();
			if(result.next()) {
				return false;
			}

			String qInsert = "INSERT INTO Account (username, password) VALUES(?, ?)";
			PreparedStatement psInsert = conn.prepareStatement(qInsert);
			psInsert.setString(1, username);
			psInsert.setString(2, passwordHash);
			psInsert.executeUpdate();
			
			LeaderDAO.addPlayer(username, LeaderDAO.getLowestRank() + 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static boolean login(String username, String passwordHash) throws SQLException {
		DBmanager dbManager = DBmanager.getInstance();
		Connection conn = dbManager.getConnection();
		
		String qSelect = "SELECT * FROM Account WHERE username = ? AND password = ?";
		PreparedStatement psSelect = conn.prepareStatement(qSelect);
		psSelect.setString(1, username);
		psSelect.setString(2, passwordHash);
		ResultSet result = psSelect.executeQuery();
		while (result.next()) {
			return true;
		}
		return false;
	}
}
