package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class RoomViewController extends BaseController implements Initializable
{

	@FXML
    private ListView<String> channelList;

    @FXML
    private ListView<String> chatList;

    @FXML
    private MenuButton menuButton;

    @FXML
    private ListView<String> userList;
    
	public RoomViewController(String fxmlName, ViewTransitionalModel vtm, ConcordClientModel client)
	{
		super(fxmlName, vtm, client);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		menuButton.setText(client.getSelectedRoom().get());
		channelList.setItems(client.getChannels());
		chatList.setItems(client.getChats());
		userList.setItems(client.getUsers());
		
		userList.getSelectionModel().selectedItemProperty().addListener((observer, oldValue, newValue) -> {
			vtm.showUserProfilePopup(newValue);
		});
		
		Bindings.bindBidirectional(client.getSelectedRoom(), menuButton.textProperty());
	}

}
