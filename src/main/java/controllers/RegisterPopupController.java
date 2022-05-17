package controllers;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import Database.User;
import ServerClientModel.Client;
import ServerClientModel.ServerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class RegisterPopupController extends BaseController
{

	@FXML
    private Label errorMsg;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField profileDataTextField;

    @FXML
    private Button signupButton;

    @FXML
    private TextField usernameTextField;
    
    @FXML
    private CheckBox statusCheckbox;
    
	public RegisterPopupController(String fxmlName, ViewTransitionalModel vtm, ConcordClientModel client)
	{
		super(fxmlName, vtm, client);
		// TODO Auto-generated constructor stub
	}

	@FXML
    void onSignUpButtonClicked(ActionEvent event) {
		if ((usernameTextField.getText().strip().equals("")) || 
			(passwordTextField.getText().strip().equals(""))) {
			errorMsg.setText("Error; please enter data into all text fields");
			
			return;
		}
		errorMsg.setText("");	
		String username = usernameTextField.getText().strip();
		String password = passwordTextField.getText().strip();
		String profileData = profileDataTextField.getText().strip();
		Boolean status = statusCheckbox.isSelected();
		
		ServerInterface server;
		try
		{
			server = (ServerInterface) Naming.lookup("rmi://127.0.0.1/CONCORD");
			User user = server.addUser(username, password, profileData, status);
			Client general_client = new Client(server, user);
			server.addClient(general_client);
			vtm.closeStageFromNode(errorMsg);
		} catch (MalformedURLException | RemoteException | NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public void clearSelections()
	{
		// TODO Auto-generated method stub
		
	}
}
