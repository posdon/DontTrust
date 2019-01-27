package bot.command;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import bot.BotListener;
import bot.MainBot;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandManager {

	private static CommandManager INSTANCE = null;
	private MainBot mainRef;
	private HashMap<String,CommandBean> commands;
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	private CommandManager(MainBot mainRef) {
		this.mainRef = mainRef;
	}
	
	public static CommandManager getInstance(MainBot mainRef) {
		if(INSTANCE == null) INSTANCE = new CommandManager(mainRef);
		return INSTANCE;
	}
   
    public void registerCommands(BotListener...listeners){
        for(BotListener listener : listeners) registerCommand(listener);
    }
   
    public void registerCommand(BotListener listener){
        for(Method method : listener.getClass().getDeclaredMethods()){
            if(method.isAnnotationPresent(Command.class)){
                Command command = method.getAnnotation(Command.class);
                method.setAccessible(true);
                CommandBean commandBean = new CommandBean(command.name(), command.description(), command.type(), listener, method);
                commands.put(command.name(), commandBean);
            }
        }
    }
   
    @SuppressWarnings("unchecked")
	public void commandConsole(String command){
        Object[] object = getCommand(command);
        if(object[0] == null) {
        	LOG.warn("Unknown command.");
        	return;
        }
        
        ExecutorType executorType = ((CommandBean)object[0]).getExecutorType();
        if(executorType != ExecutorType.CONSOLE && executorType != ExecutorType.ALL){
            LOG.warn("Bad executor command.");
            return;
        }
        
        try{
            execute(((CommandBean)object[0]), (ArrayList<String>)object[1], null);
        }catch(Exception exception){
            LOG.error("The method "+((CommandBean)object[0]).getMethod().getName()+" isn't correctly initialized.");
        }
    }
   
    @SuppressWarnings("unchecked")
	public void commandUser(User user, String command, Message message){
        Object[] object = getCommand(command);
        if(object[0] == null) {
        	LOG.warn("Unknown command.");
        	return;
        }
        ExecutorType executorType = ((CommandBean)object[0]).getExecutorType();
        if( executorType != ExecutorType.ALL && executorType != ExecutorType.BOT) {
            LOG.warn("Bad executor command.");
        	return ;
        }
        try{
            execute(((CommandBean)object[0]), (ArrayList<String>)object[1], message);
        }catch(Exception exception){
            LOG.error("The method "+((CommandBean)object[0]).getMethod().getName()+" isn't correctly initialized.");
        }
    }
   
    private Object[] getCommand(String command){
        String[] commandSplit = command.split(" ");
        List<String> args = new ArrayList<>();
        for(int i = 1; i < commandSplit.length; i++) {
        	String argument  = commandSplit[i];
        	if(StringUtils.isNotBlank(argument)) {
        		args.add(argument);
        	}
        }
        CommandBean commandBean = commands.get(commandSplit[0]);
        return new Object[]{commandBean, args};
    }
	
	public Collection<CommandBean> getCommands(){
        return commands.values();
    }
   
    private void execute(CommandBean commandBean, List<String> args, Message message) throws Exception{
        Parameter[] parameters = commandBean.getMethod().getParameters();
        Object[] objects = new Object[parameters.length];
        int argsCount = 0;
        for(int i = 0; i < parameters.length; i++){
            if(parameters[i].getType() == (new ArrayList<String>()).getClass()) objects[i] = args;
            else if(parameters[i].getType() == User.class) objects[i] = message == null ? null : message.getAuthor();
            else if(parameters[i].getType() == TextChannel.class) objects[i] = message == null ? null : message.getTextChannel();
            else if(parameters[i].getType() == PrivateChannel.class) objects[i] = message == null ? null : message.getPrivateChannel();
            else if(parameters[i].getType() == Guild.class) objects[i] = message == null ? null : message.getGuild();
            else if(parameters[i].getType() == Message.class) objects[i] = message;
            else if(parameters[i].getType() == JDA.class) objects[i] = mainRef.getJda();
            else if(parameters[i].getType() == MessageChannel.class) objects[i] = message.getChannel();
            else if(parameters[i].getType() == String.class) {
            	objects[i] = (argsCount < args.size())?args.get(argsCount):"";
            	argsCount++;
            }
        }
        commandBean.getMethod().invoke(commandBean.getObject(), objects);
    }
}
