package bot.command;

import java.lang.reflect.Method;

import bot.MainBot;
import bot.command.basic.Command;
import bot.command.basic.ExecutorType;
import net.dv8tion.jda.core.entities.MessageChannel;

public class BestiaryBotCommand extends BasicCommand {

	public BestiaryBotCommand(MainBot mainRef) {
		super(mainRef);
	}

	private String generateHelperMessage() {
		String message = "This is all the command enable :";
		for(Method method : this.getClass().getMethods()) {
			if(method.isAnnotationPresent(Command.class)) {
				Command command = method.getAnnotation(Command.class);
				if(ExecutorType.BOT.equals(command.type())) message += "\n - !"+command.name()+" : "+command.description();
			}
		}
		return message;
	}
	
	@Command(name="help", type=ExecutorType.BOT, description="List all the command helper")
	public void helper(MessageChannel mChannel) {
		String helperMessage = generateHelperMessage();
		sendMessage(helperMessage, mChannel);
	}

}
