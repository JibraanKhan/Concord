package controllers;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import Database.Noob;
import Database.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class ExploreController extends BaseController implements Initializable
{

	@FXML
    private ListView<Room> exploreRoomsList;
    Room selectedRoom = null;
	public ExploreController(String fxmlName, ViewTransitionalModel vtm, ConcordClientModel client)
	{
		super(fxmlName, vtm, client);
		// TODO Auto-generated constructor stub
	}

	@FXML
    void onAddButtonClicked(ActionEvent event) {
		//System.out.println(selectedRoom);
		if (selectedRoom != null) {
			try
			{
				client.loadMyRooms();
				if (client.getMyRooms().contains(selectedRoom)) {
					return;
				}
				client.getClient().addUserToRoom(selectedRoom.getRoomID(), new Noob());
				//client.load_allRooms();
				//client.load_usersRooms();
				client.loadMyRooms();
				//System.out.println("Loaded everything");
			} catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		vtm.closeStageFromNode(exploreRoomsList);
		vtm.showMainFrame();
    }

    @FXML
    void onCancelButtonClicked(ActionEvent event) {
    	vtm.closeStageFromNode(exploreRoomsList);
		vtm.showMainFrame();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		// TODO Auto-generated method stub
		//client.load_allRooms();
		//roomList.setItems(client.getAllRooms());
		client.loadAllRooms();
		exploreRoomsList.setItems(client.getAllRooms());
		exploreRoomsList.getSelectionModel().selectedItemProperty().addListener((observer, old_value, new_value) -> {
			if (new_value == null) {
				return;
			}
			selectedRoom = new_value;
		});
	}

	@Override
	public void clearSelections()
	{
		exploreRoomsList.getSelectionModel().clearSelection();
	}
}
