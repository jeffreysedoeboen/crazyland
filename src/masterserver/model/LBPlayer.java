package masterserver.model;

public class LBPlayer {
	private long rank, kills, deaths;
	private String userName;
	
	public LBPlayer(long rank, String userName, Long kills, long deaths) {
		this.rank 		= rank;
		this.userName 	= userName;
		this.kills 		= kills;
		this.deaths 	= deaths;
	}

	public long getRank() {
		return rank;
	}

	public void setRank(long rank) {
		this.rank = rank;
	}

	public long getKills() {
		return kills;
	}

	public void setKills(long kills) {
		this.kills = kills;
	}

	public long getDeaths() {
		return deaths;
	}

	public void setDeaths(long deaths) {
		this.deaths = deaths;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String toString() {
		return "" + rank + " " + userName + " " + kills + " " + deaths + " " + kills/deaths;
	}

	public float getKd() {
		return kills/deaths;
	}

}
