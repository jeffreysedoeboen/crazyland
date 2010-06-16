package masterserver.model;

public class GameServer {
	private String name;
	private String ip;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public GameServer (String ip, String name) {
		this.ip = ip;
		this.name = name;
	}
}
