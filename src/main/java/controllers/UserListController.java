package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Database.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class UserListController extends BaseController implements Initializable
{

	@FXML
    private ListView<User> usersList;
	
	public UserListController(String fxmlName, ViewTransitionalModel vtm, ConcordClientModel client)
	{
		super(fxmlName, vtm, client);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		client.loadAllUsers();
		usersList.setItems(client.getAllUsers());
		usersList.getSelectionModel().selectedItemProperty().addListener((observer, oldvalue, newvalue) -> {
			if (newvalue == null) {
				return;
			}
			
			client.startDM(newvalue.getUserID());
		});
	}

	@Override
	public void clearSelections()
	{
		usersList.getSelectionModel().clearSelection();
	}

}
