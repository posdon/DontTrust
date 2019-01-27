package bot;

import bot.command.BasicCommand;
import bot.command.BestiaryBotCommand;
import bot.command.CommandManager;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

public class BotListener implements EventListener {

	protected CommandManager commandManager;
	
	public BotListener(MainBot mainRef) {
		commandManager = CommandManager.getInstance(mainRef);
		commandManager.registerCommands(new BasicCommand(mainRef), new BestiaryBotCommand(mainRef));
	}
		
	@Override
	public void onEvent(Event event) {
		if(event instanceof MessageReceivedEvent) onMessage((MessageReceivedEvent) event);
	}
	
	
	private void onMessage(MessageReceivedEvent event) {
		if(event.getAuthor().equals(event.getJDA().getSelfUser())) return;

		String message = event.getMessage().getContentDisplay();
		if(message.startsWith(commandManager.getTag())) {
			message = message.replaceFirst(commandManager.getTag(), "");
			commandManager.commandUser(event.getAuthor(), message, event.getMessage());
		}
	}
	
	public void onConsole(String message) {
		commandManager.commandConsole(message);
	}
}
