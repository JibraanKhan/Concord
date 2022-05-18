package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Database.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class InviteUserPopupController extends BaseController implements Initializable
{
	@FXML
    private ListView<User> usersListView;
	User selectedUser = null;
	
	public InviteUserPopupController(String fxmlName, ViewTransitionalModel vtm, ConcordClientModel client)
	{
		super(fxmlName, vtm, client);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void clearSelections()
	{
		usersListView.getSelectionModel().clearSelection();
	}
	
	@FXML
    void onCancelButtonPressed(ActionEvent event) {
		clearSelections();
		vtm.closeStageFromNode(usersListView);
    }

    @FXML
    void onInviteButtonPressed(ActionEvent event) {
    	if (selectedUser != null) {
    		client.inviteUser(selectedUser);
    		vtm.closeStageFromNode(usersListView);
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		client.loadAllUsers();
		usersListView.setItems(client.getAllUsers());
		usersListView.getSelectionModel().selectedItemProperty().addListener((observer, old_val, new_val) -> {
			selectedUser = new_val;
		});
	}

}
