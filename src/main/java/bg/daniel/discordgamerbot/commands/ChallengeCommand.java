package bg.daniel.discordgamerbot.commands;

import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.stereotype.Component;

import bg.daniel.discordgamerbot.Utils;
import bg.daniel.discordgamerbot.game.Player;
import bg.daniel.discordgamerbot.game.Request;

@Component
public class ChallengeCommand implements MessageCreateListener {
	
	final private static Pattern challengeSelf = Pattern.compile("!challenge (<@!(\\d+)>)");
	final private static Pattern challenge = Pattern.compile("!challenge (<@(\\d+)>)");

	@Override
	public void onMessageCreate(MessageCreateEvent e) {
		String cmd = e.getMessageContent().toLowerCase();
		
		if(cmd.startsWith("!challenge")) {
			System.out.println(cmd);
			Matcher matcherSelf = challengeSelf.matcher(e.getMessageContent().toLowerCase());
			Matcher matcher = challenge.matcher(cmd);
			
			boolean mA = matcherSelf.matches();
			boolean mB = matcher.matches();
			
			if(mA || mB) {
				try {
					String user;
					if(mA)
					user = matcherSelf.group(1);
					else user = matcher.group(1);
					
					Utils.makePlayer(e.getApi().getUserById(Utils.getIDByMention(user)).get());
					Utils.makePlayer(e.getMessageAuthor().asUser().get());
					
					Player p1 = Utils.getPlayer(e.getApi().getUserById(Utils.getIDByMention(user)).get());
					Player p2 = Utils.getPlayer(e.getMessageAuthor().asUser().get());
					
					if(Utils.getRequest(p1, p2) != null) {
						e.getChannel().sendMessage(Utils.mentionUser(e.getMessageAuthor().getIdAsString()) + " you already requested a game with " + user + "!");
						return;
					}
					
					e.getChannel().sendMessage(user + ", you have been challenged by " + Utils.mentionUser(e.getMessageAuthor().getIdAsString()) + "! Do you accept?");
					
					Utils.makeRequest(p1, p2);
					Request r = Utils.getRequest(p1, p2);
					
					Utils.timer.schedule(new TimerTask() {
						
						@Override
						public void run() {
							if(r.isAccepted() || r.isDeclined())
								return;
							
							e.getChannel().sendMessage("Timeout! " + user + " did not accept " + Utils.mentionUser(e.getMessageAuthor().getIdAsString()) + " offer.");
							r.timeout();
						}
					}, 1000L * 60L * 1L); // 5 mins
				} catch (InterruptedException | ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				e.getChannel().sendMessage("Wrong usage! Please use !challenge @user");
			}
		}
	}

}
