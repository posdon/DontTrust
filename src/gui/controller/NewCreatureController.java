package gui.controller;

import org.apache.commons.lang3.StringUtils;

import gui.MainGui;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Bestiary;
import model.CreatureBuilder;

public class NewCreatureController {

	@FXML
	private TextField nameTextField;

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
	}
	
	public boolean verifNameValid() {
		String name = nameTextField.getText();
		if(Bestiary.bestiary.isValidName(name)) {
			try {
				builder.setName(name);
				verificationNameLabel.setText(VERIFICATION_NAME_TEXT_OK);
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
