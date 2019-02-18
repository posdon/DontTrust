package bot;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Bestiary;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import storage.StorageManager;

public class MainBot implements Runnable {
	
	private boolean running = true;
	private static String tag;
	private final static Logger LOG = LoggerFactory.getLogger(MainBot.class);
	private JDA JDA;
	private BotListener botListener = new BotListener(this);
	
	private final static Properties BOT_PROP = new Properties();
	private final static Properties DATABASE_PROP = new Properties();
	private final static String BOT_PROPERTIES_FILE_PATH = "src/bot/bot.properties";
	private final static String DATABASE_PROPERTIES_FILE_PATH = "src/bot/database.properties";
	
	private Bestiary bestiary = Bestiary.bestiary;
	private StorageManager storageManager;

	private Scanner scanner;
	
	public JDA getJda() {
		return JDA;
	}
	
	public String getTag() {
		return tag;
	}
	
	@Override
	public void run() {
		LOG.info("Starting the bot.");
		try {
			JDA = new JDABuilder(AccountType.BOT).addEventListener(botListener).setToken(BOT_PROP.getProperty("token")).build();
		} catch (LoginException e) {
			LOG.error(e.getMessage());
			this.running = false;
		}
		storageManager = new StorageManager(DATABASE_PROP.getProperty("storage_path"));
		storageManager.readAllFiles();
		scanner = new Scanner(System.in);
		while(this.running) {
			if(scanner.hasNextLine()) {
				botListener.onConsole(scanner.nextLine());
			}
		}
		scanner.close();
		JDA.shutdown();
		LOG.info("Shut down.");
	}
	
	public void stop() {
		this.running = false;
	}
	
	public static void main(String[] args) {
		boolean loading = loadBotProperties();
		if(loading) loading = loadDatabaseProperties();
		if(loading) {
			tag = BOT_PROP.getProperty("tag");
			(new Thread(new MainBot())).start();
		}
		
	}
	
	private static boolean loadBotProperties() {
		try {	
			FileInputStream input = new FileInputStream(BOT_PROPERTIES_FILE_PATH);
			BOT_PROP.load(input);
			return true;
		} catch (IOException e) {
			LOG.error("Impossible to load bot properties. Please check if the file path is correct.");
			return false;
		}
	}

	private static boolean loadDatabaseProperties() {
		try {	
			FileInputStream input = new FileInputStream(DATABASE_PROPERTIES_FILE_PATH);
			DATABASE_PROP.load(input);
			return true;
		} catch (IOException e) {
			LOG.error("Impossible to load database properties. Please check if the file path is correct.");
			return false;
		}
	}
}
