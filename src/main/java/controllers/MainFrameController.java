package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Database.Room;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class MainFrameController extends BaseController implements Initializable
{

	public MainFrameController(String fxmlName, ViewTransitionalModel vtm, ConcordClientModel client)
	{
		super(fxmlName, vtm, client);
		// TODO Auto-generated constructor stub
	}
	
	@FXML
    private Label profileUsernameLabel;
	
	@FXML
    private ListView<Room> roomList;

    @FXML
    private Label tempRoomName;

    @FXML
    void onAddButtonClicked(ActionEvent event) {
    	vtm.showRoomNamePopup();
    }

    @FXML
    void onDMButtonClicked(ActionEvent event) {
    	vtm.showDMs();
    }

    @FXML
    void onExploreButtonClicked(ActionEvent event) {
    	vtm.showExplore();
    }
    
    @FXML
    void onEditProfileButtonClicked(ActionEvent event) {
    	vtm.showEditProfilePopup();
    }

    @FXML
    void onLogOutButtonClicked(ActionEvent event) {
    	client.logOut();
    	vtm.showLogin();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		/*
		System.out.println("Rooms:" + client.getRooms());
		roomList.setItems(client.getRooms());*/
		client.loadMyRooms();
		roomList.setItems(client.getMyRooms());
		/*client.getMyRooms().addListener((ListChangeListener.Change<? extends Room> room) -> {
			System.out.println("Room got added: " + room);
			//client.loadMyRooms();
			ObservableList<Room> myRooms = client.getMyRooms();
			
			for (Room roomOther:myRooms) {
				System.out.println("Inside initialize: " + roomOther);
			}
			roomList.setItems(client.getMyRooms());
		});*/
		profileUsernameLabel.setText(client.getUsername());
		Bindings.bindBidirectional(client.getUsernameTextProperty(), profileUsernameLabel.textProperty());
		//Bindings.bindBidirectional(client.getAllRooms(), roomList.);
		
		roomList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			if (newValue == null) {
				return;
			}
			vtm.showRoomView();
			client.setSelectedRoom(newValue.getName());
			//System.out.println("Setting selected Room and selectedRoomID: " + newValue.getRoomID());
			client.setSelectedRoomID(newValue.getRoomID()); 
			client.loadRoomsChatLogs();
			client.loadRoomsUsers();
			//client.load_allChannels(); //We need to load all the channels for the room that is clicked.
			//client.load_usersInRoom();
		});
	}


}
