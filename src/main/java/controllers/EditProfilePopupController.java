package controllers;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import Database.User;
import ServerClientModel.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class EditProfilePopupController extends BaseController implements Initializable
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

	@Override
	public void clearSelections()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		String pass = client.getPassword();
		String username = client.getUsername();
		Client clientObj = client.getClient();
		String profileData = null;
		Boolean status = null;
		User actualUser = client.getUserFromServer();

		profileData = actualUser.getProfileData();
		status = actualUser.isStatus();
		
		if (pass != null) {
			passwordTextField.setText(pass);
		}
		
		if (username != null) {
			usernameTextField.setText(username);
		}
		
		if (profileData != null) {
			profileDataTextField.setText(profileData);
		}
		
		if (status != null && status) {
			statusCheckbox.setSelected(status);
		}
	}
}
