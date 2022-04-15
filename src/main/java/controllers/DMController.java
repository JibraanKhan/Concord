package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class DMController extends BaseController implements Initializable
{
	@FXML
    private ListView<String> dmLists;

    @FXML
    void onUsersButtonClicked(ActionEvent event) {
    	vtm.showUsersList();
    }

	public DMController(String fxmlName, ViewTransitionalModel vtm, ConcordClientModel client)
	{
		super(fxmlName, vtm, client);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		dmLists.setItems(client.getDMs());
		
		dmLists.getSelectionModel().selectedItemProperty().addListener((observer, oldValue, newValue) -> {
			vtm.showChatList();
		});
	}
	
	
}