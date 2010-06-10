package masterserver.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import masterserver.model.GameServer;

public class GameServerDAO {
	
	public static ArrayList<GameServer> getGameServers() throws SQLException {
		DBmanager dbManager = DBmanager.getInstance();
		Connection conn = dbManager.getConnection();
		ArrayList<GameServer> serverlist = new ArrayList<GameServer>();
	    String qSelect = "SELECT ip, name FROM gameservers";
		PreparedStatement psSelect = conn.prepareStatement(qSelect);
		ResultSet result = psSelect.executeQuery();
		while (result.next()) {
			serverlist.add(new GameServer(result.getString("ip"), result.getString("name")));
		}
		return serverlist;
	}
}
