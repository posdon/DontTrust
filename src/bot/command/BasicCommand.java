package bot.command;

import bot.MainBot;
import bot.command.basic.Command;
import bot.command.basic.ExecutorType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;

public class BasicCommand {
	
	protected MainBot mainRef;
	
	public BasicCommand(MainBot mainRef) {
		this.mainRef = mainRef;
	}
	
	@Command(name="stop", type=ExecutorType.CONSOLE, description="Stop the bot")
	public void stop() {
		this.mainRef.stop();
	}
	
	protected void sendMessage(String message, MessageChannel channel) {
		if(message == null || "".equals(message)) return;
		channel.sendMessage(message).complete();
	}
	
	protected void sendEmbed(EmbedBuilder builder, MessageChannel channel) {
		if(builder.isEmpty()) return;
		channel.sendMessage(builder.build()).queue();
	}
}
