package controllers;

import java.net.URL;
import java.util.Collection;
import java.util.Hashtable;
import java.util.ResourceBundle;

import Database.Chat;
import Database.ChatLog;
import Database.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class ChatListController extends BaseController implements Initializable
{
	@FXML
    private ListView<Chat> chatListView;
	
	@FXML
    private TextField chatTextField;
	
	public ChatListController(String fxmlName, ViewTransitionalModel vtm, ConcordClientModel client)
	{
		super(fxmlName, vtm, client);
		// TODO Auto-generated constructor stub
	}

	@FXML
    void onSendChatButtonClicked(ActionEvent event) {
		if (chatTextField.getText().strip().equals("")) {
			return;
		}
		
		client.sendDM(chatTextField.getText());
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		// TODO Auto-generated method stub
		chatListView.setItems(client.getDMsChats());
	}
}
