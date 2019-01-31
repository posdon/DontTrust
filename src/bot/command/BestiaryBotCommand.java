package bot.command;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.Collection;

import bot.MainBot;
import bot.command.basic.Command;
import bot.command.basic.ExecutorType;
import model.Bestiary;
import model.Creature;
import net.dv8tion.jda.core.EmbedBuilder;
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
	
	// TODO : Page system to limit overload
	@Command(name="listAll", description="List all the creature in the bestiary")
	public void listAllCreature(MessageChannel mChannel) {
		Collection<Creature> creatures = Bestiary.bestiary.getAllCreatures();
		String message = "This is all creatures :";
		for(Creature creature : creatures) {
			message += "\n - "+creature.getName();
		}
		sendMessage(message, mChannel);
	}
	
	// TODO : Page system to limit overload
	@Command(name="info", description="Get the detail of a creature", type=ExecutorType.BOT)
	public void infoCreature(MessageChannel mChannel, String creatureName) {
		Creature creature = Bestiary.bestiary.getCreature(creatureName);
		EmbedBuilder embedBuilder = new EmbedBuilder().setAuthor(creature.getName());
		Color color = null;
		switch(creature.getFaction()) {
			case AGGRESSIVE :
				color = Color.RED;
				break;
			case NEUTRAL :
				color = Color.GRAY;
				break;
			case PEACEFUL :
				color = Color.GREEN;
				break;
			case DEVIANT :
				color = Color.BLUE;
				break;
			default :
				color = Color.WHITE;
				break;
		}
		embedBuilder.setColor(color);
		sendEmbed(embedBuilder, mChannel);
	}
	
	@Command(name="help", type=ExecutorType.BOT, description="List all the command helper")
	public void helper(MessageChannel mChannel) {
		String helperMessage = generateHelperMessage();
		sendMessage(helperMessage, mChannel);
	}

}
