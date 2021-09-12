package bg.daniel.discordgamerbot.game;

import bg.daniel.discordgamerbot.Utils;

public class Request {
	Player p1, p2;
	boolean accept, declined;
	
	public Request(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public void accept() { accept = true; }
	
	public void decline() { declined = true; Utils.removeRequest(this); }
	
	public void timeout() { }
	
	public Player getPlayer1() {
		return p1;
	}
	
	public Player getPlayer2() {
		return p2;
	}
	
	public boolean isAccepted() {
		return accept;
	}
	
	public boolean isDeclined() {
		return declined;
	}
}