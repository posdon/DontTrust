package gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.FamilyBook;
import model.FamilyBuilder;

public class NewFamilyController {

	@FXML
	private TextField nameTextField;
	
	private final String VERIFICATION_NAME_TEXT_OK = "The name is valid !";
	private final String VERIFICATION_NAME_TEXT_KO = "The name is already used.";
	@FXML
	private Label verificationNameLabel;
	
	private Stage dialogStage;
	
	private boolean okClicked;
	
	private FamilyBuilder builder;
	
	@FXML
	public void initialize() {
		verificationNameLabel.setText(VERIFICATION_NAME_TEXT_KO);
	}
	
	public boolean verifNameValid() {
		String name = nameTextField.getText();
		if(FamilyBook.familyBook.isValidName(name)) {
			try {
				builder.setFamilyName(name);
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
	        alert.setTitle("New Family Error");
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
    
    public void setFamilyBuilder(FamilyBuilder builder) {
    	this.builder = builder;
    }
}

