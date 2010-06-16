package masterserver.db;

import java.sql.*;

public class DBmanager {
	private static DBmanager uniqueInstance = null;
	private static Connection con = null;

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

	public static synchronized DBmanager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new DBmanager();
		}
		return uniqueInstance;
	}

	private Boolean dbExists() {
		Boolean exists = false;
		if (con != null) {
			exists = true;
		}
		return exists;
	}

	public void close() {
		try {
			con.close();
			uniqueInstance = null;
			con = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection(){
		return con;
	}

	private static Connection getCon() throws SQLException,
	ClassNotFoundException {
		String driver = "org.gjt.mm.mysql.Driver";
		String url = "jdbc:mysql://127.0.0.1/crazyland?";
		String username = "root";
		String password = "vertrigo";
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