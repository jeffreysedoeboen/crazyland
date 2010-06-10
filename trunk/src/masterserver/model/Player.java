package masterserver.model;

public class Player {
	private String name;
	private int rank;
	private int kills;
	private int deaths;
	
	public Player (String name, int rank, int kills, int deaths) {
		this.name = name;
		this.rank = rank;
		this.kills = kills;
		this.deaths = deaths;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	
	
}
