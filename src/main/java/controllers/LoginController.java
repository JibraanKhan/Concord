package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class LoginController extends BaseController
{
	
	 @FXML
	 private TextField passwordField;

	 @FXML
	 private TextField userNameField;

	 public LoginController(String fxmlName, ViewTransitionalModel vtm, ConcordClientModel client) {
			super(fxmlName, vtm, client);
	 }
	 
	 @FXML
	 void loginButtonClicked(ActionEvent event) {
		 vtm.closeStageFromNode(passwordField);
		 vtm.showMainFrame();
	 }
}
