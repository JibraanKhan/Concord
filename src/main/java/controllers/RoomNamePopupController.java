package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class RoomNamePopupController extends BaseController
{
	@FXML
    private TextField nameField;

    @FXML
    void onCancelButtonClicked(ActionEvent event) {
    	vtm.closeStageFromNode(nameField);
    	vtm.showMainFrame();
    }

    @FXML
    void onCreateButtonClicked(ActionEvent event) {
    	if ((nameField.getText() != null) && (nameField.getText().strip() != "")) {
    		client.addRoom(nameField.getText());
    		//vtm.showRoomView();
    	}
    	
    	vtm.closeStageFromNode(nameField);
    	vtm.showMainFrame();
    }

	public RoomNamePopupController(String fxmlName, ViewTransitionalModel vtm, ConcordClientModel client)
	{
		super(fxmlName, vtm, client);
		// TODO Auto-generated constructor stub
	}

}
