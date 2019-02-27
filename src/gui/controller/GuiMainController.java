package gui.controller;

import java.io.IOException;

import exception.CreatureListException;
import exception.FamilyListException;
import gui.MainGui;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import model.Bestiary;
import model.Creature;
import model.CreatureBuilder;
import model.Family;
import model.FamilyBook;
import model.FamilyBuilder;

public class GuiMainController {
	
	@FXML
	private Label creatureNameLabel;
	
	@FXML
	private Label creatureFamilyLabel;
	
	@FXML
	private Label creatureFactionLabel;
	
	@FXML
	private Label creatureLifeLabel;
	
	@FXML
	private Label creatureStrengthLabel;
	
	@FXML
	private Label creatureMadnessLabel;
	
	@FXML
	private Label descriptionFamilyLabel;
	
	@FXML
	private Label histoireFamilyLabel;

	@FXML
	private ListView<String> creatureList;
	private ListProperty<String> creatureListProperty = new SimpleListProperty<>();
	
	@FXML
	private Label familyNameLabel;
	
	@FXML
	private ListView<String> familyList;
	private ListProperty<String> familyListProperty = new SimpleListProperty<>();
	
	@FXML
	private TabPane mainTabPane;
	
	private MainGui mainRef;
	
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
		
		familyList.itemsProperty().bind(familyListProperty);
		familyList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		familyList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				refreshFamilyDetail(FamilyBook.familyBook.getFamily(familyList.getSelectionModel().getSelectedItem()));
			}
		});
		refreshCreatureList();
		refreshCreatureDetail(null);
		refreshFamilyList();
		refreshFamilyDetail(null);
	}
	
	public void setMainRef(MainGui mainGui) {
		this.mainRef = mainGui;
	}
	
	@FXML
	public void handlerNewButton() throws IOException, FamilyListException {
		switch(mainTabPane.getSelectionModel().getSelectedIndex()) {
			case 0 : 
				newButtonCreatureAction();
				break;
			case 1 : 
				newButtonFamilyAction();
				break;
		}
	}
		
	private void newButtonCreatureAction() throws IOException {
		CreatureBuilder builder = new CreatureBuilder();
		if(this.mainRef.showNewCreatureDialog(builder)) {
			try {
				Bestiary.bestiary.addCreature(builder.build());
				refreshCreatureList();
			} catch (CreatureListException e) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("New Creature Error");
		        alert.setHeaderText("Creature already exist");
		        alert.setContentText("The creature '"+builder.getName()+"' already exist.");
		        alert.showAndWait(); 
			}
		}
	}
	
	private void newButtonFamilyAction() throws IOException {
		FamilyBuilder builder = new FamilyBuilder();
		if(this.mainRef.showNewFamilyDialog(builder)) {
			try {
				FamilyBook.familyBook.addFamily(builder.build());
				refreshFamilyList();
			} catch (FamilyListException e) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("New Family Error");
		        alert.setHeaderText("Family already exist");
		        alert.setContentText("The family '"+builder.getFamilyName()+"' already exist.");
		        alert.showAndWait(); 
			}
		}
	}
	
	@FXML
	public void handlerDeleteButton() {
		switch(mainTabPane.getSelectionModel().getSelectedIndex()) {
		case 0 : 
			deleteButtonCreatureAction();
			break;
		case 1 : 
			deleteButtonFamilyAction();
			break;
		}
	}
	
	private void deleteButtonCreatureAction() {
		String creatureName = this.creatureList.getSelectionModel().getSelectedItem();
		try {
			Bestiary.bestiary.removeCreature(creatureName);
			refreshCreatureList();
		} catch (CreatureListException e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Delete Creature Error");
	        alert.setHeaderText("Creature doesn't exist");
	        alert.setContentText("The creature '"+creatureName+"' doesn't exist.");
	        alert.showAndWait(); 
		}
	}
	
	private void deleteButtonFamilyAction() {
		String familyName = this.familyList.getSelectionModel().getSelectedItem();
		try {
			FamilyBook.familyBook.removeFamily(familyName);
			refreshFamilyList();
		} catch (FamilyListException e) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Delete Family Error");
	        alert.setHeaderText("Family doesn't exist");
	        alert.setContentText("The family '"+familyName+"' doesn't exist.");
	        alert.showAndWait(); 
		}
	}
	
	@FXML
	public void handlerSaveButton() {
		mainRef.saveAll();
	}
	
	private void refreshCreatureList() {
		creatureListProperty.set(FXCollections.observableArrayList(Bestiary.bestiary.getAllCreaturesName()));
	}
	
	private void refreshCreatureDetail(Creature creature) {
		if(creature == null) {
			creatureNameLabel.setText("");
			creatureFactionLabel.setText("");
			creatureFamilyLabel.setText("");
			creatureLifeLabel.setText("");
			creatureStrengthLabel.setText("");
			creatureMadnessLabel.setText("");
		} else {
			creatureNameLabel.setText((creature.getName() == null)?"":creature.getName());
			creatureFactionLabel.setText((creature.getFaction() == null)?"":creature.getFaction().toString());
			creatureFamilyLabel.setText((creature.getFamily() == null)?"":creature.getFamily().getFamilyName());
			creatureLifeLabel.setText((creature.getCaracteristic() == null)?"":Integer.toString(creature.getCaracteristic().getLife()));
			creatureStrengthLabel.setText((creature.getCaracteristic() == null)?"":Integer.toString(creature.getCaracteristic().getStrength()));
			creatureMadnessLabel.setText((creature.getCaracteristic() == null)?"":Integer.toString(creature.getCaracteristic().getMadness()));
		}
	}
	
	private void refreshFamilyList() {
		familyListProperty.set(FXCollections.observableArrayList(FamilyBook.familyBook.getAllFamiliesName()));
	}
	
	private void refreshFamilyDetail(Family family) {
		if(family == null) {
			familyNameLabel.setText("");
			histoireFamilyLabel.setText("");
			descriptionFamilyLabel.setText("");
		} else {
			familyNameLabel.setText((family.getFamilyName() == null)?"":family.getFamilyName());
			histoireFamilyLabel.setText((family.getHistoire() == null)?"":family.getHistoire());
			descriptionFamilyLabel.setText((family.getDescription()== null)?"":family.getDescription()); 
		}
	}
}
