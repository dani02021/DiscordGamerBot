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
public class AcceptCommand implements MessageCreateListener {
	final private static Pattern accept = Pattern.compile("!accept (<@(\\d+)>)");
	final private static Pattern acceptSelf = Pattern.compile("!accept (<@!(\\d+)>)");

	@Override
	public void onMessageCreate(MessageCreateEvent e) {
		String cmd = e.getMessageContent().toLowerCase();
		
		if(cmd.startsWith("!accept")) {
			Matcher matcher = accept.matcher(e.getMessageContent().toLowerCase());
			Matcher matcherSelf = acceptSelf.matcher(e.getMessageContent().toLowerCase());
			
			boolean mA = matcher.matches();
			boolean mB = matcherSelf.matches();
			System.out.println(e.getMessageContent().toLowerCase());
			
			if(mA || mB) {
				try {
					String user;
					
					if(mA)
					user = matcher.group(1);
					else user = matcherSelf.group(1);

					Utils.makePlayer(e.getApi().getUserById(Utils.getIDByMention(user)).get());
					Utils.makePlayer(e.getMessageAuthor().asUser().get());
					
					Player p1 = Utils.getPlayer(e.getMessageAuthor().asUser().get());
					Player p2 = Utils.getPlayer(e.getApi().getUserById(Utils.getIDByMention(user)).get());
					
					Request r = Utils.getRequest(p1, p2);
					if(r != null) {
						e.getChannel().sendMessage(Utils.mentionUser(e.getMessageAuthor().getIdAsString()) + " has accepted the request by " + user + " The game is starting soon!");
						r.accept();
						
						Utils.timer.schedule(new TimerTask() {
							
							@Override
							public void run() {
								e.getChannel().sendMessage(Utils.mentionUser(e.getMessageAuthor().getIdAsString()) + " VS " + user + " !!!");
								Utils.makeGame(r, e.getChannel());
								p1.getGame().start();
							}
						}, 1000L * 5L); // 3 secs
					} else {
						e.getChannel().sendMessage(Utils.mentionUser(e.getMessageAuthor().getIdAsString()) + " you don't have any challenge requests from " + user + "!");
					}
				} catch (InterruptedException | ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				e.getChannel().sendMessage("Wrong usage! Please use !accept @user");
			}
		}
	}

}
