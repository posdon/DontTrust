package gui.controller;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Bestiary;
import model.Creature;
import model.Family;

public class GuiMainController {
	
	@FXML
	private ListView<String> creatureList;
	
	@FXML
	private ListView<Family> familyList;
	
	@FXML
	private AnchorPane familyDetailPane;
	
	@FXML
	private AnchorPane creatureDetailPane;
	
	@FXML
	private Label creatureNameLabel;
	
	private ListProperty<String> creatureListProperty = new SimpleListProperty<>();
	
	@FXML
	public void initialize() {
		creatureList.itemsProperty().bind(creatureListProperty);
		creatureList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		creatureList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				refreshCreatureDetail(Bestiary.bestiary.getCreature(creatureList.getSelectionModel().getSelectedItem()));
			}
		});
		refreshCreatureList();
		refreshCreatureDetail(null);
	}
	
	private void refreshCreatureList() {
		creatureListProperty.set(FXCollections.observableArrayList(Bestiary.bestiary.getAllCreaturesName()));
	}
	
	private void refreshCreatureDetail(Creature creature) {
		creatureNameLabel.setText((creature == null)?"":creature.getName());
	}
}
