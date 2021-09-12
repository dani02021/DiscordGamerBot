package bg.daniel.discordgamerbot.game;

import org.javacord.api.entity.channel.TextChannel;

import bg.daniel.discordgamerbot.Utils;

public class Game {
	private Player p1, p2;
	public Player turn;
	private boolean start;
	
	private TextChannel channel;
	
	public Game() { }
	
	public Game(Player p1, Player p2, TextChannel channel) {
		this.p1 = p1;
		this.p2 = p2;
		this.channel = channel;
		
		p1.setGame(this);
		p2.setGame(this);
		
		turn = p2;
	}
	
	public void start() {
		if(p1 == null || p2 == null) {
			System.out.println("Error: Can't start the game with less than 2 players!");
			return;
		}
		
		start = true;
	}
	
	// Emergency stop
	public void stop() {
		start = false;
		
		p1.removeFromGame();
		p2.removeFromGame();
	}
	
	// Game finished
	public void finish() {
		start = false;
		
		p1.removeFromGame();
		p2.removeFromGame();
		
		Utils.games.remove(this);
		
		channel.sendMessage("Game ended! Winner: " + getWiner().getName());
	}
	
	public void nextTurn() {
		if(turn.equals(p1)) {
			turn = p2;
		} else turn = p1;
	}
	
	public void setPlayer1(Player p) {
		p1 = p;
		p1.setGame(this);
	}
	
	public void setPlayer2(Player p) {
		p2 = p;
		p2.setGame(this);
	}
	
	public Player getPlayer1() {
		return p1;
	}
	
	public Player getPlayer2() {
		return p2;
	}
	
	public boolean isStarted() {
		return start;
	}
	
	public Player getWiner() {
		if(p1.getHP() > 0) {
			return p1;
		}
		
		return p2;
	}
}
