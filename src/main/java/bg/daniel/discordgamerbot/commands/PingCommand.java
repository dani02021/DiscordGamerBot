package bg.daniel.discordgamerbot.commands;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.stereotype.Component;

@Component
public class PingCommand implements MessageCreateListener {

	@Override
	public void onMessageCreate(MessageCreateEvent e) {
		if(e.getMessageContent().equalsIgnoreCase("!ping"))
			e.getChannel().sendMessage("Pong! :)");
	}

}
