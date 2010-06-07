package db;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDAO {
	
	/*
	 * return waarde's:
	 * 1: gebruikersnaam niet ingevuld
	 * 2: wachtwoord niet ingevuld
	 * 3: wachtwoord niet gelijk aan herhaling wachtwoord
	 * 4: gebruikersnaam staat al in database
	 * 5: account aanmaken is gelukt!
	 */
	public int addAccount(String username, String password, String passwordRepeat) throws SQLException, NoSuchAlgorithmException {
		DBmanager dbManager = DBmanager.getInstance();
		Connection conn = dbManager.getConnection();
		try
		{
			String qSelect = "SELECT * FROM Account WHERE username = ?";
			PreparedStatement psSelect = conn.prepareStatement(qSelect);
			psSelect.setString(1, username);
			ResultSet result = psSelect.executeQuery();
			
			if(username.equals("")) {
				return 1;
			} else if(password.equals("")) {
				return 2;
			} else if(!password.equals(passwordRepeat)) {
				return 3;
			} else if(result.next()) {
				return 4;
			}
			
		    MessageDigest m=MessageDigest.getInstance("MD5");
		    m.update(password.getBytes(),0,password.length());

			String qInsert = "INSERT INTO Account (username, password) VALUES(?, ?)";
			PreparedStatement psInsert = conn.prepareStatement(qInsert);
			psInsert.setString(1, username);
			psInsert.setString(2, new BigInteger(1,m.digest()).toString(16));
			psInsert.executeUpdate();
		}
		finally {
			conn.close();
		}
		return 5;
	}
	
	public boolean login(String username, String password) throws SQLException, NoSuchAlgorithmException {
		DBmanager dbManager = DBmanager.getInstance();
		Connection conn = dbManager.getConnection();
		
		try
		{
			MessageDigest m=MessageDigest.getInstance("MD5");
		    m.update(password.getBytes(),0,password.length());
			
			String qSelect = "SELECT * FROM Account WHERE username = ? AND password = ?";
			PreparedStatement psSelect = conn.prepareStatement(qSelect);
			psSelect.setString(1, username);
			psSelect.setString(2, new BigInteger(1,m.digest()).toString(16));
			ResultSet result = psSelect.executeQuery();
			while (result.next()) {
				return true;
			}
		}
		finally {
			conn.close();
		}
		return false;
	}
	
	public void getAccounts() throws SQLException {
		DBmanager dbManager = DBmanager.getInstance();
		Connection conn = dbManager.getConnection();
		try
		{
		    String qSelect = "SELECT * FROM Account";
			PreparedStatement psSelect = conn.prepareStatement(qSelect);
			ResultSet result = psSelect.executeQuery();
			while (result.next())
			{
				System.out.println(result.getString(2) + ", " + result.getString(3));
			}
		}
		finally {
			conn.close();
		}
	}
	
	public static void main(String args[]) throws NoSuchAlgorithmException, SQLException
	{
		AccountDAO accountDao = new AccountDAO();
		accountDao.addAccount("gromberg10", "lala", "lala");
		
		accountDao.getAccounts();
		if(accountDao.login("gromberg5", "lalala")) {
			System.out.println("test");
		}
	}
}
