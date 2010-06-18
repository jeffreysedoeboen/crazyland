package masterserver.db;

import java.sql.*;

public class DBmanager {
	private static DBmanager uniqueInstance = null;
	private static Connection con = null;

	/**
	 * Er word gekeken of er geen connectie is met de database, zoniet dan word
	 * de methode getCon() aangeroepen
	 */
	private DBmanager() {
		if (!dbExists()) {
			try {
				con = getCon();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Deze methode zorgt dat er maar één verbinding met de database mag zijn.
	 * Als er nog geen verbinding is wordt die hier aangemaakt anders wordt de
	 * bestaande verbinding gereturned
	 * 
	 * @return uniqueInstance De dbmanager
	 */
	public static synchronized DBmanager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new DBmanager();
		}
		return uniqueInstance;
	}
	
	
	/**
	 * Kijkt of er een verbinding is met de database
	 * @return exists: true als er een verbindig is : false als dat niet zo is
	 */
	private Boolean dbExists() {
		Boolean exists = false;
		if (con != null) {
			exists = true;
		}
		return exists;
	}

	
	/**
	 * Sluit de verbinding met de database helemaal af
	 */
	public void close() {
		try {
			con.close();
			uniqueInstance = null;
			con = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return con;
	}

	/**
	 * Deze methode zorgt voor het verbinding maken met de database
	 * @return De connectie met de database
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private static Connection getCon() throws SQLException,
			ClassNotFoundException {
//		String driver = "org.gjt.mm.mysql.Driver";
//		String url = "jdbc:mysql://www.freesql.org/crazyland?";
//		String username = "crazyland";
//		String password = "EIN2grules";
		String driver = "org.gjt.mm.mysql.Driver";
		String url = "jdbc:mysql://localhost/crazyland?";
		String username = "root";
		String password = "";
		try {
			Class.forName(driver).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection(url, username, password);
	}
}