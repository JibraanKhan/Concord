package controllers;

import java.net.URL;
import java.util.ResourceBundle;

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
    private ListView<String> roomList;

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

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		roomList.setItems(client.getRooms());
		roomList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			vtm.showRoomView();
			client.setSelectedRoom(newValue);
		});
	}


}
