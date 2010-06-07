package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.Properties;

public class DBmanager {
	private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String FS = System.getProperty("file.separator");
    private static final String URL = "jdbc:derby:" + System.getProperty("user.dir") + FS + "database" + FS + "crazyland_db;create=true" ;
	private static DBmanager uniqueInstance=null;
	private static Connection con = null ;
	
	private DBmanager()
	{
		if(!dbExists())
		{
			System.err.println("de database bestaat niet....");
		}
	}
	
	public static synchronized DBmanager getInstance()
	{
		if (uniqueInstance==null)
		{
			uniqueInstance = new DBmanager();
		}
		return uniqueInstance;
	}
	
	private Boolean dbExists()
	{
		try
		{
			getConnection();
		}
		catch(SQLException se)
		{
			return false;
		}
		return(true) ;
	}
	
	public void close()
	{
		try
		{
			con.close();
			uniqueInstance=null;
			con=null;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printSQLException(SQLException se) {
        while(se != null) {

            System.out.print("SQLException: State:   " + se.getSQLState());
            System.out.println("Severity: " + se.getErrorCode());
            System.out.println(se.getMessage());            
            
            se = se.getNextException();
        }
    }
	
	public Connection getConnection() throws SQLException
	{
		try {
            Class.forName(DRIVER) ;
            con = DriverManager.getConnection(URL);

		} catch (SQLException se) {
            printSQLException(se) ;
        } catch(ClassNotFoundException e){
            System.out.println("JDBC Driver " + DRIVER + " not found in CLASSPATH") ;
        }
        return con;
	}
}
