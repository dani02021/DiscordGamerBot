package bg.daniel.discordgamerbot;

import java.util.ArrayList;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

import bg.daniel.discordgamerbot.game.Game;
import bg.daniel.discordgamerbot.game.Player;
import bg.daniel.discordgamerbot.game.Request;

public class Utils {
	
	final private static Pattern MENTION = Pattern.compile("<@(\\d+)>");
	final private static Pattern MENTION_SELF = Pattern.compile("<@!(\\d+)>");
	
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static ArrayList<Game> games = new ArrayList<Game>();
	public static ArrayList<Request> requests = new ArrayList<Request>();
	
	public static Timer timer = new Timer();
	
	// Users utils
	public static String mentionUser(String userId) {
		return "<@!" + userId + ">";
	}
	
	public static long getIDByMention(String user) {
		Matcher matcher = MENTION.matcher(user);
		Matcher matcherSelf = MENTION_SELF.matcher(user);
		if(matcher.matches() || matcherSelf.matches()) {
			return Long.parseLong(user.replaceAll("\\D", ""));
		}
		return -1;
	}
	
	public static void makePlayer(User u) {
		if(getPlayer(u) != null)
			return;
			
		Player p = new Player(u.getNicknameMentionTag(), u.getId());
		players.add(p);
	}

	public static void makeGame(Request r, TextChannel channel) {
		Game g = new Game(r.getPlayer1(), r.getPlayer2(), channel);
		if(!games.contains(g)) {
			games.add(g);
		}
	}
	
	public static void makeRequest(Player p1, Player p2) {
		Request r = new Request(p1, p2);
		if(!requests.contains(r)) {
			requests.add(r);
		}
	}
	
	public static Request getRequest(Player p1, Player p2) {
		for(Request r : requests) {
			if(r.getPlayer1().equals(p1) && r.getPlayer2().equals(p2))
				return r;
		}
		
		return null;
	}
	
	public static void removeRequest(Request r) {
		requests.remove(r);
	}
	
	public static void removeGame(Game g) {
		g.stop();
		
		games.remove(g);
	}
	
	public static Player getPlayer(User u) {
		for(Player p : players) {
			if(u.getId() == p.getID())
				return p;
		}
		
		return null;
	}
	
	public static Player getEnemy(Player p, Game g) {
		Player p1 = g.getPlayer1();
		Player p2 = g.getPlayer2();
		
		if(p.equals(p1))
			return p2;
		
		return p1;
	}
	
	public static String getHealthMessage(Player p1, Player p2) {
		return p1.getName() + ": " + p1.getHP() + "\n" + p2.getName() + ": " + p2.getHP();
	}
}
