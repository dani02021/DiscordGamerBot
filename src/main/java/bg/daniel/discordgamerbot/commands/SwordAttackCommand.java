package bg.daniel.discordgamerbot.commands;


import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.stereotype.Component;

import bg.daniel.discordgamerbot.Utils;
import bg.daniel.discordgamerbot.game.Player;

@Component
public class SwordAttackCommand implements MessageCreateListener {

	@Override
	public void onMessageCreate(MessageCreateEvent e) {
		String cmd = e.getMessageContent().toLowerCase();
		
		if(cmd.equalsIgnoreCase("!swordattack")) {
			Player p = Utils.getPlayer(e.getMessageAuthor().asUser().get());
			
			if(p.getGame() == null) {
				e.getChannel().sendMessage("You are not in a game!");
				return;
			}
			
			Player enemy = Utils.getEnemy(p, p.getGame());
			
			if(!p.getGame().turn.equals(p)) {
				e.getChannel().sendMessage("It's not your turn!");
				return;
			}
			
			if(!p.isInGame()) {
				e.getChannel().sendMessage("You are not in a game!");
				return;
			}
			
			short attackQuo = (short) ((Math.random() * 3) + 1);
			int attack = 50 * attackQuo;
			
			p.getGame().nextTurn();
			
			enemy.damage(attack);
			
			e.getChannel().sendMessage(e.getMessageAuthor().getName() + " attacked! " + attack + " dmg!");
			
			e.getChannel().sendMessage(Utils.getHealthMessage(p, enemy));
		}
	}

}
