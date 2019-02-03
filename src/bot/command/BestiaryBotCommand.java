package bot.command;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import bot.MainBot;
import bot.command.basic.Command;
import bot.command.basic.ExecutorType;
import exception.MessageOver2000Exception;
import model.Bestiary;
import model.Creature;
import model.Family;
import model.FamilyBook;
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
	@Command(name="listAllFamily", description="List all the creature in the bestiary")
	public void listAllFamily(MessageChannel mChannel) throws MessageOver2000Exception {
		Collection<Family> families = FamilyBook.familyBook.getFamilies();
		String message = "This is all families :";
		for(Family family : families) {
			message += "\n - "+family.getFamilyName();
		}
		try {
			sendMessage(message, mChannel);
		} catch (MessageOver2000Exception e) {
			sendMessage("It looks like a family have a name which is longer than 2000 caracters... What a troller...", mChannel);
		}
	}
	
	// TODO : Page system to limit overload
		@Command(name="listInFamily", description="Give a family name, list all the creature in this family")
		public void listAllFamily(MessageChannel mChannel, String familyName) throws MessageOver2000Exception {
			Family family = FamilyBook.familyBook.getFamily(familyName);
			if(family == null) {
				sendMessage("The family '"+familyName+"' doesn't exist.", mChannel);
				return;
			}
			List<String> creaturesNames = FamilyBook.familyBook.getCreaturesFromFamily(family);
			String message = "This is all creature for the family '"+family+"' :";
			for(String creatureName : creaturesNames) {
				message += "\n - "+creatureName;
			}
			try {
				sendMessage(message, mChannel);
			} catch (MessageOver2000Exception e) {
				sendMessage("It looks like a creature have a name which is longer than 2000 caracters... What a troller...", mChannel);
			}
		}
	
	// TODO : Page system to limit overload
	@Command(name="listAllCreature", description="List all the creature in the bestiary")
	public void listAllCreature(MessageChannel mChannel) throws MessageOver2000Exception {
		Collection<Creature> creatures = Bestiary.bestiary.getAllCreatures();
		String message = "This is all creatures :";
		for(Creature creature : creatures) {
			message += "\n - "+creature.getName();
		}
		try {
			sendMessage(message, mChannel);
		} catch (MessageOver2000Exception e) {
			sendMessage("It looks like an creature have a name which is longer than 2000 caracters... What a troller...", mChannel);
		}
	}
	
	// TODO : Page system to limit overload
	@Command(name="infoCreature", description="Given a creeature name, get the detail of a creature", type=ExecutorType.BOT)
	public void infoCreature(MessageChannel mChannel, String creatureName) {
		Creature creature = Bestiary.bestiary.getCreature(creatureName);
		EmbedBuilder embedBuilder = new EmbedBuilder().setAuthor(creature.getName().toUpperCase())
				.addField("Description physique :", creature.getDescriptionPhysique(), false);
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
	
	@Command(name="infoFamily", description="Given a family name, get the detail of a family", type=ExecutorType.BOT)
	public void infoFamily(MessageChannel mChannel, String familyName) {
		Family family = FamilyBook.familyBook.getFamily(familyName);
		EmbedBuilder embedBuilder = new EmbedBuilder().setAuthor(family.getFamilyName().toUpperCase());
		sendEmbed(embedBuilder, mChannel);
	}
	
	@Command(name="help", type=ExecutorType.BOT, description="List all the command helper")
	public void helper(MessageChannel mChannel) throws MessageOver2000Exception {
		String helperMessage = generateHelperMessage();
		try {
			sendMessage(helperMessage, mChannel);
		} catch (MessageOver2000Exception e) {
			sendMessage("It looks like a command have a name and description which are longer than 2000 caracters... What a troller...", mChannel);
		}
	}

}
