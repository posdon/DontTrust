package gui.controller;

import org.apache.commons.lang3.StringUtils;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Bestiary;
import model.CaracteristicBuilder;
import model.CreatureBuilder;
import model.Family;
import model.FamilyBook;
import model.gameStates.FactionState;

public class NewCreatureController {

	@FXML
	private TextField nameTextField;
	
	@FXML
	private ChoiceBox<Family> familyChoiceBox;
	
	@FXML
	private ChoiceBox<FactionState> factionChoiceBox;
	
	@FXML
	private TextField lifeTextField;
	
	@FXML
	private TextField strengthTextField;
	
	@FXML
	private TextField madnessTextField;

	private final String VERIFICATION_NAME_TEXT_OK = "The name is valid !";
	private final String VERIFICATION_NAME_TEXT_KO = "The name is already used.";
	@FXML
	private Label verificationNameLabel;
	
	private Stage dialogStage;
	
	private boolean okClicked;
	
	private CreatureBuilder builder;
	
	@FXML
	public void initialize() {
		verificationNameLabel.setText(VERIFICATION_NAME_TEXT_KO);
		factionChoiceBox.setItems(FXCollections.observableArrayList(FactionState.values()));
		factionChoiceBox.getSelectionModel().select(FactionState.NEUTRAL);
		familyChoiceBox.setItems(FXCollections.observableArrayList(FamilyBook.familyBook.getFamilies()));
	}
	
	public boolean verifNameValid() {
		String name = nameTextField.getText();
		if(Bestiary.bestiary.isValidName(name)) {
			try {
				builder.setName(name);
				verificationNameLabel.setText(VERIFICATION_NAME_TEXT_OK);
				builder.setCaracteristic((new CaracteristicBuilder())
						.setLife(StringUtils.isBlank(lifeTextField.getText())?0:Integer.parseInt(lifeTextField.getText()))
						.setStrength(StringUtils.isBlank(strengthTextField.getText())?0:Integer.parseInt(strengthTextField.getText()))
						.setMadness(StringUtils.isBlank(madnessTextField.getText())?0:Integer.parseInt(madnessTextField.getText()))
						.build());
				builder.setFamily(familyChoiceBox.getValue());
				builder.setFactionState(factionChoiceBox.getValue());
				return true;
			} catch (Exception e) {}
		}
		verificationNameLabel.setText(VERIFICATION_NAME_TEXT_KO);
		return false;
	}
	
	@FXML
	public void handlerNameChanged() {
		verifNameValid();
	}
	
	@FXML
	public void handlerOkButton() {
		if(verifNameValid()) {
			okClicked = true;
			dialogStage.close();
		}else {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("New Creature Error");
	        alert.setHeaderText("Not a valid name");
	        alert.setContentText("The name must be not blank and not used.");
	        alert.showAndWait();
		}
	}
	
	@FXML 
	public void handlerCancelButton() {
		okClicked = false;
		dialogStage.close();
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setCreatureBuilder(CreatureBuilder builder) {
    	this.builder = builder;
    }
}
