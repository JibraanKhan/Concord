package controllers;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import ServerClientModel.Server;
import ServerClientModel.ServerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class LoginController extends BaseController
{
	@FXML
    private Label errorMsg;
	 
	@FXML
	private TextField passwordField;

	@FXML
	private TextField userNameField;

    @FXML
    private Button signupButton;
	
	public LoginController(String fxmlName, ViewTransitionalModel vtm, ConcordClientModel client) {
		super(fxmlName, vtm, client);
	}
	 
	@FXML
	void loginButtonClicked(ActionEvent event) throws RemoteException {
		String username = userNameField.getText();
		String password = passwordField.getText();
		if (((username == "") || (username == null)) || ((password == "") || (password == null))) {
			errorMsg.setText("Error; please enter a password and a username to login.");
			return;
		}
		errorMsg.setText("");
		client.setPassword(password);
		client.setUsername(username);
		if (client.logOn()) {
			vtm.closeStageFromNode(passwordField);
			vtm.showMainFrame();
			System.out.println("Client with password and username exists.");
		} else{
			errorMsg.setText("Error; either the password or the username is wrong.");
			System.out.println("Client with password and username does not exist.");
		};
		
	 }
	
	@FXML
    void onRefreshDataButtonClicked(ActionEvent event) {
		try
		{
			ServerInterface server = (ServerInterface) Naming.lookup("rmi://127.0.0.1/CONCORD");
			server.eraseData();
			server.storeDataDisk();
			server.readDataFromDisk();
		} catch (RemoteException | MalformedURLException | NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@FXML
    void signupButtonClicked(ActionEvent event) {
		vtm.showRegisterUserPopup();
    }

	@Override
	public void clearSelections()
	{
		// TODO Auto-generated method stub
		
	}
}
