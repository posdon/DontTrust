package gui.controller;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import model.Bestiary;
import model.Family;

public class GuiMainController {

	@FXML
	private Button saveButton;
	
	@FXML
	private Button newButton;
	
	@FXML
	private Button suppressButton;
	
	@FXML
	private ListView<String> creatureList;
	
	@FXML
	private ListView<Family> familyList;
	
	@FXML
	private AnchorPane familyDetailPane;
	
	@FXML
	private AnchorPane creatureDetailPane;
	
	private ListProperty<String> creatureListProperty = new SimpleListProperty<>();
	
	@FXML
	public void initialize() {
		creatureList.itemsProperty().bind(creatureListProperty);
		creatureListProperty.set(FXCollections.observableArrayList(Bestiary.bestiary.getAllCreaturesName()));
	}
}
