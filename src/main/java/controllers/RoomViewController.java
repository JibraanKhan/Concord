package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Database.Chat;
import Database.ChatLog;
import Database.User;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class RoomViewController extends BaseController implements Initializable
{

	@FXML
    private ListView<ChatLog> chatLogList;

    @FXML
    private ListView<Chat> chatList;

    @FXML
    private MenuButton menuButton;

    @FXML
    private ListView<User> userList;
    
    @FXML
    private TextField chatTextField;
    
    ChatLog selectedChatLog = null;
    
	public RoomViewController(String fxmlName, ViewTransitionalModel vtm, ConcordClientModel client)
	{
		super(fxmlName, vtm, client);
		// TODO Auto-generated constructor stub
	}

    @FXML
    void createChannelButtonClicked(ActionEvent event) {
    	System.out.println("Create channel button clicked");
    	vtm.showCreateChannelPopup();
    }

    @FXML
    void inviteUserButtonClicked(ActionEvent event) {

    }

    @FXML
    void removeUserButtonClicked(ActionEvent event) {

    }
	
    @FXML
    void deleteChatLogButtonClicked(ActionEvent event) {
    	if (selectedChatLog == null) {
    		return;
    	}
    	client.deleteChatLog(client.getSelectedRoomID(), selectedChatLog.getChatLogID());
    }
    
    @FXML
    void deleteRoomButtonClicked(ActionEvent event) {
    	client.deleteRoom(client.getSelectedRoomID());
    	client.loadRoomsChatLogs();
    }
    
    @FXML
    void onSendChatButtonClicked(ActionEvent event) {
    	String chatText = chatTextField.getText();
    	
    	if (chatText == "" || client.getSelectedRoomID() == -1 || selectedChatLog == null) {
    		return;
    	}
    	
    	client.createChat(client.getSelectedRoomID(), selectedChatLog.getChatLogID(), chatText);
    	
    	chatTextField.setText("");
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		chatTextField.setPromptText("Keep calm and Concord on!");
		menuButton.setText(client.getSelectedRoom().get());
		chatLogList.setItems(client.getRoomsChatLogs());
		chatList.setItems(client.getChatLogsChats());
		userList.setItems(client.getRoomsUsers());
		/*channelList.setItems(client.getChannels());
		chatList.setItems(client.getChats());
		userList.setItems(client.getUsers());
		*/
		userList.getSelectionModel().selectedItemProperty().addListener((observer, oldValue, newValue) -> {
			vtm.showUserProfilePopup(newValue);
		});
		
		chatLogList.getSelectionModel().selectedItemProperty().addListener((observer, oldValue, newValue) -> {
			selectedChatLog = newValue;
			if (selectedChatLog == null) {
				return;
			}
			client.setSelectedChatLogID(selectedChatLog.getChatLogID());
			
			client.loadChatLogsChats();
		});
		
		Bindings.bindBidirectional(client.getSelectedRoom(), menuButton.textProperty());
	}
}
