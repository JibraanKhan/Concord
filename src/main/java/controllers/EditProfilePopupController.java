package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class EditProfilePopupController extends BaseController
{

	public EditProfilePopupController(String fxmlName, ViewTransitionalModel vtm, ConcordClientModel client)
	{
		super(fxmlName, vtm, client);
		// TODO Auto-generated constructor stub
	}

	@FXML
    private TextField passwordTextField;

    @FXML
    private TextField profileDataTextField;

    @FXML
    private CheckBox statusCheckbox;

    @FXML
    private TextField usernameTextField;
    
	@FXML
    void onSaveButtonClicked(ActionEvent event) {
    	String password = passwordTextField.getText();
    	String username = usernameTextField.getText();
    	String profileData = profileDataTextField.getText();
    	Boolean status = statusCheckbox.isSelected();
    	if (password == "" || username == "") {
    		return;
    	}
    	
    	client.updateInfo(username, password, profileData, status);
    	vtm.closeStageFromNode(passwordTextField);
    }
}
