package gui;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.MainBot;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Bestiary;
import storage.StorageManager;

public class MainGui extends Application {

	private final static Properties DATABASE_PROP = new Properties();
	private final static String DATABASE_PROPERTIES_FILE_PATH = "src/gui/database.properties";
	private final static Logger LOG = LoggerFactory.getLogger(MainBot.class);
	
	private StorageManager storageManager;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		storageManager = new StorageManager(Bestiary.bestiary, DATABASE_PROP.getProperty("storage_path"));
		storageManager.readAllFiles();
		Parent root = FXMLLoader.load(getClass().getResource("view/mainView.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Bestiary Manager");
		primaryStage.setScene(scene);
        primaryStage.show();
	}

	public static void main(String[] args) {
		loadDatabaseProperties();
		launch(args);
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
