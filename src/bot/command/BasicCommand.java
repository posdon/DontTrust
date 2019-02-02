package bot.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import bot.MainBot;
import bot.command.basic.Command;
import bot.command.basic.ExecutorType;
import exception.MessageOver2000Exception;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;

public class BasicCommand {
	
	protected MainBot mainRef;
	private final int MESSAGE_LENGHT_MAX = 2000;
	
	public BasicCommand(MainBot mainRef) {
		this.mainRef = mainRef;
	}
	
	@Command(name="stop", type=ExecutorType.CONSOLE, description="Stop the bot")
	public void stop() {
		this.mainRef.stop();
	}
	
	protected void sendMessage(String message, MessageChannel channel) throws MessageOver2000Exception {
		if(message == null || "".equals(message)) return;
		List<String> splittedMessages = splitMessageByLine(message);
		for(String splittedMessage : splittedMessages) channel.sendMessage(splittedMessage).queue();
	}

	private List<String> splitMessageByLine(String message) throws MessageOver2000Exception {
		ArrayList<String> result = new ArrayList<String>();
		String[] messageLines = message.split(System.getProperty("line.separator"));
		String tempLine = "";
		for(String line : messageLines) {
			if(line.length() >= MESSAGE_LENGHT_MAX) throw new MessageOver2000Exception(line);
			if(tempLine.length() + line.length() >= MESSAGE_LENGHT_MAX) {
				result.add(tempLine);
				tempLine = line;
			}else {
				tempLine+= line;
			}			
		}
		if(StringUtils.isNotBlank(tempLine)) result.add(tempLine);
		return result;
	}
	
	protected void sendEmbed(EmbedBuilder builder, MessageChannel channel) {
		if(builder.isEmpty()) return;
		channel.sendMessage(builder.build()).queue();
	}
}
