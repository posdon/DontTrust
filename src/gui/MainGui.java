package gui;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bot.MainBot;
import gui.controller.GuiMainController;
import gui.controller.NewCreatureController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Bestiary;
import model.CreatureBuilder;
import storage.StorageManager;

public class MainGui extends Application {

	private final static Properties DATABASE_PROP = new Properties();
	private final static String DATABASE_PROPERTIES_FILE_PATH = "src/gui/database.properties";
	private final static Logger LOG = LoggerFactory.getLogger(MainBot.class);
	
	private Stage primaryStage;
	private StorageManager storageManager;
	
	@Override
	public void start(Stage primaryStg) throws Exception {
		primaryStage = primaryStg;
		storageManager = new StorageManager(DATABASE_PROP.getProperty("storage_path"));
		storageManager.readAllFiles();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("view/mainView.fxml"));
		Parent root = loader.load();
		
		GuiMainController guiMainController = loader.getController();
		guiMainController.setMainRef(this);
		
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
	
	public boolean showNewCreatureDialog(CreatureBuilder builder) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("view/newCreatureView.fxml"));
		AnchorPane newCreaturePage = (AnchorPane) loader.load();
		
		Stage newCreatureDialogStage = new Stage();
		newCreatureDialogStage.setTitle("New Creature");
		newCreatureDialogStage.initModality(Modality.WINDOW_MODAL);
		newCreatureDialogStage.initOwner(primaryStage);
		
		Scene scene = new Scene(newCreaturePage);
		newCreatureDialogStage.setScene(scene);
		
		NewCreatureController controller = loader.getController();
		controller.setDialogStage(newCreatureDialogStage);
		controller.setCreatureBuilder(builder);
		
		newCreatureDialogStage.showAndWait();
		
		return controller.isOkClicked();
	}
	
	public void saveAll() {
		storageManager.saveAllFiles();
	}
}
