package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDB {
	public static void main(String args[]) throws SQLException
	{
		DBmanager dbManager = DBmanager.getInstance();
		Connection conn = dbManager.getConnection();
		try
		{
			String qDelete = "DROP TABLE Account";
			PreparedStatement psDelete = conn.prepareStatement(qDelete);
			psDelete.executeUpdate();
	
			String qCreate = "CREATE TABLE Account (" +
					"id INT NOT NULL AUTO_INCREMENT," +
					"username VARCHAR(255) NOT NULL," +
					"password VARCHAR(255) NOT NULL," +
					"PRIMARY KEY (id)," +
					"UNIQUE (username)" +
					")";
			PreparedStatement psCreate = conn.prepareStatement(qCreate);
			psCreate.executeUpdate();
		}
		finally {
			conn.close();
		}
	}
}
