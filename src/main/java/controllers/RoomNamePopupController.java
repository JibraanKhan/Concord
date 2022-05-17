package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class RoomNamePopupController extends BaseController
{
	@FXML
    private TextField descriptionTextField;

    @FXML
    private TextField logoTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private CheckBox privateCheckbox;

    @FXML
    void onCancelButtonClicked(ActionEvent event) {
    	vtm.closeStageFromNode(nameTextField);
    	vtm.showMainFrame();
    }

    @FXML
    void onCreateButtonClicked(ActionEvent event) {
    	if ((nameTextField.getText() != null) && (nameTextField.getText().strip() != "")) {
    		if (nameTextField.getText().equals("")) {
    			return;
    		}
    		String name = nameTextField.getText();
    		String description = descriptionTextField.getText();
    		String logo = logoTextField.getText();
    		Boolean priv = !privateCheckbox.isSelected(); //If it is checked then it is private. But, true means public in our database
    		client.createRoom(name, description, logo, priv);
    		//client.addRoom(nameTextField.getText(), descriptionTextField.getText(), logoTextField.getText(), !privateCheckbox.isSelected());	
    		vtm.showRoomView();
    	}
    	
    	vtm.closeStageFromNode(nameTextField);
    	vtm.showMainFrame();
    }

	public RoomNamePopupController(String fxmlName, ViewTransitionalModel vtm, ConcordClientModel client)
	{
		super(fxmlName, vtm, client);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void clearSelections()
	{
		// TODO Auto-generated method stub
		
	}

}
