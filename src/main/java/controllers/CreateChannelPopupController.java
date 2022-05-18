package controllers;

import java.rmi.RemoteException;

import ServerClientModel.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.ConcordClientModel;
import model.ViewTransitionalModel;

public class CreateChannelPopupController extends BaseController
{

	public CreateChannelPopupController(String fxmlName, ViewTransitionalModel vtm, ConcordClientModel client)
	{
		super(fxmlName, vtm, client);
		// TODO Auto-generated constructor stub
	}

	@FXML
    private TextField nameTextField;

    @FXML
    void onCancelButtonClicked(ActionEvent event) {
    	vtm.closeStageFromNode(nameTextField);
    }

    @FXML
    void onCreateButtonClicked(ActionEvent event) {
    	if (nameTextField.getText().equals("")) {
    		return;
    	}
    	vtm.closeStageFromNode(nameTextField);
    	int SelectedRoomID = client.getSelectedRoomID();
    	String chatLogName = nameTextField.getText();
    	
    	if (SelectedRoomID == -1 || chatLogName == "") {
    		return;
    	}
    	
    	//System.out.println("Adding channel " + chatLogName);
		client.createChatLog(SelectedRoomID, chatLogName);
    }

	@Override
	public void clearSelections()
	{
		// TODO Auto-generated method stub
		
	}
}
