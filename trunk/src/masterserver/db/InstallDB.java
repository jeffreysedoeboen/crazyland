package masterserver.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InstallDB {
	public static void main(String args[]) throws SQLException
	{		
		DBmanager dbManager = DBmanager.getInstance();
		Connection conn = dbManager.getConnection();
		try
		{
			String qCreate = "CREATE TABLE Account (" +
					"id INT NOT NULL AUTO_INCREMENT," +
					"username VARCHAR(255) NOT NULL," +
					"password VARCHAR(255) NOT NULL," +
					"PRIMARY KEY (id)," +
					"UNIQUE (username)" +
					")";
			PreparedStatement psCreate = conn.prepareStatement(qCreate);
			psCreate.executeUpdate();
			
			qCreate = "CREATE TABLE leaderboard (" +
			"rank bigint NOT NULL," +
			"username VARCHAR(255) NOT NULL," +
			"kills bigint NOT NULL DEFAULT 0," +
			"deaths bigint NOT NULL DEFAULT 0," +
			"PRIMARY KEY (username)," +
			"UNIQUE (rank)" +
			")";
			psCreate = conn.prepareStatement(qCreate);
			psCreate.executeUpdate();
			
			qCreate = "CREATE TABLE gameservers (" +
			"name varchar(255) NOT NULL," +
			"ip VARCHAR(15) NOT NULL," +
			"PRIMARY KEY (ip)," +
			"UNIQUE (ip)" +
			")";
			psCreate = conn.prepareStatement(qCreate);
			psCreate.executeUpdate();
		}
		finally {
			conn.close();
		}
	}
}
